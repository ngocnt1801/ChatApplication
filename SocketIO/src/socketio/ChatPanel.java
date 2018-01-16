/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketio;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 *
 * @author ngocnt
 */
public class ChatPanel extends javax.swing.JPanel {

    String sender;
    String receiver;
    ObjectOutputStream objOutMsg, objOutFile;
    JFileChooser fc;

    public ChatPanel(String sender, String receiver, ObjectOutputStream objOutMsg, ObjectOutputStream objOutFile) {
        initComponents();
        this.sender = sender;
        this.receiver = receiver;
//        this.socket = socket;
        this.objOutMsg = objOutMsg;
        this.objOutFile = objOutFile;
        this.setPreferredSize(new Dimension(500, 300));
    }

    public JPanel getMessageContainer() {
        return messageContainer;
    }

    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        messageContainer = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtMessage = new javax.swing.JTextArea();
        btnSend = new javax.swing.JButton();
        btnSendFile = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setHorizontalScrollBar(null);

        messageContainer.setBackground(new java.awt.Color(255, 255, 255));
        messageContainer.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        messageContainer.setLayout(new javax.swing.BoxLayout(messageContainer, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane3.setViewportView(messageContainer);

        add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        txtMessage.setColumns(20);
        txtMessage.setRows(5);
        jScrollPane4.setViewportView(txtMessage);

        jPanel3.add(jScrollPane4);

        btnSend.setText("SEND");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });
        jPanel3.add(btnSend);

        btnSendFile.setText("Send File");
        btnSendFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendFileActionPerformed(evt);
            }
        });
        jPanel3.add(btnSendFile);

        add(jPanel3, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        String msg = txtMessage.getText().trim();
        try {
            objOutMsg.writeObject(new Message(sender, msg, receiver));
            objOutMsg.flush();
            messageContainer.add(new MessageLine(sender + ": " + msg + "\n"));
            this.updateUI();
//            txtMessages.append();
            txtMessage.setText("");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnSendActionPerformed

    private void btnSendFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendFileActionPerformed
        fc = new JFileChooser();
        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File f = fc.getSelectedFile();
                if (f.isFile()) {
                    FileInputStream fi = new FileInputStream(f);
                    byte[] b = new byte[10000000];
                    byte[] d = new byte[0];
                    int bytesRead;
                    while ((bytesRead = fi.read(b)) != -1){
//                        data.add(new DataElement(bytesRead, b));
                            byte[] d2 = new byte[d.length];
                            System.arraycopy(d, 0, d2, 0, d.length);
                            d = new byte[d.length +  bytesRead];
                            System.arraycopy(d2, 0, d, 0, d2.length);
                            System.arraycopy(b, 0, d, d2.length, bytesRead);
                            System.out.println("Current mb: " + (d.length / 1024 / 1024));
                    }
                    
                    
                    objOutFile.writeObject(new FileMsg(f.getName(), d, sender, receiver));
                    FileLine fl = new FileLine(f.getName(), d, sender);
                    messageContainer.add(fl);
                    fl.setPreferredSize(new Dimension(this.getWidth() - 100, 30));
                    System.out.println("Width: " + fl.getWidth());
                    this.updateUI();
                    System.out.println("File name: " + f.getName());
//                    FileLine fl = new FileLine(f.getName(), d);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }//GEN-LAST:event_btnSendFileActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSend;
    private javax.swing.JButton btnSendFile;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPanel messageContainer;
    private javax.swing.JTextArea txtMessage;
    // End of variables declaration//GEN-END:variables
}
