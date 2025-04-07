Feature: LexisNexis Career Site Job Search
  As a job seeker
  I want to search for automation tester jobs on LexisNexis career site
  So that I can find suitable employment opportunities

  Scenario: Search for automation tester position
    Given the user navigates to LexisNexis UK homepage
    And the user handles any cookie consent popups
    When the user navigates to Careers page via the About Us menu
    And the user clicks on "Search all jobs" link
    And the user searches for "Finance" positions
    Then the user should see at least one job search result
