package WizardTD;

import processing.core.PApplet;
import processing.core.PVector;

import static WizardTD.App.CELLSIZE;

public class Tower extends Tile {
    float tower_range;
    float tower_firing_speed;
    int tower_damage;
    int init_damage;

    float intervalFrames;

    public int level;

    private int count = 0;
    Manapool manapool;

    int rangelvl = 0;
    int speedlvl = 0;
    int damagelvl = 0;



    public Tower(App app, tiletype type, int x, int y ){
        super(app, type,x,y);
        this.level = 0;
        manapool = app.manapool;
        setProp();
        setImage();


    }

    public void setProp(){
        tower_range = app.initial_tower_range;
        tower_firing_speed = app.initial_tower_firing_speed;
        intervalFrames = App.FPS/tower_firing_speed;
        init_damage = app.initial_tower_damage;
        tower_damage = app.initial_tower_damage;
    }


    @Override
    public void setImage(){
        if(rangelvl>=2 &&damagelvl>=2 &&speedlvl>=2  ){
            level = 2;
            image = app.imageMap.get("tower2");
        }else if(rangelvl>=1 &&damagelvl>=1 &&speedlvl>=1 ){
            level =1;
            image = app.imageMap.get("tower1");
        }else{
            level =0;
            image = app.imageMap.get("tower0");
        }

    }


    public void draw(){
        app.image(image,x,y);
        if(app.pause){
            return;
        }
        drawSymbols();
        count +=app.gameSpeed ;
        if(count > intervalFrames){
            fire();
            count =0;

        }
    }

    public void drawSymbols(){
        if(speedlvl - level >0) {
            app.noFill();
            app.stroke(173, 216, 230);
            app.strokeWeight( 2 + ((speedlvl - level)));
            app.rect(x + 5, y + 5, CELLSIZE - 2 * 5, CELLSIZE - 2 * 5);
        }
        if(rangelvl - level > 0) {
            for(int i=0; i<rangelvl-level; i++){
                app.fill(255, 0, 255);
                app.text("O", x + 2 + (i * 10) , y + 10);
            }

        }
        if(damagelvl - level > 0){
            for (int i = 0; i < damagelvl - level; i++) {
                app.fill(255, 0, 255);
                app.text("X", x + 2 + (i * 10) , y + CELLSIZE);
            }
        }



    }
    protected void fire(){
        if(!app.monsterList.isEmpty()){
            Monster target = null;

            float cdistance = tower_range;

            for (Monster monster : app.monsterList) {
                if(monster.isAlive()){
                    PVector mposition = monster.getPosition();
                    float distance = PApplet.dist(x+ CELLSIZE/2, y+CELLSIZE/2, mposition.x+10, mposition.y+10);
                    if (distance < cdistance) {
                        cdistance = distance;
                        target = monster;
                    }

                }

            }
            if(target != null){
                app.fireballList.add(new Fireball(app, new PVector(x,y), tower_damage, target));
            }




        }


    }


    public boolean updateRange(){
        if(rangelvl<3 && app.manapool.use(20+10*rangelvl)){
            tower_range += 32;
            rangelvl += 1;
            setImage();
            return true;
        }else {
            return false;
        }




    }
    public void updateSpeed(){
        if(speedlvl<3 && app.manapool.use(20+10*speedlvl)){
            tower_firing_speed += 0.5F;
            intervalFrames = App.FPS/tower_firing_speed;
            speedlvl += 1;
            setImage();
        }
    }
    public void updateDamage(){
        if(damagelvl<3 && app.manapool.use(20+10*damagelvl)){
            tower_damage += init_damage/2;
            damagelvl += 1;
            setImage();
        }
    }




}
