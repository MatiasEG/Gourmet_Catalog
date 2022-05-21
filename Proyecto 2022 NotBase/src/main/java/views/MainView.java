package views;

import dyds.gourmetCatalog.fulllogic.MainWindow;
import presenter.GourmetCatalogPresenterInterface;

import javax.swing.*;
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
    private GourmetCatalogPresenterInterface gourmetCatalogPresenter;

    public MainView(GourmetCatalogPresenterInterface gourmetCatalogPresenter){
        this.gourmetCatalogPresenter = gourmetCatalogPresenter;

        mainFrame = new JFrame("Gourmet Catalog");
        mainFrame.setContentPane(contentPane);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.pack();
    }

    @Override
    public String getSearchField() {
        //TODO
        return null;
    }

    @Override
    public int getIndexOfSelectedSearchResult() {
        //TODO
        return 0;
    }

    @Override
    public int getIndexOfSelectedLocalCopy() {
        //TODO
        return 0;
    }

    @Override
    public String getContentTextOfLocalCopy() {
        //TODO
        return null;
    }

    @Override
    public void setListOfLocalCopies(List<String> localCopies) {
        //TODO
    }

    @Override
    public void setContentTextOfLocalCopy(String contentText) {
        //TODO
    }

    @Override
    public void setListOfSearchResults(List<String> searchResults) {
        //TODO
    }

    @Override
    public void setContentTextOfSearchResult(String contentText) {
        //TODO
    }

    @Override
    public void showView(){
        mainFrame.setVisible(true);
        //TODO
    }
}
