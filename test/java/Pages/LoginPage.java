package Pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
public class LoginPage {

    private WebDriver driver;
    private final By usernameBy = By.id("username");
    private final By passwordBy = By.id("password");
    private final By submitBy = By.id("submit");

    public LoginPage(WebDriver driver) { this.driver = driver; }

    public void enterUsername(String username) {
        driver.findElement(usernameBy).sendKeys(username);
    }
    public void enterPassword(String password) {
        driver.findElement(passwordBy).sendKeys(password);
    }
    public void clickSubmit() {
        driver.findElement(submitBy).click();
    }
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}