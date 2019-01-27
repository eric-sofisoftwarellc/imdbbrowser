package com.sofisoftware.imdbbrowser.viewmodel;

import com.sofisoftware.imdbbrowser.api.ImdbApi;
import com.sofisoftware.imdbbrowser.api.ImdbResponse;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ImdbViewModel helps us manage queries and their results.
 */
public class ImdbViewModel extends ViewModel {
    private MutableLiveData<ImdbResponse> imdbResponse = new MutableLiveData<>();

    private void runQuery(String query) {
        if (query == null || query.isEmpty()) {
            return;
        }

        // Do an asynchronous operation to fetch users.
        new ImdbApi().imdbQuery(query).enqueue(new Callback<ImdbResponse>() {
            @Override
            public void onResponse(Call<ImdbResponse> call, Response<ImdbResponse> response) {
                imdbResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ImdbResponse> call, Throwable t) {
                ImdbResponse failed = new ImdbResponse();
                failed.setResponse("False");
                imdbResponse.setValue(failed);
            }
        });
    }

    @VisibleForTesting
    public MutableLiveData<ImdbResponse> getImdbResponse() {
        return imdbResponse;
    }

    /**
     * Make a call to the server.
     *
     * @param query Search terms
     * @return LiveData for result; observe this to receive results when they arrive
     */
    public LiveData<ImdbResponse> queryImdb(String query) {
        runQuery(query);

        return imdbResponse;
    }
}
