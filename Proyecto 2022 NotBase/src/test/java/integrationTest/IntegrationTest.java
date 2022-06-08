package integrationTest;

import model.searchModel.ISearchModel;
import model.searchModel.SearchModel;
import model.searchModel.searchLogic.ISearchLogic;
import model.searchModel.searchLogic.SearchResult;
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


    @Before
    public void setUp(){
        searchLogic = mock(ISearchLogic.class);

        searchModel = new SearchModel(searchLogic);
        storedInfoModel = new StoredInfoModel();
        searchPresenter = new SearchPresenter(searchModel, storedInfoModel);
        storedInfoPresenter = new StoredInfoPresenter(storedInfoModel);

        mainView = new MainView(storedInfoPresenter, searchPresenter);

        searchPresenter.setView(mainView);
        storedInfoPresenter.setView(mainView);

        searchView = new SearchView(searchPresenter);
    }

    @Test
    public void testSearch() throws Exception {
        searchView.setTextToSearch("Pizza");
        searchPresenter.onEventSearchArticles();
        this.waitForViewPresenterTask();

        List<SearchResult> resultList = new ArrayList<>();
        SearchResult searchResult1 = new SearchResult("Pizza rica", "", "Yumi");
        SearchResult searchResult2 = new SearchResult("Pizza fea", "", "Puaj");
        SearchResult searchResult3 = new SearchResult("Pizza grande", "", "Wow");
        SearchResult searchResult4 = new SearchResult("Pizza con queso", "", "Un clasico");
        resultList.add(searchResult1);
        resultList.add(searchResult2);
        resultList.add(searchResult3);
        resultList.add(searchResult4);
        when(searchLogic.searchTermInWikipediaAndParse("Pizza")).thenReturn(resultList);

        searchView
    }

    private void waitForViewPresenterTask() throws InterruptedException{
        while(searchPresenter.isActivelyWorking()) Thread.sleep(1);
    }
}
