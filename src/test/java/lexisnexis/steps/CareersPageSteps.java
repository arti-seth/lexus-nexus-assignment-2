package lexisnexis.steps;

import lexisnexis.pages.CareersPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.thucydides.core.annotations.Steps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CareersPageSteps {
    private static final Logger logger = LoggerFactory.getLogger(CareersPageSteps.class);
    @Steps
    private CareersPage careersPage;

    @When("the user clicks on {string} link")
    public void clickOnLink(String linkText) {
        if (linkText.equalsIgnoreCase("Search all jobs")) {
            careersPage.clickSearchAllJobs();
            logger.info("Clicked on 'Search all jobs' link");
        } else {
            throw new RuntimeException("Link not recognized: " + linkText);
        }
    }
}