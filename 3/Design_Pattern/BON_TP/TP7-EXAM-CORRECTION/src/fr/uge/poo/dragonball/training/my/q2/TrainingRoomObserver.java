package fr.uge.poo.dragonball.training.my.q2;

public

interface TrainingRoomObserver {
    void onNewFighter(TrainingRoom.Fighter fighter);
    void onLeaveFighter(TrainingRoom.Fighter leaveFighter);
    void newDay();
}
