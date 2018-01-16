/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketio;

import java.io.Serializable;

/**
 *
 * @author ngocnt
 */
public class FileMsg implements Serializable{
    String fileName;
    byte[] data;
    String sender;
    String receiver;

    public FileMsg(String fileName, byte[] data, String sender, String receiver) {
        this.fileName = fileName;
        this.data = data;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    
    
    
}
