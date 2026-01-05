package fr.uge.poo.weatherservice.question1;

import com.evilcorp.weatherservice.WeatherService;
import com.evilcorp.weatherservice.WeatherServiceTS;

public class WeatherServices {

  private WeatherServices() {
  }

  private static final WeatherService INSTANCE = new WeatherServiceTS();

  public static WeatherService uniqueService() {
    return INSTANCE;
  }

}
