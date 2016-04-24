package com.ytying.ytblog.network;

/**
 * Created by UKfire on 16/3/11.
 */
public class RequestFactoryFile {

    private static final String ROOT_URL = "http://120.26.213.87:1234/";

    private static final String UPLOAD_HEADIMAGE = "headImage/";
    private static final String UPLOAD_BACKIMAGE = "backImage/";
    private static final String ADDDESIGN = "addDesign/";
    private static final String ADDDESIGNIMAGEANDROID = "addDesignImageAndroid/";

    private static final String ADDBLOG_PIC = "addBlogPic/";

    public static Request UploadObject(String url, String file) {
        return new Request(ROOT_URL + url, file);
    }

    public static Request UploadHeadImage(String file) {
        return UploadObject(UPLOAD_HEADIMAGE, file);
    }

    public static Request UploadBackImage(String file) {
        return UploadObject(UPLOAD_BACKIMAGE, file);
    }

    public static Request AddBlogPic(String file, String funId) {
        return new Request(ROOT_URL + ADDBLOG_PIC, file)
                .add("funId", funId);
    }
}
