package fr.cekogha.coursemanager.dto;

import jdk.jfr.Event;

public enum EventType {
    Course("course"), Partant("partant");

    String value;

    private EventType(String value){
        this.value = value;
    }

    public String value(){
        return this.value;
    }
}
