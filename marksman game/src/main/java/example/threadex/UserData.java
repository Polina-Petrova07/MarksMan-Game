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
public class UserData implements Msg.IData{
    public int y;
    public long id;
    public int count_shoot;
    public int score;
    public String userName;

    public UserData(long id, int y, int count_shoot, int score, String userName) {
        this.id = id;
        this.y = y;
        this.count_shoot = count_shoot;
        this.score = score;
        this.userName = userName;
    }

}
