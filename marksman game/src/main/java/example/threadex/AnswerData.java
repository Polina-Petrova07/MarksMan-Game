/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example.threadex;

/**
 *
 * @author user
 */
public class AnswerData implements Msg.IData{
    int x = 382, y = 200, y2=200;
    int[] p1 = new int[] {10, 10, 30};
    int[] p2 = new int[] {240, 160, 200};
    int[] s1 = new int[] {55, 55, 60};
    int[] s2 = new int[] {205, 195, 200};
    int r = 30, r2=15;
    int xs1= 35, xs2=60, ys=200;

    @Override
    public String toString() {
        return "AnswerMsg{" + "x=" + x + ", y=" + y + ", y2=" + y2 + ", p1=" + p1 + ", p2=" + p2 + ", s1=" + s1 + ", s2=" + s2 + ", r=" + r + ", r2=" + r2 + ", xs1=" + xs1 + ", xs2=" + xs2 + ", ys=" + ys + '}';
    }
    
    
}
