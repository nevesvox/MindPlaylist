package com.example.mindplaylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MyPlaylist extends AppCompatActivity {

    private AppDataBase dataBase;
    private List<Music> playlist;
    private RecyclerView recyclerView;
    private ModeloAdapterCustom adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_playlist);

        dataBase = Room.databaseBuilder(
                getApplicationContext(),
                AppDataBase.class,
                "banco").allowMainThreadQueries().build();

        playlist = dataBase.musicDAO().getPlaylist();

        if (playlist.size() == 0) {
            Toast.makeText(MyPlaylist.this, "Nenhuma música na sua Playlist =( ",
                    Toast.LENGTH_LONG).show();
        }

        recyclerView = findViewById(R.id.recyclePlaylist);
        adapter = new ModeloAdapterCustom(this, playlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // Adiciona uma divisória entre uma linha do recyclerView
        DividerItemDecoration divider = new DividerItemDecoration(
                recyclerView.getContext(),
                layoutManager.getOrientation()
        );
        recyclerView.addItemDecoration(divider);
    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}