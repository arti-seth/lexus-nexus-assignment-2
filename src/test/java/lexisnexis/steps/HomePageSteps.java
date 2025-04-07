package lexisnexis.steps;

import lexisnexis.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HomePageSteps {
    private static final Logger logger = LoggerFactory.getLogger(HomePageSteps.class);
    private WebDriver driver;
    @Steps
    private HomePage homePage;


    @Given("the user navigates to LexisNexis UK homepage")
    public void navigateToHomepage() {
        homePage.open();
        homePage.verifyPageLoaded();
        logger.info("Navigated to LexisNexis UK homepage");
    }

    @Given("the user handles any cookie consent popups")
    public void handleConsentPopups() {
        homePage.handleCookieConsent();
        logger.info("Handled any cookie consent popups");
    }

    @When("the user navigates to Careers page via the About Us menu")
    public void navigateToCareersViaAboutUs() {
        homePage.clickAboutUs();
        homePage.clickSubMenuItem("Careers", "career");
        logger.info("Navigated to Careers page via About Us menu");
    }
}