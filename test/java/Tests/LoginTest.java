package Tests;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import Pages.LoginPage;

import java.io.File;
import java.util.logging.*;
import java.nio.file.*;
import java.time.LocalDateTime;

public class LoginTest {
    private static final Logger logger = Logger.getLogger(LoginTest.class.getName());
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://practicetestautomation.com/practice- test-login/");
                loginPage = new LoginPage(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/testdata.csv", numLinesToSkip = 1)
    void testLogin(String username, String password, boolean
            shouldSucceed) {
        logger.log(Level.INFO, "START: Testing username={0}",
                username);
        try {
            loginPage.enterUsername(username);
            loginPage.enterPassword(password);
            loginPage.clickSubmit();
            String url = loginPage.getCurrentUrl();
            if (shouldSucceed) {
                Assertions.assertTrue(url.contains("logged-in-successfully/"));
            } else {
                Assertions.assertTrue(url.contains("practice-%20test-login/"));
            }
            takeScreenshot("PASS_" + username);
            logger.log(Level.INFO, "SUCCESS: username={0}", username);
        } catch (AssertionError e) {
            takeScreenshot("FAIL_" + username);
            logger.log(Level.WARNING, "FAILED: username={0}",
                    username);
            throw e;
        }
    }

    private void takeScreenshot(String testName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);
            Path dest = Paths.get("src/test/resources/screenshots/" +
                    testName + "_" +
                    LocalDateTime.now().toString().replace(':', '_') + ".png");
            Files.copy(src.toPath(), dest);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Screenshot failed", e);
        }
    }
}