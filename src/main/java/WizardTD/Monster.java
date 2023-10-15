package WizardTD;

import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Monster extends MoveElement implements Drawable{
    private final int offset = 6;

    MonsterType type;
    int hp;
    int maxhp;
    float armour;
    int mana_gained_on_kill;

    private int pointIndex;

    private PVector target;
    private PVector position;

    private boolean alive;

    private int deadindex =0;




    List<Point> path;

    PImage image;

    List<PImage> dead = new ArrayList<>();

    public Monster(App app, MonsterType type, int hp, float speed, float armour, int mana_gained_on_kill,  List<Point> path){
        super(app,speed);
        this.type = type;
        this.hp = hp;
        this.maxhp = hp;
        this.speed = speed;
        this.armour = armour;
        this.mana_gained_on_kill = mana_gained_on_kill;
        this.path = path;

        this.pointIndex = 1;

        this.position = path.get(0).toPVector();
        this.target = path.get(1).toPVector();
        this.alive = true;


        if(type == MonsterType.GREMLIN){
            image = app.imageMap.get("gremlin");
            dead.add(app.imageMap.get("gremlin1"));
            dead.add(app.imageMap.get("gremlin2"));
            dead.add(app.imageMap.get("gremlin3"));
            dead.add(app.imageMap.get("gremlin4"));
            dead.add(app.imageMap.get("gremlin5"));
        } else if (type == MonsterType.WORM) {
            image = app.imageMap.get("worm");
        } else{
            image = app.imageMap.get("beetle");
        }

    }



    public void dead(){
        app.manapool.killMonster(mana_gained_on_kill);
        app.iterator.remove();

    }

    public void draw(){

        if(alive){
            position = move(position, target);
            drawhp();

            app.image(image, position.x+offset, position.y+offset);

            float distance = PVector.dist(position, target);
            if (distance <= speed*app.gameSpeed/2) {
                pointIndex +=1;
                if (pointIndex >= path.size()){
                    respawn();
                }
                target = path.get(pointIndex).toPVector();



            }
        }else {
            if(deadindex/4 >= dead.size()){
                dead();
            }else {
                app.image(dead.get(deadindex/4), position.x+offset, position.y+offset);

                deadindex += app.gameSpeed;
            }

        }









    }

    public void respawn(){
        position = path.get(0).toPVector();
        pointIndex = 1;
        if(!app.manapool.use(hp)){
            app.result =2 ;
        }



        //todo


    }

    private void drawhp(){
        app.stroke(0);
        app.strokeWeight(1);

        app.fill(255,0,0);
        app.rect(position.x, position.y - 10, 32, 5);


        app.fill(0, 255, 0);
        float per = (float) hp /maxhp;

        app.rect(position.x, position.y - 10, 32*per, 5);
    }

    public void takeDamage(int damage){

        hp-=damage;
        if(hp<=0){
            alive = false;
        }
    }

    public PVector getPosition(){
        return new PVector(position.x+offset, position.y+offset);
    }

    public boolean isAlive(){
        return alive;
    }









}
