package com.ytying.ytblog.network;

/**
 * Created by UKfire on 16/3/11.
 */
public class RequestFactoryFile {

    private static final String ROOT_URL = "http://172.19.142.128:8000/hzx/";

    private static final String UPLOAD_HEADIMAGE = "headImage/";
    private static final String ADDDESIGN = "addDesign/";
    private static final String ADDDESIGNIMAGEANDROID = "addDesignImageAndroid/";

    public static Request UploadObject(String url, String file) {
        return new Request(ROOT_URL + url, file);
    }

    public static Request UploadHeadImage(String file) {
        return UploadObject(UPLOAD_HEADIMAGE, file);
    }

    public static Request AddDesign(String file, String content) {
        return new Request(ROOT_URL + ADDDESIGN, file)
                .add("content", content);
    }

}
