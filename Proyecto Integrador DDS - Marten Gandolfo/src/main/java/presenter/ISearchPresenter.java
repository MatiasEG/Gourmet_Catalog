package presenter;

import model.searchModel.searchLogic.SearchResult;
import views.IMainView;

import java.util.List;

public interface ISearchPresenter {

    void setView(IMainView mainView);
    void onEventSearchArticles();
    void onEventSelectArticle();
    void onEventSaveArticle();
    void setSearchResultsList(List<SearchResult> searchResultsList);
    void setSelectedSearchResult(SearchResult selectedSearchResult);
    boolean isActivelyWorking();
}
