package presenter;

import model.StoredInfoModel.StoredInfoModelInterface;
import model.listeners.StoredArticlesListener;
import views.MainView;
import views.MainViewInterface;
import views.StoredInfoView;

public class StoredInfoPresenter implements StoredInfoPresenterInterface {
    MainViewInterface mainView;
    StoredInfoView storedInfoView;
    StoredInfoModelInterface storedInfoModel;

    public StoredInfoPresenter(StoredInfoModelInterface storedInfoModel){
        this.storedInfoModel = storedInfoModel;
    }

    public void setUp(MainView mainView){
        this.mainView = mainView;
        this.storedInfoView = mainView.getStoredInfoView();
        this.storedInfoView.setStoredArticlesTitles(this.storedInfoModel.getTitlesOfStoredArticles());
        initListeners();
    }

    private void initListeners(){
        storedInfoModel.addLoadArticleListener(() -> storedInfoView.setStoredArticleContentText(storedInfoModel.getSelectedStoredArticleContent()));

        storedInfoModel.addStoredArticlesListener(new StoredArticlesListener() {
            @Override
            public void didUpdateArticle() {
                notifyInfoToUser("Article Updated");
            }

            @Override
            public void didSaveArticle() {
                storedInfoView.setStoredArticlesTitles(storedInfoModel.getTitlesOfStoredArticles());
                storedInfoView.clearStoredArticleView();
                notifyInfoToUser("Article Saved");
            }

            @Override
            public void didDeleteArticle() {
                storedInfoView.setStoredArticlesTitles(storedInfoModel.getTitlesOfStoredArticles());
                storedInfoView.clearStoredArticleView();
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
    public void onEventSelectStoredArticle() {
        String storedArticleTitle = storedInfoView.getSelectedStoredArticleTitle();
        storedInfoModel.selectStoredArticle(storedArticleTitle);
    }

    @Override
    public void onEvenDeleteStoredArticle() {
        String storedArticleTitle = storedInfoView.getSelectedStoredArticleTitle();
        storedInfoModel.deleteArticle(storedArticleTitle);
    }

    @Override
    public void onEventUpdateStoredArticle() {
        String storedArticleTitle = storedInfoView.getSelectedStoredArticleTitle();
        String storedArticleContent = storedInfoView.getStoredArticleContentText();
        storedInfoModel.updateArticle(storedArticleTitle, storedArticleContent);
    }

}
