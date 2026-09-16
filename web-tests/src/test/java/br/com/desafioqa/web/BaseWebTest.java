package br.com.desafioqa.web;

import br.com.desafioqa.web.support.ScreenshotOnFailureExtension;
import br.com.desafioqa.web.support.WebDriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public abstract class BaseWebTest {

    protected WebDriver driver;

    @RegisterExtension
    final ScreenshotOnFailureExtension screenshotOnFailure =
            new ScreenshotOnFailureExtension(() -> driver);

    @BeforeEach
    void setUpBrowser() {
        driver = WebDriverFactory.createChrome();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(45));
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
    }

    @AfterEach
    void tearDownBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
