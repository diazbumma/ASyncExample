package com.diazbumma.asyncexample;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvHeroes;
    private ProgressBar pbLoadHeroes;
    private TextView tvError;

    private static final String JSON_HEROES_URL = "https://simplifiedcoding.net/demos/view-flipper/heroes.php";

    String jsonStr;

    private ArrayList<Hero> heroes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvHeroes = findViewById(R.id.rv_heroes);
        pbLoadHeroes = findViewById(R.id.pb_load_heroes);
        tvError = findViewById(R.id.tv_error_message_display);

        loadHeroesData();

    }

    private void loadHeroesData() {
        showFlightDataView();
        new FetchHeroesTask().execute();
    }

    private void showFlightDataView() {
        tvError.setVisibility(View.INVISIBLE);
        rvHeroes.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        rvHeroes.setVisibility(View.INVISIBLE);
        tvError.setVisibility(View.VISIBLE);
    }

    public class FetchHeroesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbLoadHeroes.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            jsonStr = sh.makeServiceCall(JSON_HEROES_URL);

            if (jsonStr != null) {
                try {
                    JSONObject responseObj = new JSONObject(jsonStr);
                    JSONArray heroesArray = responseObj.getJSONArray("heroes");
                    JSONObject heroObj;

                    for (int i=0; i<heroesArray.length(); i++){
                        heroObj = heroesArray.getJSONObject(i);

                        Hero hero = new Hero(
                                heroObj.getString("name"),
                                heroObj.getString("imageurl"));

                        heroes.add(hero);
                    }

                } catch (final JSONException ignored) {

                }
            } else {
                showErrorMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pbLoadHeroes.setVisibility(View.INVISIBLE);

            HeroAdapter adapter = new HeroAdapter(heroes, getApplicationContext());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvHeroes.getContext(),
                    layoutManager.getOrientation());
            rvHeroes.setLayoutManager(layoutManager);
            rvHeroes.addItemDecoration(dividerItemDecoration);
            rvHeroes.setHasFixedSize(true);
            rvHeroes.setAdapter(adapter);
        }
    }
}
