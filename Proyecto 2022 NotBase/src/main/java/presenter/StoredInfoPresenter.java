package presenter;

import model.StoredInfoModel.StoredInfoModelInterface;
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
        Object[] storedArticleTitles = storedInfoModel.getStoredArticleTitles();
        this.storedInfoView.setStoredArticlesTitles(storedArticleTitles);
        initListeners();
    }

    private void initListeners(){
        storedInfoModel.addLoadArticleListener(() -> {
            String articleContent = storedInfoModel.getLoadedArticleContent();
            storedInfoView.setArticleContent(articleContent);
        });

        storedInfoModel.addStoredArticlesListener(new StoredArticlesListener() {
            @Override
            public void didUpdateArticle() {
                notifyInfoToUser("Article Updated");
            }

            @Override
            public void didSaveArticle() {
                Object[] storedArticleTitles = storedInfoModel.getStoredArticleTitles();
                storedInfoView.setStoredArticlesTitles(storedArticleTitles);
                storedInfoView.clearView();
                notifyInfoToUser("Article Saved");
            }

            @Override
            public void didDeleteArticle() {
                Object[] storedArticleTitles = storedInfoModel.getStoredArticleTitles();
                storedInfoView.setStoredArticlesTitles(storedArticleTitles);
                storedInfoView.clearView();
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
