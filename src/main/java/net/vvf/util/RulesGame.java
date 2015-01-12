package net.vvf.util;

import java.awt.event.ActionEvent;
import java.util.*;

import net.vvf.elements.*;
import net.vvf.enums.*;
import net.vvf.elements.ReadableCell;
import net.vvf.util.interfaces.*;

public class RulesGame implements Rules{
    private SapperGame sapperGame;
    private javax.swing.Timer timer;
    @Override
    public BorderImage getImageFlashCell(Cell cell){
        switch(cell.getStatus()){
            case Open:                 
                if(cell.getValue().equals(ValueCell.Mine))
                    return BorderImage.MineFlash;
                else
                    return getOpenValue(cell);
            case Close:
            case CloseDown: 
            case Questioned:
            case QuestionedDown: 
                if(cell.getValue().equals(ValueCell.Mine))
                    return BorderImage.Mine;
                else
                    return getOpenValue(cell);
            case Marker:
            case MarkerDown:
                if(cell.getValue().equals(ValueCell.Mine))
                    return BorderImage.FieldMarker;
                else
                    return BorderImage.MineFault;
        }
        return null;
    }

    @Override
    public BorderImage getImageCell(Cell cell) throws UnsupportedOperationException{
        switch(cell.getStatus()){
            case Open: return getOpenValue(cell);    
            case Close: return BorderImage.Field;
            case CloseDown: return BorderImage.FieldDown;
            case Questioned: return BorderImage.Question;
            case QuestionedDown: return BorderImage.QuestionDown;
            case Marker: return BorderImage.FieldMarker;
            case MarkerDown: return BorderImage.FieldMarkerDown;
        }
        return null;
    }

    @Override
    public void actionReleased(ReadableCell rCell, SettingsGame gSettings, ArrayList<Cell> gridFields) {
        switch(rCell.getKey()){
            case MouseLeftButton:
                if(!rCell.getCell().getStatus().equals(StatusCell.MarkerDown)){
                    rCell.getCell().setStatus(StatusCell.Open);
                    if(rCell.getCell().getValue().equals(ValueCell.Empty))
                        openAdjacentCells(rCell, gSettings, gridFields);
                }
                else
                    rCell.getCell().setStatus(StatusCell.Marker);
                break;
            case MouseRightButton:
                break;
            case MouseCenterButton:
                rCell.getCell().setStatus(StatusCell.Open);
                downUpAdjacentCells(rCell, gSettings, gridFields);
                openAdjacentCells(rCell, gSettings, gridFields);
                break;
        }
    }

    @Override
    public void actionPressed(ReadableCell rCell, SettingsGame gSettings, ArrayList<Cell> gridFields) {
        if(!sapperGame.isStartGame()){
            timer.start();
            sapperGame.setStartGame(true);
        }
        switch(rCell.getKey()){
            case MouseLeftButton:
                    switch(rCell.getCell().getStatus()){
                        case Close: rCell.getCell().setStatus(StatusCell.CloseDown);
                            break;
                        case Marker: rCell.getCell().setStatus(StatusCell.MarkerDown);
                            break;
                        case Questioned: rCell.getCell().setStatus(StatusCell.QuestionedDown);                
                    }
                break;
            case MouseRightButton:
                    switch(rCell.getCell().getStatus()){
                        case Close: rCell.getCell().setStatus(StatusCell.Marker);
                        sapperGame.setCounterMine(sapperGame.getCounterMine() - 1);
                            break;
                        case Marker: rCell.getCell().setStatus(StatusCell.Questioned);
                            sapperGame.setCounterMine(sapperGame.getCounterMine() + 1);
                            break;
                        case Questioned: rCell.getCell().setStatus(StatusCell.Close);                
                    }
                break;
            case MouseCenterButton:
                    downUpAdjacentCells(rCell, gSettings, gridFields);
                break;
        }
    }

    @Override
    public void generationMine(SettingsGame gSettings, ArrayList<Cell> gridFields) {
        int counterMine = gSettings.getMine();
        int rangeMine = gridFields.size()-1;
        Random generator = new Random();
        timer = new javax.swing.Timer(Rules.delay, this);

        while(counterMine > 0){
            int indexMine = generator.nextInt(rangeMine);
            Cell cell = gridFields.get(indexMine);
            if(cell.getValue() != ValueCell.Mine){
                cell.setValue(ValueCell.Mine);
                cell.setType(TypeMine.getType(generator.nextInt(TypeMine.values().length)));
                HashSet<Integer> workList = getWorkRange(gSettings.getWidth(), gSettings.getHeight(), indexMine);
                for(Integer indexCube : workList){
                    cell = gridFields.get(indexCube);
                    if(cell.getValue() != ValueCell.Mine)
                        cell.setValue(ValueCell.getValueCell(cell.getValue().getValue() + 1));
                }
                counterMine--;
            }
        }
    }
    
    private boolean stopGame(Cell cell){
        switch(cell.getStatus()){
            case Open: 
                if(cell.getValue() == ValueCell.Mine){
                    timer.stop();
                    sapperGame.setStartGame(false);
                    sapperGame.setResultGame("Game Over");
                    return true;
                }
        }
        return false;
    }
    
    @Override
    public void winGame(Integer checkedCells, Integer allCells){
        if(checkedCells == allCells){
            timer.stop();
            sapperGame.setStartGame(false);
            sapperGame.setResultGame("You Win!");
        }
    }
    
    private BorderImage getOpenValue(Cell cell) throws UnsupportedOperationException{
        switch(cell.getValue()){
            case Empty:
                break;
            case One: return BorderImage.One;
            case Two: return BorderImage.Two;
            case Three: return BorderImage.Three;    
            case Four: return BorderImage.Four;
            case Five: return BorderImage.Five;    
            case Six: return BorderImage.Six;
            case Seven: return BorderImage.Seven;
            case Eight: return BorderImage.Eight;
            case Mine: 
                if(stopGame(cell))
                    throw new UnsupportedOperationException();
                return  BorderImage.MineFault; 
       }
       return null; 
    } 
    
    private HashSet<Integer> getWorkRange(int width, int height, int index){
        HashSet<Integer> range = new HashSet<>(8);
        int row = index / width;
        int column = index - row * width;
        if(column - 1 >= 0)
            range.add(index - 1);
        if(column + 1 < width)
            range.add(index + 1);
        if(row - 1 >= 0){
            range.add(index - width);
            if(column - 1 >= 0)
                range.add(index - 1 - width);
            if(column + 1 < width)
                range.add(index + 1 - width);
        }
        if(row + 1 < height){
            range.add(index + width);
            if(column - 1 >= 0 )
                range.add(index - 1 + width);
            if(column + 1 < width)
                range.add(index + 1 + width);
        }
        return range;
    }
    
    private int calculateIndex(int row, int column, int width){
        return row * width + column;
    }

    private void downUpAdjacentCells(ReadableCell rCell, SettingsGame gSettings, ArrayList<Cell> gridFields){
        int index = calculateIndex(rCell.getRow(), rCell.getColumn(), gSettings.getWidth());
        HashSet<Integer> wRange = getWorkRange(gSettings.getWidth(), gSettings.getHeight(), index);
        for(Integer iElement : wRange){
            Cell cell = gridFields.get(iElement);
            switch(cell.getStatus()){
                case Close: 
                    cell.setStatus(StatusCell.CloseDown);
                    break;
                case CloseDown: 
                    if(rCell.getCell().getStatus().equals(StatusCell.Open)){
                        if(!cell.getValue().equals(ValueCell.Empty))
                            cell.setStatus(StatusCell.Open);
                        else
                            cell.setStatus(StatusCell.Close);
                    }else
                        cell.setStatus(StatusCell.Close);
                    break;
                case Questioned: 
                    cell.setStatus(StatusCell.QuestionedDown);
                    break;
                case QuestionedDown:
                    if(rCell.getCell().getStatus().equals(StatusCell.Open))
                        if(!cell.getValue().equals(ValueCell.Empty))
                                cell.setStatus(StatusCell.Open);
                            else
                                cell.setStatus(StatusCell.Close);
                    else
                        cell.setStatus(StatusCell.Questioned);
            }
        }
    }
    
    private void openAdjacentCells(ReadableCell rCell, SettingsGame gSettings, ArrayList<Cell> gridFields) {
        int index = calculateIndex(rCell.getRow(), rCell.getColumn(), gSettings.getWidth());
        HashSet<Integer> wRange = getWorkRange(gSettings.getWidth(), gSettings.getHeight(), index);
        
        while(true){
            HashSet<Integer> poolIndex = new HashSet<>();
            for(Integer iElement : wRange){
                Cell cell = gridFields.get(iElement);
                if(cell.getStatus().equals(StatusCell.Close) || cell.getStatus().equals(StatusCell.Questioned)){
                    cell.setStatus(StatusCell.Open);
                    if(cell.getValue().equals(ValueCell.Empty))
                        poolIndex.addAll(getWorkRange(gSettings.getWidth(), gSettings.getHeight(), iElement));
                }
            }
            if(poolIndex.size() > 0)
                wRange = poolIndex;
            else
                break;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        sapperGame.setCounterTime(sapperGame.getCounterTime() + 1);
    }

    @Override
    public void setSapperGame(SapperGame game) {
        this.sapperGame = game;
    }
}
