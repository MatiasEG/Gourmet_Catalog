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
    private List<String> searchResults;
    private IMainView mainView;

    public SearchView(ISearchPresenter searchPresenter, IMainView mainView){
        this.searchPresenter = searchPresenter;
        this.mainView = mainView;
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
    public void setSearchResultsList(List<String> searchResults) {
        this.searchResults = searchResults;
        searchOptionsMenu = new JPopupMenu("Search Results");
        selectedSearchResultIndex = -1;
        int searchResultIndex = 0;
        for(String searchResult : searchResults){
            addSearchResultMenuItem(searchResult, searchResultIndex);
            ++searchResultIndex;
        }
        if(mainView.isVisible())
            searchOptionsMenu.show(searchTextField, searchTextField.getX(), searchTextField.getY());
    }

    private void addSearchResultMenuItem(String searchResult, int searchResultIndex) {
        JMenuItem searchResultMenuItem = new JMenuItem(searchResult);
        searchOptionsMenu.add(searchResultMenuItem);
        searchResultMenuItem.addActionListener(actionEvent -> {
            selectedSearchResultIndex = searchResultIndex;
            searchPresenter.onEventSelectArticle();
        });
    }

    @Override
    public void setArticleContent(String contentText) {
        wikipediaArticleContentTextPane.setText(contentText);
        wikipediaArticleContentTextPane.setCaretPosition(0);
    }

    @Override
    public void setSearchText(String searchText){
        searchTextField.setText(searchText);
    }

    @Override
    public void setSelectedSearchResultIndex(int selectedSearchResultIndex){
        this.selectedSearchResultIndex = selectedSearchResultIndex;
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
    public String getArticleContent(){
        return wikipediaArticleContentTextPane.getText();
    }

    @Override
    public List<String> getSearchResults(){
        return searchResults;
    }

    @Override
    public void selectFullArticleOption(){
        fullArticleRadioButton.setSelected(true);
    }

    @Override
    public void selectArticleSummaryOption(){
        articleSummaryRadioButton.setSelected(true);
    }


    @Override
    public boolean fullArticleIsSelected(){ return fullArticleRadioButton.isSelected(); }

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
