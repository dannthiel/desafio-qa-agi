package br.com.desafioqa.web.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BlogHomePage {

    private static final String DEFAULT_URL = "https://blogdoagi.com.br/";

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By searchToggles = By.cssSelector(
            ".ast-header-search .astra-search-icon, .ast-search-menu-icon .search-icon");
    private final By searchFields = By.cssSelector("form.search-form input[name='s']");

    public BlogHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Step("Abrir o Blog do Agi")
    public BlogHomePage open() {
        driver.get(System.getProperty("blog.baseUrl", DEFAULT_URL));
        wait.until(ExpectedConditions.urlContains("blog.agibank.com.br"));
        return this;
    }

    @Step("Pesquisar pelo termo: {term}")
    public SearchResultsPage searchFor(String term) {
        WebElement field = firstDisplayed(searchFields);
        if (field == null) {
            WebElement toggle = wait.until(driver -> firstDisplayed(searchToggles));
            toggle.click();
            field = wait.until(driver -> firstDisplayed(searchFields));
        }

        field.clear();
        field.sendKeys(term, Keys.ENTER);
        return new SearchResultsPage(driver).waitUntilLoaded(term);
    }

    private WebElement firstDisplayed(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        return elements.stream().filter(WebElement::isDisplayed).findFirst().orElse(null);
    }
}
