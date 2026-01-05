package fr.uge.poo.weatherservice.question2;

import com.evilcorp.weatherservice.WeatherService;

public class WeatherServices {

  private WeatherServices() {
  }

  private static final WeatherService INSTANCE = new WeatherServiceProxy();

  public static WeatherService uniqueService() {
    return INSTANCE;
  }

}
