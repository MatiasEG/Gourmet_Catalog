package integrationTest;

import model.searchModel.ISearchModel;
import model.searchModel.SearchModel;
import model.searchModel.searchLogic.ISearchLogic;
import model.searchModel.searchLogic.SearchResult;
import model.storedInfoModel.DataBase;
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

    @Before
    public void setUp() throws Exception {
        DataBase.clearDataBase();
        DataBase.saveArticle("Pizza", "pizzapizzapizza");
        DataBase.saveArticle("Coca-Cola", "cocacolacocacola");
        DataBase.saveArticle("Cheese", "cheesecheesecheese");

        searchLogic = mock(ISearchLogic.class);

        searchModel = new SearchModel(searchLogic);
        storedInfoModel = new StoredInfoModel();
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
    }

    @Test
    public void testSearch() throws Exception {
        searchView.setSearchText("Pizza");

        List<String> resultListOnView = new ArrayList<>();
        resultListOnView.add("Pizza rica: Yumi");
        resultListOnView.add("Pizza fea: Puaj");
        resultListOnView.add("Pizza grande: Wow");
        resultListOnView.add("Pizza con queso: Un clasico");

        searchPresenter.onEventSearchArticles();
        this.waitForViewPresenterTask();

        assertEquals(resultListOnView, searchView.getSearchResults());
    }

    @Test
    public void testSelectFullArticle() throws Exception {
        searchView.setSearchText("Pizza");
        searchPresenter.onEventSearchArticles();
        this.waitForViewPresenterTask();

        searchView.setSelectedSearchResultIndex(3);
        searchView.selectFullArticleOption();

        searchPresenter.onEventSelectArticle();
        waitForViewPresenterTask();

        assertTrue(searchView.getArticleContent().contains("Pizza con queso: Un clasico"));
    }

    @Test
    public void testSelectArticleSummary() throws Exception {
        searchView.setSearchText("Pizza");
        searchPresenter.onEventSearchArticles();
        this.waitForViewPresenterTask();

        searchView.setSelectedSearchResultIndex(2);
        searchView.selectArticleSummaryOption();

        searchPresenter.onEventSelectArticle();
        waitForViewPresenterTask();

        assertTrue(searchView.getArticleContent().contains("Pizza grande: Wow"));
    }

    @Test
    public void saveArticle() throws Exception {
        searchView.setSearchText("Pizza");
        searchPresenter.onEventSearchArticles();
        this.waitForViewPresenterTask();

        searchView.setSelectedSearchResultIndex(3);
        searchView.selectFullArticleOption();

        searchPresenter.onEventSelectArticle();
        this.waitForViewPresenterTask();

        searchPresenter.onEventSaveArticle();

        assertTrue(DataBase.getArticleContent("Pizza con queso").contains("Pizza con queso: Un clasico"));
    }

    @Test
    public void saveArticleNegative(){
        searchPresenter.onEventSaveArticle();
        assertEquals(mainView.getLastError(), "Search Result Not Selected");
    }

    @Test
    public void textSearchNegative() throws InterruptedException {
        searchPresenter.onEventSearchArticles();
        this.waitForViewPresenterTask();

        assertEquals(mainView.getLastError(), "Empty Search Field");
    }

    @Test
    public void testSearchException() throws Exception {
        when(searchLogic.searchTermInWikipediaAndParse(any())).thenThrow(new Exception());
        searchView.setSearchText("Pizza");
        searchPresenter.onEventSearchArticles();
        this.waitForViewPresenterTask();
        assertEquals(mainView.getLastError(), "Search error");
    }

    @Test
    public void testSelectStoredArticle(){
        storedInfoView.setSelectedArticleTitle("Pizza");
        storedInfoPresenter.onEventSelectArticle();
        assertTrue(storedInfoView.getArticleContent().contains("pizzapizzapizza"));
    }

    @Test
    public void testDeleteArticle() throws Exception{
        storedInfoView.setSelectedArticleTitle("Coca-Cola");
        storedInfoPresenter.onEventSelectArticle();
        storedInfoPresenter.onEvenDeleteArticle();
        assertFalse(DataBase.getAllArticleTitles().contains("Coca-Cola"));
    }

    @Test
    public void testUpdateArticle() throws Exception{
        storedInfoView.setSelectedArticleTitle("Cheese");
        storedInfoPresenter.onEventSelectArticle();
        storedInfoView.setArticleContent("cheeseeeeeeeeeeeeeeeee");
        storedInfoPresenter.onEventUpdateArticle();
        assertTrue(DataBase.getArticleContent("Cheese").contains("cheeseeeeeeeeeeeeeeeee"));
    }

    @Test
    public void testDeleteArticleWithoutSelecting() throws Exception{
        storedInfoPresenter.onEvenDeleteArticle();
        assertEquals(mainView.getLastError(), "Article Not Selected");
    }

    @Test
    public void testUpdateArticleWithoutSelecting() throws Exception{
        storedInfoPresenter.onEventUpdateArticle();
        assertEquals(mainView.getLastError(), "Article Not Selected");
    }

    private void waitForViewPresenterTask() throws InterruptedException{
        while(searchPresenter.isActivelyWorking()) Thread.sleep(1);
    }
}
