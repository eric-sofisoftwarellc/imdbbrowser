package com.sofisoftware.imdbbrowser.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sofisoftware.imdbbrowser.R;
import com.sofisoftware.imdbbrowser.api.ImdbResponse;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Adapter which takes responses and create/bind to view items
public class ImdbAdapter extends RecyclerView.Adapter<ImdbViewHolder> {
    private ImdbResponse response;

    public ImdbAdapter(ImdbResponse response) {
        this.response = response;
    }

    @NonNull
    @Override
    public ImdbViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_imdb_entry, parent, false);
        return new ImdbViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImdbViewHolder holder, int position) {
        holder.bind(response.getEntries().get(position));
    }

    @Override
    public int getItemCount() {
        return response == null || response.getEntries() == null ? 0 : response.getEntries().size();
    }

    public void setResponse(ImdbResponse response) {
        this.response = response;

        notifyDataSetChanged();
    }
}
