package com.example.mindplaylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MusicDetails extends AppCompatActivity {

    TextView txtCountry;
    TextView txtTrackName;
    TextView txtCollection;
    TextView txtGenre;
    ImageView imageView;
    Music music = new Music();
    private AppDataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_details);

        dataBase = Room.databaseBuilder(
                getApplicationContext(),
                AppDataBase.class,
                "banco").allowMainThreadQueries().build();

        Intent intent = getIntent();
        // Recupera os dados da música
        music.setTrackName(intent.getStringExtra("trackName"));
        music.setCollectionName(intent.getStringExtra("collectionName"));
        music.setArtworkUrl100(intent.getStringExtra("artworkUrl100"));
        music.setCountry(intent.getStringExtra("country"));
        music.setPrimaryGenreName(intent.getStringExtra("primaryGenreName"));
        music.setRate(0);

        txtCountry = findViewById(R.id.txtCountry2);
        txtTrackName = findViewById(R.id.txtTrackName2);
        txtCollection = findViewById(R.id.txtCollection2);
        txtGenre = findViewById(R.id.txtGenre2);
        imageView = findViewById(R.id.imageView3);

        txtCountry.setText("País: " + music.getCountry());
        txtTrackName.setText(music.getTrackName());
        txtCollection.setText("Álbum: " + music.getCollectionName());
        txtGenre.setText("Gênero: " + music.getPrimaryGenreName());
        Picasso.get().load(music.getArtworkUrl100()).error(R.drawable.ic_launcher_background)
                .resize(200, 200).into(imageView);

    }

    public void insertMusicOnPlaylist(View view) {
        dataBase.musicDAO().addMusic(music);

        Intent intent = new Intent(this, MyPlaylist.class);
        startActivity(intent);
    }
}