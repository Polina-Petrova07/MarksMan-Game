/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example.threadex.client;

import example.threadex.UserData;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;

/**
 *
 * @author user
 */
public class UserEntity {
    int[] p1 = new int[] {10, 10, 30};
    int[] p2 = new int[] {240, 160, 200};
    int y = 200;
    int x = 10;
    int count_shoot = 0;
    int score = 0;
    String name = "Not Set";
    long id;
    Color color;

    public UserEntity(long id) {
        this.id = id;
        this.color = randomColor(id);
    }
    
    public void setCoord (int x, int y) {
        this.x = x;
        this.y = y;
        p1[0] = x;
        p1[1] = x;
        p1[2] = x + 20;
        
        p2[0] = y + 40;
        p2[1] = y - 40;
        p2[2] = y;
        
    }

    public void readPacket (UserData data) {
        this.name = data.userName;
        this.setCoord(10, data.y);
        this.score = data.score;
        this.count_shoot = data.count_shoot;
    }
    public static Color randomColor(long id) {
        Random rnd = new Random(id);
        return Color.getHSBColor(rnd.nextFloat(), rnd.nextFloat() * 0.5f + 0.5f, 1.0f);
    }
    public void draw(Graphics2D g2d) {
        
        g2d.setColor(color);
        g2d.drawPolygon(p1, p2, 3);
        g2d.setColor(color);
        g2d.fillPolygon(p1, p2, 3);
        g2d.setColor(Color.BLACK);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("Serif", Font.PLAIN, 12);
        g2d.setFont(font);
        g2d.drawString(name, x, y);
        g2d.drawString("" + score, x, y + 7);
        g2d.drawString("" + count_shoot, x, y + 20);
        
    }
}
