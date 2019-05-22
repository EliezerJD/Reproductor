package app.reproductor;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import java.io.InputStream;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import android.widget.ArrayAdapter;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mp;
    InputStream path;
    ListView simpleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Lista de canciones");
        simpleList= (ListView) findViewById(R.id.simpleListView);


        final ArrayList<Song> songs = new ArrayList<>();

        songs.add(new Song ("Old Town Road", "Lil Nas X", R.drawable.oldtownroad));
        songs.add(new Song ("Verte Ir", "Anuel AA", R.drawable.verteir));
        songs.add(new Song ("Rebota", "Gaurnaa", R.drawable.rebota));
        songs.add(new Song ("Cold Water", "Major Lazer feat. Justin Bieber", R.drawable.coldwater));
        songs.add(new Song ("Aullando", "Wisin & Yandel", R.drawable.aullando));
        songs.add(new Song ("Contra la pared", "Sean Paul, J Balvin", R.drawable.contralapared));

        simpleList.setAdapter(new ListSongAdapter(MainActivity.this, songs));

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, activity_reproductor.class);
                intent.putExtra("number", i);
                startActivity(intent);
            }
        });

        path = getResources().openRawResource(R.raw.oldtownroad);

        mp= MediaPlayer.create(MainActivity.this, R.raw.oldtownroad);


    }
    public class ListSongAdapter extends BaseAdapter {
        private final Context context;
        private final ArrayList<Song> songs;

        public ListSongAdapter(Context context, ArrayList<Song> songs){
            this.context = context;
            this.songs = songs;
        }

        @Override
        public int getCount(){
            return  songs.size();
        }

        @Override
        public Object getItem(int position){
            return position;
        }

        @Override
        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(int postion, View view, ViewGroup viewGroup){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

            Song song = songs.get(postion);
            MainActivity.ListSongAdapter.ListViewHolder holder;
            if(view == null){
                view = inflater.inflate(R.layout.activity_listview, viewGroup, false);
                holder = new MainActivity.ListSongAdapter.ListViewHolder();
                holder.txtName = (TextView) view.findViewById(R.id.txtName);
                holder.txtArtist = (TextView) view.findViewById(R.id.txtArtist);
                holder.imgSong = (ImageView) view.findViewById(R.id.imgSong);

                view.setTag(holder);
            }else{
                holder = (MainActivity.ListSongAdapter.ListViewHolder) view.getTag();
            }

            holder.txtName.setText(song.name);
            holder.txtArtist.setText(song.artist);
            holder.imgSong.setImageResource(song.image);

            return view;

        }

        public class ListViewHolder {
            TextView txtName;
            TextView txtArtist;
            ImageView imgSong;
        }


    }






    public void play(View view) {
        startActivity(new Intent(MainActivity.this, activity_reproductor.class));
        //mp.start();

    }

    public void stop(View view) {
        mp.stop();
        mp.release();
        mp = MediaPlayer.create(MainActivity.this, R.raw.oldtownroad);
    }
}
