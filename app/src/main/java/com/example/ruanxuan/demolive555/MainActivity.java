package com.example.ruanxuan.demolive555;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RunnableFuture;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {

    RtpClient mRtpClient;
    private SurfaceView mSv;
    private MediaPlayer mMediaPlayer = null;
    private SurfaceHolder mSurfaceHolder = null;
    private  String videoUri = "rtsp://218.204.223.237:554/live/1/66251FC11353191F/e7ooqwcfbqjoo80j.sdp";
//    private  String videoUri = "rtsp://192.168.1.100/H264?W=720&W=400&BR=1000000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myHandle handler = new myHandle();

        if(mMediaPlayer == null){
            Log.e("rx","create Media Player");
            mMediaPlayer = new MediaPlayer();
        }

        mSv = (SurfaceView)findViewById(R.id.previewId);
        mSurfaceHolder = mSv.getHolder();

        mSurfaceHolder.addCallback(svCB);
        mSv.getHolder().setFixedSize(720,400);

        mRtpClient = new RtpClient();

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(mRtpClient.stringFromJNI());

        Log.i("rx","start play");
//        new  Thread(new Runnable() {
//            @Override
//            public void run() {
//                play();
//            }
//        }).start();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                play();
            }
        };

        handler.postDelayed(runnable,1000);

//        play();
        Log.i("rx","start play done");

//        mRtpClient.start();

        Log.i("rx","test callback start");

    }

    private SurfaceHolder.Callback svCB = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.i("rx","surface created");
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.i("rx","surface changed");
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            mMediaPlayer.release();
            Log.i("rx","surface destoryed");
        }
    };

    private Map<String, String> createHeaders() {


        Map<String, String> headers =  new HashMap<String, String>();
        String camUser = "guest";//m_sharedPref.getString(AppConfig.CAM_USER,"");
        String camPassword = "tseug";//m_sharedPref.getString(AppConfig.CAM_PASSWORD,"");
        String describe = "DESCRIBE " + videoUri + " RTSP/1.0";
        String accept = "application/sdp";
        String basicAuthValue = "";

        if (camUser != "") {
            String credentials = camUser + ":" + camPassword;
            byte[] bytes = credentials.getBytes();
            int flags = Base64.URL_SAFE|Base64.NO_WRAP;
            basicAuthValue = "Basic " + Base64.encodeToString(bytes, flags);
            headers.put("Authorization", basicAuthValue);
        }

        headers.put("Request", describe);
        headers.put("Accept", accept);
        Log.i("rx", "Describe: " + describe);
        Log.i("rx", "Authorization: " + basicAuthValue);
        Log.i("rx", "Accept: " + accept);
        return headers;
    }

    private void play(){
        if(mMediaPlayer == null){
            Log.e("rx","create Media Player");
            mMediaPlayer = new MediaPlayer();
        }
        Log.e("rx","set error listen");
        setErrorListen();
        Log.e("rx","set Media Player display");
        mMediaPlayer.setDisplay(mSurfaceHolder);
        Log.e("rx","set On Prepared Listener");
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.i("rx","Media player on Prepared");
                mMediaPlayer.start();
            }
        });
        Map<String,String> head = createHeaders();
        try {
            Log.e("rx","set data source");
            mMediaPlayer.setDataSource(getApplicationContext(), Uri.parse(videoUri),head);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.e("rx","IOException");
            e.printStackTrace();
        }
    }

    private void setErrorListen(){
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                switch (extra){
                    case MediaPlayer.MEDIA_ERROR_IO:
                        Log.e("rx","MEDIA_ERROR_IO");
                        break;
                    case MediaPlayer.MEDIA_ERROR_MALFORMED:
                        Log.e("rx","MEDIA_ERROR_MALFORMED");
                        break;
                    case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                        Log.e("rx","MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK");
                        break;
                    case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                        Log.e("rx","MEDIA_ERROR_SERVER_DIED");
                        break;
                    case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                        Log.e("rx","MEDIA_ERROR_TIMED_OUT");
                        break;
                    case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                        Log.e("rx","MEDIA_ERROR_UNKNOWN");
                        break;
                    case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                        Log.e("rx","MEDIA_ERROR_UNSUPPORTED");
                        break;
                }
                return false;
            }
        });
    }

    class myHandle extends android.os.Handler{
        @Override
        public void handleMessage(Message msg) {
//            play();
        }
    }

}
