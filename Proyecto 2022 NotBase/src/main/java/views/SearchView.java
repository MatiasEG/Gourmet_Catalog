package views;

import presenter.SearchPresenterInterface;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SearchView implements SearchViewInterface{
    private JPanel searchInWikipediaPanel;
    private JScrollPane wikipediaArticleContentScrollPane;
    private JTextPane wikipediaArticleContentTextPane;
    private JTextField searchTextField;
    private JRadioButton fullArticleRadioButton;
    private JRadioButton articleSummaryRadioButton;
    private JButton saveLocallyButton;
    private int selectedSearchResultIndex;

    private SearchPresenterInterface searchPresenter;

    public SearchView(SearchPresenterInterface searchPresenter){
        this.searchPresenter = searchPresenter;
        initView();
        initListeners();
    }

    private void initView(){
        initTextPanes();
        initRadioButtons();
    }

    private void initTextPanes() {
        wikipediaArticleContentTextPane.setContentType("text/html");
        wikipediaArticleContentTextPane.setEditable(false);
    }

    private void initRadioButtons(){
        fullArticleRadioButton.setText("Full Article");
        articleSummaryRadioButton.setText("Article Summary");
        articleSummaryRadioButton.setSelected(true);
        ButtonGroup group = new ButtonGroup();
        group.add(fullArticleRadioButton);
        group.add(articleSummaryRadioButton);
    }

    private void initListeners(){
        searchTextField.addActionListener(actionEvent -> searchPresenter.onEventSearchWikipediaArticle());
        saveLocallyButton.addActionListener(actionEvent -> searchPresenter.onEventSaveWikipediaArticle());
    }

    @Override
    public JPanel getPanel(){
        return searchInWikipediaPanel;
    }

    @Override
    public String getSearchText() {
        return searchTextField.getText();
    }

    @Override
    public int getSelectedSearchResultIndex(){
        return selectedSearchResultIndex;
    }

    @Override
    public void setSearchResultsList(List<String> searchResults) {
        //TODO revisar
        JPopupMenu searchOptionsMenu = new JPopupMenu("Search Results");
        selectedSearchResultIndex = -1;
        int currentIndex = 0;
        for(String searchResult : searchResults){
            JMenuItem searchResultMenuItem = new JMenuItem(searchResult);
            searchOptionsMenu.add(searchResultMenuItem);
            int searchResultIndex = currentIndex;
            searchResultMenuItem.addActionListener(actionEvent -> {
                selectedSearchResultIndex = searchResultIndex;
                searchPresenter.onEventSelectWikipediaArticle();
            });
            ++currentIndex;
        }
        searchOptionsMenu.show(searchTextField, searchTextField.getX(), searchTextField.getY());
    }

    @Override
    public boolean fullArticleIsSelected(){ return fullArticleRadioButton.isSelected(); }

    @Override
    public void setArticleContent(String contentText) {
        wikipediaArticleContentTextPane.setText(contentText);
        wikipediaArticleContentTextPane.setCaretPosition(0);
    }

    @Override
    public void startWorkingStatus() {
        for(Component component: this.searchInWikipediaPanel.getComponents()) component.setEnabled(false);
        wikipediaArticleContentTextPane.setEnabled(false);
    }

    @Override
    public void stopWorkingStatus() {
        for(Component component: this.searchInWikipediaPanel.getComponents()) component.setEnabled(true);
        wikipediaArticleContentTextPane.setEnabled(true);
    }
}
