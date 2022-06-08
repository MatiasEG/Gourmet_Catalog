package views;

import presenter.IStoredInfoPresenter;

import javax.swing.*;

public class StoredInfoView implements IStoredInfoView {
    private JComboBox storedArticlesComboBox;
    private JScrollPane storedArticleContentScrollPane;
    private JTextPane storedArticleContentTextPane;
    private JPanel storedInfoPanel;
    private JMenuItem deleteMenuItem;
    private JMenuItem saveMenuItem;
    private String selectedArticleTitle;

    private IStoredInfoPresenter storedInfoPresenter;

    public StoredInfoView(IStoredInfoPresenter storedInfoPresenter){
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
            if(storedArticlesComboBox.getSelectedIndex() > -1) {
                selectedArticleTitle = storedArticlesComboBox.getSelectedItem().toString();
                storedInfoPresenter.onEventSelectArticle();
            }
        });
        deleteMenuItem.addActionListener(actionEvent -> storedInfoPresenter.onEvenDeleteArticle());
        saveMenuItem.addActionListener(actionEvent -> storedInfoPresenter.onEventUpdateArticle());
    }

    @Override
    public JPanel getPanel(){
        return storedInfoPanel;
    }

    @Override
    public void setStoredArticlesTitles(Object[] storedArticlesTitles) {
        storedArticlesComboBox.setModel(new DefaultComboBoxModel<Object>(storedArticlesTitles));
        clearView();
    }

    private void clearView(){
        storedArticlesComboBox.setSelectedIndex(-1);
        selectedArticleTitle = "";
        storedArticleContentTextPane.setText("");
    }

    @Override
    public String getSelectedArticleTitle(){
        return selectedArticleTitle;
    }

    @Override
    public void setSelectedArticleTitle(String articleTitle){
        selectedArticleTitle = articleTitle;
    }

    @Override
    public String getArticleContent() {
        return storedArticleContentTextPane.getText();
    }

    @Override
    public void setArticleContent(String contentText) {
        storedArticleContentTextPane.setText(contentText);
        storedArticleContentTextPane.setCaretPosition(0);
    }
}
