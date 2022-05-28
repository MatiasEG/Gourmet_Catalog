package views;

import presenter.SearchPresenterInterface;
import presenter.StoredInfoPresenterInterface;

import javax.swing.*;

public class MainView implements  MainViewInterface{

    private JTabbedPane mainTabbedPane;
    private JPanel searchInWikipediaPanel;
    private JPanel storedInfoPanel;
    private JPanel mainPanel;
    private JFrame mainFrame;
    private StoredInfoPresenterInterface storedInfoViewPresenter;
    private SearchPresenterInterface searchViewPresenter;
    private SearchView searchView;
    private StoredInfoView storedInfoView;

    public MainView(StoredInfoPresenterInterface storedInfoViewPresenter, SearchPresenterInterface searchViewPresenter){
        this.storedInfoViewPresenter = storedInfoViewPresenter;
        this.searchViewPresenter = searchViewPresenter;
        initView();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.storedInfoView = new StoredInfoView(storedInfoViewPresenter);
        this.storedInfoPanel = storedInfoView.getPanel();

        this.searchView = new SearchView(searchViewPresenter);
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
