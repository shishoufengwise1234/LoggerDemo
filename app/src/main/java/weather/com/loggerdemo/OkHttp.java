package weather.com.loggerdemo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ${shishoufeng} on 16/12/14.
 * email:shishoufeng1227@126.com
 */
public class OkHttp {

    private static OkHttpClient client;

    public static OkHttpClient getClient(){

        if (client != null)
            return client;

        //自定义logger 网络拦截器 打印网络请求数据
        LoggerHttpInterceptor loggerHttpInterceptor = new LoggerHttpInterceptor();

        client = new OkHttpClient.Builder()
                .addInterceptor(loggerHttpInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "Application/json")
                                .build();
                        return chain.proceed(request);
                    }
                })
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(20,TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        return client;
    }
}
