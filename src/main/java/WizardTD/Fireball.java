package WizardTD;

import processing.core.PImage;
import processing.core.PVector;

public class Fireball extends MoveElement implements Drawable{
    PVector position;
    Monster target;
    PImage image;

    int damage;
    public Fireball( App app, PVector position, int damage, Monster target) {
        super(app,5.0f);
        this.position = position.add(13f,13f);
        this.target = target;
        this.damage = damage;
        setImage();
    }


    public void setImage() {
        this.image = app.imageMap.get("fireball");
    }

    public void draw(){
        app.image(image, position.x, position.y);
        PVector targetLocation = target.getPosition().add(7f,7f);
        position = move(position, targetLocation);
        float distance = PVector.dist(position, targetLocation);
        if (distance < speed*app.gameSpeed) {
          target.takeDamage(damage);
          app.iterator.remove();
        }

    }



}
