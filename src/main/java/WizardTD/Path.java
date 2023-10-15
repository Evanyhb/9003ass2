package WizardTD;

import processing.core.PApplet;

import java.util.HashSet;
import java.util.Set;

public class Path extends Tile{
    enum Location{
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private Set<Location> neighbors = new HashSet<>();

    public Path(App app, int x, int y) {
        super(app, tiletype.PATH, x, y);

    }
    @Override
    public void setImage(){
        switch(neighbors.size()){
            case 0:
                image = app.imageMap.get("path0");
            case 1:
                if(neighbors.contains(Location.UP) || neighbors.contains(Location.DOWN)){
                    image = app.rotateImageByDegrees(app.imageMap.get("path0"), 90);
                }else {
                    image = app.imageMap.get("path0");
                }
                break;
            case 2:
                if(neighbors.contains(Location.UP)&&neighbors.contains(Location.DOWN) ){
                    image = app.rotateImageByDegrees(app.imageMap.get("path0"), 90);
                }else if(neighbors.contains(Location.LEFT) && neighbors.contains(Location.RIGHT)){
                    image = app.imageMap.get("path0");

                }else if (neighbors.contains(Location.UP)&&neighbors.contains(Location.LEFT) ){
                    image = app.rotateImageByDegrees(app.imageMap.get("path1"), 90);
                }else if (neighbors.contains(Location.UP)&&neighbors.contains(Location.RIGHT) ){
                    image = app.rotateImageByDegrees(app.imageMap.get("path1"), 180);
                }else if (neighbors.contains(Location.DOWN)&&neighbors.contains(Location.LEFT) ){
                    image = app.imageMap.get("path1");
                }else if (neighbors.contains(Location.DOWN)&&neighbors.contains(Location.RIGHT) ){
                    image = app.rotateImageByDegrees(app.imageMap.get("path1"), 270);
                }
                break;
            case 3:
                if(!neighbors.contains(Location.UP)){
                    image = app.imageMap.get("path2");
                } else if (!neighbors.contains(Location.RIGHT)) {
                    image = app.rotateImageByDegrees(app.imageMap.get("path2"), 90);
                } else if (!neighbors.contains(Location.DOWN)) {
                    image = app.rotateImageByDegrees(app.imageMap.get("path2"), 180);
                } else if (!neighbors.contains(Location.LEFT)) {
                    image = app.rotateImageByDegrees(app.imageMap.get("path2"), 270);
                }
                break;
            case 4:
                image = app.imageMap.get("path3");
                break;

        }

//        switch(neighborNum){
//            case 0:
//                image = app.imageMap.get("path0");
//            case 1:
//                if(neighbors[0] || neighbors[1]){
//                    image = app.rotateImageByDegrees(app.imageMap.get("path0"), 90);
//                }else {
//                    image = app.imageMap.get("path0");
//                }
//                break;
//            case 2:
//                if(neighbors[0]&&neighbors[1] ){
//                    image = app.rotateImageByDegrees(app.imageMap.get("path0"), 90);
//                }else if(neighbors[2] && neighbors[3]){
//                    image = app.imageMap.get("path0");
//
//                }else if (neighbors[0]&&neighbors[2] ){
//                    image = app.rotateImageByDegrees(app.imageMap.get("path0"), 90);
//                }else if (neighbors[0]&&neighbors[3] ){
//                    image = app.rotateImageByDegrees(app.imageMap.get("path0"), 180);
//                }else if (neighbors[1]&&neighbors[2] ){
//                    image = app.imageMap.get("path0");
//                }else if (neighbors[1]&&neighbors[3] ){
//                    image = app.rotateImageByDegrees(app.imageMap.get("path0"), 270);
//                }
//                break;
//            case 3:
//                if(!neighbors[0]){
//                    image = app.imageMap.get("path2");
//                } else if (!neighbors[3]) {
//                    image = app.rotateImageByDegrees(app.imageMap.get("path2"), 90);
//                } else if (!neighbors[1]) {
//                    image = app.rotateImageByDegrees(app.imageMap.get("path2"), 180);
//                } else if (!neighbors[2]) {
//                    image = app.rotateImageByDegrees(app.imageMap.get("path2"), 270);
//                }
//                break;
//            case 4:
//                image = app.imageMap.get("path3");
//                break;
//
//        }


    }
    @Override
    public void setNeighbors (Location location){
        neighbors.add(location);


    }
    public Set<Location> getNeighbors(){
        return neighbors;
    }
}
