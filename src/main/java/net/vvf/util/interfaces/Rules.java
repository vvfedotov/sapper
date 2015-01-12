package net.vvf.util.interfaces;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import net.vvf.elements.*;
import net.vvf.enums.*;
import net.vvf.elements.ReadableCell;

public interface Rules extends ActionListener{
    public final Integer delay = 1000;
    public void generationMine(SettingsGame gSettings, ArrayList<Cell> gridFields);
    public BorderImage getImageFlashCell(Cell cell);
    public BorderImage getImageCell(Cell cell);
    public void actionReleased(ReadableCell rCell, SettingsGame gSettings, ArrayList<Cell> gridFields);
    public void actionPressed(ReadableCell rCell, SettingsGame gSettings, ArrayList<Cell> gridFields);
    public void setSapperGame(SapperGame game);
    public void winGame(Integer checkedCells, Integer allCells);
}
