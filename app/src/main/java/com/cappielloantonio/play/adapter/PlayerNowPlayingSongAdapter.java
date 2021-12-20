package com.cappielloantonio.play.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.cappielloantonio.play.R;
import com.cappielloantonio.play.glide.CustomGlideRequest;
import com.cappielloantonio.play.model.Song;
import com.cappielloantonio.play.service.MusicPlayerRemote;

import java.util.ArrayList;
import java.util.List;

public class PlayerNowPlayingSongAdapter extends RecyclerView.Adapter<PlayerNowPlayingSongAdapter.ViewHolder> {
    private static final String TAG = "PlayerNowPlayingSongAdapter";

    private final LayoutInflater inflater;
    private final Context context;

    private List<Song> songs;

    public PlayerNowPlayingSongAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.songs = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_player_now_playing_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Song song = songs.get(position);

        CustomGlideRequest.Builder
                .from(context, song.getId(), CustomGlideRequest.SONG_PIC, null)
                .build()
                .transform(new RoundedCorners(CustomGlideRequest.CORNER_RADIUS))
                .into(holder.cover);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public Song getItem(int position) {
        try {
            return songs.get(position);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public void setItems(List<Song> songs) {
        this.songs = songs;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView cover;

        ViewHolder(View itemView) {
            super(itemView);

            cover = itemView.findViewById(R.id.now_playing_song_cover_image_view);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (MusicPlayerRemote.isPlaying()) {
                MusicPlayerRemote.pauseSong();
            } else {
                MusicPlayerRemote.resumePlaying();
            }
        }
    }
}