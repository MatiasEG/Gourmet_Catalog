package views;

import presenter.SearchPresenterInterface;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SearchView implements SearchViewInterface{
    private JPanel searchInWikipediaPanel;
    private JScrollPane articleContentScrollPane;
    private JTextPane articleContentTextPane;
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
    @Override
    public JPanel getPanel(){
        return searchInWikipediaPanel;
    }

    private void initView(){
        initTextPanes();
        initRadioButtons();
    }

    private void initListeners(){
        searchTextField.addActionListener(actionEvent -> searchPresenter.onEventSearchWikipediaArticle());
        saveLocallyButton.addActionListener(actionEvent -> searchPresenter.onEventSaveWikipediaArticle());
    }

    private void initTextPanes() {
        articleContentTextPane.setContentType("text/html");
        articleContentTextPane.setEditable(false);
    }

    private void initRadioButtons(){
        fullArticleRadioButton.setText("Full Article");
        articleSummaryRadioButton.setText("Article Summary");
        fullArticleRadioButton.setSelected(true);
        ButtonGroup group = new ButtonGroup();
        group.add(fullArticleRadioButton);
        group.add(articleSummaryRadioButton);
    }

    public String getSearchText() {
        return searchTextField.getText();
    }

    public int getSelectedSearchResultIndex(){
        return selectedSearchResultIndex;
    }

    public void setSearchResultsList(List<String> searchResults) {
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

    public boolean fullArticleIsSelected(){ return fullArticleRadioButton.isSelected(); }

    public void setArticleContent(String contentText) {
        articleContentTextPane.setText(contentText);
        articleContentTextPane.setCaretPosition(0);
    }

    public void startWorkingStatus() {
        for(Component c: this.searchInWikipediaPanel.getComponents()) c.setEnabled(false);
        articleContentTextPane.setEnabled(false);
    }

    public void stopWorkingStatus() {
        for(Component c: this.searchInWikipediaPanel.getComponents()) c.setEnabled(true);
        articleContentTextPane.setEnabled(true);
    }
}
