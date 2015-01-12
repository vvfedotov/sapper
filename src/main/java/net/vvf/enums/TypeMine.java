package net.vvf.enums;;

public enum TypeMine {
    ObjectMine(1),
    SignalMine(2), 
    MineTrap(3);

    private int type;
    
    TypeMine(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
    
    public static TypeMine getType(int type){
        switch(type){
            case 1: return ObjectMine;
            case 2: return SignalMine;
            case 3: return MineTrap;
        }
        return null;
    }
}
