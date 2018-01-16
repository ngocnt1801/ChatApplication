/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketio;

import java.io.ObjectInputStream;
import java.util.Vector;
import javax.swing.JList;

/**
 *
 * @author ngocnt
 */
public class ReceiveListOnlineThread implements Runnable {

    JList<String> list;
    ObjectInputStream objInListClients;

    public ReceiveListOnlineThread(JList<String> list, ObjectInputStream objInListClients) {
        this.list = list;
        this.objInListClients = objInListClients;
    }
    
    @Override
    public void run() {
        while (true){
            try {
                Vector data = (Vector) objInListClients.readObject();
                list.setListData(data);
                
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
    }
    
}
