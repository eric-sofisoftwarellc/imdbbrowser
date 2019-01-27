package com.sofisoftware.imdbbrowser;

import com.sofisoftware.imdbbrowser.api.ImdbApi;
import com.sofisoftware.imdbbrowser.api.ImdbResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = BuildConfig.TARGET_SDK_VERSION)
public class ImdbResponseTest extends BaseApiTest {
    @Test
    public void testGoodResponse() {
        server.enqueue("hello");

        final Call<ImdbResponse> hello = new ImdbApi().imdbQuery("hello");
        hello.enqueue(new Callback<ImdbResponse>() {
            @Override
            public void onResponse(Call<ImdbResponse> call, Response<ImdbResponse> response) {
                Assert.assertEquals("True", response.body().getResponse());
                Assert.assertEquals("https://m.media-amazon.com/images/M/MV5BMTg0NTM3MTI1MF5BMl5BanBnXkFtZTgwMTAzNTAzNzE@._V1_SX300.jpg", response.body().getEntries().get(0).getPoster());
                Assert.assertEquals("Hello, My Name Is Doris", response.body().getEntries().get(0).getTitle());
                Assert.assertEquals("2015", response.body().getEntries().get(0).getYear());

                Assert.assertEquals("https://m.media-amazon.com/images/M/MV5BZmQ3YmM0NGMtYmRmNi00ZWY4LTk5MGYtYzUyODA4ODBlODE3XkEyXkFqcGdeQXVyMjQzNzk2ODk@._V1_SX300.jpg", response.body().getEntries().get(9).getPoster());
                Assert.assertEquals("Oh, Hello on Broadway", response.body().getEntries().get(9).getTitle());
                Assert.assertEquals("2017", response.body().getEntries().get(9).getYear());
            }

            @Override
            public void onFailure(Call<ImdbResponse> call, Throwable t) {
                Assert.fail();
            }
        });
    }

    @Test
    public void testBadResponse() {
        server.enqueue("notfound");

        final Call<ImdbResponse> hello = new ImdbApi().imdbQuery("hello");
        hello.enqueue(new Callback<ImdbResponse>() {
            @Override
            public void onResponse(Call<ImdbResponse> call, Response<ImdbResponse> response) {
                Assert.assertEquals("False", response.body().getResponse());
            }

            @Override
            public void onFailure(Call<ImdbResponse> call, Throwable t) {
                Assert.fail();
            }
        });
    }
}
