package model.storedInfoModel;

import model.listeners.ErrorListener;
import model.listeners.LoadArticleListener;
import model.listeners.StoredArticlesListener;

import java.util.ArrayList;
import java.util.List;

public class StoredInfoModel implements IStoredInfoModel {

    private String loadedArticleContent;
    private Object[] storedArticleTitles;
    private List<LoadArticleListener> loadArticleListeners = new ArrayList<>();
    private List<StoredArticlesListener> storedArticlesListeners = new ArrayList<>();
    private List<ErrorListener> errorListeners = new ArrayList<>();

    public StoredInfoModel(){
        DataBase.createDatabaseIfDoesNotExists();
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
            loadArticleListener.didLoadArticleContent();
    }

    private void notifyLoadTitles(){
        for(LoadArticleListener loadArticleListener : loadArticleListeners)
            loadArticleListener.didLoadTitles();
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
            try {
                DataBase.deleteArticle(articleTitle);
                notifyDeleteArticle();
            } catch (Exception e) {
                notifyErrorOccurred("Database Error");
            }
        }
    }

    private boolean isEmptyOrInvalid(String string){ return string == null || string.equals(""); }

    @Override
    public void saveArticle(String articleTitle, String articleContent) {
        if(isEmptyOrInvalid(articleTitle))
            notifyErrorOccurred("Article Not Selected");
        else {
            try {
                DataBase.saveArticle(articleTitle, articleContent);
                notifySaveArticle();
            } catch (Exception e) {
                notifyErrorOccurred("Database Error");
            }
        }
    }

    @Override
    public void updateArticle(String articleTitle, String articleContent) {
        if(isEmptyOrInvalid(articleTitle))
            notifyErrorOccurred("Article Not Selected");
        else {
            try {
                DataBase.saveArticle(articleTitle, articleContent);
                notifyUpdateArticle();
            } catch (Exception e) {
                notifyErrorOccurred("Database Error");
            }
        }
    }

    @Override
    public void loadArticle(String articleTitle) {
        if(isEmptyOrInvalid(articleTitle))
            notifyErrorOccurred("Article Not Selected");
        else {
            try {
                loadedArticleContent = DataBase.getArticleContent(articleTitle);
                notifyLoadArticle();
            } catch (Exception e) {
                notifyErrorOccurred("Database Error");
            }
        }
    }

    @Override
    public String getLoadedArticleContent() {
        return loadedArticleContent;
    }

    @Override
    public void loadStoredArticleTitles(){
        try {
            storedArticleTitles = DataBase.getAllArticleTitles().stream().sorted().toArray();
            notifyLoadTitles();
        } catch (Exception e) {
            notifyErrorOccurred("Database Error");
        }
    }

    @Override
    public Object[] getStoredArticleTitles(){
        return storedArticleTitles;
    }
}
