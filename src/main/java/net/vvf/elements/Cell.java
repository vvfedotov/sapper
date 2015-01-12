package net.vvf.elements;

import net.vvf.enums.*;

public class Cell {
    private ValueCell value; 
    private StatusCell status;
    private TypeMine type;

    public Cell(){
        this.value = ValueCell.Empty;
        this.status = StatusCell.Close;
    }
    
    public ValueCell getValue() {
        return value;
    }

    public void setValue(ValueCell value) {
        this.value = value;
    }

    public StatusCell getStatus() {
        return status;
    }

    public void setStatus(StatusCell status) {
        this.status = status;
    }

    public TypeMine getType() {
        return type;
    }

    public void setType(TypeMine type) {
        this.type = type;
    }
}
