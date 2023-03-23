/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author binhp
 */
public class TimeSlot {
    private int id;
    private String des;
    private ArrayList<Session> ses = new ArrayList<>();

    public ArrayList<Session> getSes() {
        return ses;
    }

    public void setSes(ArrayList<Session> ses) {
        this.ses = ses;
    }
    public TimeSlot() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
    

}