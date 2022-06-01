package presenter;

import model.searchModel.Search.SearchResult;
import views.IMainView;

import java.util.List;

public interface ISearchPresenter {

    void setView(IMainView mainView);
    void setSearchResultsList(List<SearchResult> searchResultsList);
    void setSelectedSearchResult(SearchResult selectedSearchResult);
    void onEventSearchArticles();
    void onEventSelectArticle();
    void onEventSaveArticle();
    boolean isActivelyWorking();
}
