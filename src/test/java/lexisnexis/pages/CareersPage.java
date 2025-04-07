package lexisnexis.pages;

import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class CareersPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(CareersPage.class);
    private static final By VIEW_ALL_JOBS_BUTTON = By.xpath("//a[contains(text(), 'VIEW ALL JOBS')]");
    protected static final By COOKIE_DIALOG = By.xpath("//div[contains(@role, 'dialog') or contains(@class, 'cookie') or contains(@id, 'cookie')]");
    protected static final By ACCEPT_COOKIES_BUTTON = By.xpath("//button[contains(text(), 'Accept All')]");


    public void clickSearchAllJobs() {
        switchToNewTab();
        waitForPageToLoad();
        handleCookieDialog();
        clickViewAllJobsButton();
    }

    protected void handleCookieDialog() {
        if (!isCookieDialogPresent()) {
            return;
        }
        logger.info("Cookie dialog detected");
        if (isElementVisible(ACCEPT_COOKIES_BUTTON)) {
            clickElement(find(ACCEPT_COOKIES_BUTTON));
            logger.info("Clicked Accept All button");
            waitABit(STANDARD_WAIT);
            return;
        }
        clickAcceptButtonInDialog();
    }

    private void clickViewAllJobsButton() {
        if (isElementVisible(VIEW_ALL_JOBS_BUTTON)) {
            clickElement(find(VIEW_ALL_JOBS_BUTTON));
            logger.info("Clicked View All Jobs button");
            waitABit(STANDARD_WAIT);
            return;
        }
        findAndClickJobsButton();
    }

    private void findAndClickJobsButton() {
        List<WebElementFacade> links = findAll(By.tagName("a"));

        WebElementFacade jobsButton = links.stream()
                .filter(link -> link.getText().toUpperCase().contains("VIEW") &&
                        link.getText().toUpperCase().contains("JOB"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Failed to find View All Jobs button"));
        clickOn(jobsButton);
        logger.info("Clicked on Find and Click View All Jobs button");
    }


    protected boolean isCookieDialogPresent() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(COOKIE_DIALOG_TIMEOUT));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(COOKIE_DIALOG)) != null;
    }

    protected void clickAcceptButtonInDialog() {
        WebElement dialog = find(COOKIE_DIALOG);
        List<WebElement> buttons = dialog.findElements(By.tagName("button"));

        for (WebElement button : buttons) {
            String text = button.getText().toLowerCase();
            if (button.isDisplayed() && (text.contains("accept") || text.contains("allow"))) {
                clickElement(button);
                logger.info("Clicked cookie accept button: {}", text);
                waitABit(STANDARD_WAIT);
                break;
            }
        }
    }
}