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
    private StoredInfoPresenterInterface storedInfoPresenter;
    private SearchPresenterInterface searchPresenter;
    private SearchViewInterface searchView;
    private StoredInfoViewInterface storedInfoView;

    public MainView(StoredInfoPresenterInterface storedInfoPresenter, SearchPresenterInterface searchPresenter){
        this.storedInfoPresenter = storedInfoPresenter;
        this.searchPresenter = searchPresenter;
        initView();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.storedInfoView = new StoredInfoView(storedInfoPresenter);
        this.storedInfoPanel = storedInfoView.getPanel();

        this.searchView = new SearchView(searchPresenter);
        this.searchInWikipediaPanel = searchView.getPanel();
    }

    @Override
    public StoredInfoViewInterface getStoredInfoView(){
        return storedInfoView;
    }

    @Override
    public SearchViewInterface getSearchView(){
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
