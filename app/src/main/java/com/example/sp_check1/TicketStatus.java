package com.example.sp_check1;

public class TicketStatus {
    private String sensor= "";
    private static TicketStatus instance;
    private String statusT="";

    private TicketStatus() {
        // Private constructor to prevent instantiation
    }

    public static synchronized TicketStatus getInstance() {
        if (instance == null) {
            instance = new TicketStatus();
        }
        return instance;
    }

    public void setStatusT(String statusT) {
        this.statusT = statusT;
    }
    public String getStatusT() {
        return statusT;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }
}


