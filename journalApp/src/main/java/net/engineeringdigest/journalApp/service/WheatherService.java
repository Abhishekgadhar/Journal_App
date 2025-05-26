package net.engineeringdigest.journalApp.service;


import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class WheatherService {


    @Value("${weather.api.key}")
    private  String api_key;

//
//    private static final String api ="http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;


    public WeatherResponse getWeather(String city){
        String finalapi =  appCache.APP_CACHE.get("weather_api").replace("<API_KEY>", api_key).replace( "<CITY>",city);
        System.out.println(finalapi);
        try {
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalapi, HttpMethod.GET,null, WeatherResponse.class);
            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Body: " + response.getBody());
            return  response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  null;
    }

    // vdo 29
//    4 steps
//   1. Resttemplate create
//   2. Add controller
//   3. write service for api (external)
//   4. create pojo and pass the pojo in service
}
