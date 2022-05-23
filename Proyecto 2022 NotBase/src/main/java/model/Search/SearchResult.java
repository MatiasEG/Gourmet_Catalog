package model.Search;

public class SearchResult {
    private String title;
    private String pageID;
    private String snippet;

    public SearchResult(String title, String pageID, String snippet) {
        this.title = title;
        this.pageID = pageID;
        this.snippet = snippet;
    }

    public String getTitle() {
        return title;
    }

    public String getPageID() {
        return pageID;
    }

    public String getSnippet() {
        return snippet;
    }
}
