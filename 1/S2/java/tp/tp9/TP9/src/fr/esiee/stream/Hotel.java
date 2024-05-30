package fr.esiee.stream;

import java.util.*;
import java.util.stream.Collectors;


public record Hotel(String name, List<Room> rooms) {
  public Hotel{
    Objects.requireNonNull(name, "Le nom de l'hotel ne peut pas être null");
    Objects.requireNonNull(rooms, "La liste des chambres de l'hotel ne peut pas être null");
  }

  public String roomInfo(){
    return rooms.stream()
        .map(room -> room.name())
        .collect(Collectors.joining(", "));
  }

  public String roomInfoSortedByFloor() {
    return rooms.stream()
        .sorted(Comparator.comparingInt(Room::floor))
        .map(Room::name)
        .collect(Collectors.joining(", "));
  }

  public double averagePrice() {
    return rooms.stream()
        .mapToLong(Room::price)
        .average().orElse(Double.NaN);
  }

  public Optional<Room> roomForPrice1(long maxPrice){
    return rooms.stream()
        .filter(room -> room.price() <= maxPrice)
        .sorted(Comparator.comparingLong(Room::price).reversed())
        .findFirst();
  }

  public Optional<Room> roomForPrice2(long maxPrice){
    return rooms.stream()
        .filter(room -> room.price() <= maxPrice)
        .max(Comparator.comparingLong(Room::price));

  }

  public static List<String> expensiveRoomNames(List<Hotel> hotels){
    return hotels.stream()
        .flatMap(hotel -> hotel.rooms.stream()
        .sorted(Comparator.comparingLong(Room::price).reversed())
        .limit(2)
        .map(Room::name))
      .collect(Collectors.toList());
  }
  
  public Map<Integer, List<Room>> roomInfoGroupedByFloor(){
    return rooms.stream()
        .collect(Collectors.groupingBy(Room::floor, Collectors.toList()));
  }
  
  public Map<Integer, List<Room>> roomInfoGroupedByFloorInOrder(){
    return rooms.stream()
        .collect(Collectors.groupingBy(Room::floor, TreeMap::new, Collectors.toList()));
  }
}
