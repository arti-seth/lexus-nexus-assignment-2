package lexisnexis.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.thucydides.core.webdriver.DriverSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverManager implements DriverSource {
    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);

    @Override
    public WebDriver newDriver() {
        String browserType = System.getProperty("browser.type", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        logger.info("Creating new driver: {} (headless={})", browserType, headless);

        WebDriver driver;
        switch (browserType) {
            case "chrome":
                driver = setupChromeDriver(headless);
                break;
            case "firefox":
                driver = setupFirefoxDriver(headless);
                break;
            case "edge":
                driver = setupEdgeDriver(headless);
                break;
            case "safari":
                driver = setupSafariDriver();
                break;
            default:
                logger.warn("Unsupported browser type: {}. Defaulting to Chrome.", browserType);
                driver = setupChromeDriver(headless);
                break;
        }

        if (!headless) {
            maximizeWindow(driver);
        }

        return driver;
    }

    private void maximizeWindow(WebDriver driver) {
        logger.info("Maximizing browser window");
        driver.manage().window().maximize();
    }

    @Override
    public boolean takesScreenshots() {
        return true;
    }

    private WebDriver setupChromeDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = configureChromeOptions(headless);

        logger.info("Initializing ChromeDriver");
        return new ChromeDriver(options);
    }

    private ChromeOptions configureChromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");

        if (headless) {
            options.addArguments("--headless=new");
        }

        return options;
    }

    private WebDriver setupFirefoxDriver(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = configureFirefoxOptions(headless);

        logger.info("Initializing FirefoxDriver");
        return new FirefoxDriver(options);
    }

    private FirefoxOptions configureFirefoxOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-notifications");

        FirefoxProfile profile = createFirefoxProfile();
        if (headless) {
            options.addArguments("--headless");
        }
        options.setProfile(profile);
        return options;
    }

    private FirefoxProfile createFirefoxProfile() {
        FirefoxProfile profile = new FirefoxProfile();
        // Prevent password prompts
        profile.setPreference("signon.rememberSignons", false);
        return profile;
    }

    private WebDriver setupEdgeDriver(boolean headless) {
        WebDriverManager.edgedriver()
                .avoidBrowserDetection()
                .setup();

        EdgeOptions options = configureEdgeOptions(headless);
        return new EdgeDriver(options);
    }

    private EdgeOptions configureEdgeOptions(boolean headless) {
        EdgeOptions options = new EdgeOptions();
            options.addArguments("--headless");
        logger.info("Initializing EdgeDriver");
        return options;
    }

    private WebDriver setupSafariDriver() {
        WebDriverManager.safaridriver().setup();
        logger.info("Initializing SafariDriver");
        return new SafariDriver();
    }
}