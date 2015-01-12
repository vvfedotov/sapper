package net.vvf.enums;

public enum BorderImage {
    One(0),
    Two(1),
    Three(2),
    Four(3),
    Five(4),
    Six(5),
    Seven(6),
    Eight(7),
    FieldDown(8),
    Field(9),
    FieldMarkerDown(10),
    FieldMarker(11),
    QuestionDown(12),
    Question(13),
    Mine(14),
    MineFlash(15),
    MineFault(16);

    private Integer index;
    
    BorderImage(Integer index){
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }
}
