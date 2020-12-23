package Activities;
import com.example.axe.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;



public class Exo_Player extends AppCompatActivity {
    String videourl1;
    public static final String TAG = "TAG";
    ProgressBar spiiner;
    ImageView fullScreenOp;
    FrameLayout frameLayout;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    ImageView fullscreenButton;
    boolean fullscreen = false;
    Uri VideoURL;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_exo__player);
        //  frameLayout = findViewById(R.id.frameLayout);
        // videoplayer = findViewById(R.id.exoplayer);

        Bundle extras = getIntent().getExtras();
        VideoURL = Uri.parse(extras.getString("link"));
        Log.d("url", VideoURL.toString());
//customize exoplayer////
        progressBar=findViewById(R.id.progressBar);
        player = ExoPlayerFactory.newSimpleInstance(getApplicationContext());
        playerView = findViewById(R.id.player);
        fullscreenButton = playerView.findViewById(R.id.exo_fullscreen_icon);
//        fullscreenButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (fullscreen) {
//                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(Exo_Player.this, R.drawable.ic_fullscreen_open));
//
//                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//
//                    if (getSupportActionBar() != null) {
//                        getSupportActionBar().show();
//                    }
//
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
//                    params.width = params.MATCH_PARENT;
//                    params.height = (int) (200 * getApplicationContext().getResources().getDisplayMetrics().density);
//                    playerView.setLayoutParams(params);
//
//                    fullscreen = false;
//                } else {
//                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(Exo_Player.this, R.drawable.ic_full_screen_close));
//
//                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//
//                    if (getSupportActionBar() != null) {
//                        getSupportActionBar().hide();
//                    }
//
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//
//                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
//                    params.width = params.MATCH_PARENT;
//                    params.height = params.MATCH_PARENT;
//                    playerView.setLayoutParams(params);
//
//                    fullscreen = true;
//                }
//
//            }
//        });
        playerView.setPlayer(player);
        player.addListener(new ExoPlayer.EventListener() {

            public void onTimelineChanged(Timeline timeline, Object manifest) {}

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {}

            @Override
            public void onLoadingChanged(boolean isLoading) {}

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == ExoPlayer.STATE_BUFFERING){
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }


            public void onPlayerError(ExoPlaybackException error) {}


            public void onPositionDiscontinuity() {}


            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {}
        });
       // playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(), Util.getUserAgent(getApplicationContext(), getApplicationContext().getString(R.string.app_name)));

        MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(VideoURL);



        player.prepare(videoSource);

        player.setPlayWhenReady(true);
        //////////


//        Intent intent = getIntent();

//        videourl1 = intent.getStringExtra("url");
//        Log.d(TAG, "onResponse: " + videourl1);

//
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setupexoplayer();
    }

//    private void setupexoplayer() {
//
//        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this);
//
//        videoplayer.setPlayer(simpleExoPlayer);
//        DataSource.Factory datasourcefatcory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "moviesapllication"));
//        MediaSource mediaSource = new ExtractorMediaSource.Factory(datasourcefatcory).createMediaSource(VideoURL);
//        simpleExoPlayer.prepare(mediaSource);
//        simpleExoPlayer.setPlayWhenReady(true);
//
//
//    }

    //    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        simpleExoPlayer.release();
//    }
    @Override
    public void onPause() {
        super.onPause();
        player.setPlayWhenReady(false);
    }

    @Override
    public void onDestroy() {
        player.release();
        super.onDestroy();
    }
}


