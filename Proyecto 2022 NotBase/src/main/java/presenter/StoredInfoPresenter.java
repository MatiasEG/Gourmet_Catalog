package presenter;

import model.StoredInfoModel.StoredInfoModelInterface;
import model.listeners.LoadArticleListener;
import model.listeners.StoredArticlesListener;
import views.MainView;
import views.MainViewInterface;
import views.StoredInfoViewInterface;

public class StoredInfoPresenter implements StoredInfoPresenterInterface {
    MainViewInterface mainView;
    StoredInfoViewInterface storedInfoView;
    StoredInfoModelInterface storedInfoModel;

    public StoredInfoPresenter(StoredInfoModelInterface storedInfoModel){
        this.storedInfoModel = storedInfoModel;
    }

    public void setView(MainView mainView){
        this.mainView = mainView;
        this.storedInfoView = mainView.getStoredInfoView();
        initListeners();
        storedInfoModel.loadStoredArticleTitles();
    }

    private void initListeners(){
        storedInfoModel.addLoadArticleListener(new LoadArticleListener() {
            public void didLoadTitles() {
                Object[] storedArticleTitles = storedInfoModel.getStoredArticleTitles();
                storedInfoView.setStoredArticlesTitles(storedArticleTitles);
                storedInfoView.clearView();
            }
            public void didLoadArticleContent() {
                String articleContent = storedInfoModel.getLoadedArticleContent();
                storedInfoView.setArticleContent(articleContent);
            }
        });

        storedInfoModel.addStoredArticlesListener(new StoredArticlesListener() {
            public void didUpdateArticle() { notifyInfoToUser("Article Updated"); }
            public void didSaveArticle() {
                storedInfoModel.loadStoredArticleTitles();
                notifyInfoToUser("Article Saved");
            }
            public void didDeleteArticle() {
                storedInfoModel.loadStoredArticleTitles();
                notifyInfoToUser("Article Deleted");
            }
        });

        storedInfoModel.addErrorListener(errorMessage -> notifyErrorToUser(errorMessage));
    }

    private void notifyInfoToUser(String message){
        mainView.notifyMessageToUser(message, "Info");
    }

    private void notifyErrorToUser(String message){
        mainView.notifyMessageToUser(message, "Error");
    }

    @Override
    public void onEventSelectArticle() {
        String storedArticleTitle = storedInfoView.getSelectedArticleTitle();
        storedInfoModel.loadArticle(storedArticleTitle);
    }

    @Override
    public void onEvenDeleteArticle() {
        String storedArticleTitle = storedInfoView.getSelectedArticleTitle();
        storedInfoModel.deleteArticle(storedArticleTitle);
    }

    @Override
    public void onEventUpdateArticle() {
        String storedArticleTitle = storedInfoView.getSelectedArticleTitle();
        String storedArticleContent = storedInfoView.getArticleContent();
        storedInfoModel.updateArticle(storedArticleTitle, storedArticleContent);
    }

}
