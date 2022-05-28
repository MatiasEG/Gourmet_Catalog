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
        storedInfoModel.addLoadArticleListener(() -> mainView.getStoredInfoView().setStoredArticleContentText(storedInfoModel.getSelectedStoredArticleContent()));

        storedInfoModel.addStoredArticlesListener(new StoredArticlesListener() {
            @Override
            public void didUpdateArticle() {
                notifyInfoToUser("Article Updated");
            }

            @Override
            public void didSaveArticle() {
                mainView.getStoredInfoView().setStoredArticlesTitles(storedInfoModel.getTitlesOfStoredArticles());
                mainView.getStoredInfoView().clearStoredArticleView();
                notifyInfoToUser("Article Saved");
            }

            @Override
            public void didDeleteArticle() {
                mainView.getStoredInfoView().setStoredArticlesTitles(storedInfoModel.getTitlesOfStoredArticles());
                mainView.getStoredInfoView().clearStoredArticleView();
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
        String storedArticleTitle = mainView.getStoredInfoView().getSelectedStoredArticleTitle();
        storedInfoModel.selectStoredArticle(storedArticleTitle);
    }

    @Override
    public void onEvenDeleteStoredArticle() {
        String storedArticleTitle = mainView.getStoredInfoView().getSelectedStoredArticleTitle();
        storedInfoModel.deleteArticle(storedArticleTitle);
    }

    @Override
    public void onEventUpdateStoredArticle() {
        String storedArticleTitle = mainView.getStoredInfoView().getSelectedStoredArticleTitle();
        String storedArticleContent = mainView.getStoredInfoView().getStoredArticleContentText();
        storedInfoModel.updateArticle(storedArticleTitle, storedArticleContent);
    }

}
