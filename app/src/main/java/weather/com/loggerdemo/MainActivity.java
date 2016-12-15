package weather.com.loggerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

//    private static final String BASE_URL = "http://apis.baidu.com/";
    private static final String BASE_URL = "https://free-api.heweather.com/";

    //API KEY 第三方数据提供商
    private static final String API_KEY = "83e60ba7ede9476ea91e0d6a50c4863f";

    EditText editSearch;

    private TextView tvResult;

    APIService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Logger.init(TAG);

        int number = 10;
        String id = "1323290412";

        String message = "msg";

        String name = "小明";

        List<String> strings = new ArrayList<>(Arrays.asList("北京","上海"));

        List<City> cityList = new ArrayList<>(Arrays.asList(
                        new City("北京"),
                        new City("上海"),
                        new City("广州"),
                        new City("杭州")));


        loggerI(message,id,name,number);

        loggerD(strings,cityList);

        LoggerJosn();

        LoggerXml();

        Logger.t(TAG).d(message);

        editSearch = (EditText) findViewById(R.id.edit_search);

        tvResult = (TextView) findViewById(R.id.tv_result);

        initApiService();

        findViewById(R.id.btn_loadData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadData();
            }
        });
    }

    private void initApiService(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(OkHttp.getClient())
                .build();


        api = retrofit.create(APIService.class);
    }

    /**
     *
     * 网络请求数据
     */
    private void loadData() {

        String name = editSearch.getText().toString().trim();

        if (name == null || TextUtils.equals(name,"")){
            Toast.makeText(MainActivity.this,"请输入查询内容",Toast.LENGTH_SHORT).show();
            return;
        }
        api.getWeather(name,API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        //打印错误日志
                        if (e != null)
                            Logger.e(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody response) {

                        if (response != null){

                            try {
                                String jsonMsg = response.string();

                                //打印Josn 数据
                                Logger.json(jsonMsg);

                                if (jsonMsg != null){

                                    tvResult.setText(jsonMsg);
                                }


                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }

    private void LoggerXml() {

        //简单模拟xml数据格式
        String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" +
                "<note>\n" +
                "<to>George</to>\n" +
                "<from>John</from>\n" +
                "<heading>Reminder</heading>\n" +
                "<body>Don't forget the meeting!</body>\n" +
                "</note>";

        Logger.xml(xml);

    }

    private void LoggerJosn() {

        /**
         * 生成json 用于测试
         */
        City city1 = new City("北京");
        City city2 = new City("上海");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cityName1",city1.getCityName());
            jsonObject.put("cityName2",city2.getCityName());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Logger.json(jsonObject.toString());
    }

    private void loggerD(List<String> strings, List<City> cityList) {

        Logger.d(strings);

        Logger.d(cityList);


    }

    private void loggerI(String message, String id, String name, int number) {

        Logger.i(message);

        Logger.i(message+" %s %d",name,number);

    }
}
