package com.ytying.ytblog.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.ytying.ytblog.R;

/**
 * Created by UKfire on 16/3/10.
 */
public class ImageLoaderUtil {

    private static ImageLoader newImageLoader;
    private static DisplayImageOptions dioRound;
    private static DisplayImageOptions dioSquare;

    public static ImageLoader getImageLoader(Context ctx){
        if(newImageLoader == null){
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(ctx)
                    .threadPoolSize(3)
                    .threadPriority(Thread.NORM_PRIORITY - 2)
                    .denyCacheImageMultipleSizesInMemory()
                    .tasksProcessingOrder(QueueProcessingType.LIFO).defaultDisplayImageOptions(DisplayImageOptions.createSimple()).imageDownloader(new BaseImageDownloader(ctx, 5 * 1000, 30 * 1000))
                    .build();
            ImageLoader.getInstance().init(config);
            newImageLoader = ImageLoader.getInstance();
        }
        return newImageLoader;
    }

    public static DisplayImageOptions getDioSquare()
    {
        if(dioSquare==null)
        {
            dioSquare = new DisplayImageOptions.Builder()
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .showImageOnLoading(R.color.gray_five) //设置图片在下载期间显示的图片
                    .showImageForEmptyUri(R.color.gray_five)//设置图片Uri为空或是错误的时候显示的图片
                    .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                    .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
                    .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                    .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
                    .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                    .build();//构建完成
        }
        return dioSquare;
    }

    public static DisplayImageOptions getDioRound()
    {
        if(dioRound==null)
        {
            dioRound = new DisplayImageOptions.Builder()
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .showImageOnLoading(R.drawable.round_head_grey) //设置图片在下载期间显示的图片
                    .showImageForEmptyUri(R.mipmap.icon_myfriends)//设置图片Uri为空或是错误的时候显示的图片
                    .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                    .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
                    .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                    .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
                    .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                    .displayer(new RoundedBitmapDisplayer(1000))//是否设置为圆角，弧度为多少
                    .build();//构建完成
        }
        return dioRound;
    }

}