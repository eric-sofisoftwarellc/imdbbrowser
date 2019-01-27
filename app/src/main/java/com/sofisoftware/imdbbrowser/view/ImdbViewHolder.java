package com.sofisoftware.imdbbrowser.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sofisoftware.imdbbrowser.R;
import com.sofisoftware.imdbbrowser.api.ImdbEntry;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

// Viewholder which manages a response item
class ImdbViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView year;
    private ImageView poster;

    ImdbViewHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.title);
        year = itemView.findViewById(R.id.year);
        poster = itemView.findViewById(R.id.poster);
    }

    /**
     * Bind a single item to the view.
     * Loads the poster as an image, resizing it to fit.
     *
     * @param entry Entry to display
     */
    void bind(ImdbEntry entry) {
        title.setText(entry.getTitle());
        year.setText(entry.getYear());

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(poster.getContext());
        circularProgressDrawable.setStrokeWidth(5);
        circularProgressDrawable.setCenterRadius(30);
        circularProgressDrawable.start();

        Picasso.get()
                .load(entry.getPoster())
                .placeholder(circularProgressDrawable)
                .error(R.drawable.ic_error)
                .into(poster);
    }
}
