package com.sofisoftware.imdbbrowser;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.sofisoftware.imdbbrowser.api.ImdbResponse;
import com.sofisoftware.imdbbrowser.view.ImdbAdapter;
import com.sofisoftware.imdbbrowser.viewmodel.ImdbViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private EditText queryText;
    private ImdbAdapter imdbAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queryText = findViewById(R.id.query_text);
        RecyclerView resultList = findViewById(R.id.result_list);
        progressBar = findViewById(R.id.progress);

        progressBar.setVisibility(View.GONE);

        resultList.setHasFixedSize(true);
        resultList.setLayoutManager(new LinearLayoutManager(this));

        imdbAdapter = new ImdbAdapter(null);
        resultList.setAdapter(imdbAdapter);

        queryText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE
                    || actionId == EditorInfo.IME_ACTION_GO
                    || actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_NEXT) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                onSearch();
                return true;
            }

            return false;
        });
    }

    private void onSearch() {
        String text = queryText.getText().toString();

        imdbAdapter.setResponse(new ImdbResponse());

        if (!text.isEmpty()) {
            ImdbViewModel model = ViewModelProviders.of(this).get(ImdbViewModel.class);

            progressBar.setVisibility(View.VISIBLE);

            // Observe the query's resulting data; when it changes, calls onResponse
            model.queryImdb(text).observe(this, this::onResponse);
        }
    }

    // Update the UI with a response
    private void onResponse(ImdbResponse imdbResponse) {
        progressBar.setVisibility(View.GONE);

        imdbAdapter.setResponse(imdbResponse);
    }
}
