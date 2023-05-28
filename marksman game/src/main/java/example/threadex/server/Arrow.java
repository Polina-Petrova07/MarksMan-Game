/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example.threadex.server;

import example.threadex.Action;
import example.threadex.ArrowData;
import example.threadex.Msg;

/**
 *
 * @author user
 */
public class Arrow {
    int x;
    int y;
    clientAtServer owner;
    boolean enable = false;

    public Arrow(int y, clientAtServer owner) {
        this.x = 50;
        this.y = y;
        this.owner = owner;
    }
    
    public boolean tryHit(Target target) {
        boolean flag = false;
        if (x > target.x && y > target.y - target.r && y < target.y + target.r) {  // (x > target.x + target.r && y > target.y - target.r && y < target.y + target.r)
            System.out.println("[ARROW] i am hit!");
            flag = true;
        }
        System.out.println("[ARROW] tryHit");
        return flag;
    }
    public void tick (Target... targets) { // для одного кадра
        if (enable == false) {
            this.y = owner.y;
            return;
        } 
        this.x = x + 20;
        System.out.println("[TARGET] " +targets.length);
        for (Target target:targets) {
            if (this.tryHit(target)) {
                this.x = 50;
                enable = false;
                owner.score += target.hitScore;
                owner.setDirty();
            }
        }
        if (x > 618) {
            x = 50;
            enable = false;
        }   
        owner.ms.sendAll(new Msg(Action.ARROW, new ArrowData(x, y, owner.id)));  
    }
}
