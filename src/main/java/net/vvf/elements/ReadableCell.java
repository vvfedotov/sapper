package net.vvf.elements;

import net.vvf.elements.Cell;
import net.vvf.enums.KeyButtons;

public class ReadableCell {
    private KeyButtons key;
    private Cell cell;
    private int row;
    private int column;

    public ReadableCell(Cell cell, int row, int column) {
        this.cell = cell;
        this.row = row;
        this.column = column;
    }

    public boolean equals(int row, int column){
        if(row == this.row && column == this.column)
            return true;
        return false;
    }
    
    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public KeyButtons getKey() {
        return key;
    }

    public void setKey(KeyButtons key) {
        this.key = key;
    }
    
}
