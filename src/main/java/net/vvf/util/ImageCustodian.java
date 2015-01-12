package net.vvf.util;

import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.vvf.enums.*;

public class ImageCustodian {
    private HashMap<BorderImage, Image> images = new HashMap<>();
    private int hightImage;
    private int widthImage;
    
    public ImageCustodian(java.net.URL imgURL){
        if (imgURL != null) {
            try {
                init(ImageIO.read(imgURL));
            } catch (IOException ex) {
                Logger.getLogger(ImageCustodian.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
    
    private void init(BufferedImage icon){
        hightImage = icon.getHeight();
        widthImage = icon.getWidth();
//All icons are square and placed in one line
        for(BorderImage element : BorderImage.values()){
            Image image = null;
            if((element.getIndex() + 1) * hightImage <= widthImage)
                image = icon.getSubimage(element.getIndex() * hightImage, 0, hightImage, hightImage);
            images.put(element, image);
        }
        hightImage++;
        widthImage = hightImage;
    }
    
    public Image getImage(BorderImage bImage){
        return images.get(bImage);
    }

    public int getHightImage() {
        return hightImage;
    }

    public int getWidthImage() {
        return widthImage;
    }
    
    
}

