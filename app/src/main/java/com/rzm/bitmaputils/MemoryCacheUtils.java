package com.rzm.bitmaputils;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by rzm on 2016/10/6.
 * 内存缓存：通过LruCache来进行数据的存储
 */
public class MemoryCacheUtils {

    static {
        //HashMap caches = new HashMap<>();
        // Android虚拟机的内存只有16M,容易产生OOM异常（内存溢出）
        //java语言提供了另外一种机制：软引用、弱引用、虚引用
        //软引用：当虚拟机内存不足的时候，回收软引用的对象
        //弱引用:当对象没有应用的时候，马上回收
        //虚引用 ：任何情况下都可能回收
        //java默认的数据类型是强引用类型
        //caches = new HashMap<>();

        //因为从 Android 2.3 (API Level 9)开始，垃圾回收器会更倾向于回收持有软引用或弱引用的对象，这让软引用和弱引用变得不再可靠。

        //LruCache lru:least recently used 最近最少使用的算法
        //A
        //B
        //C(最近使用的最少   优先被回收)
        //B
        //A
        long maxMemory = Runtime.getRuntime().maxMemory();//获取Dalvik 虚拟机最大的内存大小：16

        lruCache = new LruCache<String,Bitmap>((int) (maxMemory/8)){//指定内存缓存集合的大小
            //获取图片的大小
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight();
            }
        };

    }

    private static LruCache<String, Bitmap> lruCache;
    /*private static HashMap<String, SoftReference<Bitmap>> caches;
    private static HashMap<String, Bitmap> caches;*/

    //写缓存
    public static void saveCache(Bitmap bitmap,String url){
        /*caches.put(url,bitmap);
        SoftReference<Bitmap> soft = new SoftReference<Bitmap>(bitmap);
        caches.put(url,soft);*/
        lruCache.put(url,bitmap);
    }

    //读缓存
    public static Bitmap readCache(String url){
        /*Bitmap bitmap = caches.get(url);
        SoftReference<Bitmap> soft = caches.get(url);
        if(soft != null){
            Bitmap bitmap = soft.get();
            return bitmap;
        }*/
        return lruCache.get(url);
    }
}