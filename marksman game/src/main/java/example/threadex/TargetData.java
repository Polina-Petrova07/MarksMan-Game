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
public class TargetData implements Msg.IData{
    public int x;
    public int y;  
    public int r;
    public long id;

    public TargetData(int x, int y, int r, long id) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.id = id;
    }

}
