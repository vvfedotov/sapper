package net.vvf.util.interfaces;

import java.awt.event.*;


public interface DeviceListeners<T, I> extends MouseListener, MouseMotionListener{
    public void runListeners();
    public void stopListeners();
    public void setWorkObject(I obj);
    public void setListenerObject(T obj);
}
