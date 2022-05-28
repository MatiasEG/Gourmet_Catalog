package views;

import presenter.StoredInfoPresenterInterface;

import javax.swing.*;

public class StoredInfoView implements StoredInfoViewInterface{
    private JComboBox storedArticlesComboBox;
    private JScrollPane storedArticleContentScrollPane;
    private JTextPane storedArticleContentTextPane;
    private JPanel storedInfoPanel;
    private JMenuItem deleteMenuItem;
    private JMenuItem saveMenuItem;

    private StoredInfoPresenterInterface storedInfoPresenter;

    public StoredInfoView(StoredInfoPresenterInterface storedInfoPresenter){
        this.storedInfoPresenter = storedInfoPresenter;
        initView();
        initListeners();
    }

    private void initView(){
        initTextPanes();
        initPopupMenu();
    }

    private void initTextPanes() {
        storedArticleContentTextPane.setContentType("text/html");
    }

    private void initPopupMenu() {
        JPopupMenu storedInfoPopupMenu = new JPopupMenu();
        deleteMenuItem = new JMenuItem("Delete!");
        saveMenuItem = new JMenuItem("Save Changes!");
        storedInfoPopupMenu.add(deleteMenuItem);
        storedInfoPopupMenu.add(saveMenuItem);
        storedArticleContentTextPane.setComponentPopupMenu(storedInfoPopupMenu);
    }

    private void initListeners(){
        storedArticlesComboBox.addItemListener(actionEvent -> {
            if(storedArticlesComboBox.getSelectedIndex() > -1)
                storedInfoPresenter.onEventSelectStoredArticle();
        });
        deleteMenuItem.addActionListener(actionEvent -> storedInfoPresenter.onEvenDeleteStoredArticle());
        saveMenuItem.addActionListener(actionEvent -> storedInfoPresenter.onEventUpdateStoredArticle());
    }

    @Override
    public JPanel getPanel(){
        return storedInfoPanel;
    }

    @Override
    public void setStoredArticlesTitles(Object[] storedArticlesTitles) {
        storedArticlesComboBox.setModel(new DefaultComboBoxModel<Object>(storedArticlesTitles));
    }

    @Override
    public void clearView(){
        storedArticlesComboBox.setSelectedIndex(-1);
        storedArticleContentTextPane.setText("");
    }

    @Override
    public String getSelectedArticleTitle(){ return storedArticlesComboBox.getSelectedIndex() > -1 ? storedArticlesComboBox.getSelectedItem().toString() : ""; }

    @Override
    // TODO check this name.
    public String getArticleContent() {
        return storedArticleContentTextPane.getText();
    }

    @Override
    public void setArticleContent(String contentText) {
        storedArticleContentTextPane.setText(contentText);
        storedArticleContentTextPane.setCaretPosition(0);
    }
}
