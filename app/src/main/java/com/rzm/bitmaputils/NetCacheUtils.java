package com.rzm.bitmaputils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rzm on 2016/10/6.
 * 网络缓存
 */
public class NetCacheUtils {

    private static final String TAG = "NetCacheUtils";
    private Context context;
    //从网络获取图片
    public void getBitmapFromNet(Context context,ImageView iv, String url){
        this.context = context;
        //让ImageView和url关联起来
        iv.setTag(url);

        //异步操作
        new BitmapTask().execute(iv,url);
    }

    //异步任务
    class BitmapTask extends AsyncTask<Object,Void,Bitmap>{

        private ImageView iv;
        private String url;

        @Override
        protected Bitmap doInBackground(Object... params) {
            //获取参数
            iv = (ImageView) params[0];
            url = (String) params[1];

            //下载图片
            Bitmap bitmap = downloadBitmap(url);
            Log.i(TAG,"从网络上加载了图片");
            //执行磁盘缓存
            LocalCacheUtils.saveCache(context,bitmap,url);
            //把数据缓存在内存中
            MemoryCacheUtils.saveCache(bitmap,url);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //获取ImageView对应的url
            String url = (String) iv.getTag();
            if(bitmap != null && this.url.equals(url)){
                iv.setImageBitmap(bitmap);
            }
        }
    }

    //下载图片
    private Bitmap downloadBitmap(String url) {
        Bitmap bitmap = null;
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(6000);
            conn.connect();
            int responseCode = conn.getResponseCode();
            if(responseCode == 200){
                InputStream inputStream = conn.getInputStream();
                //把流转换成Bitmap对象
                bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(conn != null){
                conn.disconnect();
            }
        }
        return bitmap;
    }
}