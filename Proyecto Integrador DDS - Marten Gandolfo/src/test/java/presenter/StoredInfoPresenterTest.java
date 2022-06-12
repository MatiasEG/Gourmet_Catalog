package presenter;

import model.storedInfoModel.IStoredInfoModel;
import org.junit.Before;
import org.junit.Test;
import views.IMainView;
import views.IStoredInfoView;

import static org.mockito.Mockito.*;

public class StoredInfoPresenterTest {

    private IStoredInfoModel storedInfoModel;
    private IStoredInfoView storedInfoView;
    private IMainView mainView;
    private IStoredInfoPresenter storedInfoPresenter;

    @Before
    public void setUp(){
        storedInfoModel = mock(IStoredInfoModel.class);

        storedInfoView = mock(IStoredInfoView.class);
        when(storedInfoView.getSelectedArticleTitle()).thenReturn("mocked title");
        when(storedInfoView.getArticleContent()).thenReturn("mocked content");
        mainView = mock(IMainView.class);
        when(mainView.getStoredInfoView()).thenReturn(storedInfoView);

        storedInfoPresenter = new StoredInfoPresenter(storedInfoModel);
        storedInfoPresenter.setView(mainView);

    }

    @Test
    public void testSelectArticle() throws InterruptedException {
        storedInfoPresenter.onEventSelectArticle();
        this.waitForPresenterTask();
        verify(storedInfoModel, times(1)).loadArticle("mocked title");
    }

    @Test
    public void testDeleteArticle() throws InterruptedException {
        storedInfoPresenter.onEvenDeleteArticle();
        this.waitForPresenterTask();
        verify(storedInfoModel, times(1)).deleteArticle("mocked title");
    }

    @Test
    public void testUpdateArticle() throws InterruptedException {
        storedInfoPresenter.onEventUpdateArticle();
        this.waitForPresenterTask();
        verify(storedInfoModel, times(1)).updateArticle("mocked title", "mocked content");
    }

    private void waitForPresenterTask() throws InterruptedException{
        while(storedInfoPresenter.isActivelyWorking()) Thread.sleep(1);
    }
}
