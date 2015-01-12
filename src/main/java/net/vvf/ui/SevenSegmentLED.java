
package net.vvf.ui;

import java.awt.*;
import java.awt.image.*;
import java.util.HashMap;
import javax.swing.JPanel;
import net.vvf.enums.Element;

public class SevenSegmentLED extends JPanel{
    private final Integer leftBorder = 2;
    private final Integer countVerticalSegment = 2;
    private final Integer countHorizontalSegment = 1;
    private final Integer horizontalClearance = 3;
    private final Integer verticalClearance = 5;
    private final Integer topVerticalSegment = 0;
    private final Integer bottomVerticalSegment = 1;
    
    private GraphicsConfiguration graphicsConfiguration;
    
    private Color backGround;
    private Color segmentGlowing;
    private Color segmentNotGlowing;
    private Integer countLED;
    private Integer lengthSegment;
    private String value;
    private HashMap<Byte, BufferedImage> store;
    
    public SevenSegmentLED(){
        this.backGround = Color.BLACK;
        this.segmentGlowing = Color.RED;
        this.segmentNotGlowing = Color.GRAY;
        this.countLED = 3;
        init();
        this.setOpaque(false);
    }
    
    public SevenSegmentLED(Color backGround, Color segmentGlowing, Color segmentNotGlowing, Integer countLED ){
        this.backGround = backGround;
        this.segmentGlowing = segmentGlowing;
        this.segmentNotGlowing = segmentNotGlowing;
        this.countLED = countLED;
        init();
    }
    
    private void init(){
        this.lengthSegment = 15;
        graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment()
                                 .getDefaultScreenDevice()
                                 .getDefaultConfiguration();
        store = new HashMap<>(Element.values().length);
    }
    
    private Integer calculateHeight(){
        return lengthSegment * countVerticalSegment + horizontalClearance;
    } 

    private Integer calculateWidth(){
        return (lengthSegment * countHorizontalSegment + verticalClearance) * countLED;
    } 
    
    private Integer calculateWidthOneLED(){
        return (lengthSegment * countHorizontalSegment + verticalClearance);
    }
        
    private BufferedImage createBufferedImage(){
        return graphicsConfiguration.createCompatibleImage(
                    calculateWidth(),
                    calculateHeight()
               );
    }
    
    private BufferedImage createBufferedImageOneLED(){
        return graphicsConfiguration.createCompatibleImage(
                    calculateWidthOneLED(),
                    calculateHeight()
               );
    }
    
    private Graphics initBufferedImage(BufferedImage bImage){
        Graphics gBackGround = bImage.getGraphics();
        gBackGround.setColor(backGround);
        gBackGround.fillRect(0, 0, bImage.getWidth(), bImage.getHeight());
        return gBackGround;
    }
    
    private BufferedImage paintSegments(Element element){
        BufferedImage draw = createBufferedImageOneLED();
        Graphics g = initBufferedImage(draw);
        int step = 0;
        while(step < 7){
            if(((element.getBitCode() >> step) & 1) == 1)
                g.setColor(segmentGlowing);
            else
                g.setColor(segmentNotGlowing);
            switch(step){
                case 0: paintTopSegment(g);
                    break;
                case 1: paintRightSegment(g, topVerticalSegment);
                    break;
                case 2: paintRightSegment(g, bottomVerticalSegment);
                    break;
                case 3: paintBottomSegment(g);
                    break;
                case 4: paintLeftSegment(g, bottomVerticalSegment);
                    break;
                case 5: paintLeftSegment(g, topVerticalSegment);
                    break;
                case 6: paintCenterSegment(g);
                    break;
            }
            step++;
        }
        return draw;
    }

   private void paintTopSegment(Graphics g){
       g.drawLine(leftBorder + 1, 1, lengthSegment, 1);
       g.drawLine(leftBorder + 2, 2, lengthSegment - 1, 2);
       g.drawLine(leftBorder + 3, 3, lengthSegment - 2, 3);
   } 
    
   private void paintBottomSegment(Graphics g){
       int height = calculateHeight() - 2;
       g.drawLine(leftBorder + 1, height, lengthSegment, height);
       g.drawLine(leftBorder + 2, height - 1, lengthSegment - 1, height - 1);
       g.drawLine(leftBorder + 3, height - 2, lengthSegment - 2, height - 2);
   }   
   
   private void paintCenterSegment(Graphics g){
       int height = (calculateHeight() - 1) >> 1;
       g.drawLine(leftBorder + 1, height, lengthSegment, height);
       g.drawLine(leftBorder + 2, height - 1, lengthSegment - 1, height - 1);
       g.drawLine(leftBorder + 2, height + 1, lengthSegment - 1, height + 1);
   } 
      
   private void paintLeftSegment(Graphics g, int index){
       int shift = index * lengthSegment;
       g.drawLine(leftBorder, shift + 2, leftBorder, shift + lengthSegment);
       g.drawLine(leftBorder + 1, shift + 3, leftBorder + 1, shift + lengthSegment - 1);
       g.drawLine(leftBorder + 2, shift + 4, leftBorder + 2, shift + lengthSegment - 2);
   } 
   
   private void paintRightSegment(Graphics g, int index){
       int shift = index * lengthSegment;
       g.drawLine(leftBorder + lengthSegment - 1, shift + 2, leftBorder + lengthSegment - 1, shift + lengthSegment);
       g.drawLine(leftBorder + lengthSegment - 2, shift + 3, leftBorder + lengthSegment - 2, shift + lengthSegment - 1);
       g.drawLine(leftBorder + lengthSegment - 3, shift + 4, leftBorder + lengthSegment - 3, shift + lengthSegment - 2);
   } 
   
   @Override
   public Dimension getPreferredSize() {
     return new Dimension(calculateWidth(), calculateHeight());
   }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }
    
   
   @Override
   public void paintComponent(Graphics g) {
     BufferedImage fieldBackGround = createBufferedImage();
     Graphics gBackGround = initBufferedImage(fieldBackGround);
     int index = value.length() - 1;
     for(int i = countLED - 1; i >= 0; i--){
         Element element;
         try{
             element = Element.getElementByName(value.substring(index, index + 1));
         }catch(Exception e){
             element = Element.Empty;
         }
        BufferedImage drawLED = store.get(element.getBitCode());
        if(drawLED == null){
            drawLED = paintSegments(element);
            store.put(element.getBitCode(), drawLED);
        }
        gBackGround.drawImage(drawLED, i * calculateWidthOneLED(), 0, null);
        index--;
     }
     super.paintComponent(g);
     g.drawImage(fieldBackGround, 0, 0, null);
   } 

    public void setValue(String value) {
        if(!value.equals(this.value)){
            this.value = value;
            update(getGraphics());
        }
    }
}

