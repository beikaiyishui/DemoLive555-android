package com.example.ruanxuan.demolive555;

import android.util.Log;

/**
 * Created by ruanxuan on 16/10/4.
 */

public class RtpClient {
    private Thread mRtpClientThredad = null;
    private static long count = 0;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("liblive555");
    }

    public void start(){
        Log.i("rx","rtp client start");
        if(mRtpClientThredad != null)
            return;
        Log.i("rx","create rtp client thread");
        mRtpClientThredad = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("rx","start test rtsp thread");
//                setRtpClientEnv();
                if(rtpClientMain() == 1){
                    Log.i("rx ","return success");

                }else{

                    Log.i("rx ","return fail");
                }
            }
        });
        mRtpClientThredad.start();
    }

    public void stop(){
        mRtpClientThredad.destroy();
        mRtpClientThredad = null;
    }

    public static void getFrame(int size,int num){
//        Log.i("rx","["+count+++"]size:"+size + "   num:"+num);
    }

    public void testCallback(int m,int n){
        Log.i("rx","C++ call test callback[m="+m+"  N="+n+"]");
        return;
    }

    /**
     * A native method that is impl
     * emented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native int rtpClientMain();
//    public native void setRtpClientEnv();
}
