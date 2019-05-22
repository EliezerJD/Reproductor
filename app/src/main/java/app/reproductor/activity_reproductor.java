package app.reproductor;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class activity_reproductor extends AppCompatActivity {
    MediaPlayer mp;
    ImageButton btn_pause;
    ImageButton btn_play;
    SeekBar sb;
    Handler handler;
    Runnable runnable;
    TextView txt_actual;
    TextView txt_duration;
    int ind = 0;
    ImageButton btn_next;
    String str = "";
    ImageView imgAlbum;
    WebView webView;
    Switch sw;
    int random = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);
        btn_pause = findViewById(R.id.btnpause);
        btn_play = findViewById(R.id.btnplay);
        btn_play.setVisibility(View.GONE);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Atrás");

        webView = findViewById(R.id.web);

        txt_actual = findViewById(R.id.txtactual);
        txt_duration = findViewById(R.id.txtduration);

        webView.setBackgroundColor(Color.TRANSPARENT);

        int position = getIntent().getExtras().getInt("number");
        ind = position;

        imgAlbum = findViewById(R.id.imgAlbum);
        btn_next = findViewById(R.id.btnnext);

        handler = new Handler();
        sb = findViewById(R.id.sb);
        sw = findViewById(R.id.switchaleatorio);

        run(position);
    }

    public void run(int position){
        ind = position;
        if(ind >5){
            ind = 0;
        }else if(ind <0){
            ind = 5;
        }
        switch (ind){
            case 0:{
                str = "<html><FONT color='white' SIZE='5px' FACE='courier'><marquee behavior='scroll' direction='left' scrollamount=5>"
                        + "Old Town Road - Lil Nas X" + "</marquee></FONT></html>";
                imgAlbum.setImageResource(R.drawable.oldtownroad);
                mp= MediaPlayer.create(this, R.raw.oldtownroad);
                break;
            }
            case 1:{
                str = "<html><FONT color='white' SIZE='5px' FACE='courier'><marquee behavior='scroll' direction='left' scrollamount=5>"
                        + "Verte Ir - Anuel AA" + "</marquee></FONT></html>";
                imgAlbum.setImageResource(R.drawable.verteir);
                mp= MediaPlayer.create(this, R.raw.verteir);
                break;
            }
            case 2:{
                str = "<html><FONT color='white' SIZE='5px' FACE='courier'><marquee behavior='scroll' direction='left' scrollamount=5>"
                        + "Rebota - Gaurnaa" + "</marquee></FONT></html>";
                imgAlbum.setImageResource(R.drawable.rebota);
                mp= MediaPlayer.create(this, R.raw.rebota);
                break;
            }
            case 3:{
                str = "<html><FONT color='white' SIZE='5px' FACE='courier'><marquee behavior='scroll' direction='left' scrollamount=5>"
                        + "Cold Water - Major Lazer feat. Justin Bieber" + "</marquee></FONT></html>";
                imgAlbum.setImageResource(R.drawable.coldwater);
                mp= MediaPlayer.create(this, R.raw.coldwater);
                break;
            }
            case 4:{
                str = "<html><FONT color='white' SIZE='5px' FACE='courier'><marquee behavior='scroll' direction='left' scrollamount=5>"
                        + "Aullando - Wisin & Yandel" + "</marquee></FONT></html>";
                imgAlbum.setImageResource(R.drawable.aullando);
                mp= MediaPlayer.create(this, R.raw.aullando);
                break;
            }
            case 5:{
                str = "<html><FONT color='white' SIZE='5px' FACE='courier'><marquee behavior='scroll' direction='left' scrollamount=5>"
                        + "Contra la pared - Sean Paul, J Balvin" + "</marquee></FONT></html>";
                imgAlbum.setImageResource(R.drawable.contralapared);
                mp= MediaPlayer.create(this, R.raw.contralapared);
                break;
            }
        }
        mp.start();
        txt_duration.setText(convertirHora(mp.getDuration()));
        webView.loadData(str, "text/html", "utf-8");


        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                sb.setMax(mp.getDuration());
                playCycle();
            }
        });

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(sw.isChecked()){
                    random = (int) Math.floor(Math.random()*6);
                    while(random == ind){
                        random = (int) Math.floor(Math.random()*6);
                    }
                    run(random);
                }else{
                    ind = ind +1;
                    run(ind);
                }
            }
        });
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean input) {
                if(input){
                    mp.seekTo(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void playCycle(){

        sb.setProgress(mp.getCurrentPosition());
        txt_actual.setText(convertirHora(mp.getCurrentPosition()));

        if(mp.isPlaying()){
            runnable = new Runnable(){
                @Override
                public void run(){
                   playCycle();
                }
            };
            handler.postDelayed(runnable, 1000);
        }
    }

    public String convertirHora (long i){
        int  m = (int) (i %(1000* 60 * 60)) / (1000 * 60);
        long s = (int) ((i % (1000 * 60 * 60)) % (1000* 60) / 1000);

        String r = String.format("%02d:%02d", m, s);
        return r;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mp.stop();
                Intent intent = new Intent(activity_reproductor.this, MainActivity.class);
                startActivity(intent);
                break;

        }
        return true;
    }

    @Override
    public void onBackPressed(){
        mp.stop();
        Intent intent = new Intent(activity_reproductor.this, MainActivity.class);
        startActivity(intent);
    }


    public void pausa(View view) {
        mp.pause();
        btn_pause.setVisibility(View.GONE);
        btn_play.setVisibility(View.VISIBLE);
    }

    public void play(View view) {
        btn_play.setVisibility(View.GONE);
        btn_pause.setVisibility(View.VISIBLE);
        int length = mp.getCurrentPosition();
        mp.seekTo(length);
        mp.start();
        playCycle();
    }


    public void next(View view) {
        mp.pause();
        mp.stop();
        mp.release();
        ind = ind + 1;
        run(ind);
    }

    public void prev(View view) {
        mp.pause();
        mp.stop();
        mp.release();
        ind = ind - 1;
        run(ind);
    }
    public void aleatorio(View view) {
        if(sw.isChecked()){
            mp.pause();
            mp.stop();
            mp.release();
            random = (int) Math.floor(Math.random()*6);
            while(random == ind){
                random = (int) Math.floor(Math.random()*6);
            }
            run(random);
        }
    }
}
