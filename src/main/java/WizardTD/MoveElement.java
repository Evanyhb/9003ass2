package WizardTD;

import processing.core.PVector;

public class MoveElement {
    App app;

    float speed;





    MoveElement(App app, float speed){

        this.app = app;
        this.speed = speed;

    }
    public PVector move(PVector position, PVector target){
        PVector velocity = PVector.sub(target, position);
        velocity.normalize();
        velocity.mult(speed*app.gameSpeed);

        position.add(velocity);
        return position;
    }



}
