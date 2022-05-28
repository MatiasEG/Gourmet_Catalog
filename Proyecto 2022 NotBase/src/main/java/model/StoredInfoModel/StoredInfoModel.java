package model.StoredInfoModel;

import model.listeners.ErrorListener;
import model.listeners.LoadArticleListener;
import model.listeners.StoredArticlesListener;

import java.util.ArrayList;
import java.util.List;

public class StoredInfoModel implements StoredInfoModelInterface {

    private String articleContent;
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
        if(isValidString(articleTitle)) {
            DataBase.deleteEntry(articleTitle);
            notifyDeleteArticle();
        } else
            notifyErrorOccurred("Article Not Selected");
    }

    private boolean isValidString(String string){ return string != null && !string.equals(""); }

    @Override
    public void saveArticle(String articleTitle, String articleContent) {
        DataBase.saveInfo(articleTitle, articleContent);
        notifySaveArticle();
    }

    @Override
    public void updateArticle(String articleTitle, String articleContent) {
        if(isValidString(articleTitle)) {
            DataBase.saveInfo(articleTitle, articleContent);
            notifyUpdateArticle();
        } else
            notifyErrorOccurred("Article Not Selected");
    }

    @Override
    public void selectStoredArticle(String articleTitle) {
        if(isValidString(articleTitle)) {
            articleContent = DataBase.getContent(articleTitle);
            notifyLoadArticle();
        } else
            notifyErrorOccurred("Article Not Selected");
    }

    @Override
    public String getSelectedStoredArticleContent() {
        return articleContent;
    }

    @Override
    public Object[] getTitlesOfStoredArticles(){ return DataBase.getTitles().stream().sorted().toArray(); }
}
