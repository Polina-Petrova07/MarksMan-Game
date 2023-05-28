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
public class UserNameData implements Msg.IData{
    public String name;

    public UserNameData(String name) {
        this.name = name;
    }
}
