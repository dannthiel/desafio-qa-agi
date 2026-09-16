package br.com.desafioqa.web.support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public final class WebDriverFactory {

    private WebDriverFactory() {
    }

    public static WebDriver createChrome() {
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "true"));

        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--window-size=1920,1080",
                "--lang=pt-BR",
                "--disable-dev-shm-usage",
                "--no-sandbox"
        );

        if (headless) {
            options.addArguments("--headless=new");
        }

        return new ChromeDriver(options);
    }
}
