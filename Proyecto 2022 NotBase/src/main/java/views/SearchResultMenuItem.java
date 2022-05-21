package views;

import model.SearchResult;

import javax.swing.*;

public class SearchResultMenuItem extends JMenuItem {
    private SearchResult searchResult;

    public SearchResultMenuItem(SearchResult searchResult) {
        this.searchResult = searchResult;
        String itemText = searchResult.getTitle() + ": " + searchResult.getSnippet();
        itemText =itemText.replace("<span class=\"searchmatch\">", "")
                .replace("</span>", "");
        this.setText(itemText);
    }

    public SearchResult getSearchResult() {
        return searchResult;
    }
}
