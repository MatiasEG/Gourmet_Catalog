package model.StoredInfoModel;

import model.listeners.ErrorListener;
import model.listeners.LoadArticleListener;
import model.listeners.StoredArticlesListener;

import java.util.ArrayList;
import java.util.List;

public class StoredInfoModel implements StoredInfoModelInterface {

    private String loadedArticleContent;
    private List<LoadArticleListener> loadArticleListeners = new ArrayList<>();
    private List<StoredArticlesListener> storedArticlesListeners = new ArrayList<>();
    private List<ErrorListener> errorListeners = new ArrayList<>();

    public StoredInfoModel(){
        DataBase.loadDatabase();
    }


    @Override
    public void addStoredArticlesListener(StoredArticlesListener storedArticlesListener) {
        storedArticlesListeners.add(storedArticlesListener);
    }

    @Override
    public void addLoadArticleListener(LoadArticleListener loadArticleListener) {
        loadArticleListeners.add(loadArticleListener);
    }

    @Override
    public void addErrorListener(ErrorListener errorListener){
        errorListeners.add(errorListener);
    }

    private void notifyUpdateArticle(){
        for(StoredArticlesListener storedArticlesListener : storedArticlesListeners)
            storedArticlesListener.didUpdateArticle();
    }

    private void notifySaveArticle(){
        for(StoredArticlesListener storedArticlesListener : storedArticlesListeners)
            storedArticlesListener.didSaveArticle();
    }

    private void notifyDeleteArticle(){
        for(StoredArticlesListener storedArticlesListener : storedArticlesListeners)
            storedArticlesListener.didDeleteArticle();
    }

    private void notifyLoadArticle(){
        for(LoadArticleListener loadArticleListener : loadArticleListeners)
            loadArticleListener.didLoadArticle();
    }

    private void notifyErrorOccurred(String errorMessage){
        for(ErrorListener errorListener : errorListeners)
            errorListener.didErrorOccurred(errorMessage);
    }

    @Override
    public void deleteArticle(String articleTitle) {
        if(isEmptyOrInvalid(articleTitle))
            notifyErrorOccurred("Article Not Selected");
        else {
            DataBase.deleteEntry(articleTitle);
            notifyDeleteArticle();
        }
    }

    private boolean isEmptyOrInvalid(String string){ return string == null || string.equals(""); }

    @Override
    public void saveArticle(String articleTitle, String articleContent) {
        if(isEmptyOrInvalid(articleTitle))
            notifyErrorOccurred("Article Not Selected");
        else {
            DataBase.saveInfo(articleTitle, articleContent);
            notifySaveArticle();
        }
    }

    @Override
    public void updateArticle(String articleTitle, String articleContent) {
        if(isEmptyOrInvalid(articleTitle))
            notifyErrorOccurred("Article Not Selected");
        else {
            DataBase.saveInfo(articleTitle, articleContent);
            notifyUpdateArticle();
        }
    }

    @Override
    public void loadArticle(String articleTitle) {
        if(isEmptyOrInvalid(articleTitle))
            notifyErrorOccurred("Article Not Selected");
        else {
            loadedArticleContent = DataBase.getContent(articleTitle);
            notifyLoadArticle();
        }
    }

    @Override
    public String getLoadedArticleContent() {
        return loadedArticleContent;
    }

    @Override
    public Object[] getStoredArticleTitles(){ return DataBase.getTitles().stream().sorted().toArray(); }
}
