package fr.uge.poo.weatherservice.question2;

import com.evilcorp.weatherservice.WeatherService;
import com.evilcorp.weatherservice.WeatherServiceNTS;

public class WeatherServiceProxy implements WeatherService {

  private final Object lock = new Object();

  private final WeatherServiceNTS weatherServiceNTS = new WeatherServiceNTS();

  public WeatherServiceProxy() {
  }

  @Override
  public int query(String city) {
    synchronized (lock) {
      return weatherServiceNTS.query(city);
    }
  }
}
