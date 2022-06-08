package presenterUnitTest;

import model.storedInfoModel.IStoredInfoModel;
import model.searchModel.ISearchModel;
import model.searchModel.searchLogic.SearchResult;
import org.junit.Before;
import org.junit.Test;
import presenter.ISearchPresenter;
import presenter.SearchPresenter;
import views.IMainView;
import views.ISearchView;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class SearchPresenterTest {

    private ISearchModel searchModel;
    private IStoredInfoModel storedInfoModel;
    private ISearchView searchView;
    private IMainView mainView;
    private ISearchPresenter searchPresenter;

    @Before
    public void setUp(){
        searchModel = mock(ISearchModel.class);
        storedInfoModel = mock(IStoredInfoModel.class);
        searchView = mock(ISearchView.class);
        mainView = mock(IMainView.class);

        when(mainView.getSearchView()).thenReturn(searchView);

        searchPresenter = new SearchPresenter(searchModel, storedInfoModel);
        searchPresenter.setView(mainView);
    }

    @Test
    public void testSearch() throws InterruptedException {
        when(searchView.getSearchText()).thenReturn("Cake");
        searchPresenter.onEventSearchArticles();
        this.waitForViewPresenterTask();
        verify(searchModel, times(1)).searchAllCoincidencesInWikipedia("Cake");
    }

    @Test
    public void testSelectFullArticle() throws InterruptedException {
        List<SearchResult> resultList = new ArrayList<>();
        SearchResult searchResultCake = new SearchResult("Cake", "", "Yumi");
        SearchResult searchResultRandom = new SearchResult("Not this", "", "asdasd");
        resultList.add(searchResultCake);
        resultList.add(searchResultRandom);
        searchPresenter.setSearchResultsList(resultList);

        when(searchView.getSelectedSearchResultIndex()).thenReturn(0);
        when(searchView.fullArticleIsSelected()).thenReturn(true);

        searchPresenter.onEventSelectArticle();
        this.waitForViewPresenterTask();

        verify(searchModel, times(1)).searchFullArticleInWikipedia(searchResultCake);
    }

    @Test
    public void testSelectArticleSummary() throws InterruptedException {
        List<SearchResult> resultList = new ArrayList<>();
        SearchResult searchResultCake = new SearchResult("Cake", "", "Yumi");
        SearchResult searchResultRandom = new SearchResult("Not this", "", "asdasd");
        resultList.add(searchResultCake);
        resultList.add(searchResultRandom);
        searchPresenter.setSearchResultsList(resultList);

        when(searchView.getSelectedSearchResultIndex()).thenReturn(1);
        when(searchView.fullArticleIsSelected()).thenReturn(false);

        searchPresenter.onEventSelectArticle();
        this.waitForViewPresenterTask();

        verify(searchModel, times(1)).searchArticleSummaryInWikipedia(searchResultRandom);
    }

    @Test
    public void testSaveArticle(){
        SearchResult searchResultCoke = new SearchResult("Coke", "", "Pssssssss");
        searchPresenter.setSelectedSearchResult(searchResultCoke);

        when(searchModel.getFoundArticleContent()).thenReturn("mock_content");

        searchPresenter.onEventSaveArticle();

        verify(storedInfoModel, times(1)).saveArticle("Coke", "mock_content");
    }

    @Test
    public void testSaveNullArticle(){
        searchPresenter.setSelectedSearchResult(null);

        searchPresenter.onEventSaveArticle();

        verify(storedInfoModel, never()).saveArticle(any(), any());
    }

    private void waitForViewPresenterTask() throws InterruptedException{
        while(searchPresenter.isActivelyWorking()) Thread.sleep(1);
    }
}
