/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example.threadex.server;

import example.threadex.Action;
import example.threadex.Msg;
import example.threadex.TargetData;
import example.threadex.server.MainServer;

/**
 *
 * @author user
 */
public class Target {
    int x;
    int y;
    int speed;
    int r;
    int maxY;
    int hitScore;
    MainServer ms;
    int id;

    public Target(MainServer ms, int id, int x, int y, int speed, int r, int maxY, int hitScore) {
        this.id = id;
        this.ms = ms;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.r = r;
        this.maxY = maxY;
        this.hitScore = hitScore;
    }
    
    public void tick () { // для одного кадра
        this.y = y + speed;
        if (y > maxY)
            y = 0;
        ms.sendAll(new Msg(Action.TARGET, new TargetData(x, y, r, id)));  
        
    }
}
