package com.ytying.ytblog.component.chatbox;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ytying.ytblog.R;
import com.ytying.ytblog.utils.DimenUtil;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by UKfire on 16/4/16.
 */
public class VoicePanel extends FrameLayout {

    final static int MAX_SECOND = 60;

    CallBack callback = null;

    private TextView tvTime;

    private ImageView btRecord;

    private Button btCancel;

    Timer mTimer;
    TimerTask mTimerTask;
    int recordTime;
    PowerManager.WakeLock wakeLock;

    private float center = DimenUtil.getWidth() / 2;

    private boolean send = true;

    public Handler changeMicPicHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    Handler mHandler = new Handler();

    public VoicePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VoicePanel(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.cpnt_voice_panel, this);
        btCancel = (Button) findViewById(R.id.voice_cancel);
        btRecord = (ImageView) findViewById(R.id.btRecord);
        tvTime = (TextView) findViewById(R.id.tvTime);
        btCancel.setVisibility(INVISIBLE);

        setOnTouchListener();
    }

    private void setOnTouchListener() {
        btRecord.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionIndex() > 0)
                    return true;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        return true;
                    case MotionEvent.ACTION_MOVE:

                        return true;
                    case MotionEvent.ACTION_UP:

                        return true;

                    default:

                        return false;
                }
            }
        });
    }

    private void onErrorStop(String toast) {

    }

    public void setCallBack(CallBack callback) {
        this.callback = callback;
    }

    public interface CallBack {
        void onSuccRecord(File file, int length);
    }

    public void onActivityPause() {

    }


}
