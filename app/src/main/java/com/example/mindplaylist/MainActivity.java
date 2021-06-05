package com.example.mindplaylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.RequestQueue;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Abre a página de busca de Música
    public void clickSearchMusic(View view) {
        Intent intent = new Intent(this, SearchMusic.class);
        startActivity(intent);
    }

    // Abra a página de Playlist
    public void clickMyPlaylist(View view) {
        Intent intent = new Intent(this, MyPlaylist.class);
        startActivity(intent);
    }


}