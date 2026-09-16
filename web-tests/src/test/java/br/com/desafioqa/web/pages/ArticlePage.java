package br.com.desafioqa.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ArticlePage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By articleTitle = By.cssSelector("article h1.entry-title, main h1.entry-title");

    public ArticlePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public ArticlePage waitUntilLoaded(String expectedTitle) {
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("?s=")));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(articleTitle, expectedTitle));
        return this;
    }

    public String getTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(articleTitle)).getText();
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }
}
