package com.extensionhandler.sro;

import java.io.Serializable;

/**
 * Created by ridkapoor on 6/5/17.
 */
public class RoomSRO implements Serializable{

    private int NumberOfAdults;

    public int getNumberOfAdults() {
        return NumberOfAdults;
    }

    public void setNumberOfAdults(int numberOfAdults) {
        NumberOfAdults = numberOfAdults;
    }

    public RoomSRO(int numberOfAdults) {
        NumberOfAdults = numberOfAdults;
    }
}
