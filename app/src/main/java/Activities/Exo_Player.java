package Activities;
import com.example.axe.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.text.TextRenderer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SubtitleView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;

import java.util.List;


public class Exo_Player extends AppCompatActivity {
    //    String videourl1;
//    public static final String TAG = "TAG";
//    ProgressBar spiiner;
//    ImageView fullScreenOp;
//    FrameLayout frameLayout;
//    private PlayerView playerView;
//    private SimpleExoPlayer player;
//    ImageView fullscreenButton;
//    boolean fullscreen = false;
    Uri VideoURL;

    PlayerView playerView;
    ImageView btFullScreen,subtitlesselection,languageselection;
    SimpleExoPlayer simpleExoPlayer;

    boolean flag = false;
    ImageView btQuality;
    ProgressBar progressBar;
    private  SubtitleView subtitleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_exo__player);
        //  frameLayout = findViewById(R.id.frameLayout);
        // videoplayer = findViewById(R.id.exoplayer);
    }
//
//        Bundle extras = getIntent().getExtras();
//        VideoURL = Uri.parse(extras.getString("link"));
//        Log.d("url", VideoURL.toString());
////customize exoplayer////
//        progressBar=findViewById(R.id.progressBar);
//        //  player = ExoPlayerFactory.newSimpleInstance(getApplicationContext());
//        playerView = findViewById(R.id.player);
//        // fullscreenButton = playerView.findViewById(R.id.exo_fullscreen_icon);
////        fullscreenButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                if (fullscreen) {
////                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(Exo_Player.this, R.drawable.ic_fullscreen_open));
////
////                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
////
////                    if (getSupportActionBar() != null) {
////                        getSupportActionBar().show();
////                    }
////
////                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
////
////                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
////                    params.width = params.MATCH_PARENT;
////                    params.height = (int) (200 * getApplicationContext().getResources().getDisplayMetrics().density);
////                    playerView.setLayoutParams(params);
////
////                    fullscreen = false;
////                } else {
////                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(Exo_Player.this, R.drawable.ic_full_screen_close));
////
////                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
////                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
////                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
////
////                    if (getSupportActionBar() != null) {
////                        getSupportActionBar().hide();
////                    }
////
////                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
////
////                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
////                    params.width = params.MATCH_PARENT;
////                    params.height = params.MATCH_PARENT;
////                    playerView.setLayoutParams(params);
////
////                    fullscreen = true;
////                }
////
////            }
////        });
//        //  playerView.setPlayer(player);
////        player.addListener(new ExoPlayer.EventListener() {
////
////            public void onTimelineChanged(Timeline timeline, Object manifest) {}
////
////            @Override
////            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {}
////
////            @Override
////            public void onLoadingChanged(boolean isLoading) {}
////
////            @Override
////            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
////                if (playbackState == ExoPlayer.STATE_BUFFERING){
////                    progressBar.setVisibility(View.VISIBLE);
////                } else {
////                    progressBar.setVisibility(View.INVISIBLE);
////                }
////            }
////
////
////            public void onPlayerError(ExoPlaybackException error) {}
////
////
////            public void onPositionDiscontinuity() {}
////
////
////            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {}
////        });
////       // playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT);
////
////        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(), Util.getUserAgent(getApplicationContext(), getApplicationContext().getString(R.string.app_name)));
////
////        MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
////                .createMediaSource(VideoURL);
////
////
////
////        player.prepare(videoSource);
////
////        player.setPlayWhenReady(true);
////        //////////
////
////
//////        Intent intent = getIntent();
////
//////        videourl1 = intent.getStringExtra("url");
//////        Log.d(TAG, "onResponse: " + videourl1);
////
//////
//////        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//////        setupexoplayer();
////    }
////
//////    private void setupexoplayer() {
//////
//////        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this);
//////
//////        videoplayer.setPlayer(simpleExoPlayer);
//////        DataSource.Factory datasourcefatcory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "moviesapllication"));
//////        MediaSource mediaSource = new ExtractorMediaSource.Factory(datasourcefatcory).createMediaSource(VideoURL);
//////        simpleExoPlayer.prepare(mediaSource);
//////        simpleExoPlayer.setPlayWhenReady(true);
//////
//////
//////    }
////
////    //    @Override
//////    protected void onDestroy() {
//////        super.onDestroy();
//////        simpleExoPlayer.release();
//////    }
////    @Override
////    public void onPause() {
////        super.onPause();
////        player.setPlayWhenReady(false);
////    }
////
////    @Override
////    public void onDestroy() {
////        player.release();
////        super.onDestroy();
////    }
//
//
////        btFullScreen= playerView.findViewById(R.id.bt_fullscreen);
////        subtitlesselection=playerView.findViewById(R.id.subtitles);
////        //languageselection=playerView.findViewById(R.id.trackchange);
////        btQuality= playerView.findViewById(R.id.bt_quality);
////        subtitles=(SubtitleView)findViewById(R.id.subtitleh);
//
//
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        // Uri VideoUrl= Uri.parse("https://www.radiantmediaplayer.com/media/big-buck-bunny-360p.mp4");
//        // Uri subtitleUri=Uri.parse("https://firebasestorage.googleapis.com/v0/b/findandfix-2f4a9.appspot.com/o/Despacito%20Remix%20Luis%20Fonsi%20ft.Daddy%20Yankee%20Justin%20Bieber%20Lyrics%20%5BSpanish%5D.srt?alt=media&token=63344d04-af1c-4e2c-9d15-381bf7159308");
//
//
//
//        LoadControl loadControl= new DefaultLoadControl();
//
//        BandwidthMeter bandwidthMeter=  new DefaultBandwidthMeter();
//
////        TrackSelector trackSelector= new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
//
//
////        simpleExoPlayer= ExoPlayerFactory.newSimpleInstance(Exo_Player.this,trackSelector,loadControl);
//
//
//        DefaultHttpDataSourceFactory factory= new DefaultHttpDataSourceFactory("exoplayer_video");
//
//        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
//
//        MediaSource  mediaSource= new ExtractorMediaSource(VideoURL,factory,extractorsFactory,null,null);
//
//        //subtitleView=(SubtitleView)findViewById(com.google.android.exoplayer2.R.id.exo_subtitles);
//        // simpleExoPlayer.setTextOutput(new ComponentListener());
//
//
////        Format textFormat = Format.createTextSampleFormat(null, MimeTypes.APPLICATION_SUBRIP,
////                null, Format.NO_VALUE, Format.NO_VALUE, "en", null, Format.OFFSET_SAMPLE_RELATIVE);
////        MediaSource textMediaSource = new SingleSampleMediaSource.Factory(factory)
////                .createMediaSource(subtitleUri, textFormat, C.TIME_UNSET);
////        mediaSource = new MergingMediaSource(mediaSource, textMediaSource);
//
//
//
//        playerView.setPlayer(simpleExoPlayer);
//        playerView.setKeepScreenOn(true);
//        simpleExoPlayer.prepare(mediaSource);
//        simpleExoPlayer.setPlayWhenReady(true);
//
////
////        btQuality.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                PopupMenu popup = new PopupMenu(MainActivity.this, view);
////                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
////                    @Override public boolean onMenuItemClick(MenuItem item) {
////                        simpleExoPlayer.setSelectedTrack(0, (item.getItemId() - 1));
////                        return false;
////                    }
////                });
////                ArrayList<Integer> formats = new ArrayList<>();
////                Menu menu = popup.getMenu();
////                menu.add(Menu.NONE, 0, 0, "Bitrate");
////                for (int i = 0; i < simpleExoPlayer.getTrackCount(0); i++) {
////                    MediaFormat format = simpleExoPlayer.getTrackFormat(0, i);
////                    if (MimeTypes.isVideo(format.mimeType)) {
////                        Log.e("dsa", format.bitrate + "");
////                        if (format.adaptive) {
////                            menu.add(1, (i + 1), (i + 1), "Auto");
////                        } else {
////                            if (!formats.contains(format.bitrate)) {
////                                menu.add(1, (i + 1), (i + 1), (format.bitrate) / 1000 + " kbps");
////                                formats.add(format.bitrate);
////                            }
////                        }
////                    }
////                }
////
////
////                menu.setGroupCheckable(1, true, true);
////                menu.findItem((simpleExoPlayer.getSelectedTrack(0) + 1)).setChecked(true);
////                popup.show();
////            }
////
////        });
//
//
////
//
//
//
//        btQuality.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                PopupMenu popup= new PopupMenu(Exo_Player.this,view);
//
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//
//                        Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_LONG).show();
//
//                        return false;
//                    }
//                });
//
//
//                Menu menu= popup.getMenu();
//                menu.add(Menu.NONE,0,0,"Video quality");
//                popup.show();
//
//
//
//
//            }
//        });
//        languageselection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                PopupMenu popup1= new PopupMenu(Exo_Player.this,view);
//
//                popup1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//
//                        Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_LONG).show();
//
//                        return false;
//                    }
//                });
//
//
//                Menu menu= popup1.getMenu();
//                menu.add(Menu.NONE,0,0,"Change Track");
//                popup1.show();
//
//
//
//
//            }
//        });
//        subtitlesselection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                PopupMenu popup2= new PopupMenu(Exo_Player.this,view);
//
//                popup2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//
//                        Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_LONG).show();
//
//                        return false;
//                    }
//                });
//
//
//                Menu menu= popup2.getMenu();
//                menu.add(Menu.NONE,0,0,"Select Subtitles");
//                popup2.show();
//
//
//
//
//            }
//        });
//
//
//
//
//        simpleExoPlayer.addListener(new Player.EventListener() {
//            @Override
//            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
//
//            }
//
//            @Override
//            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
//
//            }
//
//            @Override
//            public void onLoadingChanged(boolean isLoading) {
//
//            }
//
//            @Override
//            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//
//                if(playbackState== Player.STATE_BUFFERING){
//
//                    progressBar.setVisibility(View.VISIBLE);
//
//                }else if(playbackState==Player.STATE_READY){
//                    progressBar.setVisibility(View.GONE);
//
//
//                }
//
//            }
//
//            @Override
//            public void onRepeatModeChanged(int repeatMode) {
//
//            }
//
//            @Override
//            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
//
//            }
//
//            @Override
//            public void onPlayerError(ExoPlaybackException error) {
//
//            }
//
//            @Override
//            public void onPositionDiscontinuity(int reason) {
//
//            }
//
//            @Override
//            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
//
//            }
//
//            @Override
//            public void onSeekProcessed() {
//
//            }
//        });
//
//        btFullScreen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(flag){
//
//                    btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_fullscreen_24));
//
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//                    flag= false;
//                }else {
//
//                    btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_fullscreen_exit_24));
//
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//
//                    flag= true;
//
//                }
//
//            }
//        });
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        simpleExoPlayer.setPlayWhenReady(false);
//        simpleExoPlayer.getPlaybackState();
//
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        simpleExoPlayer.setPlayWhenReady(true);
//        simpleExoPlayer.getPlaybackState();
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.exo_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.action_add:
//                return true;
//            case R.id.action_settings:
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    private class ComponentListener implements TextOutput {
//
//        @Override
//        public void onCues(List<Cue> cues) {
//            if (subtitleView != null) {
//                subtitleView.onCues(cues);
//            }
//        }
//    }
}


