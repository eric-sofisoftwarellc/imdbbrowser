package com.sofisoftware.imdbbrowser.api;

import java.util.concurrent.ExecutorService;

import androidx.annotation.AnyThread;
import androidx.annotation.VisibleForTesting;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * This class encapsulates REST calls to the omdbapi server.
 */
public class ImdbApi {
    // Base URL of service
    private static final String API_URL = "http://www.omdbapi.com/";

    // Api key
    private static final String API_KEY = "<replace-with-key>";

    private static ImdbService service;
    private static ExecutorService executorService;

    private interface ImdbService {
        @GET("/")
        Call<ImdbResponse> imdbQuery(@Query("apikey") String apiKey, @Query("s") String query);
    }

    // helper to create retrofit singleton
    private static ImdbService makeService(String url) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(url)
                .client(makeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        return retrofit.create(ImdbService.class);
    }

    // For unit tests, override the service endpoint
    @VisibleForTesting
    public static void makeTestService(String url) {
        service = makeService(url);
    }

    // For unit tests, override the executor used by retrofit/okhttp
    @VisibleForTesting
    public static void setExecutorService(ExecutorService executorService) {
        ImdbApi.executorService = executorService;
    }

    // helper to create/retrieve the service singleton
    private static ImdbService getService() {
        if (service == null) {
            synchronized (ImdbApi.class) {
                if (service == null) {
                    service = makeService(API_URL);
                }
            }
        }

        return service;
    }

    private static OkHttpClient makeOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (executorService != null) {
            builder.dispatcher(new Dispatcher(executorService));
        }

        return builder.build();
    }

    /**
     * Call the service for a given search query
     *
     * @param query Search terms
     * @return Future which will receive the response
     */
    @AnyThread
    public Call<ImdbResponse> imdbQuery(String query) {
        return getService().imdbQuery(API_KEY, query);
    }
}
