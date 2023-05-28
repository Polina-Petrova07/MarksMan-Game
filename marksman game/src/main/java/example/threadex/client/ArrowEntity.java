/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example.threadex.client;

import example.threadex.ArrowData;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;

/**
 *
 * @author user
 */
public class ArrowEntity {
    int[] s1 = new int[] {55, 55, 60};
    int[] s2 = new int[] {205, 195, 200};
    int xs1 = 35, xs2 = 60, ys = 200;
    
    long id;
    public ArrowEntity(long id) {
        this.id = id;
    }
    public void setCoord (int x, int y) {
        xs1 = x - 25;
        xs2 = x;
        ys = y;
        s1[0] = xs2 + 3;
        s1[1] = xs2 + 3;
        s1[2] = xs2 + 15;
        
        s2[0] = ys + 5;
        s2[1] = ys - 5;
        s2[2] = ys;
        
    }
    
    public void readPacket (ArrowData data) {
        this.setCoord(data.x, data.y);
    }
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.drawLine(xs1, ys, xs2, ys);
        g2d.drawPolygon(s1, s2, 3);
        g2d.fillPolygon(s1, s2, 3);
    }
}
