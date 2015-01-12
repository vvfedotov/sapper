package net.vvf.ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;
import net.vvf.elements.Cell;
import net.vvf.enums.*;
import net.vvf.util.*;
import net.vvf.util.interfaces.*;
import net.vvf.elements.ReadableCell;

public class GameField extends JPanel implements SapperGame{
    private Color backGround;
    private SettingsGame gSettings;
    private ImageCustodian iCustodian;
    private ArrayList<Cell> gridFields;
    private Rules rules;
    private DeviceListeners dListener;
    private Integer counterMine;
    private Integer counterTime;
    private Boolean startGame;
    private Boolean resultGame;
    private String resultGameMessage;
    
    private ReadableCell currentCell;
    private ReadableCell previousCell;
    private GraphicsConfiguration graphicsConfiguration; 
    
    public GameField(SettingsGame settings, ImageCustodian custodian, Rules rules){
        this.backGround = Color.lightGray;
        this.gSettings = settings;
        this.iCustodian = custodian;
        this.rules = rules;
        init();
        this.setOpaque(false);
    }
    
    private void init(){
        graphicsConfiguration = GraphicsEnvironment
                                .getLocalGraphicsEnvironment()
                                .getDefaultScreenDevice()
                                .getDefaultConfiguration();

        gridFields = new ArrayList<>(gSettings.getHeight() * gSettings.getWidth());
        for(int i = gSettings.getHeight() * gSettings.getWidth(); i > 0 ; i--)
            gridFields.add(new Cell());
        rules.generationMine(gSettings, gridFields);
        rules.setSapperGame(this);
        setCounterMine(gSettings.getMine());
        setCounterTime(0);
        startGame = false;
        resultGame = false;
    }

    public void setDListener(DeviceListeners dListener) {
        this.dListener = dListener;
    }
    
    private void setPreviousCell() {
        this.previousCell = currentCell;
    }
    
    private BufferedImage createBufferedImage(){
        return graphicsConfiguration.createCompatibleImage(
                    gSettings.getWidth()  * iCustodian.getWidthImage(),
                    gSettings.getHeight() * iCustodian.getHightImage());
    }
    
    private Graphics initBufferedImage(BufferedImage bImage){
        Graphics gBackGround = bImage.getGraphics();
        gBackGround.setColor(backGround);
        gBackGround.fillRect(0, 0, gSettings.getWidth()  * iCustodian.getWidthImage(), gSettings.getHeight() * iCustodian.getHightImage());
        drawGrid(gBackGround);
        return gBackGround;
    }
    
    private void drawGrid(Graphics g){
        g.setColor(Color.GRAY);
        for(int column = gSettings.getWidth(); column >= 0; column--)
            g.drawLine(column  * iCustodian.getWidthImage(), 0, column  * iCustodian.getWidthImage(), iCustodian.getHightImage() * gSettings.getHeight());
        for(int row = gSettings.getHeight(); row >= 0 ; row--)
            g.drawLine(0, row * iCustodian.getHightImage(), iCustodian.getWidthImage() * gSettings.getWidth(), row * iCustodian.getHightImage());
    }

    private void drawCells(Graphics g) throws UnsupportedOperationException{
        int index = 0;
        int checkedCells = 0;
        for(Cell cell : gridFields){
            if(cell.getStatus().equals(StatusCell.Open) || cell.getStatus().equals(StatusCell.Marker))                    
                checkedCells++;
            index = drawField(g, index, rules.getImageCell(cell));
        }
        rules.winGame(checkedCells, gridFields.size());
    }
    
    private void drawOpenGameField(Graphics g){
        int index = 0;
        for(Cell cell : gridFields)
            index = drawField(g, index, rules.getImageFlashCell(cell));
    }

    private int drawField(Graphics g, int index, BorderImage bImage){
        int column = index % gSettings.getWidth();
        int row = index / gSettings.getWidth();
        Image image = iCustodian.getImage(bImage);
        if(image != null)
            g.drawImage(image, (column * iCustodian.getWidthImage()) + 1, (row * iCustodian.getWidthImage()) + 1, null);
        return index + 1;
    }

    @Override
    public Integer getCounterMine() {
        return counterMine;
    }

    @Override
    public Integer getCounterTime() {
        return counterTime;
    }

    @Override
    public void setCounterMine(Integer counterMine) {
        this.counterMine = counterMine;
    }

    @Override
    public void setCounterTime(Integer counterTime) {
        this.counterTime = counterTime;
    }

    @Override
    public Boolean isStartGame() {
        return startGame;
    }

    @Override
    public void setStartGame(Boolean startGame) {
        this.startGame = startGame;
    }

    @Override
    public void setResultGame(String resultGameMessage) {
        resultGame = true;
        this.resultGameMessage = resultGameMessage;
    }
    
    @Override
    public String getResultGameMessage(){
        return resultGameMessage;
    }
    @Override
    public Boolean isResultGame(){
        return resultGame;
    }
    @Override
    public Dimension getPreferredSize() {
      return new Dimension(gSettings.getWidth()*iCustodian.getWidthImage() + 1, gSettings.getHeight()*iCustodian.getHightImage() + 1);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        BufferedImage fieldBackGround = createBufferedImage();
        Graphics gBackGround = initBufferedImage(fieldBackGround);
        try{
            drawCells(gBackGround);
        }catch(UnsupportedOperationException e){
            fieldBackGround = createBufferedImage();
            gBackGround = initBufferedImage(fieldBackGround);
            dListener.stopListeners();
            drawOpenGameField(gBackGround);
        }
        super.paintComponent(g);
        g.drawImage(fieldBackGround, 0, 0, null);
    }
    
    @Override
    public SettingsGame getGameSettings() {
        return gSettings;
    }

    @Override
    public ImageCustodian getImageCustodian() {
        return iCustodian;
    }

    @Override
    public void setCurrentCell(int column, int row) {
        if(currentCell != null){
            if(!currentCell.equals(row, column)){
                setPreviousCell();
                currentCell = new ReadableCell(gridFields.get(row * gSettings.getWidth() + column), row, column);
            }
        }else
            currentCell = new ReadableCell(gridFields.get(row * gSettings.getWidth() + column), row, column);
    }   

    @Override
    public void setPressedCell(){
        rules.actionPressed(currentCell, gSettings, gridFields);
        update(getGraphics());
    }
     
    @Override    
    public void setReleasedCell(){
        rules.actionReleased(currentCell, gSettings, gridFields);
        update(getGraphics());
    }
    
    @Override
    public void setKey(KeyButtons kb){
        currentCell.setKey(kb);
    }
}
