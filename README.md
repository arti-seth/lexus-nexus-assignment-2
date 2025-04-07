# LexisNexis Career Site Automation Framework

## Overview
This project demonstrates a Behavior Driven Development (BDD) approach to web application testing using Cucumber, Selenium, and Serenity BDD. The framework automates testing of the LexisNexis UK career site job search functionality.

## Features
- BDD-style tests written in Gherkin language
- Page Object Model design pattern for better maintainability
- Automatic WebDriver management
- Comprehensive logging
- Detailed test reporting with screenshots
- Cross-browser testing capability

## Prerequisites
- Java JDK 11 or higher
- Maven 3.6 or higher
- Chrome, Firefox, or Edge browser installed

## Project Structure

```
├── src/
│   └── test/
│       ├── java/
│       │   └── lexisnexis/
│       │       ├── pages/           # Page Object classes
│       │       │   ├── BasePage.java
│       │       │   ├── CareersPage.java
│       │       │   ├── HomePage.java
│       │       │   └── JobSearchPage.java
│       │       ├── steps/           # Step definitions
│       │       │   ├── CareersPageSteps.java
│       │       │   ├── HomePageSteps.java
│       │       │   └── JobSearchSteps.java
│       │       ├── runners/         # Test runners
│       │       │   └── TestRunner.java
│       │       └── driver/          # Driver configuration
│       │           └── DriverManager.java
│       └── resources/
│           ├── features/            # Feature files
│           │   └── JobSearch.feature
│           ├── serenity.properties  # Serenity configuration
│           └── logback.xml          # Logging configuration
└── pom.xml                          # Project dependencies
```

## Running the Tests

### Using Maven Wrapper
The project includes a Maven Wrapper, so you don't need to install Maven to run the tests.

#### On Linux/Unix/macOS:
```bash
# Run tests with default browser (Chrome)
./mvnw clean verify

# Run tests with a specific browser
./mvnw clean verify -Dbrowser.type=firefox
```
#### On Windows:
```cmd
# Run tests with default browser (Chrome)
mvnw clean verify

# Run tests with a specific browser
mvnw clean verify -Dbrowser.type=firefox
```

### Using Maven (if installed)
If you have Maven installed, you can use it directly:
```bash
# Run tests with default browser (Chrome)
mvn clean verify

# Run tests with a specific browser
mvn clean verify -Dbrowser.type=firefox
```

### From IDE
Run the `TestRunner` class as a JUnit test.


## Feature Scenarios

The main feature file `job_search.feature` includes a scenario that:

1. Navigates to the LexisNexis UK homepage
2. Handles any cookie consent popups
3. Navigates to the Careers page via the About Us menu
4. Clicks on the "Search all jobs" link
5. Searches for "automation tester" positions  #updated finance as 0 results found for automation tester
6. Verifies that at least one search result is displayed

## Test Reports

After test execution, Serenity generates detailed HTML reports in the `target/site/serenity` directory. Open `index.html` to view the report.





