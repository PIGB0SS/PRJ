/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;


public class Group {
    private int id;
    private String name;
    private Subject sub;
    private Lecture lec;
    private ArrayList<Session> ses = new ArrayList<>();
    private ArrayList<Student> students = new ArrayList<>();
    private String sem;
    private int year;

    public Group() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subject getSub() {
        return sub;
    }

    public void setSub(Subject sub) {
        this.sub = sub;
    }

    public Lecture getLec() {
        return lec;
    }

    public void setLec(Lecture lec) {
        this.lec = lec;
    }

    public ArrayList<Session> getSes() {
        return ses;
    }

    public void setSes(ArrayList<Session> ses) {
        this.ses = ses;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
}
