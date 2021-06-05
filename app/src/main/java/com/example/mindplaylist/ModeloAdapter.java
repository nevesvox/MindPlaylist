package com.example.mindplaylist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ModeloAdapter extends RecyclerView.Adapter<ModeloAdapter.ViewHolder> {

    // Esta declaração deverá ser adaptada para projetos futuros
    private List<Music> dados;
    private LayoutInflater inflater;
    private ItemClickListener itemClickListener;
    private ItemLongClickListener itemLongClickListener;

    // O segundo parâmetro deverá ser adaptado para futuros projetos
    public ModeloAdapter(Context context, List<Music> dados) {
        this.inflater = LayoutInflater.from(context);
        this.dados = dados;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // A única coisa a ser adaptada aqui seria o nome do arquivo de layout da linha
        View view = inflater.inflate(R.layout.linha_recycleview, parent, false);
        return new ViewHolder(view);
    }

    // IMPORTANTÍSSIMO!
    // É aqui que você deve associar seus dados com os widgets existentes nas linhas
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Music music = dados.get(position);
        holder.textView.setText(music.getTrackName());
        Picasso.get().load(music.getArtworkUrl100()).error(R.drawable.ic_launcher_background)
                .resize(100, 100).into(holder.imageView);

    }

    // Na maioria das vezes não precisa ser alterada.
    // Deve retornar o número de itens (linhas) que existirão na lista
    @Override
    public int getItemCount() {
        return dados.size();
    }

    // Armazenar e reciclar as linhas que foram roladas para fora da tela
    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        // No caso de mais elementos no layout da linha, estes devem ser declarados aqui
        // e inicializados com findViewById no construtor abaixo.
        TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            itemView.findViewById(R.id.btnDetails).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Music selectedMusic = dados.get(getAdapterPosition());
                    Log.d("teste", "teste" + selectedMusic.getTrackName());
                    Intent intent = new Intent(v.getContext(), MusicDetails.class);
                    intent.putExtra("trackName", selectedMusic.getTrackName());
                    intent.putExtra("collectionName", selectedMusic.getCollectionName());
                    intent.putExtra("artworkUrl100", selectedMusic.getArtworkUrl100());
                    intent.putExtra("country", selectedMusic.getCountry());
                    intent.putExtra("primaryGenreName", selectedMusic.getPrimaryGenreName());
                    v.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(v, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (itemLongClickListener != null) {
                itemLongClickListener.onItemLongClick(v, getAdapterPosition());
            }
            return true;
        }
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    void setLongClickListener(ItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface ItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}