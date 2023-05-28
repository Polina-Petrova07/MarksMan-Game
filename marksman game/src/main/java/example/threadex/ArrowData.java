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
public class ArrowData implements Msg.IData {
    public int x;
    public int y;
    public long id;
    public ArrowData(int x, int y, long id) {
        this.x = x;
        this.y = y;
        this.id = id;
    } 
}
