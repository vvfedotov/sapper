package net.vvf.enums;

public enum Element {
    /*
        __0__
       |     |
       5     1
       |__6__|
       |     |
       4     2
       |__3__|    
    
    */
    Empty("", (byte)0),
    Zero("0", (byte)63),
    One("1", (byte)6),
    Two("2", (byte)91),
    Thre("3", (byte)79),
    Four("4", (byte)102),
    Five("5", (byte)109),
    Six("6", (byte)125),
    Seven("7", (byte)7),
    Eigth("8", (byte)127),
    Nine("9", (byte)111),
    Minus("-", (byte)64),
    Error("E", (byte)121);
    
    private final String name;
    private final byte bitCode;

    Element(String name, byte bitCode){
        this.name = name;
        this.bitCode = bitCode;
    }

    public String getName() {
        return name;
    }

    public byte getBitCode() {
        return bitCode;
    }
    
    public boolean equals(Element element){
        return this.name.equals(element.getName());
    }
    
    public static Element getElementByName(String name){
        for(Element element : Element.values())
            if(element.getName().equals(name))
                return element;
        return Empty;
    }
}
