import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;
import java.util.logging.Logger;

public class SearchingBlogForPastaWithVegetablesTest {

    private static final Logger LOGGER = Logger.getLogger(SearchingBlogForPastaWithVegetablesTest.class.getName());

    private final WebDriver driver = new FirefoxDriver();

    @BeforeEach
    public void waitingMethod() {
        System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver.exe");
        driver.manage().window().maximize();
        TestUtils.setDriver(driver);
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        LOGGER.info("Timeouts work.");
    }

    @Test
    public void searchingForPastaWithVegetables() {
        TestUtils.goToPage();
        TestUtils.acceptCookies();
        TestUtils.clickBlogButton();
        TestUtils.clickingMakaronyButton();
        TestUtils.clickingPastaWithWegetablesTile();
    }

    @AfterEach
    public void closingDriver() {
        driver.quit();
        LOGGER.info("Driver closed.");
    }
}
