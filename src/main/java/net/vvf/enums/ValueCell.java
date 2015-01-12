package net.vvf.enums;

public enum ValueCell {
    Mine(-1),
    Empty(0),
    One(1),
    Two(2),
    Three(3),
    Four(4),
    Five(5),
    Six(6),
    Seven(7),
    Eight(8);
    
    private int value;
    
    ValueCell(int value){
        this.value = value;  
    }

    public int getValue() {
        return value;
    }
    
    public static ValueCell getValueCell(int value){
        switch(value){
            case -1: return Mine;
            case 0:  return Empty;
            case 1:  return One;
            case 2:  return Two;
            case 3:  return Three;
            case 4:  return Four;
            case 5:  return Five;
            case 6:  return Six;
            case 7:  return Seven;
            case 8:  return Eight;                
        }
       return null; 
    }
    
    public boolean equals(ValueCell obj){
        return this.value == obj.getValue();
    }
}
