/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketio;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author ngocnt
 */
public class ClientChatter extends javax.swing.JFrame {

    Socket chatSocket;
    Socket fileSocket;
    Socket listClientsSocket;
    String clientName;
    ObjectOutputStream objOutMsg, objOutFile, objOutListClients;
    ObjectInputStream objInMsg, objInFile, objInListClients;
    Vector<String> clientsName;

    public ClientChatter() {
        initComponents();
        this.setSize(600, 500);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JPanel1 = new javax.swing.JPanel();
        lblStaff = new javax.swing.JLabel();
        txtStaff = new javax.swing.JTextField();
        lblServerIP = new javax.swing.JLabel();
        txtServerIP = new javax.swing.JTextField();
        lblServerPort = new javax.swing.JLabel();
        txtServerPort = new javax.swing.JTextField();
        btnConnect = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        listClients = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Staff and Server Info"));
        JPanel1.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        lblStaff.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblStaff.setText("Staff:");
        JPanel1.add(lblStaff);

        txtStaff.setText("Ngoc");
        txtStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStaffActionPerformed(evt);
            }
        });
        JPanel1.add(txtStaff);

        lblServerIP.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblServerIP.setText("Mng IP");
        lblServerIP.setPreferredSize(new java.awt.Dimension(10, 14));
        JPanel1.add(lblServerIP);

        txtServerIP.setText("127.0.0.1");
        JPanel1.add(txtServerIP);

        lblServerPort.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblServerPort.setText("Port:");
        JPanel1.add(lblServerPort);

        txtServerPort.setText("1111");
        JPanel1.add(txtServerPort);

        btnConnect.setText("Connect");
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });
        JPanel1.add(btnConnect);

        getContentPane().add(JPanel1, java.awt.BorderLayout.PAGE_START);
        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        listClients.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listClientsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listClients);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.EAST);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStaffActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStaffActionPerformed

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
        String serverIp = txtServerIP.getText().trim();
        String serverPort = txtServerPort.getText().trim();
        clientName = txtStaff.getText().trim();
        int port = Integer.parseInt(serverPort);
        clientsName = new Vector<>();
        btnConnect.setEnabled(false);

        try {
            //connect chatSocket
            chatSocket = new Socket(serverIp, port);
            //connect listClientsSocket
            listClientsSocket = new Socket(serverIp, port);
            //connect fileSocket
            fileSocket = new Socket(serverIp, port);

            //name: chatSocket
            //name-listClients: listClientsSocket
            //name-file: fileSocket
            objOutMsg = new ObjectOutputStream(chatSocket.getOutputStream());
            objOutMsg.writeObject(clientName + "-chat");
            objOutMsg.flush();

            objOutListClients = new ObjectOutputStream(listClientsSocket.getOutputStream());
            objOutListClients.writeObject(clientName + "-list");
            objOutListClients.flush();

            objOutFile = new ObjectOutputStream(fileSocket.getOutputStream());
            objOutFile.writeObject(clientName + "-file");
            objOutFile.flush();

            //start thread to received list online
            objInListClients = new ObjectInputStream(listClientsSocket.getInputStream());
            ReceiveListOnlineThread rList = new ReceiveListOnlineThread(listClients, objInListClients);
            Thread listT = new Thread(rList);
            listT.start();

            //start thread to received message
            objInMsg = new ObjectInputStream(chatSocket.getInputStream());
            ReadThread rt = new ReadThread(objInMsg, jTabbedPane1, objOutMsg, objOutFile);
            Thread t = new Thread(rt);
            t.start();

            //start thread to received file
            objInFile = new ObjectInputStream(fileSocket.getInputStream());
            ClientReadFileThread crf = new ClientReadFileThread(jTabbedPane1, objInFile, objOutMsg, objOutFile);
            Thread fileT = new Thread(crf);
            fileT.start();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Server is not running");
            btnConnect.setEnabled(true);
//            ex.printStackTrace();

        }


    }//GEN-LAST:event_btnConnectActionPerformed

    private void listClientsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listClientsMouseClicked
        ArrayList<String> test = (ArrayList) listClients.getSelectedValuesList();
        if (test.size() != 1) {
            return;
        }
        ChatPanel p = new ChatPanel(clientName, test.get(0), objOutMsg, objOutFile);
        jTabbedPane1.add(test.get(0), p);
        listClients.clearSelection();


    }//GEN-LAST:event_listClientsMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientChatter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientChatter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientChatter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientChatter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientChatter().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPanel1;
    private javax.swing.JButton btnConnect;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblServerIP;
    private javax.swing.JLabel lblServerPort;
    private javax.swing.JLabel lblStaff;
    private javax.swing.JList<String> listClients;
    private javax.swing.JTextField txtServerIP;
    private javax.swing.JTextField txtServerPort;
    private javax.swing.JTextField txtStaff;
    // End of variables declaration//GEN-END:variables
}
