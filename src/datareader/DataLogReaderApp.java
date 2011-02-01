/* DataReader
 *
 * Copyright 2011
 *
 * This file is part of the DataReader project.
 *
 * DataReader software is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DataReader software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with any DataReader software.  If not, see http://www.gnu.org/licenses/
 *
 * I ask that if you make any changes to this file you fork the code on github.com!
 *
 */

/*
 * DataLogReaderApp.java
 *
 * Created on Jan 26, 2011, 2:55:31 PM
 */

package datareader;

import Decoder.FreeEMSBin;
import GenericLog.GenericLog;
import Graphing.DrawnGraph;
import java.awt.Dimension;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Bryan
 */
public class DataLogReaderApp extends javax.swing.JFrame {

    /** Creates new form DataLogReaderApp */
    public DataLogReaderApp() {
        initComponents();
    }

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        drawnGraph = new DrawnGraph();
        playBar = new PlayBarPanel();
        
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        openFileMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        setLayout(new java.awt.BorderLayout());
        add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.BorderLayout());

        drawnGraph.setName("pl"); // NOI18N
        drawnGraph.setPreferredSize(new Dimension(600,400));
        drawnGraph.setLayout(new java.awt.FlowLayout());
        

        jPanel1.add(drawnGraph, java.awt.BorderLayout.CENTER);

        

        jPanel1.add(playBar, java.awt.BorderLayout.SOUTH);

        jMenuBar1.setName("jMenuBar1"); // NOI18N

        jMenu1.setText("File");
        jMenu1.setName("jMenu1"); // NOI18N

        openFileMenuItem.setText("Open Log");
        openFileMenuItem.setName("openlog"); // NOI18N
        openFileMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                openFileMenuItemMouseReleased(evt);
            }
        });
        jMenu1.add(openFileMenuItem);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenu2.setName("jMenu2"); // NOI18N
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);
        pack();
    }
    private void openFileMenuItemMouseReleased( java.awt.event.MouseEvent evt) {
        DataLogReaderApp.openFile();
    }

    public void setLog(GenericLog genericLog) {
        drawnGraph.setLog(genericLog);
    }
    
    /**
     * Returns the reference to this instance, it is meant to be a method to make getting the main frame simpler
     * @return <code>this</code> instance
     */
    public static DataLogReaderApp getInstance() {
        return mainAppRef;
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               mainAppRef = new DataLogReaderApp();
                       mainAppRef.setVisible(true);
            }
        });
    }

    public DrawnGraph getDrawnGraph() {
        return drawnGraph;
    }

    public static void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        DLRFileFilter filter = new DLRFileFilter();
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        int acceptValue = fileChooser.showOpenDialog(DataLogReaderApp.getInstance());
        if(acceptValue == JFileChooser.APPROVE_OPTION) {
            File openFile = fileChooser.getSelectedFile();
            new FreeEMSBin(openFile);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static DataLogReaderApp mainAppRef;

    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem openFileMenuItem;
    private javax.swing.JPanel jPanel1;
    private DrawnGraph drawnGraph;
    private PlayBarPanel playBar;
    
    // End of variables declaration//GEN-END:variables
    private GenericLog genLog;
    private FreeEMSBin fems;
    //DrawnGraph drawnGraph;
}