/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketio;

import java.io.ObjectOutputStream;

/**
 *
 * @author ngocnt
 */
public class Client {

    String name;
    ObjectOutputStream objOutMsg, objOutListClients, objOutFile;

    public Client(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObjectOutputStream getObjOutMsg() {
        return objOutMsg;
    }

    public void setObjOutMsg(ObjectOutputStream objOutMsg) {
        this.objOutMsg = objOutMsg;
    }

    public ObjectOutputStream getObjOutListClients() {
        return objOutListClients;
    }

    public void setObjOutListClients(ObjectOutputStream objOutListClients) {
        this.objOutListClients = objOutListClients;
    }

    public ObjectOutputStream getObjOutFile() {
        return objOutFile;
    }

    public void setObjOutFile(ObjectOutputStream objOutFile) {
        this.objOutFile = objOutFile;
    }

}
