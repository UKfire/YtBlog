package com.ytying.ytblog.component.chatbox;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.ytying.ytblog.R;
import com.ytying.ytblog.YtApp;
import com.ytying.ytblog.component.emotion.SmileLayout;
import com.ytying.ytblog.event.KeyEmotionBoardPopupEvent;

import java.util.List;

/**
 * Created by UKfire on 16/4/16.
 */
public class ChatBox extends FrameLayout implements View.OnClickListener {

    public AppCompatEditText edittext;
    public Button sendButton;
    public FrameLayout voice;
    public FrameLayout image;
    public FrameLayout camera;
    public FrameLayout emoji;
    public RelativeLayout bottom_panel;
    public VoicePanel voicePanel;
    public ImagePanel imagePanel;
    public SmileLayout smilePanel;

    ChatBoxCallBack callback;

    public ChatBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.cpnt_chatbox, this);
        edittext = (AppCompatEditText) findViewById(R.id.newChatBox_EditText);
        sendButton = (Button) findViewById(R.id.newChatBox_Button);
        voice = (FrameLayout) findViewById(R.id.chatbox_voice);
        image = (FrameLayout) findViewById(R.id.chatbox_image);
        camera = (FrameLayout) findViewById(R.id.chatbox_camera);
        emoji = (FrameLayout) findViewById(R.id.chatbox_emoji);
        bottom_panel = (RelativeLayout) findViewById(R.id.bottom_panel);
        voicePanel = (VoicePanel) findViewById(R.id.voice_panel);
        imagePanel = (ImagePanel) findViewById(R.id.image_panel);
        smilePanel = (SmileLayout) findViewById(R.id.smile_panel);

        smilePanel.init(edittext);

        voice.setOnClickListener(this);
        image.setOnClickListener(this);
        camera.setOnClickListener(this);
        emoji.setOnClickListener(this);
        edittext.setOnClickListener(this);
        sendButton.setOnClickListener(this);

        EditTextListener();
    }

    /**
     * 监听EditText变化
     */
    private void EditTextListener() {
        onTextEmpty();
        edittext.addTextChangedListener(SimpleTextMatcher.create(new SimpleTextMatcher.TextChange() {
            @Override
            public void onTextChange(CharSequence s, int count) {
                if (TextUtils.isEmpty(edittext.getText()))
                    onTextEmpty();
                else
                    onTextNotEmpty();
            }
        }));
    }

    /**
     * EditText不为空时干什么
     */
    private void onTextNotEmpty() {
        sendButton.setTextColor(Color.WHITE);
        sendButton.setEnabled(true);
        sendButton.setClickable(true);
    }

    /**
     * EditText为空时干什么
     */
    private void onTextEmpty() {
        sendButton.setTextColor(Color.WHITE);
        sendButton.setEnabled(false);
        sendButton.setClickable(false);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chatbox_voice:
                break;
            case R.id.chatbox_camera:
                break;
            case R.id.chatbox_image:
                break;
            case R.id.chatbox_emoji:
                break;
            case R.id.newChatBox_EditText:
                HideAll();
                showKeyboard(null);
                break;
            case R.id.newChatBox_Button:
                callback.onSend(edittext.getText().toString());
                YtApp.getOtto().post(new KeyEmotionBoardPopupEvent());
                break;
            default:
                break;
        }
    }

    private void HideAll(){
        voicePanel.setVisibility(View.GONE);
        imagePanel.setVisibility(View.GONE);
        smilePanel.setVisibility(GONE);
    }

    public void showKeyboard(String text) {
        edittext.setFocusable(true);
        edittext.setFocusableInTouchMode(true);
        edittext.requestFocus();
        if(text!=null)
            edittext.setText(text);
        InputMethodManager inputManager = (InputMethodManager) edittext.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(edittext, 0);
    }

    public void setChatBoxCallBack(ChatBoxCallBack callback){
        this.callback = callback;
    }

    public interface ChatBoxCallBack{
        void hideKeyBoard();

        void onSend(String text);

        void onCamera();

        void onSendPhoto(List<String> list);

        void onPlus();

        void NotifyData();
    }
}
