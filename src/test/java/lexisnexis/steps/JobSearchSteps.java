package lexisnexis.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lexisnexis.pages.CareersPage;
import lexisnexis.pages.JobSearchPage;
import net.thucydides.core.annotations.Steps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobSearchSteps {
    private static final Logger log = LoggerFactory.getLogger(JobSearchSteps.class);

    @Steps
    private CareersPage careersPage;

    @Steps
    private JobSearchPage jobSearchPage;

    private String lastSearchTerm = "";

    @And("the user searches for {string} positions")
    public void searchForPositions(String searchTerm) {
        lastSearchTerm = searchTerm;
        log.info("Starting search for '{}' positions", searchTerm);
        jobSearchPage.init();
        jobSearchPage.searchForJobs(searchTerm);
    }

    @Then("the user should see at least one job search result")
    public void verifySearchResults() {
        int searchResultsCount = jobSearchPage.getSearchResultCount();
        log.info("TEST INFO: Found {} job search results for '{}'", searchResultsCount, lastSearchTerm);
    }
}