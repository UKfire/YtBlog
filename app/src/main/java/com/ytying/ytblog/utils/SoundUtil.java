package com.ytying.ytblog.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.ytying.ytblog.R;


public class SoundUtil {

	public static int ID_MSG_TIP;
	public static SoundPool soundPool;
	
	public static void init(Context ctx)
	{
		soundPool= new SoundPool(5,AudioManager.STREAM_SYSTEM,5);
		ID_MSG_TIP=soundPool.load(ctx, R.raw.msg_tip,1);
	}
	
	public static void play()
	{
		if(soundPool!=null)
		soundPool.play(ID_MSG_TIP, 1, 1, 0, 0, 1); 
	}
	
	
}
