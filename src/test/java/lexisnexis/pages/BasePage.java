package lexisnexis.pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BasePage extends PageObject {
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    protected static final int PAGE_LOAD_TIMEOUT = 15;
    protected static final int COOKIE_DIALOG_TIMEOUT = 10;
    protected static final int STANDARD_WAIT = 2000;

    protected void waitForPageToLoad() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
        wait.until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));
        waitABit(STANDARD_WAIT);
    }

    protected void switchToNewTab() {
        List<String> tabs = new ArrayList<>(getDriver().getWindowHandles());
        if (tabs.size() > 1) {
            getDriver().switchTo().window(tabs.get(tabs.size() - 1));
            logger.info("Switched to tab: {}", getDriver().getCurrentUrl());
        }
    }
    public Boolean isElementVisible(By locator) {
        List<WebElement> elements = getDriver().findElements(locator);
        return !elements.isEmpty() && elements.get(0).isDisplayed();
    }

    protected void waitForElementToBeClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void clickElement(WebElement element) {
        scrollElementIntoView(element);
        javascriptClick(element);
    }

    protected void scrollElementIntoView(WebElement element) {
        evaluateJavascript("arguments[0].scrollIntoView({block: 'center'});", element);
        waitABit(500);
    }

    protected void javascriptClick(WebElement element) {
        evaluateJavascript("arguments[0].click();", element);
    }

    boolean isElementPresent(By locator) {
        return !getDriver().findElements(locator).isEmpty();
    }

    protected void jsClick(WebElement element) {
        evaluateJavascript("arguments[0].scrollIntoView({block: 'center'});", element);
        waitForElementToBeClickable(element);
        evaluateJavascript("arguments[0].click();", element);
    }

}