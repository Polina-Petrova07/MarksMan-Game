/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example.threadex.server;

import example.threadex.entity.Winusers;
import example.threadex.server.MainServer;
import com.google.gson.Gson;
import example.threadex.Action;
import example.threadex.GsonReader;
import example.threadex.Msg;
import example.threadex.UserData;
import example.threadex.UserMoveData;
import example.threadex.UserNameData;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class clientAtServer implements Runnable {
    Socket cs;
    MainServer ms;
    InputStream is;
    OutputStream os;
    DataInputStream dis;
    DataOutputStream dos;
    Winusers winuser = new Winusers();
    boolean dirty = true;
    int score = 0;
    int y;
    int countShoot = 0; // 
    Arrow arrow;
    String name = "Not setted";
    long id;
    boolean ready = false;
    public clientAtServer(Socket cs, MainServer ms, int y, int number) {
        this.cs = cs;
        this.ms = ms;
        this.arrow = new Arrow(y, this);
        this.id = number;
        try {
            os = cs.getOutputStream();
            dos = new DataOutputStream(os);
        } catch (IOException ex) {
            Logger.getLogger(clientAtServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDirty() {
        this.dirty = true;
    }
    @Override
    public void run() {
        try {
            is = cs.getInputStream();
            dis = new DataInputStream(is);
            
            System.out.println("Client thread started");
            
            while (true) {
                String s = dis.readUTF();
                System.out.println("[READ "+ id +"] " + s);
                
                Msg msg = GsonReader.gson.fromJson(s, Msg.class);
                switch(msg.act) {
                    case SHOOT:
                        ms.shoot(this);
                        if(score == 10){
                            winuser.setWinCount(winuser.getWinCount()+1);
                            Session ss = HibernateUtil.getSessionFactory().openSession();
                            Transaction tx = ss.beginTransaction();
                            ss.saveOrUpdate(winuser);

                            tx.commit(); // Фиксируем транзакцию
                            ss.close(); // Закрываем сеанс и освобождаем ресурсы (не обязательно полное отключение)
                        }
                        setDirty();
                        break;
                    case USER_MOVE:
                        y = ((UserMoveData)msg.data).y;
                        setDirty();
                        break;
                    case USER_NAME:
                        name = ((UserNameData)msg.data).name;
                        boolean f = true;
                        for(Winusers u : ms.winUsers){
                            System.out.println("loop");
                            System.out.println(u.getFullName()+" "+name);
                            if(u.getFullName().equals(name)){
                                System.out.println("loop-if");
                                f = false;
                                winuser = u;
                                break;
                            }
                        }
                        if(f){
                            System.out.println("if");
                            winuser.setFullName(name);
                            winuser.setWinCount(0);
                            ms.winUsers.add(winuser);
                            Session ss = HibernateUtil.getSessionFactory().openSession();
                            Transaction tx = ss.beginTransaction();
                            ss.save(winuser);
                            tx.commit(); // Фиксируем транзакцию
                            ss.close(); // Закрываем сеанс и освобождаем ресурсы (не обязательно полное отключение)
                            Session session = HibernateUtil.getSessionFactory().openSession();
                            ms.winUsers = session.createQuery("from Winusers", Winusers.class).list();
                            session.close(); // Закрываем сеанс и освобождаем ресурсы (не обязательно полное отключение)
                        }

                        setDirty();
                        break;
                    case READY:
                        ready = true;
                        setDirty();
                        break;
                    case PAUSE:
                        ready = false;
                        setDirty();
                        break;
                    case GET_STATISTIC:
                        boolean start = true;
                        for(Winusers i : ms.winUsers){
                            if(start){
                                send(new Msg(Action.GET_STATISTIC, new UserData(i.getId(), 1, 0, i.getWinCount(), i.getFullName())));
                                start = false;
                            }else{
                                send(new Msg(Action.GET_STATISTIC, new UserData(i.getId(), 0, 0, i.getWinCount(), i.getFullName())));
                            }
                        }
                        break;

                }  
            }
        } catch (IOException ex) {
            Logger.getLogger(clientAtServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void tick() {
        if (dirty == true) 
        {
            dirty = false;
            ms.sendAll(new Msg(Action.USER, new UserData(id, y, countShoot, score, name)));
        }
    }
    void send(Msg msg) {
        try{String s = GsonReader.gson.toJson(msg);
            System.out.println("[SEND " + id + "] " + s);
            dos.writeUTF(s);
        } catch (IOException ex) {
            Logger.getLogger(clientAtServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
