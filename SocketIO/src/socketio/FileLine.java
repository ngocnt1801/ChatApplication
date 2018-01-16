/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketio;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

/**
 *
 * @author ngocnt
 */
public class FileLine extends JLabel {

    byte[] data;
    String fileName;
    

    public FileLine(String fileName, byte[] data, String sender) {
        super(sender + ": " + fileName);
        this.data = data;
        this.fileName = fileName;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblMouseClickedLisener(e);
            }

        });
        this.setForeground(Color.blue);
    }

    private void lblMouseClickedLisener(MouseEvent e) {
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File(fileName));
        int result = fc.showSaveDialog(null);
        
        if (result == JFileChooser.APPROVE_OPTION) {
//            String name = fc.getSelectedFile().getName();
            String path = fc.getSelectedFile().getPath();

            try {
                FileOutputStream fo = new FileOutputStream(new File(path));
                fo.write(data, 0, data.length);
                fo.close();
                System.out.println("Done");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
