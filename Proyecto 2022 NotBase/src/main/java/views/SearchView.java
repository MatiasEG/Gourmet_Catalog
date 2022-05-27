package views;

import presenter.GourmetCatalogPresenterInterface;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SearchView implements SearchViewInterface{
    private JPanel searchInWikipediaPanel;
    private JScrollPane wikipediaArticleScrollPane;
    private JTextPane wikipediaArticleTextPane;
    private JTextField searchTextField;
    private JRadioButton completeArticleRadioButton;
    private JRadioButton articleExtractRadioButton;
    private JButton saveLocallyButton;
    private int indexOfSelectedSearchResult;

    private GourmetCatalogPresenterInterface gourmetCatalogPresenter;

    public SearchView(GourmetCatalogPresenterInterface gourmetCatalogPresenter){
        this.gourmetCatalogPresenter = gourmetCatalogPresenter;
        initView();
        initListeners();
    }

    public JPanel getPanel(){
        return searchInWikipediaPanel;
    }

    private void initView(){
        initTextPanes();
        initRadioButtons();
    }

    private void initListeners(){
        searchTextField.addActionListener(actionEvent -> gourmetCatalogPresenter.onEventSearchWikipediaArticle());
        saveLocallyButton.addActionListener(actionEvent -> gourmetCatalogPresenter.onEventSaveWikipediaArticle());
    }

    private void initTextPanes() {
        wikipediaArticleTextPane.setContentType("text/html");
        wikipediaArticleTextPane.setEditable(false);
    }

    private void initRadioButtons(){
        completeArticleRadioButton.setText("Complete Article");
        articleExtractRadioButton.setText("Article Extract");
        completeArticleRadioButton.setSelected(true);
        ButtonGroup group = new ButtonGroup();
        group.add(completeArticleRadioButton);
        group.add(articleExtractRadioButton);
    }

    public String getSearchText() {
        return searchTextField.getText();
    }

    public int getIndexOfSelectedSearchResult(){
        return indexOfSelectedSearchResult;
    }

    public void setSearchResultsList(List<String> searchResults) {
        JPopupMenu searchOptionsMenu = new JPopupMenu("Search Results");
        indexOfSelectedSearchResult= -1;
        int currentIndex = 0;
        for(String searchResult : searchResults){
            JMenuItem searchResultMenuItem = new JMenuItem(searchResult);
            searchOptionsMenu.add(searchResultMenuItem);
            int searchResultIndex = currentIndex;
            searchResultMenuItem.addActionListener(actionEvent -> {
                indexOfSelectedSearchResult= searchResultIndex;
                gourmetCatalogPresenter.onEventSelectWikipediaArticle();
            });
            ++currentIndex;
        }
        searchOptionsMenu.show(searchTextField, searchTextField.getX(), searchTextField.getY());
    }

    public boolean completeArticleIsSelected(){ return completeArticleRadioButton.isSelected(); }

    public void setContentTextOfSearchResult(String contentText) {
        wikipediaArticleTextPane.setText(contentText);
        wikipediaArticleTextPane.setCaretPosition(0);
    }

    public void startWorkingStatus() {
        for(Component c: this.searchInWikipediaPanel.getComponents()) c.setEnabled(false);
        wikipediaArticleTextPane.setEnabled(false);
    }

    public void stopWorkingStatus() {
        for(Component c: this.searchInWikipediaPanel.getComponents()) c.setEnabled(true);
        wikipediaArticleTextPane.setEnabled(true);
    }
}
