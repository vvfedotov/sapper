package net.vvf.enums;

public enum SettingsGame {
    Newbie(10, 10, 10, "Newbie"),
    Amateur(16, 16, 40, "Amateur"),
    Professional(30, 16, 99, "Professional"),
    Custom(null, null, null, "Custom");
    
    private Integer width;
    private Integer height;
    private Integer mine;
    private String name;
    
    
    SettingsGame(Integer width, Integer height, Integer mine, String name){
        this.width = width;
        this.height = height;
        this.mine = mine;
        this.name = name;
    }

    public static SettingsGame getSettingsByName(String name){
        for(SettingsGame sg : SettingsGame.values())
            if(sg.getName().equals(name))
                return sg;
        return Newbie;
    }
    
    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getMine() {
        return mine;
    }

    public void setMine(Integer mine) {
        this.mine = mine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
