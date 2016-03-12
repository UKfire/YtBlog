package com.ytying.ytblog.utils;


import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class BmpUtil {
	
	public enum DefinationLevel
	{
		EXTREME,	//接近原画
		HIGH,		//高清
		STANDARD;	//标清
		
		public static DefinationLevel int2enum(int a)
		{
			switch(a)
			{
			case 0:
				return EXTREME;
			case 1:
				return HIGH;
			case 2:
				return STANDARD;
			}
			return STANDARD;
		}
	};
	
	public static Bitmap view2bmp(View v)
	{
		if(v!=null)
			return getViewBitmap(v, v.getWidth(), v.getHeight());
		else
			return null;
	}
	
	public static String convertIconToString(Bitmap bitmap)  
    {  
		if(bitmap==null)
			return "";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream  
        bitmap.compress(CompressFormat.PNG, 100, baos);  
        byte[] appicon = baos.toByteArray();// 转为byte数组  
        return Base64.encodeToString(appicon, Base64.DEFAULT);  
  
    }  
  
    /**  
     * string转成bitmap  
     *   
     * @param st  
     */  
    public static Bitmap convertStringToIcon(String st)  
    {  
        // OutputStream out;  
        Bitmap bitmap = null;  
        try  
        {  
            // out = new FileOutputStream("/sdcard/aa.jpg");  
            byte[] bitmapArray;  
            bitmapArray = Base64.decode(st, Base64.DEFAULT);  
            bitmap =  
                    BitmapFactory.decodeByteArray(bitmapArray, 0,  
                            bitmapArray.length);  
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);  
            return bitmap;  
        }  
        catch (Exception e)  
        {  
            return null;  
        }  
    }  
	/**
	 * 从文件中读Bitmap
	 */
	public static Bitmap compressImageFromFile(String srcPath, DefinationLevel defination) {
		if (defination == DefinationLevel.EXTREME)
			return compressImageFromFilePrivate(srcPath, 1600, 4800);
		else if (defination == DefinationLevel.HIGH)
			return compressImageFromFilePrivate(srcPath, 1200, 1200);
		else
			return compressImageFromFilePrivate(srcPath, 600, 600);
	}

	/**
	 * 把Bitmap保存到文件中去
	 */
	public static String compressBmpToFile(Bitmap bmp, DefinationLevel defination) {
		File file = new File(PathUtil.getAppTempPath(), System.currentTimeMillis() + "_btf.jpeg");
		int maxBytes = 50;
		if (defination == DefinationLevel.EXTREME)
			maxBytes = 400;
		else if (defination == DefinationLevel.HIGH)
			maxBytes = 150;
		else
			maxBytes = 60;
		return compressBmpToFile(bmp, file, maxBytes);
	}
	
	/**
	 * 把Bitmap保存到文件中去--- 用户主动要求
	 */
	public static File compressBmpToSaveFile(Bitmap bmp, DefinationLevel defination) {
		File file = new File(PathUtil.getAppSavePath(), System.currentTimeMillis() + "_btf.jpeg");
		int maxBytes = 50;
		if (defination == DefinationLevel.EXTREME)
			maxBytes = 400;
		else if (defination == DefinationLevel.HIGH)
			maxBytes = 150;
		else
			maxBytes = 60;
		return compressBmpToFileM(bmp, file, maxBytes);
	}

	private static File compressBmpToFileM(Bitmap bmp, File file, int kb) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 80;// 个人喜欢从80开始,
		bmp.compress(CompressFormat.JPEG, options, baos);
		while (baos.toByteArray().length / 1024 > kb) {
			baos.reset();
			options -= 10;
			bmp.compress(CompressFormat.JPEG, options, baos);
			if (options == 20)
				break;
		}
		try {

			FileOutputStream fos = new FileOutputStream(file);

			fos.write(baos.toByteArray());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}


	/**
	 * 下面是私有方法，可以不关注
	 */

	private static Bitmap compressImageFromFilePrivate(String srcPath, int ww, int hh) {

		srcPath=srcPath.replace("file://", "");

		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;// 只读边,不读内容
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;

		int be = 1;
		if (w > h && w > ww) {
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置采样率

		newOpts.inPreferredConfig = Config.RGB_565;// 该模式是默认的,可不设
		newOpts.inPurgeable = true;// 同时设置才会有效
		newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收

		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return bitmap;
	}


	private static String compressBmpToFile(Bitmap bmp, File file, int kb) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 80;// 个人喜欢从80开始,
		bmp.compress(CompressFormat.JPEG, options, baos);
		while (baos.toByteArray().length / 1024 > kb) {
			baos.reset();
			options -= 10;
			bmp.compress(CompressFormat.JPEG, options, baos);
			if (options == 20)
				break;
		}
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baos.toByteArray());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file.getPath();
	}


	public static Bitmap comp(Bitmap image,float hh,float ww) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(CompressFormat.JPEG, 100, baos);
		if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();//重置baos即清空baos
			image.compress(CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		//现在主流手机比较多是800*480分辨率，所以高和宽我们设置为

		//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;//be=1表示不缩放
		if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;//设置缩放比例
		//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return bitmap;
	}


	public static Bitmap getViewBitmap(View comBitmap, int width, int height) {
		Bitmap bitmap = null;
		if (comBitmap != null) {
			comBitmap.clearFocus();
			comBitmap.setPressed(false);

			boolean willNotCache = comBitmap.willNotCacheDrawing();
			comBitmap.setWillNotCacheDrawing(false);

			// Reset the drawing cache background color to fully transparent
			// for the duration of this operation
			int color = comBitmap.getDrawingCacheBackgroundColor();
			comBitmap.setDrawingCacheBackgroundColor(0);
			float alpha = comBitmap.getAlpha();
			comBitmap.setAlpha(1.0f);

			if (color != 0) {
				comBitmap.destroyDrawingCache();
			}

			int widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
			int heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
			comBitmap.measure(widthSpec, heightSpec);
			comBitmap.layout(0, 0, width, height);

			comBitmap.buildDrawingCache();
			Bitmap cacheBitmap = comBitmap.getDrawingCache();
			if (cacheBitmap == null) {
				Log.e("view.ProcessImageToBlur", "failed getViewBitmap(" + comBitmap + ")",
						new RuntimeException());
				return null;
			}
			bitmap = Bitmap.createBitmap(cacheBitmap);
			// Restore the view
			comBitmap.setAlpha(alpha);
			comBitmap.destroyDrawingCache();
			comBitmap.setWillNotCacheDrawing(willNotCache);
			comBitmap.setDrawingCacheBackgroundColor(color);
		}
		return bitmap;
	}
}
