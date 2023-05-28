/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example.threadex.server;

import com.google.gson.Gson;
import example.threadex.entity.Winusers;
import org.hibernate.Session;
import example.threadex.Action;
import example.threadex.GsonReader;
import example.threadex.Msg;
import org.hibernate.Transaction;

import java.awt.Panel;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */

public class MainServer {
    ServerSocket ss;
    //Session session = HibernateUtil.getSessionFactory().openSession();
    boolean started = false;
    int port = 3124;
    InetAddress ip = null;
    ExecutorService service = Executors.newCachedThreadPool();
    ArrayList<clientAtServer> allClients = new ArrayList<>();
    List<Winusers> winUsers = new LinkedList<>();

    int number = 0;
    void shoot(clientAtServer Owner) {
       if (Owner.arrow.enable == true)
           return;
       Owner.arrow.y = Owner.y;
       Owner.arrow.enable = true;
       Owner.countShoot += 1; //
    }
    
    void UserLogicTick() {
        for (clientAtServer client: allClients) {
            client.tick();
        }
    }
    void ArrowLogicTick(Target... targets){ 
        for (clientAtServer client: allClients) {
            client.arrow.tick(targets);
        }
    }
 
    void TargetLogicTick(Target... targets) {
        for (Target target:targets) {
            target.tick();
        }
    }
    public void ServerStart() throws UnknownHostException {
        Target target1 = new Target(this, 1, 382, 200, 3, 30, 380, 1);
        Target target2 = new Target(this, 2, 382 + 90, 200, 6, 15, 380, 2);
        Thread logic = new Thread(()->
            {
                try {
                    while(true) {
                        Thread.sleep(100);
                        if (started) {
                            TargetLogicTick(target1, target2);
                            ArrowLogicTick(target1, target2);
                        } else {
                            boolean ready = !allClients.isEmpty();
                            for (clientAtServer client: allClients) {
                                if (client.ready == false)
                                    ready = false;
                            }
                            if (ready == true)
                                started = true;
                        }
                        // обновление информации
                        UserLogicTick();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
                }

        });
        logic.start();
        
        try {
            ip = InetAddress.getLocalHost();
            ss = new ServerSocket(port, 0, ip);
            System.out.append("Server start\n");
            new Thread(()->{
                Session session = HibernateUtil.getSessionFactory().openSession();
                winUsers = session.createQuery("from Winusers", Winusers.class).list();
                session.close(); // Закрываем сеанс и освобождаем ресурсы (не обязательно полное отключение)
            }).start();
           // session.getTransaction().begin();
            // ловим accept (ждем клинетов)
            while(true)
            {

                Socket cs;
                cs = ss.accept();
                if (allClients.size() > 4) {
                    cs.close();
                    System.out.println("Server is full!");
                    continue;
                }
                System.out.append("Client connect \n");  
                clientAtServer c = new clientAtServer(cs, this, 200, number);
                number += 1;
                allClients.add(c);
                
                //появлися новый клиент
                Msg msg = new Msg(Action.NEW_CONNECTED);
                sendAll(msg);
                
                
                
                service.submit(c); 
            }
            
        } catch (IOException ex) {}
    }
    
    void sendAll(Msg msg) {
        try{
            String s = GsonReader.gson.toJson(msg);
            System.out.println("[SEND ALL] " + s);
            for (clientAtServer client: allClients) {
                client.dos.writeUTF(s);
            }
        } catch (IOException ex) {
            Logger.getLogger(clientAtServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            new MainServer().ServerStart();
        } catch (UnknownHostException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
