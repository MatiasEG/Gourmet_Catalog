package views;

import presenter.StoredInfoPresenterInterface;

import javax.swing.*;

public class StoredInfoView implements StoredInfoViewInterface{
    private JComboBox storedArticlesComboBox;
    private JScrollPane storedArticleScrollPane;
    private JTextPane storedArticleTextPane;
    private JPanel storedInfoPanel;
    private JPopupMenu storedInfoPopupMenu;
    private JMenuItem deleteMenuItem;
    private JMenuItem saveMenuItem;

    private StoredInfoPresenterInterface gourmetCatalogPresenter;

    public StoredInfoView(StoredInfoPresenterInterface gourmetCatalogPresenter){
        this.gourmetCatalogPresenter = gourmetCatalogPresenter;
        initView();
        initListeners();
    }

    public JPanel getPanel(){
        return storedInfoPanel;
    }

    private void initView(){
        initTextPanes();
        initPopupMenu();
    }

    private void initListeners(){
        storedArticlesComboBox.addItemListener(actionEvent -> {
            if(storedArticlesComboBox.getSelectedIndex() > -1)
                gourmetCatalogPresenter.onEventSelectStoredArticle();
        });
        deleteMenuItem.addActionListener(actionEvent -> gourmetCatalogPresenter.onEvenDeleteStoredArticle());
        saveMenuItem.addActionListener(actionEvent -> gourmetCatalogPresenter.onEventUpdateStoredArticle());
    }

    private void initTextPanes() {
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

    public void setStoredArticlesTitles(Object[] storedArticlesTitles) {
        storedArticlesComboBox.setModel(new DefaultComboBoxModel<Object>(storedArticlesTitles));
    }

    public void clearStoredArticleView(){
        storedArticlesComboBox.setSelectedIndex(-1);
        storedArticleTextPane.setText("");
    }

    public String getSelectedStoredArticleTitle(){ return storedArticlesComboBox.getSelectedIndex() > -1 ? storedArticlesComboBox.getSelectedItem().toString() : ""; }

    // TODO check this name.
    public String getStoredArticleContentText() {
        return storedArticleTextPane.getText();
    }

    public void setStoredArticleContentText(String contentText) {
        storedArticleTextPane.setText(contentText);
        storedArticleTextPane.setCaretPosition(0);
    }
}
