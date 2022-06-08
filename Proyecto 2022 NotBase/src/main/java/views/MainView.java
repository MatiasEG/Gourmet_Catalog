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
    private String lastError;

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

    // TODO que hace este metodo?
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
    public void notifyInfo(String info){
        JOptionPane.showMessageDialog(null, info, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void notifyError(String error){
        lastError = error;
        JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public String getLastError(){
        return lastError;
    }

    @Override
    public void showView(){
        mainFrame.setVisible(true);
    }

}
