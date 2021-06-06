package com.example.mindplaylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SearchMusic extends AppCompatActivity {

    private RequestQueue queue;
    private List<Music> musics;

    private ModeloAdapter adapter;
    private RecyclerView recyclerView;

    private TextView txtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_music);
    }

    public void searchClick(View view) {
        txtSearch = findViewById(R.id.txtSearch);

        musics = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new ModeloAdapter(this, musics);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        String endpoint = "https://itunes.apple.com/search?term=" + URLEncoder.encode(txtSearch.getText().toString());

        queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                endpoint,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject object = results.getJSONObject(i);
                                String trackName = object.has("trackName") ? object.getString("trackName") : "";
                                String artistName = object.has("artistName") ? object.getString("artistName") : "";
                                String artworkUrl100 = object.has("artworkUrl100") ? object.getString("artworkUrl100") : "";
                                String collectionName = object.has("collectionName") ? object.getString("collectionName") : "";
                                String primaryGenreName = object.has("primaryGenreName") ? object.getString("primaryGenreName") : "";
                                String country = object.has("country") ? object.getString("country") : "";

                                String type = object.getString("wrapperType");
                                if (type.equals("track")) {
                                    Music music = new Music();
                                    music.setTrackName(trackName +
                                            " (" + artistName + ")");
                                    music.setArtworkUrl100(artworkUrl100);
                                    music.setCollectionName(collectionName);
                                    music.setPrimaryGenreName(primaryGenreName);
                                    music.setCountry(country);

                                    musics.add(music);
                                }
                            }
                            if (results.length() == 0) {
                                Toast.makeText(SearchMusic.this, "Música não encontrada ",
                                        Toast.LENGTH_LONG).show();
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException ex) {
                            Toast.makeText(SearchMusic.this, "Erro no Json: " + ex.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SearchMusic.this, "Erro na conexão á API: " + error.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
        );
        queue.add(jsonObjectRequest);
    }
}