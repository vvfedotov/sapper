package net.vvf.enums;

import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;


public enum SapperMenu {
    Restart(0, "File", "Restart", "javax.swing.JMenuItem", KeyEvent.VK_F2, true),
    Separator0(1, "File"),
    Newbie(2, "File", "Newbie", "javax.swing.JCheckBoxMenuItem", null, SettingsGame.Newbie, true),
    Amateur(3, "File", "Amateur", "javax.swing.JCheckBoxMenuItem", null, SettingsGame.Amateur, true),
    Professional(4, "File", "Professional", "javax.swing.JCheckBoxMenuItem", null, SettingsGame.Professional, true),
    Customized(5, "File", "Customized", "javax.swing.JCheckBoxMenuItem", null, SettingsGame.Custom, false),
    Champions(6, "File", "Champions", "javax.swing.JMenuItem", KeyEvent.VK_F5, false),
    Separator1(7, "File"),
    Exit(8, "File", "Exit", "javax.swing.JMenuItem", KeyEvent.VK_ESCAPE, true),
    About(100, "Help", "About", "javax.swing.JMenuItem", null, true);
    
    
    private int id;
    private String mName;
    private String iName;
    private String typeItem;
    private KeyStroke keyStroke;
    private SettingsGame settings;
    private Boolean access;
    
    
    SapperMenu(Integer id, String mName, String iName, String typeItem, Integer keyStroke, SettingsGame settings, Boolean access){
        this.id = id;
        this.mName = mName;
        this.iName = iName;
        this.typeItem = typeItem;
        this.keyStroke = keyStroke != null ? KeyStroke.getKeyStroke(keyStroke, KeyEvent.CTRL_MASK) : null;
        this.settings = settings;
        this.access = access;
        
    }
    SapperMenu(Integer id, String mName, String iName, String typeItem, Integer keyStroke, Boolean access){
        this.id = id;
        this.mName = mName;
        this.iName = iName;
        this.typeItem = typeItem;
        this.keyStroke = keyStroke != null ? KeyStroke.getKeyStroke(keyStroke, KeyEvent.CTRL_MASK) : null;
        this.settings = null;
        this.access = access;
    }
    SapperMenu(Integer id, String mName){
        this.id = id;
        this.mName = mName;
        this.iName = null;
        this.typeItem = null;
        this.keyStroke = null;
        this.settings = null;
        this.access = true;
    }

    public int getId() {
        return id;
    }

    public String getmName() {
        return mName;
    }

    public String getiName() {
        return iName;
    }

    public String getTypeItem() {
        return typeItem;
    }

    public KeyStroke getKeyStroke() {
        return keyStroke;
    }

    public SettingsGame getSettings() {
        return settings;
    }

    public Boolean getAccess() {
        return access;
    }

    
}
