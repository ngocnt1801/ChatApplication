/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketio;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author ngocnt
 */
public class ReadThread implements Runnable {

    ObjectInputStream objIn;
    JTabbedPane tabPane;
    ObjectOutputStream objOut, objOutFile;

    public ReadThread(ObjectInputStream objIn, JTabbedPane tabPane, ObjectOutputStream objOut, ObjectOutputStream objOutFile) {
        this.objIn = objIn;
        this.tabPane = tabPane;
        this.objOut = objOut;
        this.objOutFile = objOutFile;
    }

    @Override
    public void run() {
        while (true) {
            try {
               Object obj = objIn.readObject();
               if (obj instanceof Message){
                   Message msg = (Message) obj;
                   boolean isExist = false;
                   for (int i = 0; i < tabPane.getTabCount(); i++) {
                       if (msg.sender.equals(tabPane.getTitleAt(i))){
                           isExist = true;
                           ChatPanel p = (ChatPanel) tabPane.getComponentAt(i);
                           p.getMessageContainer().add(new MessageLine(msg.sender + ": " + msg.content + "\n"));
                           p.updateUI();
                       }
                   }
                   if (!isExist){
                       ChatPanel p = new ChatPanel(msg.receiver, msg.sender, objOut, objOutFile);
                       tabPane.add(msg.sender, p);
                       p.getMessageContainer().add(new MessageLine(msg.sender + ": " + msg.content + "\n"));
                       p.updateUI();
                   }
               }
              
               
            } catch (Exception ex) {
                System.out.println("Server disconnected");                
                ex.printStackTrace();
//                System.exit(0);
//show message server disconnect for all client
                JOptionPane.showMessageDialog(null, "Server disconnected");
                //enable for button connect
                JPanel parent = (JPanel) tabPane.getParent().getComponents()[0];
                JButton btnConnect = (JButton) parent.getComponent(6);
                btnConnect.setEnabled(true);                
                break;
            }

        }
    }

}
