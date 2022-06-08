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

import static org.junit.Assert.assertEquals;
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

    public void testSelect(){
        List<String> resultListOnView = new ArrayList<>();
        resultListOnView.add("Pizza rica: Yumi");
        resultListOnView.add("Pizza fea: Puaj");
        resultListOnView.add("Pizza grande: Wow");
        resultListOnView.add("Pizza con queso: Un clasico");

        searchView.setSearchResultsList(resultListOnView);

        
    }

    private void waitForViewPresenterTask() throws InterruptedException{
        while(searchPresenter.isActivelyWorking()) Thread.sleep(1);
    }
}
