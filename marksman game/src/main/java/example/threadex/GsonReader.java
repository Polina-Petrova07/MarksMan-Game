/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example.threadex;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

/**
 *
 * @author user
 */
public class GsonReader {
    
    static RuntimeTypeAdapterFactory<Msg.IData> typeFactory = RuntimeTypeAdapterFactory
            .of(Msg.IData.class, "type")
            .registerSubtype(AnswerData.class)
            .registerSubtype(ArrowData.class)
            .registerSubtype(TargetData.class)
            .registerSubtype(UserData.class)
            .registerSubtype(UserMoveData.class)
            .registerSubtype(UserNameData.class);

    public static Gson gson = new GsonBuilder().registerTypeAdapterFactory(typeFactory).create();
}
