package br.com.desafioqa.web;

import br.com.desafioqa.web.pages.ArticlePage;
import br.com.desafioqa.web.pages.BlogHomePage;
import br.com.desafioqa.web.pages.SearchResultsPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Desafio técnico QA")
@Feature("Pesquisa de artigos do Blog do Agi")
@Tag("web")
class BlogSearchTest extends BaseWebTest {

    @Test
    @Story("Pesquisa com resultado")
    @Severity(CRITICAL)
    @DisplayName("WEB-001 - Deve exibir artigos para um termo existente")
    @Description("Pesquisa por INSS e valida o comportamento da busca sem depender de um título editorial fixo.")
    void deveExibirArtigosParaTermoExistente() {
        SearchResultsPage results = new BlogHomePage(driver)
                .open()
                .searchFor("INSS");

        assertTrue(results.getTitle().toLowerCase().contains("inss"));
        assertTrue(results.getResultCount() > 0, "A pesquisa deveria retornar ao menos um artigo");
        assertFalse(results.getResultTitles().isEmpty());
    }

    @Test
    @Story("Pesquisa sem resultado")
    @Severity(CRITICAL)
    @DisplayName("WEB-002 - Deve informar quando não existem artigos")
    @Description("Usa uma massa única e valida a mensagem de ausência de resultados.")
    void deveInformarQuandoNaoExistemArtigos() {
        SearchResultsPage results = new BlogHomePage(driver)
                .open()
                .searchFor("qaautomacao999999");

        assertTrue(results.getTitle().contains("qaautomacao999999"));
        assertTrue(results.hasNoResultsMessage());
        assertTrue(results.getResultCount() == 0, "Não deveria haver artigos para a massa inválida");
    }

    @Test
    @Story("Abertura de artigo")
    @Severity(NORMAL)
    @DisplayName("WEB-003 - Deve abrir um artigo retornado pela pesquisa")
    void deveAbrirArtigoRetornadoPelaPesquisa() {
        SearchResultsPage results = new BlogHomePage(driver)
                .open()
                .searchFor("INSS");

        ArticlePage article = results.openFirstArticle();

        assertFalse(article.getTitle().isBlank());
        assertTrue(article.getUrl().startsWith("https://blog.agibank.com.br/"));
        assertFalse(article.getUrl().contains("?s="));
    }
}
