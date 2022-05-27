package presenter;

import model.GourmetCatalogModelInterface;
import model.listeners.StoredArticlesListener;
import views.MainView;
import views.MainViewInterface;
import views.StoredInfoView;

public class GourmetCatalogStoredInfoViewPresenter implements GourmetCatalogStoredInfoViewPresenterInterface {
    MainViewInterface mainView;
    StoredInfoView storedInfoView;
    GourmetCatalogModelInterface gourmetCatalogModel;

    public GourmetCatalogStoredInfoViewPresenter(GourmetCatalogModelInterface gourmetCatalogModel){
        this.gourmetCatalogModel = gourmetCatalogModel;
    }

    public void setUp(MainView mainView){
        this.mainView = mainView;
        this.storedInfoView = mainView.getStoredInfoView();
        this.storedInfoView.setStoredArticlesTitles(this.gourmetCatalogModel.getTitlesOfStoredArticles());
        initListeners();
    }

    private void initListeners(){
        gourmetCatalogModel.addLoadArticleListener(() -> mainView.getStoredInfoView().setStoredArticleContentText(gourmetCatalogModel.getSelectedStoredArticleContent()));

        gourmetCatalogModel.addStoredArticlesListener(new StoredArticlesListener() {
            @Override
            public void didUpdateArticle() {
                notifyInfoToUser("Article Updated");
            }

            @Override
            public void didSaveArticle() {
                mainView.getStoredInfoView().setStoredArticlesTitles(gourmetCatalogModel.getTitlesOfStoredArticles());
                mainView.getStoredInfoView().clearStoredArticleView();
                notifyInfoToUser("Article Saved");
            }

            @Override
            public void didDeleteArticle() {
                mainView.getStoredInfoView().setStoredArticlesTitles(gourmetCatalogModel.getTitlesOfStoredArticles());
                mainView.getStoredInfoView().clearStoredArticleView();
                notifyInfoToUser("Article Deleted");
            }
        });

        gourmetCatalogModel.addErrorListener(errorMessage -> notifyErrorToUser(errorMessage));
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
        gourmetCatalogModel.selectStoredArticle(storedArticleTitle);
    }

    @Override
    public void onEvenDeleteStoredArticle() {
        String storedArticleTitle = mainView.getStoredInfoView().getSelectedStoredArticleTitle();
        gourmetCatalogModel.deleteArticle(storedArticleTitle);
    }

    @Override
    public void onEventUpdateStoredArticle() {
        String storedArticleTitle = mainView.getStoredInfoView().getSelectedStoredArticleTitle();
        String storedArticleContent = mainView.getStoredInfoView().getStoredArticleContentText();
        gourmetCatalogModel.updateArticle(storedArticleTitle, storedArticleContent);
    }

    public void showView(){
        storedInfoView.showView();
    }

}
