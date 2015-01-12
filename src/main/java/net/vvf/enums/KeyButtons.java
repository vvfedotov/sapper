package net.vvf.enums;

public enum KeyButtons {
    MouseLeftButton(1),
    MouseCenterButton(2),
    MouseRightButton(3),
    MouseLeftAndRightButton(4);
    
    private int key;
    
    KeyButtons(int key){
        this.key = key;
    }
    
    public int getKey() {
        return key;
    }
    
    public static KeyButtons getKeyButtons(int key, int modifiers){
        switch(key){
            case 1: return modifiers != 5120 ? MouseLeftButton : MouseLeftAndRightButton;
            case 2: return MouseCenterButton;
            case 3: return modifiers != 5120 ? MouseRightButton : MouseLeftAndRightButton;
        }
        return null;
    }
    
    public boolean equals(KeyButtons kb){
        return key == kb.key;
    }
}
