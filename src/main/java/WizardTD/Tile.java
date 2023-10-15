package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.HashSet;
import java.util.Set;

public class Tile implements Drawable{
    enum tiletype{
        GRASS,
        SHRUB,
        PATH,
        Tower,

    }
    App app;
    tiletype type;
    int x;
    int y;
    PImage image;

    public Tile(App app, tiletype type , int x, int y){
        this.app = app;
        this.type = type;
        this.x = x;
        this.y = y;


    }
    public void setImage(){
        if(type == tiletype.GRASS){
            image = app.imageMap.get("grass");

        } else if (type == tiletype.SHRUB) {
            image = app.imageMap.get("shrub");

        }  else{
            image = app.imageMap.get("grass");

        }
    }
    public void draw(){
        app.image(image,x,y);
    }
    public void setNeighbors(Path.Location location){

    }
}
