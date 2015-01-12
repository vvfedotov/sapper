package net.vvf;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.*;
import javax.swing.*;
import net.vvf.enums.*;
import net.vvf.ui.*;
import net.vvf.util.*;
import net.vvf.util.interfaces.*;


public class JSapper extends JFrame implements ActionListener, ItemListener{
    private ImageCustodian iCustodian;
    private SettingsGame currentSettings;
    private SevenSegmentLED counterMine;
    private SevenSegmentLED counterTime;
    private GameField sapperGame;
    private Box verticalBox;
    private javax.swing.Timer timer;


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JSapper("Java Sapper").setVisible(true);
            }
        });
    }

    public JSapper(String title){
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        init();
    }

    private void init(){
        verticalBox = Box.createVerticalBox();
        timer = new javax.swing.Timer(100, this);
        URL imgURL = getClass().getResource("/Images/joinImages.gif");
        if(imgURL != null){
            iCustodian = new ImageCustodian(imgURL);
        }else {
            System.err.println("Couldn't find file: " + "/Images/joinImages.gif");
            System.exit(0);
        }
        initMenu();
        getContentPane().add(verticalBox);
        pack();
        toCenterScreen();
    }

    private void toCenterScreen(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation(new Point((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.width) / 4));
    }

    private void createNewGame(SettingsGame gSettings){
        currentSettings = gSettings;
        if(counterMine == null){
            Box headerBox = new Box(BoxLayout.LINE_AXIS);
            counterMine = new SevenSegmentLED();
            counterTime = new SevenSegmentLED();
            headerBox.add(counterMine);
            headerBox.add(Box.createHorizontalGlue());
            headerBox.add(counterTime);
            verticalBox.add(headerBox);
        }
        counterMine.setValue(currentSettings.getMine().toString());
        counterTime.setValue("0");
        if(sapperGame != null)
            verticalBox.remove(sapperGame);

        DeviceListeners<GameField, SapperGame> dl = new DeviceListenerImpl();
        sapperGame = new GameField(currentSettings, iCustodian, new RulesGame());
        dl.setListenerObject(sapperGame);
        dl.setWorkObject(sapperGame);
        sapperGame.setDListener(dl);
        dl.runListeners();
        verticalBox.add(sapperGame);
        pack();
        timer.start();
    }

    private void initMenu(){
        ButtonGroup bGroup = new ButtonGroup();
        JMenuBar bar = new JMenuBar();
        HashMap<String, JMenu> menus = new HashMap<>();
        boolean firstItem = true;
        for(SapperMenu item : SapperMenu.values()){
            String mMenu = item.getmName();
            JMenu menu = menus.get(mMenu);
            if(menu == null){
                menu = new JMenu(mMenu);
                menus.put(mMenu, menu);
                bar.add(menu);
            }
            if(item.getiName() != null){
                try {
                    Class<?> element = Class.forName(item.getTypeItem());
                    Object cElement = element.newInstance();
                    if(cElement instanceof JMenuItem || cElement instanceof JCheckBoxMenuItem){
                        ((JMenuItem)cElement).setText(item.getiName());
                        ((JMenuItem)cElement).setEnabled(item.getAccess());
                        if(item.getKeyStroke() != null)
                            ((JMenuItem)cElement).setAccelerator(item.getKeyStroke());
                        if(cElement instanceof JCheckBoxMenuItem){
                            ((JCheckBoxMenuItem)cElement).addItemListener(this);
                            bGroup.add((JCheckBoxMenuItem)cElement);
                            if(firstItem){
                                ((JCheckBoxMenuItem)cElement).setState(firstItem);
                                firstItem = false;
                                createNewGame(item.getSettings());
                            }
                        }else{
                            ((JMenuItem)cElement).addActionListener(this);
                        }
                        menu.add((JMenuItem)cElement);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(JSapper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else
                menu.addSeparator();
        }
        setJMenuBar(bar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand() != null){
            SapperMenu action = SapperMenu.valueOf(e.getActionCommand());
            switch(action){
                case Exit:
                    System.exit(0);
                case Restart:
                    createNewGame(currentSettings);
                    break;
                case About:
                    String aboutMessage = "<html>Version : Ziro Alfa";
                    JOptionPane.showMessageDialog(null, aboutMessage, "About", JOptionPane.INFORMATION_MESSAGE);
                    break;
            }
        }else{
            counterMine.setValue(sapperGame.getCounterMine().toString());
            counterTime.setValue(sapperGame.getCounterTime().toString());
            if(sapperGame.isResultGame()){
                timer.stop();
                JOptionPane.showMessageDialog(null, sapperGame.getResultGameMessage(), "JSapper", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        JCheckBoxMenuItem item = (JCheckBoxMenuItem)e.getItemSelectable();
        if(item.getState()){
            createNewGame(SettingsGame.getSettingsByName(item.getText()));
        }
    }
}

