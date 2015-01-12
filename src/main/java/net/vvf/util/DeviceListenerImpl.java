package net.vvf.util;

import java.awt.event.*;
import javax.swing.JPanel;
import net.vvf.enums.KeyButtons;
import net.vvf.util.interfaces.DeviceListeners;
import net.vvf.util.interfaces.SapperGame;


public class DeviceListenerImpl<T extends JPanel, I extends SapperGame> implements DeviceListeners{
    private T objectT;
    private I objectI;
    private int maxWidth;
    private int maxHeight;
    private int imageWidth;
    private int imageHeight;


    @Override
    public void setListenerObject(Object obj) {
        this.objectT = (T) obj;
    }
    
    @Override
    public void setWorkObject(Object obj) {
        this.objectI = (I) obj;
    }
    
    @Override
    public void runListeners(){
        if(objectT != null){
            imageWidth = objectI.getImageCustodian().getWidthImage();
            imageHeight = objectI.getImageCustodian().getHightImage();
            maxWidth = objectI.getGameSettings().getWidth() * imageWidth - 1;
            maxHeight = objectI.getGameSettings().getHeight() * imageHeight - 1;
            objectT.addMouseListener(this);
            objectT.addMouseMotionListener(this);
        }
    }
    
    @Override
    public void stopListeners(){
        if(objectT != null && objectT instanceof JPanel){
            ((JPanel)objectT).removeMouseListener(this);
            ((JPanel)objectT).removeMouseMotionListener(this);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        objectI.setCurrentCell(getRow(e), getColumn(e));
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
    }

        
    @Override
    public void mousePressed(MouseEvent e) {
        objectI.setKey(KeyButtons.getKeyButtons(e.getButton(), e.getModifiersEx()));
        objectI.setPressedCell();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        objectI.setKey(KeyButtons.getKeyButtons(e.getButton(), e.getModifiersEx()));
        objectI.setReleasedCell();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {        
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    private int getRow(MouseEvent e){
        int cursorX = e.getX() > maxWidth ? maxWidth : e.getX();
        return cursorX / imageWidth;
    }
    
    private int getColumn(MouseEvent e){
        int cursorY = e.getY() > maxHeight ? maxHeight : e.getY();
        return cursorY / imageHeight;
    }
}
