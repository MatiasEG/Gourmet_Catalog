package presenter;

import org.junit.Test;
import views.ISearchView;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SearchPresenterTest {

    @Test
    public void test1(){
        ISearchView searchView = mock(ISearchView.class);
        when(searchView.getSearchText()).thenReturn("asd");
        assertEquals("asd",searchView.getSearchText());
    }
}
