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
// запрос к серверу
public class Msg {
    public Action act;
    public IData data = null;
    
    public Msg(Action act, IData data) {
        this.act = act;
        this.data = data;
    }
    public Msg(Action act) {
        this.act = act;
    }
    public interface IData {};
}
