package com.yssj.myapplication.http;

public class HttpApi {


    public static String YSS_URL_ANDROID = "http://123.58.33.33/cloud-api/";
    public static String YSS_IMGURL = "http://img.yiline.com/";
    public static String APP_ID = "wx8c5fe3e40669c535";
    public static String APP_VERSION = "V3.8.6";
    public static String VERSION_CODE = "V1.32";


    /***
     * 登陆接口
     */
    public static String ACCOUNT_LOGIN = YSS_URL_ANDROID + "user/userLogin";

    /**
     * 退出登录
     */
    public static String ACCOUNT_LOGINOUT = YSS_URL_ANDROID + "user/loginout";

    /**
     * 意见反馈
     */
    public static String USER_ADDUSERFEEDBACKINFO = YSS_URL_ANDROID + "user/addUserFeedBackInfo";

    /**
     * 修改密码
     */
    public static String USER_UPDATEPWD = YSS_URL_ANDROID + "user/updatePwd";

    /**
     * 查询绑定的手机号吗
     */
    public static String USER_QUERYPHONE = YSS_URL_ANDROID + "user/queryPhone";

    /**
     * 图片验证码
     */
    public static String VCODE_GETVCODE = YSS_URL_ANDROID + "vcode/getVcode";

    /**
     * 获取短信验证码
     */
    public static String USER_GET_PHONE_CODE = YSS_URL_ANDROID + "user/get_phone_code";

    /**
     * 验证手机
     */
    public static String USER_CHECKPHONE = YSS_URL_ANDROID + "user/checkPhone";

    /****
     * 绑定手机-验证短信验证码
     */
    public static String CHECKCODE = YSS_URL_ANDROID + "user/checkCode";

    /**
     * 可抢订单接口
     */
    public static String GRABORDERS_GRABORDERSLIST = YSS_URL_ANDROID + "grabOrders/grabOrdersList";

    /**
     * 抢单详情
     */
    public static String GRABORDERS_QUERYDETAIL = YSS_URL_ANDROID + "grabOrders/queryDetail";

    /**
     * 抢单
     */
    public static String USERGRABORDERS_ADDGRABORDERS = YSS_URL_ANDROID + "userGrabOrders/addUserGrabOrders";

    /**
     * 已抢订单接口
     */
    public static String USERGRABORDERS_GRABORDERSLIST = YSS_URL_ANDROID + "userGrabOrders/grabOrdersList";

    /**
     * 已抢订单详情
     */
    public static String USERGRABORDERS_QUERYDETAIL = YSS_URL_ANDROID + "/userGrabOrders/queryDetail";

    /**
     * 交回裁片
     */
    public static String USERGRABORDERS_UPDATEBYSELECTIVE = YSS_URL_ANDROID + "userGrabOrders/updateBySelective";

    /**
     * 账户余额
     */
    public static String WALLET_MYWALLET = YSS_URL_ANDROID + "wallet/myWallet";

    /**
     * 账户明细
     */
    public static String WALLET_FINDFUNDDETAIL = YSS_URL_ANDROID + "wallet/findFundDetail";
}
