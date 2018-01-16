/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketio;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JList;
import javax.swing.JTextArea;

/**
 *
 * @author ngocnt
 */
public class ServerReadThread implements Runnable {

    JTextArea txtMessages;
    ObjectInputStream objIn;
    ArrayList<Client> clients;
    String clientName;
    JList<String> list;

    public ServerReadThread(JTextArea txtMessages, ObjectInputStream objIn, ArrayList<Client> clients, String clientName, JList list) {
        this.txtMessages = txtMessages;
        this.objIn = objIn;
        this.clients = clients;
        this.clientName = clientName;
        this.list = list;
    }

    private Client search(String name) {
        for (Client client : clients) {
            if (client.name.equals(name)) {
                return client;
            }
        }
        return null;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message msg = (Message) objIn.readObject();
                for (Client client : clients) {
                    if (client.name.equals(msg.receiver)) {
                        client.objOutMsg.writeObject(msg);
                        client.objOutMsg.flush();
                    }
                }
                txtMessages.append("Sender: " + msg.sender + " - " + "Receiver: " + msg.receiver + "\n" + "Content: " + msg.content + "\n");
                Thread.sleep(1000);
            } catch (Exception e) {
                //cho nay bao dua disconnect
                System.out.println(clientName + " disconnected");
                txtMessages.append(clientName + " disconnected");
                //update list online again in server
                clients.remove(search(clientName));
                Vector<String> clientsName = new Vector<String>();
                for (Client client : clients) {
                    clientsName.add(client.name);
                }
                list.setListData(clientsName);
                try {
                    for (Client c : clients) {
                    c.objOutListClients.writeObject(clientsName);
                    c.objOutListClients.flush();
                    txtMessages.append("Send list client for " + c.name + "\n");
                    c.objOutListClients.reset();
                }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                
                e.printStackTrace();
                break;
            }
        }
    }

}
