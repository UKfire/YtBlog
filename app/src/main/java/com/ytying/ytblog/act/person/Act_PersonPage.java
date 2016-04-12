package com.ytying.ytblog.act.person;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ytying.ytblog.MyUser;
import com.ytying.ytblog.R;
import com.ytying.ytblog.YtApp;
import com.ytying.ytblog.act.widget.ActionBarLayout;
import com.ytying.ytblog.base.BaseActivity;
import com.ytying.ytblog.event.UpdateHeadViewEvent;
import com.ytying.ytblog.model.domin.DbUser;
import com.ytying.ytblog.model.domin.User;
import com.ytying.ytblog.network.CallBack;
import com.ytying.ytblog.network.Network;
import com.ytying.ytblog.network.RequestFactory;
import com.ytying.ytblog.network.RequestFactoryFile;
import com.ytying.ytblog.network.Response;
import com.ytying.ytblog.utils.BmpUtil;
import com.ytying.ytblog.utils.DoUtil;
import com.ytying.ytblog.utils.ImageLoaderUtil;
import com.ytying.ytblog.utils.JsonUtil;

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
    private ListView listView;

    private ArrayList<String> pathList = new ArrayList<>();
    private User user = null;

    @Override
    protected void initData() {
        user = (User) getIntent().getSerializableExtra("user");
        if (user == null)
            Act_PersonPage.this.finish();
    }

    public static Intent createIntent(Context ctx, User user) {
        Intent intent = new Intent(ctx, Act_PersonPage.class);
        intent.putExtra("user", user);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personpage);
        actionbar = (ActionBarLayout) findViewById(R.id.actionbar);
        headImage = (ImageView) findViewById(R.id.headImage);
        background = (ImageView) findViewById(R.id.background);
        nickname = (TextView) findViewById(R.id.nickname);
        listView = (ListView) findViewById(R.id.listview);

        actionbar.setTitle("个人主页");
        actionbar.setHomeBtnAsBack(this);
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

        updateUI(user);
    }

    private void updateUI(User user) {
        ImageLoaderUtil.getImageLoader(this).displayImage(user.getHeadImage(), headImage, ImageLoaderUtil.getDioRound());
        ImageLoaderUtil.getImageLoader(this).displayImage(user.getBackImage(), background, ImageLoaderUtil.getDioSquare());
        nickname.setText(user.getName());
    }

    private void Refresh() {
        showLoading("正在加载个人信息");
        Network.post(RequestFactory.GetUserDetail(user.getFunId()), new Handler(), new CallBack() {
            @Override
            public void onCommon(Response response) {
                stopLoading();
            }

            @Override
            public void onError(Response response) {

            }

            @Override
            public void onSuccess(Response response) {
                final User user = JsonUtil.Json2T(response.getDataString(), User.class, new User());
                DbUser.getInstance().saveUser(user);
                MyUser.updateSelf();
                YtApp.getOtto().post(new UpdateHeadViewEvent());
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        updateUI(user);
                    }
                });
            }
        });
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
                    Refresh();
                }
            });
        } else {

        }
    }

}
