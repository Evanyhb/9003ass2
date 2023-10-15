package WizardTD;

import processing.core.PVector;

public class Point {
    int x;
    int y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x+ "," + y + ")";
    }

    public PVector toPVector(){
        return new PVector(x,y);
    }
}
