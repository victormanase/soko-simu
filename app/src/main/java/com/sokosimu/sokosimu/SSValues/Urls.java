package com.sokosimu.sokosimu.SSValues;

import com.sokosimu.sokosimu.MainActivity;



public class Urls {
    public final static String IP_ADDRESS = "http://172.104.170.154/";
    public final static String API = "api/";
    public final static String CATEGORIES = IP_ADDRESS + API + "categories/";
    public final static String PRODUCTS = IP_ADDRESS + API + "products";
    public final static String PRDOUCT = IP_ADDRESS + API + "product/";
    public final static String IMAGES_LINK = IP_ADDRESS + "images/";
    public final static String PROFILE = IP_ADDRESS + API + "profile/";

    public final static String REDIRECT_PAGE = IP_ADDRESS + API + "redirect/";
    public final static String BACK_URL = IP_ADDRESS + API + "backurl";

    public final static String ORDERS = IP_ADDRESS + API + "orders";
    public final static String SEND_TOKEN_ORDERS = IP_ADDRESS + API + "orders/";

    public final static String REQUEST_TOKEN_URL = "https://secure.sandbox.directpay.online/API/v6/";
    public final static String PAYMENT_PAGE_URL = "https://secure.sandbox.directpay.online/payv2.php?ID=";

    public final static String API_TOKEN = MainActivity.api_token;

}