package lexisnexis.pages;

import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

@DefaultUrl("https://www.lexisnexis.co.uk")
public class HomePage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);

    private static final By ABOUT_US_LINK = By.xpath("//a[contains(text(), 'About') or contains(@href, 'about')]");

    private static final By ACCEPT_COOKIES_BUTTONS = By.xpath(
            "//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'accept all cookies') " +
                    "or contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'accept all')]"
    );

    public void verifyPageLoaded() {
        waitForPageToLoad();
        logger.info("Home page loaded successfully");
        shouldBeVisible(ABOUT_US_LINK);
    }

    public void handleCookieConsent() {
        if (isElementPresent(ACCEPT_COOKIES_BUTTONS)) {
            WebElement acceptButton = find(ACCEPT_COOKIES_BUTTONS);
            if (acceptButton.isDisplayed()) {
                acceptButton.click();
                logger.info("Accepted cookies");
            }
        } else {
            logger.info("No cookie consent popup found or it's already been handled");
        }
    }

    public void clickAboutUs() {
        WebElement aboutUsLink = find(ABOUT_US_LINK);
        scrollElementIntoView(aboutUsLink);
        aboutUsLink.click();
        waitForPageToLoad();
    }

    public void clickSubMenuItem(String menuItemText, String urlFragment) {
        String xpath = String.format("//a[contains(text(), '%s') and contains(@href, '%s')]", menuItemText, urlFragment);
        WebElement menuItem = find(By.xpath(xpath));

        if (!menuItem.isDisplayed()) {
            scrollElementIntoView(menuItem);
        }

        Actions actions = new Actions(getDriver());
        actions.moveToElement(menuItem).click().perform();
        waitForPageToLoad();
    }
}