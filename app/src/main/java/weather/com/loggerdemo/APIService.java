package weather.com.loggerdemo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ${shishoufeng} on 16/12/14.
 * email:shishoufeng1227@126.com
 */
public interface APIService {


    /***
     * 简单测试网络请求
     * @param name
     * @return
     */
    @Headers({"apikey:96869e36bb22dcb3599d6e4a91cc18eb"})
    @GET("tngou/cook/")
    Call <ResponseBody> getCook(@Query("name") String name);

    /***
     * 获取天气数据
     * @param cityName
     * @param key
     * @return
     */
    @GET("v5/forecast")
    Observable<ResponseBody> getWeather(@Query("city") String cityName, @Query("key") String key);



}
