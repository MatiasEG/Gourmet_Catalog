package views;

import presenter.GourmetCatalogPresenterInterface;

import javax.swing.*;

public class MainView implements  MainViewInterface{

    private JTabbedPane mainTabbedPane;
    private JPanel searchInWikipediaPanel;
    private JPanel storedInfoPanel;
    private JPanel mainPanel;
    private JFrame mainFrame;
    private GourmetCatalogPresenterInterface gourmetCatalogPresenter;
    private SearchView searchView;
    private StoredInfoView storedInfoView;

    public MainView(GourmetCatalogPresenterInterface gourmetCatalogPresenter){
        this.gourmetCatalogPresenter = gourmetCatalogPresenter;
        initView();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.storedInfoView = new StoredInfoView(gourmetCatalogPresenter);
        this.storedInfoPanel = storedInfoView.getPanel();

        this.searchView = new SearchView(gourmetCatalogPresenter);
        this.searchInWikipediaPanel = searchView.getPanel();
    }

    @Override
    public StoredInfoView getStoredInfoView(){
        return storedInfoView;
    }

    @Override
    public SearchView getSearchView(){
        return searchView;
    }

    private void initView(){
        initMainFrame();
    }

    private void initMainFrame() {
        mainFrame = new JFrame("Gourmet Catalog");
        mainFrame.setContentPane(mainPanel);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.pack();
    }


    @Override
    public void notifyMessageToUser(String msg, String title){
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showView(){
        mainFrame.setVisible(true);
    }

}
