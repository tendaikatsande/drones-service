package com.musalasoft.drones.helper;

public enum DroneModel {
    Lightweight, Middleweight, Cruiserweight, Heavyweight;

    public static DroneModel getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}
