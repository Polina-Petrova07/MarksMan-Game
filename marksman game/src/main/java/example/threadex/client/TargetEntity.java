/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example.threadex.client;

import example.threadex.TargetData;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;

/**
 *
 * @author user
 */
public class TargetEntity {
    int x = 382, y = 200;
    int r = 15;
    long id;
    public TargetEntity(long id) {
        this.id = id;
    }
    
    public void setCoord (int x, int y) {
       this.x = x;
       this.y = y;       
    }
    public void setRadius (int r) {
       this.r = r;    
    }
    
    public void readPacket (TargetData data) {
        this.setCoord(data.x, data.y);
        this.setRadius(data.r);
    }
    
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.drawLine(x, 0, x, y + 400);
 
        g2d.setColor(Color.BLACK);
        g2d.drawOval(x-r, y-r, 2*r, 2*r);
        
        Point2D center = new Point2D.Float(x - r + 30,y - r + 30);
        float radius = r-10;
        float[] dist={0.0f, 1.0f};
        Color[] colors = {Color.RED, Color.WHITE};
        RadialGradientPaint grd = new RadialGradientPaint(center, radius, dist, colors);
        g2d.setPaint(grd);
        g2d.fillOval(x-r, y-r, 2*r, 2*r);
    }
}
