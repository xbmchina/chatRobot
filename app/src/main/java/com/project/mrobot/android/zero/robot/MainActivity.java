package com.project.mrobot.android.zero.robot;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,HttpPostDataListener{
    private static final int SHOW_RESPONSE=0;
    private HttpData httpData;
    private List<ListData> lists;
    private ListView lv;
    private EditText sendtext;
    private Button send_btn;
    private String content_str;
    private TextAdapter adapter;
    private String[] welcome_array;
    private double currentTime=0,oldTime=0;


/*    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
// 在这里进行UI操作，将结果显示到界面上
                    pareText(response);
            }
        }
    };*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView(){
        lv= (ListView) findViewById(R.id.lv);
        sendtext = (EditText) findViewById(R.id.sendText);
        send_btn = (Button) findViewById(R.id.send_btn);
        lists = new ArrayList<ListData>();
        send_btn.setOnClickListener(this);
        adapter = new TextAdapter(lists, this);
        lv.setAdapter(adapter);
        /*
        * 在初始化时，显示欢迎语 */
        ListData listData;
        listData = new ListData(getRandomWelcomeTips(), ListData.RECEIVER,
                getTime());
        lists.add(listData);

    }

//添加欢迎语函数
    private String getRandomWelcomeTips() {
        String welcome_tip = null;
        welcome_array = this.getResources()
                .getStringArray(R.array.welcome_tips);
        int index = (int) (Math.random() * (welcome_array.length - 1));
        welcome_tip = welcome_array[index];
        return welcome_tip;
    }


    @Override
    public void onClick(View v) {
//        if (v.getId()==R.id.send_request){
//            sendRequestWithHttpURLConnection();
//        }

        getTime();
        content_str = sendtext.getText().toString();
        sendtext.setText("");//发送完后，清空输入框

        String dropk = content_str.replace(" ", "");//为了去掉输入带有空格的
        String droph = dropk.replace("\n", "");//为了去掉换行符

        /*本地发送显示输入框的聊天数据*/
        ListData listData;
        listData = new ListData(content_str, ListData.SEND, getTime());
        lists.add(listData);

        /*当数据大于30时，lists移除前10条信息
        * */
        if (lists.size() > 30) {
            for (int i = 0; i < lists.size(); i++) {
                lists.remove(i);
            }
        }
        adapter.notifyDataSetChanged();
        httpData= (HttpData) new HttpData("http://www.tuling123.com/openapi/api?key=a7c89e04ba23443babadfe6f595ffb0a&info="+droph,this).execute();



    }


/*
    private void sendRequestWithHttpURLConnection() {
// 开启线程来发起网络请求
        new Thread(new Runnable() {
            HttpClient client= new DefaultHttpClient();
            @Override
            public void run() {
//                HttpURLConnection connection = null;
//                    URL url = null;
//                    try {
//                        url = new URL("http://www.tuling123.com/openapi/api");
//                        connection = (HttpURLConnection) url.openConnection();
////                        connection.setRequestMethod("GET");
//                        connection.setRequestMethod("POST");
//                        DataOutputStream out=new DataOutputStream(connection.getOutputStream());
//                        out.writeBytes("key=a7c89e04ba23443babadfe6f595ffb0a&info=今天天气怎么样&loc=北京市中关村&userid=123456");
//                        connection.setConnectTimeout(8000);
//                        connection.setReadTimeout(8000);
//                        InputStream in = connection.getInputStream();
//// 下面对获取到的输入流进行读取
//                        BufferedReader reader = new BufferedReader(new
//                                InputStreamReader(in));
//                        StringBuilder response = new StringBuilder();
//                        String line;
//                        while ((line = reader.readLine()) != null) {
//                            response.append(line);
//                        }
//                        Message message = new Message();
//                        message.what = SHOW_RESPONSE;
//// 将服务器返回的结果存放到Message中
//                        message.obj = response.toString();
//                        handler.sendMessage(message);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }finally {
//                        if (connection!=null){
//                            connection.disconnect();
//                        }
//                    }

                String url="http://www.tuling123.com/openapi/api?key=a7c89e04ba23443babadfe6f595ffb0a&info=今天天气怎么样&loc=北京市中关村&userid=123456";
                HttpPost post=new HttpPost(url);

                try {

                    HttpResponse response=client.execute(post);
                    HttpEntity entity=response.getEntity();
                    String result= EntityUtils.toString(entity, "UTF-8");
                    Message message=new Message();
                    message.what=SHOW_RESPONSE;
                    message.obj=result.toString();
                    handler.sendMessage(message);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
*/

/*
* 解析图灵机器人返回的JSON数据，并显示到listview上*/
    private void pareText(String jsonData){
//        System.out.println("///"+jsonData);
        if (jsonData!=null){
        try {

                JSONObject jsonObject=new JSONObject(jsonData);
//                String id = jsonObject.getString("code");
//                String name = jsonObject.getString("text");
//                Log.d("MainActivity", "code is " + id);
//                Log.d("MainActivity", "text is " + name);
                ListData listData;
            listData=new ListData(jsonObject.getString("text"),ListData.RECEIVER,getTime());
            lists.add(listData);
            adapter.notifyDataSetChanged();

            } catch (JSONException e1) {
            e1.printStackTrace();
        }
        }else{
            ListData listData;
            listData=new ListData("咋，没网了",ListData.RECEIVER,getTime());
            lists.add(listData);
            adapter.notifyDataSetChanged();
        }
    }




    private String getTime() {
        currentTime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date();
        String str = format.format(curDate);
        if (currentTime - oldTime >= 500) {
            oldTime = currentTime;
            return str;
        } else {
            return "";
        }

    }


    /*通过一个接口，返回图灵机器的数据 */
    @Override
    public void getDataUrl(String data) {
        pareText(data);
    }
}
