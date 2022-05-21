package presenter;

import model.GourmetCatalogModel;
import model.GourmetCatalogModelInterface;

public class Main {
    public static void main(String[] args){
        GourmetCatalogModelInterface gourmetCatalogModel = new GourmetCatalogModel();
        GourmetCatalogPresenter gourmetCatalogPresenter = new GourmetCatalogPresenter(gourmetCatalogModel);
        gourmetCatalogPresenter.showView();
    }
}
