package com.example.david.testapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by david on 11/7/15.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {

    List<String> photos;
    String url = "http://192.168.0.12:3000/photo?filename=";

    public PhotoAdapter(List<String> photos) {
        this.photos = photos;
    }

    @Override
    public PhotoAdapter.PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View photo_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_view_photos, parent, false);
        return new PhotoHolder(photo_view);
    }

    @Override
    public void onBindViewHolder(PhotoAdapter.PhotoHolder holder, int position) {
        Context context = holder.networkImageView.getContext().getApplicationContext();
        holder.networkImageView.setImageUrl(url+this.photos.get(position), AppData.getInstance(context).getImageLoader());
    }

    @Override
    public int getItemCount() {
        return this.photos.size();
    }

    public class PhotoHolder extends RecyclerView.ViewHolder {
        NetworkImageView networkImageView;

        public PhotoHolder(View itemView) {
            super(itemView);
            this.networkImageView = (NetworkImageView)itemView.findViewById(R.id.single_photo);
        }
    }
}
