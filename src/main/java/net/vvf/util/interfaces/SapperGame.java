package net.vvf.util.interfaces;

import net.vvf.enums.KeyButtons;
import net.vvf.enums.SettingsGame;
import net.vvf.util.ImageCustodian;

public interface SapperGame {
    public SettingsGame getGameSettings();
    public ImageCustodian getImageCustodian();
    public void setCurrentCell(int row, int column);
    public void setPressedCell();
    public void setReleasedCell();
    public void setKey(KeyButtons kb);
    public Integer getCounterMine();
    public Integer getCounterTime();
    public void setCounterMine(Integer counterMine);
    public void setCounterTime(Integer counterTime);
    public Boolean isStartGame();
    public void setStartGame(Boolean startGame);
    public void setResultGame(String resultGameMessage);
    public Boolean isResultGame();
    public String getResultGameMessage();
}
