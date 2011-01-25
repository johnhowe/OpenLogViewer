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
 * DataReaderView.java
 */

package datareader;

import Decoder.FreeEMSBin;
import GenericLog.GenericLog;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;


/**
 * The application's main frame.
 */
public class DataReaderView extends FrameView {

    public DataReaderView(SingleFrameApplication app) {
        super(app);
        
        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
       Playbar = new Playback(this); // give a reference of THIS view, its not really accesable any other way, this was the only way to just move code outside this file and not make it static... i might change that later
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = DataReaderApp.getApplication().getMainFrame();
            aboutBox = new DataReaderAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        DataReaderApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        TabbedPane = new javax.swing.JTabbedPane();
        GraphingTabPane = new javax.swing.JPanel();
        GraphingScrollPane = new javax.swing.JScrollPane();
        GraphingViewPane = new javax.swing.JPanel();
        GraphWindow = new javax.swing.JPanel();
        PlayBackPanel = new javax.swing.JPanel();
        Playback_Slow = new javax.swing.JButton();
        Playback_Play = new javax.swing.JButton();
        Playback_Pause = new javax.swing.JButton();
        Playback_Stop = new javax.swing.JButton();
        Playback_Fast = new javax.swing.JButton();
        Playback_Open = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        OptionScrollPane = new javax.swing.JScrollPane();
        OptionPane = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jPanel4 = new javax.swing.JPanel();

        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setLayout(new java.awt.GridLayout(1, 0));

        TabbedPane.setName("TabbedPane"); // NOI18N

        GraphingTabPane.setName("GraphingTabPane"); // NOI18N
        GraphingTabPane.setLayout(new java.awt.BorderLayout());

        GraphingScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        GraphingScrollPane.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        GraphingScrollPane.setName("GraphingScrollPane"); // NOI18N

        GraphingViewPane.setName("GraphingViewPane"); // NOI18N
        GraphingViewPane.setLayout(new java.awt.BorderLayout());

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(datareader.DataReaderApp.class).getContext().getResourceMap(DataReaderView.class);
        GraphWindow.setBackground(resourceMap.getColor("GraphWindow.background")); // NOI18N
        GraphWindow.setMaximumSize(new java.awt.Dimension(10000, 30));
        GraphWindow.setName("GraphWindow"); // NOI18N
        GraphWindow.setLayout(new java.awt.GridLayout(1, 0));
        GraphingViewPane.add(GraphWindow, java.awt.BorderLayout.CENTER);

        GraphingScrollPane.setViewportView(GraphingViewPane);

        GraphingTabPane.add(GraphingScrollPane, java.awt.BorderLayout.CENTER);

        PlayBackPanel.setBackground(resourceMap.getColor("PlayBackPanel.background")); // NOI18N
        PlayBackPanel.setName("PlayBackPanel"); // NOI18N
        PlayBackPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        Playback_Slow.setIcon(resourceMap.getIcon("Playback_Slow.icon")); // NOI18N
        Playback_Slow.setText(resourceMap.getString("Playback_Slow.text")); // NOI18N
        Playback_Slow.setToolTipText(resourceMap.getString("Playback_Slow.toolTipText")); // NOI18N
        Playback_Slow.setContentAreaFilled(false);
        Playback_Slow.setMaximumSize(new java.awt.Dimension(30, 30));
        Playback_Slow.setMinimumSize(new java.awt.Dimension(30, 30));
        Playback_Slow.setName("Playback_Slow"); // NOI18N
        Playback_Slow.setPreferredSize(new java.awt.Dimension(30, 30));
        Playback_Slow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Playback_SlowMouseReleased(evt);
            }
        });
        PlayBackPanel.add(Playback_Slow);

        Playback_Play.setIcon(resourceMap.getIcon("Playback_Play.icon")); // NOI18N
        Playback_Play.setText(resourceMap.getString("Playback_Play.text")); // NOI18N
        Playback_Play.setToolTipText(resourceMap.getString("Playback_Play.toolTipText")); // NOI18N
        Playback_Play.setContentAreaFilled(false);
        Playback_Play.setMaximumSize(new java.awt.Dimension(30, 30));
        Playback_Play.setMinimumSize(new java.awt.Dimension(30, 30));
        Playback_Play.setName("Playback_Play"); // NOI18N
        Playback_Play.setPreferredSize(new java.awt.Dimension(30, 30));
        Playback_Play.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Playback_PlayMouseReleased(evt);
            }
        });
        PlayBackPanel.add(Playback_Play);

        Playback_Pause.setIcon(resourceMap.getIcon("Playback_Pause.icon")); // NOI18N
        Playback_Pause.setText(resourceMap.getString("Playback_Pause.text")); // NOI18N
        Playback_Pause.setToolTipText(resourceMap.getString("Playback_Pause.toolTipText")); // NOI18N
        Playback_Pause.setContentAreaFilled(false);
        Playback_Pause.setMaximumSize(new java.awt.Dimension(30, 30));
        Playback_Pause.setMinimumSize(new java.awt.Dimension(30, 30));
        Playback_Pause.setName("Playback_Pause"); // NOI18N
        Playback_Pause.setPreferredSize(new java.awt.Dimension(30, 30));
        Playback_Pause.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Playback_PauseMouseReleased(evt);
            }
        });
        PlayBackPanel.add(Playback_Pause);

        Playback_Stop.setIcon(resourceMap.getIcon("Playback_Stop.icon")); // NOI18N
        Playback_Stop.setText(resourceMap.getString("Playback_Stop.text")); // NOI18N
        Playback_Stop.setToolTipText(resourceMap.getString("Playback_Stop.toolTipText")); // NOI18N
        Playback_Stop.setContentAreaFilled(false);
        Playback_Stop.setMaximumSize(new java.awt.Dimension(30, 30));
        Playback_Stop.setMinimumSize(new java.awt.Dimension(30, 30));
        Playback_Stop.setName("Playback_Stop"); // NOI18N
        Playback_Stop.setPreferredSize(new java.awt.Dimension(30, 30));
        Playback_Stop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Playback_StopMouseReleased(evt);
            }
        });
        PlayBackPanel.add(Playback_Stop);

        Playback_Fast.setIcon(resourceMap.getIcon("Playback_Fast.icon")); // NOI18N
        Playback_Fast.setText(resourceMap.getString("Playback_Fast.text")); // NOI18N
        Playback_Fast.setToolTipText(resourceMap.getString("Playback_Fast.toolTipText")); // NOI18N
        Playback_Fast.setContentAreaFilled(false);
        Playback_Fast.setMaximumSize(new java.awt.Dimension(30, 30));
        Playback_Fast.setMinimumSize(new java.awt.Dimension(30, 30));
        Playback_Fast.setName("Playback_Fast"); // NOI18N
        Playback_Fast.setPreferredSize(new java.awt.Dimension(30, 30));
        Playback_Fast.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Playback_FastMouseReleased(evt);
            }
        });
        PlayBackPanel.add(Playback_Fast);

        Playback_Open.setIcon(resourceMap.getIcon("Playback_Open.icon")); // NOI18N
        Playback_Open.setText(resourceMap.getString("Playback_Open.text")); // NOI18N
        Playback_Open.setToolTipText(resourceMap.getString("Playback_Open.toolTipText")); // NOI18N
        Playback_Open.setContentAreaFilled(false);
        Playback_Open.setMaximumSize(new java.awt.Dimension(30, 30));
        Playback_Open.setMinimumSize(new java.awt.Dimension(30, 30));
        Playback_Open.setName("Playback_Open"); // NOI18N
        Playback_Open.setPreferredSize(new java.awt.Dimension(30, 30));
        Playback_Open.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Playback_OpenMouseReleased(evt);
            }
        });
        PlayBackPanel.add(Playback_Open);

        jButton7.setText(resourceMap.getString("jButton7.text")); // NOI18N
        jButton7.setMaximumSize(new java.awt.Dimension(30, 30));
        jButton7.setMinimumSize(new java.awt.Dimension(30, 30));
        jButton7.setName("jButton7"); // NOI18N
        jButton7.setPreferredSize(new java.awt.Dimension(30, 30));
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton7MouseReleased(evt);
            }
        });
        PlayBackPanel.add(jButton7);

        GraphingTabPane.add(PlayBackPanel, java.awt.BorderLayout.PAGE_END);

        TabbedPane.addTab(resourceMap.getString("GraphingTabPane.TabConstraints.tabTitle"), GraphingTabPane); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        OptionScrollPane.setName("OptionScrollPane"); // NOI18N

        OptionPane.setMaximumSize(OptionPane.size());
        OptionPane.setMinimumSize(new java.awt.Dimension(500, 500));
        OptionPane.setName("OptionPane"); // NOI18N
        OptionPane.setPreferredSize(new java.awt.Dimension(600, 400));
        OptionScrollPane.setViewportView(OptionPane);

        jPanel1.add(OptionScrollPane);

        TabbedPane.addTab(resourceMap.getString("jPanel1.TabConstraints.tabTitle"), jPanel1); // NOI18N

        mainPanel.add(TabbedPane);

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText(resourceMap.getString("jMenuItem1.text")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        fileMenu.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText(resourceMap.getString("jMenuItem2.text")); // NOI18N
        jMenuItem2.setName("jMenuItem2"); // NOI18N
        fileMenu.add(jMenuItem2);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(datareader.DataReaderApp.class).getContext().getActionMap(DataReaderView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        jMenuItem3.setText(resourceMap.getString("jMenuItem3.text")); // NOI18N
        jMenuItem3.setName("jMenuItem3"); // NOI18N
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText(resourceMap.getString("jMenuItem4.text")); // NOI18N
        jMenuItem4.setName("jMenuItem4"); // NOI18N
        jMenu1.add(jMenuItem4);

        jMenuItem5.setText(resourceMap.getString("jMenuItem5.text")); // NOI18N
        jMenuItem5.setName("jMenuItem5"); // NOI18N
        jMenu1.add(jMenuItem5);

        menuBar.add(jMenu1);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 874, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 704, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        jPanel4.setName("jPanel4"); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void Playback_SlowMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Playback_SlowMouseReleased
        //To-Do: fill this shit in...
        Playbar.Slower();
    }//GEN-LAST:event_Playback_SlowMouseReleased

    private void Playback_PlayMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Playback_PlayMouseReleased
        Playbar.Play();
    }//GEN-LAST:event_Playback_PlayMouseReleased

    private void Playback_PauseMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Playback_PauseMouseReleased
        Playbar.Pause();
    }//GEN-LAST:event_Playback_PauseMouseReleased

    private void Playback_StopMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Playback_StopMouseReleased
        Playbar.Stop();
    }//GEN-LAST:event_Playback_StopMouseReleased

    private void Playback_FastMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Playback_FastMouseReleased
        Playbar.Faster();
    }//GEN-LAST:event_Playback_FastMouseReleased

    private void Playback_OpenMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Playback_OpenMouseReleased
        Playbar.Eject();
    }//GEN-LAST:event_Playback_OpenMouseReleased

    private void jButton7MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseReleased
      System.out.println("Start");
        FreeEMSBin ff = new FreeEMSBin("C:\\Users\\Bryan\\Desktop\\inputlog.bin");
        System.out.println("Creating Generic Log");
       GenericLog g = ff.getGenericLog();
       System.out.println("Test Log");
       g.testLog();
    }//GEN-LAST:event_jButton7MouseReleased

    public void setStatusText(String s) {
        statusMessageLabel.setText(s);
    }

    public javax.swing.JPanel getGraphWindow() {
        return GraphWindow;
    }

    public void setGraphWindow(javax.swing.JPanel g) {
        GraphWindow = g;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel GraphWindow;
    private javax.swing.JScrollPane GraphingScrollPane;
    private javax.swing.JPanel GraphingTabPane;
    private javax.swing.JPanel GraphingViewPane;
    private javax.swing.JPanel OptionPane;
    private javax.swing.JScrollPane OptionScrollPane;
    private javax.swing.JPanel PlayBackPanel;
    private javax.swing.JButton Playback_Fast;
    private javax.swing.JButton Playback_Open;
    private javax.swing.JButton Playback_Pause;
    private javax.swing.JButton Playback_Play;
    private javax.swing.JButton Playback_Slow;
    private javax.swing.JButton Playback_Stop;
    private javax.swing.JTabbedPane TabbedPane;
    private javax.swing.JButton jButton7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private Playback Playbar;
    private JDialog aboutBox;
}