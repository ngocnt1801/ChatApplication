/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketio;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author ngocnt
 */
public class ServerReadFileThread implements Runnable {

    JTextArea txtMessages;
    ArrayList<Client> clients;
    ObjectInputStream objIn;

    public ServerReadFileThread(JTextArea txtMessages, ArrayList<Client> clients, ObjectInputStream objIn) {
        this.txtMessages = txtMessages;
        this.clients = clients;
        this.objIn = objIn;
    }

    @Override
    public void run() {
        while (true) {
            try {
                FileMsg fm = (FileMsg) objIn.readObject();
                for (Client client : clients) {
                    if (fm.receiver.endsWith(client.name)) {
                        client.getObjOutFile().writeObject(fm);
                    }
                }
                txtMessages.append("Sender: " + fm.sender + " - " + "Receiver: " + fm.receiver + "\n" + "Content: " + fm.fileName + "\n");
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
    }

}
