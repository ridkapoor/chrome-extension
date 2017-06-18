package com.extensionhandler.sro;

import java.io.Serializable;

/**
 * Created by ridkapoor on 6/6/17.
 */
public class RoomInfoSRO implements Serializable {
    private String roomType;
    private int noOfRooms;
    private int adults;
    private int[] children;

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(int noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int[] getChildren() {
        return children;
    }

    public void setChildren(int[] children) {
        this.children = children;
    }
}
