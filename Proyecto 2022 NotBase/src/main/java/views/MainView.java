package views;

import model.SearchResult;
import presenter.GourmetCatalogPresenterInterface;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainView implements  MainViewInterface{

    private JTextField textField1;
    private JPanel contentPane;
    private JTextPane textPane1;
    private JButton saveLocallyButton;
    private JTabbedPane tabbedPane1;
    private JPanel searchPanel;
    private JPanel storagePanel;
    private JComboBox<Object> comboBox1;
    private JTextPane textPane2;
    private JFrame mainFrame;
    private JPopupMenu storedInfoPopup;
    private JMenuItem deleteItem;
    private JMenuItem saveItem;
    private GourmetCatalogPresenterInterface gourmetCatalogPresenter;
    private SearchResult selectedSearchResult;

    public MainView(GourmetCatalogPresenterInterface gourmetCatalogPresenter){
        this.gourmetCatalogPresenter = gourmetCatalogPresenter;
        initView();
        initListeners();
    }

    private void initView(){
        mainFrame = new JFrame("Gourmet Catalog");
        mainFrame.setContentPane(contentPane);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.pack();

        textPane1.setContentType("text/html");
        textPane2.setContentType("text/html");

        storedInfoPopup = new JPopupMenu();
        deleteItem = new JMenuItem("Delete!");
        saveItem = new JMenuItem("Save Changes!");
        storedInfoPopup.add(deleteItem);
        storedInfoPopup.add(saveItem);
        textPane2.setComponentPopupMenu(storedInfoPopup);
    }

    private void initListeners(){
        textField1.addActionListener(actionEvent -> gourmetCatalogPresenter.onEventSearch());

        saveLocallyButton.addActionListener(actionEvent -> gourmetCatalogPresenter.onEventSaveSearchResult());

        comboBox1.addActionListener(actionEvent -> gourmetCatalogPresenter.onEventSelectLocalCopy());

        deleteItem.addActionListener(actionEvent -> gourmetCatalogPresenter.onEvenDeleteLocalCopy());

        saveItem.addActionListener(actionEvent -> gourmetCatalogPresenter.onEventUpdateLocalCopy());
    }

    @Override
    public String getSearchField() {
        return textField1.getText();
    }

    @Override
    public SearchResult getSelectedSearchResult() {
        return selectedSearchResult;
    }

    @Override
    public int getIndexOfSelectedLocalCopy() {
        return comboBox1.getSelectedIndex();
    }

    @Override
    public String getContentTextOfLocalCopy() {
        return textPane2.getText();
    }

    @Override
    public void setListOfLocalCopies(Object[] localCopies) {
        comboBox1.setModel(new DefaultComboBoxModel<Object>(localCopies));
    }

    @Override
    public void setContentTextOfLocalCopy(String contentText) {
        textPane2.setText(contentText);
    }

    @Override
    public void setListOfSearchResults(List<SearchResult> searchResults) {
        JPopupMenu searchOptionsMenu = new JPopupMenu("Search Results");
        for(SearchResult searchResult : searchResults){
            SearchResultMenuItem searchResultMenuItem = new SearchResultMenuItem(searchResult);
            searchOptionsMenu.add(searchResultMenuItem);
            searchResultMenuItem.addActionListener(actionEvent -> {
                selectedSearchResult = searchResult;
                gourmetCatalogPresenter.onEventSelectSearchResult();
            });
        }
        searchOptionsMenu.show(textField1, textField1.getX(), textField1.getY());
    }

    @Override
    public void setContentTextOfSearchResult(String contentText) {
        textPane1.setText(contentText);
    }

    @Override
    public void setWorkingStatus() {
        for(Component c: this.searchPanel.getComponents()) c.setEnabled(false);
        textPane1.setEnabled(false);
    }

    @Override
    public void setWaitingStatus() {
        for(Component c: this.searchPanel.getComponents()) c.setEnabled(true);
        textPane1.setEnabled(true);
    }

    @Override
    public void showView(){
        mainFrame.setVisible(true);
    }
}
