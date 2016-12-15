package weather.com.loggerdemo;

/**
 * Created by ${shishoufeng} on 16/12/14.
 * email:shishoufeng1227@126.com
 */
public class City {

    private  String cityName;

    public City(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityName='" + cityName + '\'' +
                '}';
    }
}
