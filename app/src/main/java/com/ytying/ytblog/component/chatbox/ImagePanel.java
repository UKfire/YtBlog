package com.ytying.ytblog.component.chatbox;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.ytying.ytblog.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by UKfire on 16/4/16.
 */
public class ImagePanel extends FrameLayout {

    private ViewPager viewPager;
    private static Button sendButton;
    private TextView photo;
    private Context context;
    private List<String> newPhoto = new ArrayList<String>();
    public static List<String> select = new ArrayList<String>();

    private PhotoPagerAdapter adapter;
    public static boolean inter = false;

    private PhotoCallBack callback;

    public ImagePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI();
    }

    private void initUI() {
        inflate(getContext(), R.layout.cpnt_image_panel, this);
        viewPager = (ViewPager) findViewById(R.id.image_viewpager);
        sendButton = (Button) findViewById(R.id.image_sendbutton);
        photo = (TextView) findViewById(R.id.image_photo);
        this.context = getContext();
        getImages();
        sendButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onSendPictures(select);
            }
        });
        photo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onPlus();
            }
        });
        sendButton.setEnabled(false);
        sendButton.setClickable(false);
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                sendButton.setEnabled(false);
                sendButton.setClickable(false);
            } else if (msg.what == 2) {
                sendButton.setEnabled(true);
                sendButton.setClickable(true);
            } else if (msg.what == 5) {
                adapter = new PhotoPagerAdapter(newPhoto);
                viewPager.setAdapter(adapter);
            }
        }
    };


    public void getImages() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            inter = false;
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    CursorLoader cursorLoader = new CursorLoader(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Images.Media.DATE_MODIFIED + " desc");
                    Cursor cursor = cursorLoader.loadInBackground();
                    String pathFirst = null;

                    int lastestNum = 0;

                    while (cursor.moveToNext()) {
                        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        path = ImageDownloader.Scheme.FILE.wrap(path);
                        if (pathFirst == null && path != null) {
                            pathFirst = path;
                        }
                        newPhoto.add(path);
                        lastestNum++;
                        if (lastestNum >= 30)
                            break;
                    }
                    handler.sendEmptyMessage(5);
                }
            }).start();
            inter = true;
        }
    }

    /**
     * @param type 1 means +,2 means -
     */
    public static void sendEnabled(int type) {
        if (select.size() == 0) {
            sendButton.setEnabled(false);
            sendButton.setClickable(false);
        } else {
            sendButton.setEnabled(true);
            sendButton.setClickable(true);
        }
    }

    class PhotoPagerAdapter extends PagerAdapter {
        List<String> list;

        public PhotoPagerAdapter(List<String> list) {
            this.list = list;
        }

        // 这个方法决定了ViewPager每个页面显示的东西
        @Override
        public View instantiateItem(ViewGroup container, int position) {
            NewChatBox_Photo_Item item = new NewChatBox_Photo_Item(getContext(), list.get(position));
            item.setList(select);
            container.addView(item);
            return item;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public float getPageWidth(int position) {
            return 0.2813F;
        }
    }

    public void clearAll() {
        select.clear();
        for (int i = 0; i < viewPager.getChildCount(); i++) {
            ((NewChatBox_Photo_Item) viewPager.getChildAt(i)).check.setChecked(false);
        }
    }

    public void setCallBack(PhotoCallBack callback) {
        this.callback = callback;
    }

    public interface PhotoCallBack {
        void onSendPictures(List<String> list);

        void onPlus();
    }
}
