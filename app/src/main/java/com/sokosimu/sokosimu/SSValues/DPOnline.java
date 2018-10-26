package com.sokosimu.sokosimu.SSValues;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;

import java.io.UnsupportedEncodingException;



public class DPOnline {

    private final static String COMPANY_TOKEN = "9F416C11-127B-4DE2-AC7F-D5710E4C5E0A";
    private final static String REQUEST = "createToken";
    private final static String COMPANY_REF = "49FKEOA";
    private final static String PAYMENT_CURRENCY = "TZS";
    private final static String SERVICE_TYPE = "3854";

    private float PAYMENT_AMOUNT;
    private String SERVICE_DESCRIPTION;
    private String SERVICE_DATE;
    private int ORDER_ID;

    private String REDIRECT_URL = Urls.REDIRECT_PAGE;
    private String BACK_URL = Urls.BACK_URL;

    private String REQUEST_BODY;


    private Context context;

    public DPOnline(Context c){context=c;}

    public void createToken(float pa, String sd, String sda, int oi, Response.ErrorListener errorListener, Response.Listener<String> listener){
        PAYMENT_AMOUNT = pa;
        SERVICE_DATE = sda;
        ORDER_ID = oi;
        SERVICE_DESCRIPTION = sd;
        final String REQUEST_BODY = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<API3G>\n" +
                "<CompanyToken>"+COMPANY_TOKEN+"</CompanyToken>\n" +
                "<Request>"+REQUEST+"</Request>\n" +
                "<Transaction>\n" +
                "<PaymentAmount>"+pa+"</PaymentAmount>\n" +
                "<PaymentCurrency>"+PAYMENT_CURRENCY+"</PaymentCurrency>\n" +
                "<CompanyRef>"+COMPANY_REF+"</CompanyRef>\n" +
                "<RedirectURL>"+REDIRECT_URL+oi+"</RedirectURL>\n" +
                "<BackURL>"+BACK_URL+"</BackURL>\n" +
                "<CompanyRefUnique>0</CompanyRefUnique>\n" +
                "<PTL>5</PTL>\n" +
                "</Transaction>\n" +
                "<Services>\n" +
                "  <Service>\n" +
                "    <ServiceType>"+SERVICE_TYPE+"</ServiceType>\n" +
                "    <ServiceDescription>"+SERVICE_DESCRIPTION+"</ServiceDescription>\n" +
                "    <ServiceDate>"+SERVICE_DATE+"</ServiceDate>\n" +
                //"    <ServiceDate>2013/12/20 19:00</ServiceDate>\n" +
                "  </Service>\n" +
                "</Services>\n" +
                "</API3G>";

        com.sokosimu.sokosimu.SSHelpers.RequestQueue requestQueu = new com.sokosimu.sokosimu.SSHelpers.RequestQueue(context);

        RequestQueue requestQueue = requestQueu.getRequestQueue();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Urls.REQUEST_TOKEN_URL, listener, errorListener){

            @Override
            public String getBodyContentType() {
                return "application/xml; charset=" +
                        getParamsEncoding();
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                String postData = REQUEST_BODY;
                try {
                    return postData == null ? null :
                            postData.getBytes(getParamsEncoding());
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }
        };
        requestQueue.add(strReq);
    }

}
