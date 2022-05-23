package model.listeners;

public interface StoredArticlesListener {

    void didUpdateArticle();

    void didSaveArticle();

    void didDeleteArticle();
}
