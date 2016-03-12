package com.ytying.ytblog.act.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.ytying.ytblog.R;
import com.ytying.ytblog.act.widget.ProgressOperatableImageView;
import com.ytying.ytblog.act.widget.StandardProgressDialog;
import com.ytying.ytblog.base.Act_Common_Linear;
import com.ytying.ytblog.utils.DimenUtil;
import com.ytying.ytblog.utils.DoUtil;
import com.ytying.ytblog.utils.FileUtil;
import com.ytying.ytblog.utils.ImageLoaderUtil;
import com.ytying.ytblog.utils.PathUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by UKfire on 16/3/10.
 */
public class Act_PicGallery extends Act_Common_Linear {

    int order;
    ArrayList<ProgressOperatableImageView> photoViewHighList;
    ArrayList<ImageView> photoViewLowList;
    ViewPager vpHigh;
    ArrayList<String> image;
    ArrayList<String> imageHD;

    public static Intent createIntent(Context ctx, int order, ArrayList<String> image, ArrayList<String> imageHD) {
        Intent it = new Intent(ctx, Act_PicGallery.class);
        it.putExtra("order", order);
        it.putStringArrayListExtra("image", image);
        it.putStringArrayListExtra("imageHD", imageHD);
        return it;
    }

    @Override
    protected void initData() {
        try {
            order = getIntent().getIntExtra("order", 0);
            image = getIntent().getStringArrayListExtra("image");
            imageHD = getIntent().getStringArrayListExtra("imageHD");
        } catch (Exception e) {
            finish();
            e.printStackTrace();
        }

        FileUtil.createDirectory(PathUtil.getAppSavePath());
        photoViewHighList = new ArrayList<>();
        photoViewLowList = new ArrayList<>();
        for (int i = 0; i < image.size(); i++) {
            ProgressOperatableImageView pv = new ProgressOperatableImageView(this, 200);
            photoViewHighList.add(pv);
            ImageView iv = new ImageView(this);
            photoViewLowList.add(iv);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBarM().setHomeBtnAsBack(this);
        getActionBarM().setBackgroundResId(R.color.black);
        getActionBarM().addOperateButton(R.mipmap.sab_save, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new savePicture().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        }, false);

        mParent.setBackgroundResource(R.color.black);
        mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Act_PicGallery.this.finish();
            }
        });
        vpHigh = new ViewPager(this);
        vpHigh.setPadding(0, DimenUtil.dp2px(48), 0, 0);
        vpHigh.setBackgroundColor(Color.TRANSPARENT);
        mParent.addView(vpHigh, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        vpHigh.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageHD.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ProgressOperatableImageView photoView = photoViewHighList.get(position);
                photoView.setImageViewClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Act_PicGallery.this.finish();
                    }
                });
                container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                if (position == order) {
                    String title = "    " + (order + 1) + "/" + imageHD.size();
                    getActionBarM().setTitle(title);
                    final ProgressOperatableImageView POIV = photoViewHighList.get(order);
                    Bitmap bmp = ImageLoaderUtil.getImageLoader(Act_PicGallery.this).loadImageSync(image.get(position), ImageLoaderUtil.getDioSquare());
                    BitmapDrawable bd = new BitmapDrawable(bmp);
                    DisplayImageOptions dioGallery = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565).showImageOnLoading(bd)
                            .cacheInMemory(false)// 设置下载的图片是否缓存在内存中
                            .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
                            .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                            .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                            .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                            .build();// 构建完成
                    ImageLoaderUtil.getImageLoader(Act_PicGallery.this).displayImage(imageHD.get(order), POIV.mImageView, dioGallery, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String s, View view) {
                            POIV.mCircleProgress.setProgress(5);
                            POIV.mCircleProgress.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onLoadingFailed(String s, View view, FailReason failReason) {

                        }

                        @Override
                        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                            POIV.mCircleProgress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadingCancelled(String s, View view) {

                        }
                    }, new ImageLoadingProgressListener() {
                        @Override
                        public void onProgressUpdate(String imageUri, View view, int current, int total) {
                            int progress = 100 * current / (total + 1);
                            if (progress < 5)
                                progress = 5;
                            POIV.mCircleProgress.setProgress(progress);
                        }
                    });
                }

                return photoView;
            }
        });
        vpHigh.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String tittle = "    " + (position + 1) + "/" + imageHD.size();
                getActionBarM().setTitle(tittle);

                Bitmap bmp = ImageLoaderUtil.getImageLoader(Act_PicGallery.this).loadImageSync(image.get(position), ImageLoaderUtil.getDioSquare());
                BitmapDrawable bd = new BitmapDrawable(bmp);
                final ProgressOperatableImageView POIV = photoViewHighList.get(position);
                DisplayImageOptions dioGallery = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565).showImageOnLoading(bd)
                        .cacheInMemory(false)// 设置下载的图片是否缓存在内存中
                        .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
                        .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                        .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                        .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                        .build();// 构建完成
                ImageLoaderUtil.getImageLoader(Act_PicGallery.this).displayImage(imageHD.get(position), POIV.mImageView, ImageLoaderUtil.getDioSquare(),
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

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpHigh.setCurrentItem(order);
    }


    class savePicture extends AsyncTask<Void, Void, Boolean> {

        StandardProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new StandardProgressDialog(Act_PicGallery.this);
            progressDialog.setMessage("保存图片中");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Bitmap bm = ImageLoaderUtil.getImageLoader(Act_PicGallery.this).loadImageSync(imageHD.get(order));
            return saveMyBitmap(bm);
        }

        @Override
        protected void onPostExecute(Boolean state) {
            progressDialog.dismiss();
            if (state)
                DoUtil.showToast(Act_PicGallery.this, "图片被保存到SD卡中com.ytying.ytblog/save目录下");
            else
                DoUtil.showToast(Act_PicGallery.this, "图片保存失败");
        }
    }

    public static boolean saveMyBitmap(Bitmap mBitmap) {
        File f = new File(PathUtil.getAppSavePath(), System.currentTimeMillis() + ".jpg");
        try {
            f.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
