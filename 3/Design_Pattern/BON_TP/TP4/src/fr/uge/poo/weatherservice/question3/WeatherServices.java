package fr.uge.poo.weatherservice.question3;

import com.evilcorp.weatherservice.WeatherService;
import com.evilcorp.weatherservice.WeatherServiceTSFail;

import java.util.Optional;

public class WeatherServices {

  private WeatherServices() {
  }

  private static final Object LOCK = new Object();

  private static WeatherService INSTANCE;

  public static Optional<WeatherService> uniqueService() {
    synchronized (LOCK) {
      try {
        if (INSTANCE != null) {
          return Optional.of(INSTANCE);
        }

        INSTANCE = new WeatherServiceTSFail();
        return Optional.of(INSTANCE);

      } catch (Exception e) {
        return Optional.empty();
      }
    }
  }
}
