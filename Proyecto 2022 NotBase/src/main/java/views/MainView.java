package views;

import presenter.ISearchPresenter;
import presenter.IStoredInfoPresenter;

import javax.swing.*;

public class MainView implements IMainView {

    private JTabbedPane mainTabbedPane;
    private JPanel searchInWikipediaPanel;
    private JPanel storedInfoPanel;
    private JPanel mainPanel;
    private JFrame mainFrame;
    private IStoredInfoPresenter storedInfoPresenter;
    private ISearchPresenter searchPresenter;
    private ISearchView searchView;
    private IStoredInfoView storedInfoView;

    public MainView(IStoredInfoPresenter storedInfoPresenter, ISearchPresenter searchPresenter){
        this.storedInfoPresenter = storedInfoPresenter;
        this.searchPresenter = searchPresenter;
        initView();
    }

    private void initView(){
        initMainFrame();
    }
    //TODO estos dos tal vez se podrian juntar en uno
    private void initMainFrame() {
        mainFrame = new JFrame("Gourmet Catalog");
        mainFrame.setContentPane(mainPanel);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.pack();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.storedInfoView = new StoredInfoView(storedInfoPresenter);
        this.storedInfoPanel = storedInfoView.getPanel();

        this.searchView = new SearchView(searchPresenter);
        this.searchInWikipediaPanel = searchView.getPanel();
    }

    @Override
    public IStoredInfoView getStoredInfoView(){
        return storedInfoView;
    }

    @Override
    public ISearchView getSearchView(){
        return searchView;
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
