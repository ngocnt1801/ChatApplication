/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketio;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author ngocnt
 */
public class ClientReadFileThread implements Runnable {

    JTabbedPane tabPane;
    ObjectInputStream objInFile;
    ObjectOutputStream objOutMsg, objOutFile;

    public ClientReadFileThread(JTabbedPane tabPane, ObjectInputStream objInFile, ObjectOutputStream objOutMsg, ObjectOutputStream objOutFile) {
        this.tabPane = tabPane;
        this.objInFile = objInFile;
        this.objOutMsg = objOutMsg;
        this.objOutFile = objOutFile;
    }

    @Override
    public void run() {
        while (true) {
            try {
                FileMsg fm = (FileMsg) objInFile.readObject();
                boolean isExist = false;
                for (int i = 0; i < tabPane.getTabCount(); i++) {
                    if (tabPane.getTitleAt(i).equals(fm.sender)) {
                        ChatPanel p = (ChatPanel) tabPane.getComponentAt(i);
                        p.getMessageContainer().add(new FileLine(fm.fileName, fm.data, fm.sender));
                        p.updateUI();
                        isExist = true;
                    }
                }
                if (!isExist) {
                    ChatPanel p = new ChatPanel(fm.receiver, fm.sender, objOutMsg, objOutFile);
                    tabPane.add(fm.sender, p);
                    p.getMessageContainer().add(new FileLine(fm.fileName, fm.data, fm.sender));
                    p.updateUI();
                    isExist = true;
                }
            } catch (Exception e) {
            }
        }
    }

}
