package com.diazbumma.asyncexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

public class HeroAdapter extends RecyclerView.Adapter<HeroAdapter.HeroViewHolder> {

    private Context context;
    private ArrayList<Hero> heroes;

    public HeroAdapter (ArrayList<Hero> heroes, Context context){
        this.heroes = heroes;
        this.context = context;
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.item_hero;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutIdForListItem, parent, false);

        return new HeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder holder, int position) {
        Hero hero = heroes.get(position);
        holder.bind(hero);
    }

    @Override
    public int getItemCount() {
        return heroes.size();
    }

    public class HeroViewHolder extends RecyclerView.ViewHolder {

        TextView tvHeroName;
        TextView tvHeroImageUrl;
        ImageView ivHeroAvatar;
        ProgressBar pbLoadHeroImage;

        public HeroViewHolder(View itemView) {
            super(itemView);
            tvHeroName = itemView.findViewById(R.id.tv_item_hero_name);
            tvHeroImageUrl = itemView.findViewById(R.id.tv_item_hero_url);
            ivHeroAvatar = itemView.findViewById(R.id.iv_hero_avatar);
            pbLoadHeroImage = itemView.findViewById(R.id.pb_load_hero_image);
        }

        public void bind (Hero hero){
            tvHeroName.setText(hero.getName());
            tvHeroImageUrl.setText(hero.getImageUrl());
            new DownloadImageTask(ivHeroAvatar).execute(hero.getImageUrl());
        }

        private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
            ImageView bmImage;

            public DownloadImageTask(ImageView bmImage) {
                this.bmImage = bmImage;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                bmImage.setVisibility(View.INVISIBLE);
                pbLoadHeroImage.setVisibility(View.VISIBLE);
            }

            protected Bitmap doInBackground(String... urls) {
                String urldisplay = urls[0];
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return mIcon11;
            }

            protected void onPostExecute(Bitmap result) {
                bmImage.setVisibility(View.VISIBLE);
                bmImage.setImageBitmap(result);
                pbLoadHeroImage.setVisibility(View.INVISIBLE);
            }
        }
    }
}
