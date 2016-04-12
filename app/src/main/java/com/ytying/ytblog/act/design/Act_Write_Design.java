package com.ytying.ytblog.act.design;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ytying.ytblog.R;
import com.ytying.ytblog.act.widget.ActionBarLayout;
import com.ytying.ytblog.act.widget.ChoosedPicBox;
import com.ytying.ytblog.base.BaseActivity;
import com.ytying.ytblog.component.choosephoto.Act_PicPreview;
import com.ytying.ytblog.component.choosephoto.PicBoxAdpter;
import com.ytying.ytblog.component.emotion.SmileLayout;
import com.ytying.ytblog.utils.DimenUtil;

import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by UKfire on 16/3/13.
 */
public class Act_Write_Design extends BaseActivity {

    private ImageView emoji;
    private SmileLayout smileLayout;
    private RelativeLayout llUp;
    private AppCompatEditText editText;
    private ActionBarLayout actionbar;
    private PicBoxAdpter picboxAdpter;
    private ChoosedPicBox picbox;

    private ArrayList<String> pathList;

    private int type;

    public static Intent createIntent(Context context, int type) {
        Intent intent = new Intent(context, Act_Write_Design.class);
        intent.putExtra("type", type);
        return intent;
    }

    @Override
    protected void initData() {
        type = getIntent().getIntExtra("type", 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_write_sth);
        pathList = new ArrayList<>();
        actionbar = (ActionBarLayout) findViewById(R.id.act_write_actionbar);
        llUp = (RelativeLayout) findViewById(R.id.llUp);
        editText = (AppCompatEditText) findViewById(R.id.blogEditText);
        emoji = (ImageView) findViewById(R.id.emoji);
        picbox = (ChoosedPicBox) findViewById(R.id.choosedPicBox);
        smileLayout = (SmileLayout) findViewById(R.id.write_smile_panel);

        if (type != 0) {
            actionbar.setTitle("秀设计稿");
        } else {
            actionbar.setTitle("发动态");
        }

        smileLayout.setVisibility(View.GONE);
        smileLayout.init(editText);
        emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();
                if (smileLayout.getVisibility() == View.GONE)
                    smileLayout.setVisibility(View.VISIBLE);
                else
                    smileLayout.setVisibility(View.GONE);
            }
        });
        llUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smileLayout.setVisibility(View.GONE);
                showKeyboard();
            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smileLayout.setVisibility(View.GONE);
                showKeyboard();
            }
        });

        actionbar.addOperateButton(R.mipmap.nav_icon_confirm, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type != 0 && !editText.getText().toString().equals("")) {
                    sendDesign();
                } else if (type == 0 && editText.getText().toString().equals("")) {
                    sendBlog();
                }
            }

        }, false);

        // 图片选择
        picbox.init(DimenUtil.getWidth(), pathList);
        picboxAdpter = new PicBoxAdpter(this, pathList, 2);
        picbox.update(picboxAdpter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ChoosedPicBox.CHOOSE_PHOTO:
                    pathList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    picbox.update(new PicBoxAdpter(Act_Write_Design.this, pathList, 2));
                    break;
                case ChoosedPicBox.PIC_PREVIEW:
                    pathList = (ArrayList<String>) Act_PicPreview.getResultPicList(data);
                    picbox.update(new PicBoxAdpter(Act_Write_Design.this, pathList, 2));
                    break;
            }
        }
    }

    /**
     * 发生活圈
     */
    private void sendBlog() {

    }

    /**
     * 发设计稿
     */
    private void sendDesign() {

    }

    /**
     * 隐藏键盘
     */
    public void hideKeyBoard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null) {
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public void showKeyboard() {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
    }
}
