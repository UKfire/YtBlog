package com.ytying.ytblog.act.person;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ytying.ytblog.R;
import com.ytying.ytblog.act.widget.ActionBarLayout;
import com.ytying.ytblog.base.BaseActivity;
import com.ytying.ytblog.network.CallBack;
import com.ytying.ytblog.network.Network;
import com.ytying.ytblog.network.RequestFactoryFile;
import com.ytying.ytblog.network.Response;
import com.ytying.ytblog.utils.BmpUtil;
import com.ytying.ytblog.utils.DoUtil;

import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by UKfire on 16/3/11.
 */
public class Act_PersonPage extends BaseActivity {

    private static final int REQUEST_IMAGE = 816;

    private ActionBarLayout actionbar;
    private ImageView headImage;
    private ImageView background;
    private TextView nickname;
    private ImageView sex;
    private TextView motto;
    private ListView listView;

    private ArrayList<String> pathList = new ArrayList<>();

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personpage);
        actionbar = (ActionBarLayout) findViewById(R.id.actionbar);
        headImage = (ImageView) findViewById(R.id.headImage);
        background = (ImageView) findViewById(R.id.background);
        nickname = (TextView) findViewById(R.id.nickname);
        sex = (ImageView) findViewById(R.id.sex);
        motto = (TextView) findViewById(R.id.motto);
        listView = (ListView) findViewById(R.id.listview);

        actionbar.setTitle("个人主页");
        actionbar.setHomeBtnAsBack(this);
        Refresh();
        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Act_PersonPage.this, MultiImageSelectorActivity.class);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
                intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, pathList);
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });
    }

    private void Refresh() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE:
                    List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    uploadHeadImage(path);
                    break;
            }
        }
    }

    private void uploadHeadImage(List<String> path) {
        if (path.size() > 0) {
            String s = path.get(0);
            BmpUtil.DefinationLevel dLevel = BmpUtil.DefinationLevel.int2enum(2);
            String file = "";
            try {
                Bitmap bmp = BmpUtil.compressImageFromFile(s, dLevel);
                file = BmpUtil.compressBmpToFile(bmp, dLevel);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Network.uploadFile(RequestFactoryFile.UploadHeadImage(file), new Handler(), new CallBack() {
                @Override
                public void onCommon(Response response) {

                }

                @Override
                public void onError(Response response) {
                    DoUtil.showToast(Act_PersonPage.this, "上传失败");
                }

                @Override
                public void onSuccess(Response response) {
                    DoUtil.showToast(Act_PersonPage.this, "上传成功");
                }
            });
        } else {

        }
    }
}
