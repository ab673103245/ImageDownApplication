package qianfeng.imagedownapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageActivity extends AppCompatActivity {

    private Button btn_1;
    private Button btn_2;
    private ImageView iv_1;
    private String url = "http://a1.peoplecdn.cn/6ced1deec309e8bca43ecf15dcdd8fcf.jpg";
    private String url2 = "http://a3.peoplecdn.cn/58437834e015451ececdc3cbd45587dd.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        btn_1 = ((Button) findViewById(R.id.btn_1));
        btn_2 = ((Button) findViewById(R.id.btn_2));

        iv_1 = ((ImageView) findViewById(R.id.iv_1));



    }

    public void download_image1(View view) {  // 下载图片一
        MyTask myTask = new MyTask();
        myTask.execute(url);

    }

    public void download_image2(View view) { // 下载图片二
        MyTask myTask = new MyTask();
        myTask.execute(url2);
    }

    // 创建一个自定义类，继承自 AsyncTask
    private class MyTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            Log.d("qianfeng", "onPreExecute: ------->");
            super.onPreExecute();
        }


        @Override
        protected Bitmap doInBackground(String... params) {  // 这个是子线程要做的事
            // 子线程要下载 图片 进 Bitmap，并返回它，这个返回值会在onPosetExecute中被接收
            Log.d("qianfeng", "doInBackground: --------->");
            // 需要一个Bitmap类型的返回值。
            return http(params[0]);
        }




        @Override
        protected void onPostExecute(Bitmap bitmap) {
            // 更新图片进入UI
            Log.d("qianfeng", "onPostExecute: ");
            if(bitmap != null) {
                iv_1.setImageBitmap(bitmap);
            }
        }

    }

    private Bitmap http(String params)  // 这个是String... params
    {
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(params);
             httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5*1000);
            httpURLConnection.connect();
            if(httpURLConnection.getResponseCode()==200)
            {
                // 进行数据处理
               // 用BitMapFactory里面的decodeStream（）来接收httpURLConnection.getInputStream()
                return BitmapFactory.decodeStream(httpURLConnection.getInputStream());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(httpURLConnection != null)
            {
                httpURLConnection.disconnect();
            }
        }
        return null; // 如果不成功，那就返回null
    }
}
