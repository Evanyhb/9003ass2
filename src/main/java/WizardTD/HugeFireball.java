package WizardTD;

import processing.core.PApplet;
import processing.core.PVector;

import static WizardTD.App.CELLSIZE;

public class HugeFireball extends Fireball{

    PVector targetLocation;
    boolean exploding = false;
    float explosionRadius = 0;

    float explodeindex;
    public HugeFireball(App app, PVector position, int damage, Monster target) {
        super(app, position, damage, target);
        speed = 1.0f;
        targetLocation = target.getPosition().add(7f,7f);
    }
    @Override
    public void setImage(){
        super.setImage();
        image.resize(24,24);
    }


    @Override
    public void draw(){
        if (exploding) {
            drawExplosion();
        } else {
            app.image(image, position.x, position.y);
            position = move(position, targetLocation);
            float distance = PVector.dist(position, targetLocation);
            if (distance <= speed*app.gameSpeed+1) {
            exploding = true;
            explode();

        }
        }

    }

    public void explode(){


        //todo
        for (Monster monster : app.monsterList) {
            if(monster.isAlive()){
                PVector mposition = monster.getPosition();
                float distance = PApplet.dist(position.x+ CELLSIZE/2, position.y+CELLSIZE/2, mposition.x+10, mposition.y+10);
                if (distance < 64) {

                    monster.takeDamage(damage);
                }

            }

        }


    }

    void drawExplosion() {
        if (explosionRadius < 64) {
            explosionRadius += 4* app.gameSpeed;
            app.stroke(255, 150, 0);
            app.fill(255, 0, 0, 70);
            app.ellipse(position.x, position.y, explosionRadius, explosionRadius);
        } else {
            app.iterator.remove();
        }
    }


}
