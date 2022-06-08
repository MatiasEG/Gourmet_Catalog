package views;

import presenter.ISearchPresenter;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SearchView implements ISearchView {
    private JPanel searchInWikipediaPanel;
    private JScrollPane wikipediaArticleContentScrollPane;
    private JTextPane wikipediaArticleContentTextPane;
    private JTextField searchTextField;
    private JRadioButton fullArticleRadioButton;
    private JRadioButton articleSummaryRadioButton;
    private JButton saveLocallyButton;
    private int selectedSearchResultIndex;
    private JPopupMenu searchOptionsMenu;

    private ISearchPresenter searchPresenter;

    public SearchView(ISearchPresenter searchPresenter){
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
        searchTextField.addActionListener(actionEvent -> searchPresenter.onEventSearchArticles());
        saveLocallyButton.addActionListener(actionEvent -> searchPresenter.onEventSaveArticle());
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
        searchOptionsMenu = new JPopupMenu("Search Results");
        selectedSearchResultIndex = -1;
        int searchResultIndex = 0;
        for(String searchResult : searchResults){
            addMenuItem(searchResult, searchResultIndex);
            ++searchResultIndex;
        }
        searchOptionsMenu.show(searchTextField, searchTextField.getX(), searchTextField.getY());
    }

    private void addMenuItem(String searchResult, int searchResultIndex) {
        JMenuItem searchResultMenuItem = new JMenuItem(searchResult);
        searchOptionsMenu.add(searchResultMenuItem);
        searchResultMenuItem.addActionListener(actionEvent -> {
            selectedSearchResultIndex = searchResultIndex;
            searchPresenter.onEventSelectArticle();
        });
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
