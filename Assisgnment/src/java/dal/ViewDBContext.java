/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;


public class ViewDBContext extends DBContext<Student> {

    @Override
    public void insert(Student model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Student model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Student model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Student get(int id) {
        try {
            String sql = "SELECT s.stdid, s.stdname, a.present, ses.sesid, t.[description], ses.[date],g.gname, g.gid\n"
                    + "FROM Student s INNER JOIN Attandance a ON s.stdid = a.stdid \n"
                    + "INNER JOIN [Session] ses ON a.sesid = ses.sesid \n"
                    + "INNER JOIN [Group] g  ON ses.gid = g.gid \n"
                    + "INNER JOIN TimeSlot t ON ses.tid = t.tid\n"
                    + "WHERE s.stdid = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            Student s = null;
            while (rs.next()) {
                if (s == null) {

                    s = new Student();
                    s.setId(rs.getInt("stdid"));
                    s.setName(rs.getString("stdname"));

                    Group g = new Group();
                    g = new Group();
                    s.setId(rs.getInt("gid"));
                    g.setName(rs.getString("gname"));
                    g.getStudents().add(s);
                    s.getGroups().add(g);
                }

                TimeSlot slot = new TimeSlot();
                slot.setDes(rs.getString("description"));

                Attendance att = new Attendance();
                att.setPresent(rs.getBoolean("present"));
                att.setStudent(s);

                Session ses = new Session();
                ses.setId(rs.getInt("sesid"));
                ses.setDate(rs.getDate("date"));
                ses.setSlot(slot);

                att.setSes(ses);

                s.getAtts().add(att);
            }
            return s;
        } catch (SQLException ex) {
            Logger.getLogger(ViewDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Student> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public ArrayList<Group> listGroup(int id) {
        ArrayList<Group> groups = new ArrayList<>();
        try {
            String sql = "SELECT s.stdid, s.stdname, ses.sesid, t.tid,ses.[date], t.[description], g.gname, sub.subname FROM Student s\n"
                    + "                    INNER JOIN Attandance a ON s.stdid = a.stdid\n"
                    + "                    INNER JOIN [Session] ses ON a.sesid = ses.sesid\n"
                    + "                    INNER JOIN [Group] g ON ses.gid = g.gid\n"
                    + "                    INNER JOIN TimeSlot t ON ses.tid = t.tid\n"
                    + "					INNER JOIN [Subject] sub ON g.subid = sub.subid\n"
                    + "                    WHERE s.stdid = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Group g = new Group();
                g.setName(rs.getString("gname"));
                
                Subject sub = new Subject();
                sub.setName(rs.getString("subname"));
                g.setSub(sub);
                
                groups.add(g);
            }
            return groups;
        } catch (SQLException ex) {
            Logger.getLogger(ViewDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Attendance> listAttendance(int id) {
        ArrayList<Attendance> atts = new ArrayList<>();
        try {
            String sql = "SELECT s.stdid, s.stdname, a.sesid, a.present FROM Student s \n"
                    + "INNER JOIN Attandance a ON s.stdid = a.stdid\n"
                    + "WHERE s.stdid = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Attendance att = new Attendance();
                att.setPresent(rs.getBoolean("present"));
                Session ses = new Session();
                ses.setId(rs.getInt("sesid"));
                att.setSes(ses);
                atts.add(att);
            }
            return atts;
        } catch (SQLException ex) {
            Logger.getLogger(ViewDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Attendance getAttendance(int id, int sesid) {
        try {
            String sql = "SELECT s.stdid, s.stdname, a.sesid, a.present FROM Student s \n"
                    + "INNER JOIN Attandance a ON s.stdid = a.stdid\n"
                    + "WHERE s.stdid = ? AND a.sesid = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            stm.setInt(2, sesid);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Attendance att = new Attendance();
                att.setPresent(rs.getBoolean("present"));
                
                Session ses = new Session();
                ses.setId(rs.getInt("sesid"));
                att.setSes(ses);
                return att;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ViewDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Session> listSession(int id) {
        ArrayList<Session> ses = new ArrayList<>();
        try {
            String sql = "SELECT s.stdid, s.stdname, ses.sesid, t.tid,ses.[date], t.[description], g.gname, a.present FROM Student s\n"
                    + "INNER JOIN Attandance a ON s.stdid = a.stdid\n"
                    + "INNER JOIN [Session] ses ON a.sesid = ses.sesid\n"
                    + "INNER JOIN [Group] g ON ses.gid = g.gid\n"
                    + "INNER JOIN TimeSlot t ON ses.tid = t.tid\n"
                    + "WHERE s.stdid = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Session s = new Session();
                s.setId(rs.getInt("sesid"));
                s.setDate(rs.getDate("date"));

                TimeSlot t = new TimeSlot();
                t.setDes(rs.getString("description"));
                t.setId(rs.getInt("tid"));
                s.setSlot(t);

                Group g = new Group();
                g.setName(rs.getString("gname"));
                s.setGroup(g);
                
                Student student = new Student();
                student.setId(rs.getInt("stdid"));
                student.setName(rs.getString("stdname"));
                
                Attendance att = new Attendance();
                att.setStudent(student);
                att.setSes(s);
                att.setPresent(rs.getBoolean("present"));
                s.getAtts().add(att);
                ses.add(s);
            }
            return ses;
        } catch (SQLException ex) {
            Logger.getLogger(ViewDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public ArrayList<TimeSlot> listTimeSlot(int id) {
        ArrayList<TimeSlot> tes = new ArrayList<>();
        try {
            String sql = "SELECT s.stdid, s.stdname, ses.sesid, t.tid,ses.[date], t.[description],g.gname FROM Student s\n"
                    + "INNER JOIN Attandance a ON s.stdid = a.stdid\n"
                    + "INNER JOIN [Session] ses ON a.sesid = ses.sesid\n"
                    + "INNER JOIN [Group] g ON ses.gid = g.gid\n"
                    + "INNER JOIN TimeSlot t ON ses.tid = t.tid\n"
                    + "WHERE s.stdid = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                TimeSlot t = new TimeSlot();
                t.setId(rs.getInt("tid"));
                t.setDes(rs.getString("description"));
                tes.add(t);
            }
            return tes;
        } catch (SQLException ex) {
            Logger.getLogger(ViewDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
}
