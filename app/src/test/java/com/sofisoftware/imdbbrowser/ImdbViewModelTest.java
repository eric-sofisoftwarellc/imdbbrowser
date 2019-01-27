package com.sofisoftware.imdbbrowser;

import com.sofisoftware.imdbbrowser.api.ImdbResponse;
import com.sofisoftware.imdbbrowser.viewmodel.ImdbViewModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import androidx.lifecycle.Observer;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = BuildConfig.TARGET_SDK_VERSION)
public class ImdbViewModelTest extends BaseApiTest {
    @Mock
    private Observer<ImdbResponse> imdbResponseObserver;

    private ImdbViewModel imdbViewModel;

    @Before
    public void setUp() {
        super.setUp();

        imdbViewModel = new ImdbViewModel();
        imdbViewModel.getImdbResponse().observeForever(imdbResponseObserver);
    }

    @Test
    public void testGoodResponse() {
        server.enqueue("hello");

        imdbViewModel.queryImdb("hello");
        Mockito.verify(imdbResponseObserver).onChanged(Matchers.any());
        Assert.assertEquals("True", imdbViewModel.getImdbResponse().getValue().getResponse());
    }
}
