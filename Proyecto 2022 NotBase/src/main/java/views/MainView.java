package views;

import model.Search.SearchResult;
import presenter.GourmetCatalogPresenterInterface;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainView implements  MainViewInterface{

    private JTextField searchTextField;
    private JPanel mainPanel;
    private JTextPane wikipediaArticleTextPane;
    private JButton saveLocallyButton;
    private JTabbedPane mainTabbedPane;
    private JPanel searchInWikipediaPanel;
    private JPanel storedInfoPanel;
    private JComboBox<Object> storedArticlesComboBox;
    private JTextPane storedArticleTextPane;
    private JRadioButton completeArticleRadioButton;
    private JRadioButton firstTermArticleRadioButton;
    private JScrollPane storedArticleScrollPane;
    private JScrollPane wikipediaArticleScrollPane;
    private JFrame mainFrame;
    private JPopupMenu storedInfoPopupMenu;
    private JMenuItem deleteMenuItem;
    private JMenuItem saveMenuItem;
    private GourmetCatalogPresenterInterface gourmetCatalogPresenter;
    private SearchResult selectedSearchResult;

    public MainView(GourmetCatalogPresenterInterface gourmetCatalogPresenter){
        this.gourmetCatalogPresenter = gourmetCatalogPresenter;
        initView();
        initListeners();
    }

    private void initView(){
        initMainFrame();
        initTextPanes();
        initPopupMenu();
        initRadioButtons();
    }

    private void initRadioButtons(){
        completeArticleRadioButton.setText("Articulo completo");
        firstTermArticleRadioButton.setText("Primer parrafo");
        ButtonGroup group = new ButtonGroup();
        group.add(completeArticleRadioButton);
        group.add(firstTermArticleRadioButton);
    }

    private void initMainFrame() {
        mainFrame = new JFrame("Gourmet Catalog");
        mainFrame.setContentPane(mainPanel);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.pack();
    }

    private void initTextPanes() {
        wikipediaArticleTextPane.setContentType("text/html");
        wikipediaArticleTextPane.setEditable(false);
        storedArticleTextPane.setContentType("text/html");
    }

    private void initPopupMenu() {
        storedInfoPopupMenu = new JPopupMenu();
        deleteMenuItem = new JMenuItem("Delete!");
        saveMenuItem = new JMenuItem("Save Changes!");
        storedInfoPopupMenu.add(deleteMenuItem);
        storedInfoPopupMenu.add(saveMenuItem);
        storedArticleTextPane.setComponentPopupMenu(storedInfoPopupMenu);
    }

    private void initListeners(){
        searchTextField.addActionListener(actionEvent -> gourmetCatalogPresenter.onEventSearchWikipediaArticle());
        saveLocallyButton.addActionListener(actionEvent -> gourmetCatalogPresenter.onEventSaveWikipediaArticle());
        storedArticlesComboBox.addActionListener(actionEvent -> gourmetCatalogPresenter.onEventSelectStoredArticle());
        deleteMenuItem.addActionListener(actionEvent -> gourmetCatalogPresenter.onEvenDeleteStoredArticle());
        saveMenuItem.addActionListener(actionEvent -> gourmetCatalogPresenter.onEventUpdateStoredArticle());
    }

    @Override
    public String getSearchText() {
        return searchTextField.getText();
    }

    @Override
    public SearchResult getSelectedSearchResult() {
        return selectedSearchResult;
    }

    @Override
    public void clearStoredArticleView(){
        storedArticlesComboBox.setSelectedIndex(-1);
        storedArticleTextPane.setText("");
    }

    @Override
    public String getSelectedStoredArticleTitle(){ return storedArticlesComboBox.getSelectedIndex() > -1 ? storedArticlesComboBox.getSelectedItem().toString() : ""; }

    @Override
    public String getStoredArticleContentText() {
        return storedArticleTextPane.getText();
    }

    @Override
    public void setStoredArticlesTitles(Object[] storedArticlesTitles) {
        storedArticlesComboBox.setModel(new DefaultComboBoxModel<Object>(storedArticlesTitles));
    }

    @Override
    public void setStoredArticleContentText(String contentText) {
        storedArticleTextPane.setText(contentText);
        storedArticleTextPane.setCaretPosition(0);
    }

    @Override
    public void setSearchResultsList(List<SearchResult> searchResults) {
        JPopupMenu searchOptionsMenu = new JPopupMenu("Search Results");
        for(SearchResult searchResult : searchResults){
            SearchResultMenuItem searchResultMenuItem = new SearchResultMenuItem(searchResult);
            searchOptionsMenu.add(searchResultMenuItem);
            searchResultMenuItem.addActionListener(actionEvent -> {
                selectedSearchResult = searchResult;
                gourmetCatalogPresenter.onEventSelectWikipediaArticle();
                saveLocallyButton.setEnabled(true);
            });
        }
        searchOptionsMenu.show(searchTextField, searchTextField.getX(), searchTextField.getY());
    }

    @Override
    public void setContentTextOfSearchResult(String contentText) {
        wikipediaArticleTextPane.setText(contentText);
        wikipediaArticleTextPane.setCaretPosition(0);
    }

    @Override
    public boolean completeArticleIsSelected(){ return completeArticleRadioButton.isSelected(); }

    @Override
    public void startWorkingStatus() {
        for(Component c: this.searchInWikipediaPanel.getComponents()) c.setEnabled(false);
        wikipediaArticleTextPane.setEnabled(false);
    }

    @Override
    public void stopWorkingStatus() {
        for(Component c: this.searchInWikipediaPanel.getComponents()) c.setEnabled(true);
        wikipediaArticleTextPane.setEnabled(true);
    }

    @Override
    public void showView(){
        mainFrame.setVisible(true);
    }
}
