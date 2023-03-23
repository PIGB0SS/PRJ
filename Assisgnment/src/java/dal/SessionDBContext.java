/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.*;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author binhp
 */
public class SessionDBContext extends DBContext<Session>{

    @Override
    public void insert(Session model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Session ses) {
        try {
            con.setAutoCommit(false);
            String sql = "UPDATE [Session] SET attanded = 1 WHERE sesid = ?";
            PreparedStatement stmSes = con.prepareStatement(sql);
            stmSes.setInt(1, ses.getId());
            stmSes.executeUpdate();
            
            String sql2 = "DELETE Attandance WHERE sesid = ?";
            PreparedStatement stmDelete = con.prepareStatement(sql2);
            stmDelete.setInt(1, ses.getId());
            stmDelete.executeUpdate();
            
            for (Attendance att : ses.getAtts()) {
                String sql3 = "INSERT INTO [Attandance]\n"
                        + "           ([sesid]\n"
                        + "           ,[stdid]\n"
                        + "           ,[present]\n"
                        + "           ,[description]\n"
                        + "           ,[record_time])\n"
                        + "     VALUES\n"
                        + "           (?\n"
                        + "           ,?\n"
                        + "           ,?\n"
                        + "           ,?\n"
                        + "           ,GETDATE())";
                PreparedStatement stmAdd = con.prepareStatement(sql3);
                stmAdd.setInt(1, ses.getId());
                stmAdd.setInt(2, att.getStudent().getId());
                stmAdd.setBoolean(3, att.isPresent());
                stmAdd.setString(4, att.getDes());
                stmAdd.executeUpdate();
            }
            con.commit();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(SessionDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(SessionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(SessionDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    @Override
    public void delete(Session model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Session get(int id) {
        try {
            String sql = "SELECT ses.sesid,ses.[index],ses.date,ses.attanded\n"
                    + "	,g.gid,g.gname\n"
                    + "	,r.rid,r.rname\n"
                    + "	,t.tid,t.[description] tdescription\n"
                    + "	,l.lid,l.lname\n"
                    + "	,sub.subid,sub.subname\n"
                    + "	,s.stdid,s.stdname\n"
                    + "	,ISNULL(a.present,0) present, ISNULL(a.[description],'') [description]\n"
                    + "		FROM [Session] ses\n"
                    + "		INNER JOIN Room r ON r.rid = ses.rid\n"
                    + "		INNER JOIN TimeSlot t ON t.tid = ses.tid\n"
                    + "		INNER JOIN Lecturer l ON l.lid = ses.lid\n"
                    + "		INNER JOIN [Group] g ON g.gid = ses.gid\n"
                    + "		INNER JOIN [Subject] sub ON sub.subid = g.subid\n"
                    + "		INNER JOIN [Student_Group] sg ON sg.gid = g.gid\n"
                    + "		INNER JOIN [Student] s ON s.stdid = sg.stdid\n"
                    + "		LEFT JOIN Attandance a ON s.stdid = a.stdid AND ses.sesid = a.sesid\n"
                    + "WHERE ses.sesid = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            Session ses = null;
            while (rs.next()) {
                if (ses == null) {
                    
                    ses = new Session();
                    //room
                    Room r = new Room();
                    r.setId(rs.getInt("rid"));
                    r.setName(rs.getString("rname"));
                    ses.setRoom(r);
                    //slot
                    TimeSlot t = new TimeSlot();
                    t.setId(rs.getInt("tid"));
                    t.setDes(rs.getString("tdescription"));
                    ses.setSlot(t);
                    //lecture
                    Lecture l = new Lecture();
                    l.setId(rs.getInt("lid"));
                    l.setName(rs.getString("lname"));
                    ses.setLec(l);
                    //group
                    Group g = new Group();
                    g.setId(rs.getInt("gid"));
                    g.setName(rs.getString("gname"));
                    ses.setGroup(g);
                    //subject
                    Subject sub = new Subject();
                    sub.setId(rs.getInt("subid"));
                    sub.setName(rs.getString("subname"));
                    g.setSub(sub);
                    //session
                    ses.setDate(rs.getDate("date"));
                    ses.setIndex(rs.getInt("index"));
                    ses.setAttanded(rs.getBoolean("attanded"));
                }
                
                //read student
                Student s = new Student();
                s.setId(rs.getInt("stdid"));
                s.setName(rs.getString("stdname"));
                
                //read attandance
                Attendance a = new Attendance();
                a.setStudent(s);
                a.setSes(ses);
                a.setPresent(rs.getBoolean("present"));
                a.setDes(rs.getString("description"));
                ses.getAtts().add(a);
            }
            return ses;
        } catch (SQLException ex) {
            Logger.getLogger(SessionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Session> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public ArrayList<Session> filter(int lid, Date from, Date to){
        ArrayList<Session> sessions = new ArrayList<>();
        try {
            String sql = "SELECT  \n"
                    + "	ses.sesid,ses.[date],ses.[index],ses.attanded\n"
                    + "	,l.lid,l.lname\n"
                    + "	,g.gid,g.gname\n"
                    + "	,sub.subid,sub.subname\n"
                    + "	,r.rid,r.rname\n"
                    + "	,t.tid,t.[description]\n"
                    + "FROM [Session] ses \n"
                    + "			INNER JOIN Lecturer l ON l.lid = ses.lid\n"
                    + "			INNER JOIN [Group] g ON g.gid = ses.gid\n"
                    + "			INNER JOIN [Subject] sub ON sub.subid = g.subid\n"
                    + "			INNER JOIN Room r ON r.rid = ses.rid\n"
                    + "			INNER JOIN TimeSlot t ON t.tid = ses.tid\n"
                    + "WHERE\n"
                    + "l.lid = ?\n"
                    + "AND ses.[date] >= ?\n"
                    + "AND ses.[date] <= ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, lid);
            stm.setDate(2, from);
            stm.setDate(3, to);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                
                Session ses = new Session();
                Lecture l = new Lecture();
                Room r = new Room();
                Group g = new Group();
                TimeSlot t = new TimeSlot();
                Subject sub = new Subject();
                //session
                ses.setId(rs.getInt("sesid"));
                ses.setDate(rs.getDate("date"));
                ses.setIndex(rs.getInt("index"));
                ses.setAttanded(rs.getBoolean("attanded"));
                //lecture
                l.setId(rs.getInt("lid"));
                l.setName(rs.getString("lname"));
                ses.setLec(l);
                //group
                g.setId(rs.getInt("gid"));
                g.setName(rs.getString("gname"));
                ses.setGroup(g);
                //subject
                sub.setId(rs.getInt("subid"));
                sub.setName(rs.getString("subname"));
                g.setSub(sub);
                //room
                r.setId(rs.getInt("rid"));
                r.setName(rs.getString("rname"));
                ses.setRoom(r);
                //Time Slot
                t.setId(rs.getInt("tid"));
                t.setDes(rs.getString("description"));
                ses.setSlot(t);
                
                sessions.add(ses);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sessions;
    }
    
}
