package fr.uge.poo.dragonball.training.my.q2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InstagramObserver implements TrainingRoomObserver{

    private final List<TrainingRoom.Fighter> leaveFighters = new ArrayList<>();

    @Override
    public void onNewFighter(TrainingRoom.Fighter leaveFighter) {

    }

    @Override
    public void onLeaveFighter(TrainingRoom.Fighter leaveFighter) {
        Objects.requireNonNull(leaveFighter);
        System.out.println("Leave: " + leaveFighter.name());
        System.out.println("Previous leave: " + leaveFighters);
        leaveFighters.addFirst(leaveFighter);
    }

    @Override
    public void newDay() {

    }
}
