package lexisnexis.pages;

import net.serenitybdd.core.pages.ListOfWebElementFacades;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class JobSearchPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(JobSearchPage.class);

    private static final By SEARCH_INPUT_BY_MODEL = By.cssSelector("input[ng-model='_searchQuery']");
    private static final By JOB_RESULTS = By.cssSelector("div.results div.job-card");
    private static final By JOB_COUNT_ELEMENT = By.cssSelector("span.jobs-found");
    private static final By NO_RESULTS_MESSAGE = By.xpath("//span[contains(@class, 'jobs-found') and contains(text(), '0 Jobs')]");
    private static final By PRIMARY_SEARCH_BUTTON = By.cssSelector("button[ng-click='jobSearch()']");
    private static final By RED_SEARCH_BUTTON = By.cssSelector("button.btn.btn-primary");
    private static final By ANY_SEARCH_BUTTON = By.xpath("//button[contains(text(), 'Search')]");

    public void init() {
        getDriver().manage().window().maximize();
        switchToCorrectTab();
        waitForPageToLoad();
    }

    private void switchToCorrectTab() {
        List<String> tabs = new ArrayList<>(getDriver().getWindowHandles());
        if (tabs.size() > 1) {
            getDriver().switchTo().window(tabs.get(tabs.size() - 1));
            logger.info("Switched to tab: {}", getDriver().getCurrentUrl());
        }
    }

    public void searchForJobs(String searchTerm) {
        logger.info("Searching for jobs with term: {}", searchTerm);
        verifyJobSearchPage();

        WebElement searchInput = waitForSearchInput();
        clearAndEnterSearchText(searchInput, searchTerm);
        clickSearchButton(searchInput);
        waitForSearchResults();
    }

    private void verifyJobSearchPage() {
        if (!getDriver().getCurrentUrl().contains("job-search")) {
            logger.warn("Not on job search page, current URL: {}", getDriver().getCurrentUrl());
            throw new RuntimeException("Not on the job search page");
        }
    }

    private WebElement waitForSearchInput() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT_BY_MODEL));
        logger.info("Found search input element");
        return searchInput;
    }

    private void clearAndEnterSearchText(WebElement searchInput, String searchTerm) {
        searchInput.clear();
        waitForElementToBeClickable(searchInput);
        typeInto(searchInput, searchTerm);
        logger.info("Entered search term: {}", searchTerm);
    }

    private void clickSearchButton(WebElement searchInput) {
        if (isElementPresent(PRIMARY_SEARCH_BUTTON)) {
            WebElement button = find(PRIMARY_SEARCH_BUTTON);
            logger.info("Found primary search button");
            jsClick(button);
            return;
        }

        if (isElementPresent(RED_SEARCH_BUTTON)) {
            WebElement button = find(RED_SEARCH_BUTTON);
            logger.info("Found red search button");
            jsClick(button);
            return;
        }

        if (isElementPresent(ANY_SEARCH_BUTTON)) {
            WebElement button = find(ANY_SEARCH_BUTTON);
            logger.info("Found button with 'Search' text");
            jsClick(button);
            return;
        }

        logger.info("No search button found, pressing Enter instead");
        searchInput.sendKeys(Keys.ENTER);
    }

    private void waitForSearchResults() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        wait.until(driver -> isElementPresent(JOB_COUNT_ELEMENT));
        waitABit(2000);
    }

    public int getSearchResultCount() {
        if (isNoResultsMessageDisplayed()) {
            logger.info("No results message is displayed (0 jobs found)");
            return 0;
        }

        int countFromText = getCountFromJobFoundText();
        return countFromText >= 0 ? countFromText : countJobCards();
    }

    private int getCountFromJobFoundText() {
        if (!isElementPresent(JOB_COUNT_ELEMENT)) {
            return -1;
        }

        WebElement jobCountElement = find(JOB_COUNT_ELEMENT);
        String jobCountText = jobCountElement.getText();
        logger.info("Jobs found text: {}", jobCountText);

        if (jobCountText.matches("\\d+\\s+Jobs.*")) {
            String countStr = jobCountText.replaceAll("\\D+", "").trim();
            int count = Integer.parseInt(countStr);
            logger.info("Parsed job count from text: {}", count);
            return count;
        }

        return -1;
    }

    private int countJobCards() {
        ListOfWebElementFacades results = findAll(JOB_RESULTS);
        int count = (int) results.stream().filter(WebElement::isDisplayed).count();
        logger.info("Counted {} job result cards", count);
        return count;
    }

    public boolean isNoResultsMessageDisplayed() {
        if (isElementPresent(NO_RESULTS_MESSAGE)) {
            return true;
        }

        if (isElementPresent(JOB_COUNT_ELEMENT)) {
            WebElement jobCountElement = find(JOB_COUNT_ELEMENT);
            return jobCountElement.getText().startsWith("0 Jobs");
        }

        return false;
    }

}