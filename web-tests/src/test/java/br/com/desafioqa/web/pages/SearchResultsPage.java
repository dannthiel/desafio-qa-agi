package br.com.desafioqa.web.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchResultsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By pageTitle = By.cssSelector("h1.page-title");
    private final By resultArticles = By.cssSelector("main#main article.ast-article-post");
    private final By resultTitles = By.cssSelector("main#main article h2.entry-title a");
    private final By noResultsMessage = By.cssSelector("section.no-results.not-found");

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public SearchResultsPage waitUntilLoaded(String term) {
        wait.until(ExpectedConditions.urlContains("?s="));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(pageTitle, term));
        return this;
    }

    public String getTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle)).getText();
    }

    public int getResultCount() {
        return driver.findElements(resultArticles).size();
    }

    public boolean hasNoResultsMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(noResultsMessage)).isDisplayed();
    }

    public List<String> getResultTitles() {
        return wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(resultTitles, 0))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    @Step("Abrir o primeiro artigo retornado")
    public ArticlePage openFirstArticle() {
        WebElement firstResult = wait.until(ExpectedConditions.elementToBeClickable(resultTitles));
        String expectedTitle = firstResult.getText();
        firstResult.click();
        return new ArticlePage(driver).waitUntilLoaded(expectedTitle);
    }
}
