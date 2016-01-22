package demo.iflytek.com.mysocketclient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iflytek.android.framework.annotation.ViewInject;
import com.iflytek.android.framework.base.BaseActivity;
import com.iflytek.android.framework.toast.BaseToast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends BaseActivity {

    /**
     * ip地址
     */
    @ViewInject(id=R.id.et)
    public EditText ip;
    /**
     * 内容展示区域
     */
    @ViewInject(id=R.id.tv1)
    private TextView tvContent;
    /**
     * 输入的内容
     */
    @ViewInject(id=R.id.et2)
    private EditText content;
    /**
     * 连接的点击按钮
     */
    @ViewInject(id=R.id.button,listenerName = "onClick",methodName = "clickDeal")
    private Button bt;
    /**
     * 发送消息的点击按钮
     */
    @ViewInject(id=R.id.button2,listenerName = "onClick",methodName = "clickDeal")
    private Button bt2;

    /**
     * socket对象
     */
    private Socket socket=null;

    /**
     * socket对象
     */
    private BufferedWriter bufferedWriter=null;

    /**
     * socket对象
     */
    private BufferedReader bufferedReader=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickDeal(View view){
        switch (view.getId()){
            case R.id.button:
                connect();
                break;
            case R.id.button2:
                send();
                break;
            default:
                break;

        }
    }


    public void connect(){
        final String ipstr = ip.getText().toString();
        AsyncTask<Void,String,Void> read = new AsyncTask<Void, String, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    socket = new Socket(ipstr,12345);
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    publishProgress("@success");
                } catch (UnknownHostException e){
                    BaseToast.showToastNotRepeat(MainActivity.this,"连接失败",1000);
                    e.printStackTrace();
                }catch (IOException e) {
                    BaseToast.showToastNotRepeat(MainActivity.this,"连接失败",1000);
                    e.printStackTrace();
                }

                String line;

                try {
                    while((line=bufferedReader.readLine())!=null){
                        publishProgress(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


                return null;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                if(values[0].equals("@success")){
                    BaseToast.showToastNotRepeat(MainActivity.this,"连接成功",1000);
                }
                tvContent.append("别人说："+values[0]);
                super.onProgressUpdate(values);
            }
        };
        read.execute();


    }

    public void send(){
        try {
            tvContent.setText("我说："+content.getText().toString()+"\n");
            bufferedWriter.write(content.getText().toString());
            bufferedWriter.flush();
            bufferedWriter.notify();
            content.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
