package net.vvf.enums;;

public enum StatusCell {
    Open(0),
    Close(1),
    CloseDown(2),
    Questioned(3),
    QuestionedDown(4),
    Marker(5),
    MarkerDown(6);
    
    private int status;
    
    StatusCell(int status){
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
    
    public boolean equals(StatusCell obj){
        return this.status == obj.getStatus();
    }
    
}
