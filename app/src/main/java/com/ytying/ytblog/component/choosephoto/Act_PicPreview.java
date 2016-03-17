package com.ytying.ytblog.component.choosephoto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.ytying.ytblog.R;
import com.ytying.ytblog.act.widget.ProgressOperatableImageView;
import com.ytying.ytblog.base.Act_Common_Linear;
import com.ytying.ytblog.utils.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by UKfire on 16/3/14.
 */
public class Act_PicPreview extends Act_Common_Linear{
    private ArrayList<ProgressOperatableImageView> photoViewList;
    private ViewPager viewPager;
    private ArrayList<String> mPathList = new ArrayList<String>();
    private ArrayList<String> tempList = new ArrayList<>();
    private ArrayList<String> mRemovePathList = new ArrayList<String>();
    private int order;
    private PagerAdapter pagerAdapter;

    @Override
    public void initData() {

        mPathList = getIntent().getBundleExtra("bundle").getStringArrayList("pathList");
        for(String s : mPathList){
            tempList.add("file://" + s);
        }
        order = getIntent().getIntExtra("order", 0);
        photoViewList = new ArrayList<ProgressOperatableImageView>();
        mRemovePathList = new ArrayList<String>();
        for (int i = 0; i < mPathList.size(); i++) {
            ProgressOperatableImageView pv = new ProgressOperatableImageView(this, 200);
            photoViewList.add(pv);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        getActionBarM().setBackgroundResId(R.color.black);
        getActionBarM().setHomeBtnAsBack(this);
        getActionBarM().getLeftOperateButtonList().get(0).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getActionBarM().addOperateButton(R.mipmap.checkbox_blue_selected, null,false);
        mParent.setBackgroundColor(Color.BLACK);

        viewPager = new ViewPager(this);
        mParent.addView(viewPager, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        pagerAdapter = new PagerAdapter() {

            public View instantiateItem(ViewGroup container, int position) {
                ProgressOperatableImageView photoView = photoViewList.get(position);
                container.addView(photoView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                return photoView;
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
                return mPathList.size();
            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
        };
        viewPager.setAdapter(pagerAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(final int arg0) {
                changePage(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

        viewPager.setCurrentItem(order);
        changePage(order);

    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent();
        Bundle b = new Bundle();
        mPathList.removeAll(mRemovePathList);
        b.putStringArrayList("pathList", mPathList);
        it.putExtra("bundle", b);
        setResult(RESULT_OK, it);
        finish();
        super.onBackPressed();
    }
    public static List<String> getResultPicList(Intent data)
    {
        return data.getBundleExtra("bundle").getStringArrayList("pathList");
    }


    public void changePage(final int arg0){



        String tittle = "    " + (arg0 + 1) + "/" + mPathList.size();
        getActionBarM().setTitle(tittle);

        Log.v("bbbb", "bbb_size=" + mRemovePathList.size());
        for (String iterable_element : mRemovePathList) {
            Log.v("bbbb", "bbb_"+iterable_element);
        }

        final ImageButton bt=(ImageButton) getActionBarM().getOperateButtonList().get(0);
        if(mRemovePathList.contains(mPathList.get(arg0)))
            bt.setImageResource(R.mipmap.checkbox_blue_normal);
        else
            bt.setImageResource(R.mipmap.checkbox_blue_selected);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.v("bbbb", "bbb_AAAAAAAA");

                if(mRemovePathList.contains(mPathList.get(arg0))){
                    Log.v("bbbb", "bbb_BBBBB");
                    mRemovePathList.remove(mPathList.get(arg0));
                    bt.setImageResource(R.mipmap.checkbox_blue_selected);
                }
                else{
                    Log.v("bbbb", "bbb_CCCCC");
                    mRemovePathList.add(mPathList.get(arg0));
                    bt.setImageResource(R.mipmap.checkbox_blue_normal);
                }
            }
        });

        final ProgressOperatableImageView POIV = photoViewList.get(arg0);

        ImageLoaderUtil.getImageLoader(Act_PicPreview.this).displayImage(tempList.get(arg0), POIV.mImageView, ImageLoaderUtil.getDioGallery(),
                new ImageLoadingListener() {

                    @Override
                    public void onLoadingStarted(String arg0, View arg1) {
                        POIV.mCircleProgress.setProgress(0);
                        POIV.mCircleProgress.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                        POIV.mCircleProgress.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onLoadingCancelled(String arg0, View arg1) {
                        // TODO Auto-generated method stub

                    }
                }, new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String imageUri, View view, int current, int total) {
                        POIV.mCircleProgress.setProgress(100 * current / (total + 1));
                    }
                });


    }
}
