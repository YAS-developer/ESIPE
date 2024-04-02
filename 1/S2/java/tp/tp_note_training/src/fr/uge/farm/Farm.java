// package fr.uge.farm;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class Farm {
	private final LinkedList<Farmable> farmList;
	private int date;
	public Farm(int date) {
		if(date < 0) {
			throw new IllegalArgumentException("la date doit être supérieur ou égale à 0.");
		}
		this.date = date;
		this.farmList = new LinkedList<Farmable>();
	}
	
	public void add(Farmable f) {
		this.farmList.add(f);
	}
	
	public int date() {
		return this.date;
	}
	
	public void setDate(int newDate) {
		this.date = newDate;
	}
	
	public List<Farmable> residents() {
		return List.copyOf(this.farmList);
	}
	
	public Stat stat(){
		var stat = new Stat();
		for(var resident: this.farmList) {
			if(resident.isCow()) {
				stat = stat.add(new Stat(1, 0));
			}
			else {
				stat = stat.add(new Stat(0, 1));
			}
		}
		return stat;
	}
	
	public LinkedHashMap<Integer, LinkedList<Farmable>> residentsByDate() {
		LinkedHashMap<Integer, LinkedList<Farmable>> mapByDate = new LinkedHashMap<>();
		
		for (Farmable resident : farmList) {
			int residentDate = resident.date();
			if(mapByDate.containsKey(residentDate)){
				LinkedList<Farmable> list = mapByDate.get(residentDate);
				list.add(resident);
				mapByDate.replace(residentDate, list);
			}
			else{
				LinkedList<Farmable> list = new LinkedList<Farmable>();
				list.add(resident);
				mapByDate.put(residentDate, list);
			}
			
			// mapByDate.computeIfAbsent(residentDate, k -> new LinkedList<>()).add(resident);
		}
		
		return mapByDate;
	}

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(var resident: this.farmList) {
			if(resident.equals(this.farmList.getLast())) {
				sb.append(resident.toString());
			}
			else {
				sb.append(resident.toString()).append(", ");
			}
		}
		
		return sb.toString();
	}
}
