package Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.axe.R;


import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.RandomTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

public class ExoPlayerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String KEY_PLAY_WHEN_READY = "play_when_ready";
    private static final String KEY_WINDOW = "window";
    private static final String KEY_POSITION = "position";

    private PlayerView playerView;
    private SimpleExoPlayer player;
    private Timeline.Window window;
    private DataSource.Factory mediaDataSourceFactory;
    private DefaultTrackSelector trackSelector;
    private TrackGroupArray lastSeenTrackGroupArray;
    private boolean shouldAutoPlay;
    private BandwidthMeter bandwidthMeter;
    private LottieAnimationView progressBar;
    private boolean playWhenReady;
    private int currentWindow;
    private long playbackPosition;
    private DataSource.Factory dataSourceFactory;
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    String video_url = "";
    String url;
    String name;
    int idOfSession;

    Dialog dialogProgressBar;
    View v_actionbar;
    TextView txtTitle;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_player);
        init();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        findViewById(R.id.go_full_screen_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public void init() {
//        utility = new Utility(this);
//
//        global = new Global(this);
        progressBar = findViewById(R.id.animation_view);
        //video_url = getIntent().getStringExtra("video_url");
        String urr="http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
        video_url= String.valueOf(Uri.parse(urr));
        name = getIntent().getStringExtra("name");
        idOfSession = getIntent().getIntExtra("id", 0);

//        dialogProgressBar = new Dialog(this, R.style.alert_dialog);
//        dialogProgressBar.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialogProgressBar.setContentView(R.layout.loading);
//        dialogProgressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        dialogProgressBar.setCancelable(false);

        v_actionbar = findViewById(R.id.action_bar_custom_video);
        imgBack = v_actionbar.findViewById(R.id.back_arrow);
        txtTitle = v_actionbar.findViewById(R.id.title_custom_actionbar);
        txtTitle.setText("" + name);


    }

    private void playerExtrator() {
        dialogProgressBar.show();
        String url = video_url;

        if (url != null && url.contains("?")) {
            String splitarray[] = url.split("\\?");
            url = splitarray[0];
        }

        if (url != null) {

            VimeoExtractor.getInstance().fetchVideoWithURL(url, null, new OnVimeoExtractionListener() {
                @Override
                public void onSuccess(VimeoVideo video) {

                    String hdStream;
                    hdStream = video.getStreams().get("720p");
                    if (hdStream == null) {
                        hdStream = video.getStreams().get("480p");
                    }
                    if (hdStream == null) {
                        hdStream = video.getStreams().get("360p");
                    }
                    if (hdStream == null) {
                        hdStream = video.getStreams().get("270p");
                    }
                    if (hdStream == null) {
                        hdStream = video.getStreams().get("240p");
                    }
                    if (hdStream == null) {
                        hdStream = video.getStreams().get("144p");
                    }

                    dialogProgressBar.dismiss();
                    if (hdStream != null) {
//                        placeholder2.setVisibility(View.GONE);
//                        run_video(hdStream);

//                        url = hdStream;
                        final String finalHdStream = hdStream;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initPlayer(finalHdStream);
                            }
                        });
                    }
                }

                @Override
                public void onFailure(final Throwable throwable) {
                    dialogProgressBar.dismiss();
                    runOnUiThread(new Runnable() {
                        public void run() {
//                            placeholder2.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(), "Some thing happened Wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } else {
//            placeholder2.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "Some thing happened Wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void initPlayer(String url) {
        bandwidthMeter = new DefaultBandwidthMeter();
        mediaDataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"));
        window = new Timeline.Window();
        playerView = findViewById(R.id.player_view);
        TrackSelection.Factory videoTrackSelectionFactory = new RandomTrackSelection.Factory();

        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        trackSelectorParameters = new DefaultTrackSelector.ParametersBuilder().build();
        trackSelector.setParameters(trackSelectorParameters);

        lastSeenTrackGroupArray = null;
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        playerView.setPlayer(player);
        player.addListener(new PlayerEventListener());
        player.setPlayWhenReady(true);

        MediaSource mediaSource;
        if (url.contains(".m3u8")) {
            mediaSource = new HlsMediaSource.Factory(mediaDataSourceFactory).createMediaSource(Uri.parse(url));
        } else {
            mediaSource = new ExtractorMediaSource.Factory(mediaDataSourceFactory).createMediaSource(Uri.parse(url));
        }

        boolean haveStartPosition = currentWindow != C.INDEX_UNSET;
        if (haveStartPosition) {
            player.seekTo(currentWindow, playbackPosition);
        }

        playerView.hideController();
        playerView.setControllerAutoShow(false);
        player.prepare(mediaSource, !haveStartPosition, false);
    }
    protected void onResume() {
        super.onResume();
        if (player != null) {
            releasePlayer();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            releasePlayer();
        }
    }

    @Override
    protected void onDestroy() {
        if (player != null) {
            releasePlayer();
        }
        super.onDestroy();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                hideSystemUI();
            }
            v_actionbar.setVisibility(View.GONE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            v_actionbar.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                showSystemUI();
            }
        }
    }

    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        decorView.setSystemUiVisibility(View.VISIBLE);
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
            mediaDataSourceFactory = null;
            trackSelector = null;
        }
    }

    @Override
    public void onBackPressed() {
        releasePlayer();
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_arrow: {
                onBackPressed();
                break;
            }
        }
    }

    private class PlayerEventListener extends Player.DefaultEventListener {

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            switch (playbackState) {
                case Player.STATE_IDLE:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                case Player.STATE_BUFFERING:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                case Player.STATE_READY:
                    progressBar.setVisibility(View.GONE);
                    break;
                case Player.STATE_ENDED:
                    progressBar.setVisibility(View.GONE);

                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                    }
                    break;
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
//            releasePlayer();
//            initializePlayer(m3u8Link);
        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            if (trackGroups != lastSeenTrackGroupArray) {
                MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();

                if (mappedTrackInfo != null) {
                    if (mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO) == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                        Toast.makeText(ExoPlayerActivity.this, "Error unsupported track", Toast.LENGTH_SHORT).show();
                    }
                }
                lastSeenTrackGroupArray = trackGroups;
            }
        }
    }


}

