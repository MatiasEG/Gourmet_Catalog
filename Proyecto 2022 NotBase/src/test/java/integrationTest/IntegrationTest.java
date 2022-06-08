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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
        List<String> resultListOnView = new ArrayList<>();
        resultListOnView.add("Pizza rica: Yumi");
        resultListOnView.add("Pizza fea: Puaj");
        resultListOnView.add("Pizza grande: Wow");
        resultListOnView.add("Pizza con queso: Un clasico");

        searchView.setSearchResultsList(resultListOnView);
        searchView.setSelectedSearchResultIndex(3);
        searchView.selectFullArticleOption();
        searchPresenter.setSearchResultsList(resultList);

        searchPresenter.onEventSelectArticle();
        waitForViewPresenterTask();

        assertTrue(searchView.getArticleContent().contains(resultListOnView.get(3)));
    }

    @Test
    public void testSelectArticleSummary() throws Exception {
        List<String> resultListOnView = new ArrayList<>();
        resultListOnView.add("Pizza rica: Yumi");
        resultListOnView.add("Pizza fea: Puaj");
        resultListOnView.add("Pizza grande: Wow");
        resultListOnView.add("Pizza con queso: Un clasico");

        searchView.setSearchResultsList(resultListOnView);
        searchView.setSelectedSearchResultIndex(2);
        searchView.selectArticleSummaryOption();
        searchPresenter.setSearchResultsList(resultList);

        searchPresenter.onEventSelectArticle();
        waitForViewPresenterTask();

        assertTrue(searchView.getArticleContent().contains(resultListOnView.get(2)));
    }

    @Test
    public void saveArticle() throws Exception {
        List<String> resultListOnView = new ArrayList<>();
        resultListOnView.add("Pizza rica: Yumi");
        resultListOnView.add("Pizza fea: Puaj");
        resultListOnView.add("Pizza grande: Wow");
        resultListOnView.add("Pizza con queso: Un clasico");

        searchView.setSearchText("Pizza");
        searchPresenter.onEventSearchArticles();
        this.waitForViewPresenterTask();

        searchView.setSearchResultsList(resultListOnView);
        searchView.setSelectedSearchResultIndex(3);
        searchView.selectFullArticleOption();
        searchPresenter.setSearchResultsList(resultList);

        searchPresenter.onEventSelectArticle();
        waitForViewPresenterTask();

        searchPresenter.onEventSaveArticle();

        assertTrue(DataBase.getArticleContent("Pizza").contains("Pizza con queso: Un clasico"));
    }

    private void waitForViewPresenterTask() throws InterruptedException{
        while(searchPresenter.isActivelyWorking()) Thread.sleep(1);
    }
}
