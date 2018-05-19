package com.rzm.bitmaputils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by rzm on 2016/10/6.
 * 磁盘缓存（该应用程序的cache目录下的zhbj_cache目录）
 */
public class LocalCacheUtils {

    private static final String CACHE_DIR = "cache_dir";

    //写缓存
    public static void saveCache(Context context,Bitmap bitmap, String url){
        //缓存目录
        File dir = new File(context.getCacheDir(),CACHE_DIR);
        if(!dir.exists()){
            dir.mkdirs();
        }

        //把图片缓存在缓存目录
        File file = new File(dir, EncryptUtil.toMD5(url));
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
    }

    //读缓存
    public static Bitmap readCache(Context context,String url){
        File dir = new File(context.getCacheDir(),CACHE_DIR);
        if(!dir.exists()){
           return null;
        }
        File file = new File(dir,EncryptUtil.toMD5(url));
        if(!file.exists()){
            return null;
        }
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        //把数据缓存在内存中
        MemoryCacheUtils.saveCache(bitmap,url);
        return bitmap;
    }
}