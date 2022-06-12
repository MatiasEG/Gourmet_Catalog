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
    private boolean visible;

    public MainView(IStoredInfoPresenter storedInfoPresenter, ISearchPresenter searchPresenter){
        this.storedInfoPresenter = storedInfoPresenter;
        this.searchPresenter = searchPresenter;
        initView();
        visible = false;
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

    private void createUIComponents() {
        this.storedInfoView = new StoredInfoView(storedInfoPresenter);
        this.storedInfoPanel = storedInfoView.getPanel();

        this.searchView = new SearchView(searchPresenter, this);
        this.searchInWikipediaPanel = searchView.getPanel();
    }

    @Override
    public boolean isVisible() {
        return visible;
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
        if(visible)
            JOptionPane.showMessageDialog(null, info, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void notifyError(String error){
        lastError = error;
        if(visible)
            JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public String getLastError(){
        return lastError;
    }

    @Override
    public void showView(){
        mainFrame.setVisible(true);
        visible = true;
    }

}
