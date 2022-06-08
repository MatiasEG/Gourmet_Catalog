package presenter;

import model.storedInfoModel.IStoredInfoModel;
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
            public void didUpdateArticle() { mainView.notifyInfo("Article Updated"); }
            public void didSaveArticle() {
                storedInfoModel.loadStoredArticleTitles();
                mainView.notifyInfo("Article Saved");
            }
            public void didDeleteArticle() {
                storedInfoModel.loadStoredArticleTitles();
                mainView.notifyInfo("Article Deleted");
            }
        });

        storedInfoModel.addErrorListener(errorMessage -> mainView.notifyError(errorMessage));
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
