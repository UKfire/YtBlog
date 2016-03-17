package com.ytying.ytblog.utils;

import com.ytying.ytblog.F;

/**
 * Created by UKfire on 15/11/22.
 */
public class DimenUtil {

    /**
     *获取屏幕高度px
     * @return
     */
    public static int getHeight(){
        return F.AppContext.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     *获取屏幕宽度px
     * @return
     */
    public static int getWidth(){
        return F.AppContext.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * dp转换成px
     * @param dpValue
     * @return
     */
    public static int dp2px(float dpValue){
        final float scale = F.AppContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px( float spValue) {
        final float fontScale = F.AppContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int getPxPerDP()
    {
        return dp2px(1.0f);
    }
}













