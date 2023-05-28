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
public class UserMoveData implements Msg.IData{
    public int y;

    public UserMoveData(int y) {
        this.y = y;
    } 
}
