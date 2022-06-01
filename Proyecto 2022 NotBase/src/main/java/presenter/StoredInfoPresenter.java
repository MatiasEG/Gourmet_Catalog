package presenter;

import model.StoredInfoModel.IStoredInfoModel;
import model.listeners.LoadArticleListener;
import model.listeners.StoredArticlesListener;
import views.IMainView;
import views.IStoredInfoView;

public class StoredInfoPresenter implements IStoredInfoPresenter {
    IMainView mainView;
    IStoredInfoView storedInfoView;
    IStoredInfoModel storedInfoModel;

    public StoredInfoPresenter(IStoredInfoModel storedInfoModel){
        this.storedInfoModel = storedInfoModel;
    }

    public void setView(IMainView mainView){
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
