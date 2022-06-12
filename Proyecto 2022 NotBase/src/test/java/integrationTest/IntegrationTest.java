package integrationTest;

import model.searchModel.ISearchModel;
import model.searchModel.SearchModel;
import model.searchModel.searchLogic.ISearchLogic;
import model.searchModel.searchLogic.SearchResult;
import model.storedInfoModel.DataBase;
import model.storedInfoModel.IDataBase;
import model.storedInfoModel.IStoredInfoModel;
import model.storedInfoModel.StoredInfoModel;
import org.junit.Before;
import org.junit.Test;
import presenter.ISearchPresenter;
import presenter.IStoredInfoPresenter;
import presenter.SearchPresenter;
import presenter.StoredInfoPresenter;
import views.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class IntegrationTest {

    private ISearchLogic searchLogic;
    private ISearchModel searchModel;
    private IStoredInfoModel storedInfoModel;
    private IMainView mainView;
    private ISearchPresenter searchPresenter;
    private IStoredInfoPresenter storedInfoPresenter;
    private IStoredInfoView storedInfoView;
    private ISearchView searchView;
    private List<SearchResult> resultList;
    private IDataBase dataBaseMock;
    private static IDataBase dataBase = new DataBase();

    @Before
    public void setUp() throws Exception {
        dataBase.clearDataBase();
        dataBase.saveArticle("Pizza", "pizzapizzapizza");
        dataBase.saveArticle("Coca-Cola", "cocacolacocacola");
        dataBase.saveArticle("Cheese", "cheesecheesecheese");

        searchLogic = mock(ISearchLogic.class);

        searchModel = new SearchModel(searchLogic);
        storedInfoModel = new StoredInfoModel(new DataBase());
        searchPresenter = new SearchPresenter(searchModel, storedInfoModel);
        storedInfoPresenter = new StoredInfoPresenter(storedInfoModel);

        mainView = new MainView(storedInfoPresenter, searchPresenter);

        searchView = mainView.getSearchView();
        storedInfoView = mainView.getStoredInfoView();

        searchPresenter.setView(mainView);
        storedInfoPresenter.setView(mainView);

        resultList = new ArrayList<>();
        resultList.add(new SearchResult("Pizza rica", "", "Yumi"));
        resultList.add(new SearchResult("Pizza fea", "", "Puaj"));
        resultList.add(new SearchResult("Pizza grande", "", "Wow"));
        resultList.add(new SearchResult("Pizza con queso", "", "Un clasico"));
        when(searchLogic.searchTermInWikipediaAndParse("Pizza")).thenReturn(resultList);
        when(searchLogic.searchFullArticleInWikipediaAndParse(any())).thenReturn("Pizza con queso: Un clasico");
        when(searchLogic.searchArticleSummaryInWikipediaAndParse(any())).thenReturn("Pizza grande: Wow");

        dataBaseMock = mock(IDataBase.class);
        when(dataBaseMock.getAllArticleTitles()).thenThrow(new Exception());
        when(dataBaseMock.getArticleContent(any())).thenThrow(new Exception());
        doThrow(new Exception()).when(dataBaseMock).deleteArticle(any());
        doThrow(new Exception()).when(dataBaseMock).saveArticle(any(), any());
    }

    @Test
    public void testSearchTerm() throws Exception {
        searchView.setSearchText("Pizza");

        List<String> resultListOnView = new ArrayList<>();
        resultListOnView.add("Pizza rica: Yumi");
        resultListOnView.add("Pizza fea: Puaj");
        resultListOnView.add("Pizza grande: Wow");
        resultListOnView.add("Pizza con queso: Un clasico");

        searchPresenter.onEventSearchArticles();
        this.waitForPresentersTask();

        assertEquals(resultListOnView, searchView.getSearchResults());
    }

    @Test
    public void testSearchFullArticle() throws Exception {
        searchView.setSearchText("Pizza");
        searchPresenter.onEventSearchArticles();
        this.waitForPresentersTask();

        searchView.setSelectedSearchResultIndex(3);
        searchView.selectFullArticleOption();

        searchPresenter.onEventSelectArticle();
        waitForPresentersTask();

        assertTrue(searchView.getArticleContent().contains("Pizza con queso: Un clasico"));
    }

    @Test
    public void testSearchArticleSummary() throws Exception {
        searchView.setSearchText("Pizza");
        searchPresenter.onEventSearchArticles();
        this.waitForPresentersTask();

        searchView.setSelectedSearchResultIndex(2);
        searchView.selectArticleSummaryOption();

        searchPresenter.onEventSelectArticle();
        waitForPresentersTask();

        assertTrue(searchView.getArticleContent().contains("Pizza grande: Wow"));
    }

    @Test
    public void testSaveArticle() throws Exception {
        searchView.setSearchText("Pizza");
        searchPresenter.onEventSearchArticles();
        this.waitForPresentersTask();

        searchView.setSelectedSearchResultIndex(3);
        searchView.selectFullArticleOption();

        searchPresenter.onEventSelectArticle();
        this.waitForPresentersTask();

        searchPresenter.onEventSaveArticle();
        this.waitForPresentersTask();

        assertTrue(dataBase.getArticleContent("Pizza con queso").contains("Pizza con queso: Un clasico"));
    }

    @Test
    public void testSaveArticleWithoutSelecting() throws InterruptedException {
        searchPresenter.onEventSaveArticle();
        this.waitForPresentersTask();
        assertEquals("Search Result Not Selected", mainView.getLastError());
    }

    @Test
    public void testEmptySearch() throws InterruptedException {
        searchPresenter.onEventSearchArticles();
        this.waitForPresentersTask();

        assertEquals("Empty Search Field", mainView.getLastError());
    }

    @Test
    public void testSearchErrorWhenSearchingTerm() throws Exception {
        when(searchLogic.searchTermInWikipediaAndParse(any())).thenThrow(new Exception());
        searchView.setSearchText("Pizza");
        searchPresenter.onEventSearchArticles();
        this.waitForPresentersTask();
        assertEquals("Search error", mainView.getLastError());
    }

    @Test
    public void testSearchErrorWhenSearchingFullArticle() throws Exception {
        when(searchLogic.searchFullArticleInWikipediaAndParse(any())).thenThrow(new Exception());
        searchView.setSearchText("Pizza");
        searchPresenter.onEventSearchArticles();
        this.waitForPresentersTask();

        searchView.setSelectedSearchResultIndex(2);
        searchView.selectFullArticleOption();

        searchPresenter.onEventSelectArticle();
        waitForPresentersTask();

        assertEquals("Search error", mainView.getLastError());
    }

    @Test
    public void testSearchErrorWhenSeearchingArticleSummary() throws Exception {
        when(searchLogic.searchArticleSummaryInWikipediaAndParse(any())).thenThrow(new Exception());
        searchView.setSearchText("Pizza");
        searchPresenter.onEventSearchArticles();
        this.waitForPresentersTask();

        searchView.setSelectedSearchResultIndex(2);
        searchView.selectArticleSummaryOption();

        searchPresenter.onEventSelectArticle();
        this.waitForPresentersTask();

        assertEquals("Search error", mainView.getLastError());
    }

    @Test
    public void testSelectStoredArticle() throws InterruptedException {
        storedInfoView.setSelectedArticleTitle("Pizza");
        storedInfoPresenter.onEventSelectArticle();
        this.waitForPresentersTask();
        assertTrue(storedInfoView.getArticleContent().contains("pizzapizzapizza"));
    }

    @Test
    public void testDeleteArticle() throws Exception{
        storedInfoView.setSelectedArticleTitle("Coca-Cola");
        storedInfoPresenter.onEventSelectArticle();
        this.waitForPresentersTask();
        storedInfoPresenter.onEvenDeleteArticle();
        this.waitForPresentersTask();
        assertFalse(dataBase.getAllArticleTitles().contains("Coca-Cola"));
    }

    @Test
    public void testUpdateArticle() throws Exception{
        storedInfoView.setSelectedArticleTitle("Cheese");
        storedInfoPresenter.onEventSelectArticle();
        this.waitForPresentersTask();
        storedInfoView.setArticleContent("cheeseeeeeeeeeeeeeeeee");
        storedInfoPresenter.onEventUpdateArticle();
        this.waitForPresentersTask();
        assertTrue(dataBase.getArticleContent("Cheese").contains("cheeseeeeeeeeeeeeeeeee"));
    }

    @Test
    public void testDeleteArticleWithoutSelecting() throws Exception{
        storedInfoPresenter.onEvenDeleteArticle();
        this.waitForPresentersTask();
        assertEquals("Article Not Selected", mainView.getLastError());
    }

    @Test
    public void testUpdateArticleWithoutSelecting() throws Exception{
        storedInfoPresenter.onEventUpdateArticle();
        this.waitForPresentersTask();
        assertEquals("Article Not Selected", mainView.getLastError());
    }

    @Test
    public void testDatabaseErrorWhenSelectingArticle() throws Exception{
        storedInfoModel.setDataBase(dataBaseMock);
        storedInfoView.setSelectedArticleTitle("Pizza");
        storedInfoPresenter.onEventSelectArticle();
        this.waitForPresentersTask();
        assertEquals("Database Error", mainView.getLastError());
    }

    @Test
    public void testDatabaseErrorWhenDeletingArticle() throws Exception{
        storedInfoModel.setDataBase(dataBaseMock);
        storedInfoView.setSelectedArticleTitle("Pizza");
        storedInfoPresenter.onEventSelectArticle();
        this.waitForPresentersTask();
        storedInfoPresenter.onEvenDeleteArticle();
        this.waitForPresentersTask();
        assertEquals("Database Error", mainView.getLastError());
    }

    @Test
    public void testDatabaseErrorWhenUpdatingArticle() throws Exception{
        storedInfoModel.setDataBase(dataBaseMock);
        storedInfoView.setSelectedArticleTitle("Pizza");
        storedInfoPresenter.onEventSelectArticle();
        this.waitForPresentersTask();
        storedInfoPresenter.onEventUpdateArticle();
        this.waitForPresentersTask();
        assertEquals("Database Error", mainView.getLastError());
    }

    private void waitForPresentersTask() throws InterruptedException{
        while(searchPresenter.isActivelyWorking() || storedInfoPresenter.isActivelyWorking()) Thread.sleep(1);
    }
}
