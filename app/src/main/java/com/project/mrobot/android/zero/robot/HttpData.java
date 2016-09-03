package com.project.mrobot.android.zero.robot;

import android.os.AsyncTask;
import android.os.Message;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/9/1.
 */
public class HttpData extends AsyncTask<String, Void, String>{
    private HttpClient mHttpClient;
    private HttpPost mHttpPost;
    private HttpResponse mHttpResponse;
    private HttpEntity mHttpEntity;
     private HttpPostDataListener listener;

    private String url;
    public HttpData(String url,HttpPostDataListener listener) {
        this.url = url;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            mHttpClient=new DefaultHttpClient();
            mHttpPost=new HttpPost(url);

            mHttpResponse= mHttpClient.execute(mHttpPost);
            mHttpEntity=mHttpResponse.getEntity();
            String result= EntityUtils.toString(mHttpEntity, "UTF-8");
            return  result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        listener.getDataUrl(s);
        super.onPostExecute(s);
    }
}
