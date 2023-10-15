package WizardTD;

import processing.core.PApplet;
import processing.core.PVector;

import static WizardTD.App.CELLSIZE;

public class Hugetower extends Tower{
    public Hugetower(App app, tiletype type, int x, int y) {
        super(app, type, x, y);
    }

    @Override
    public void setProp(){
        tower_range = app.initial_htower_range;
        tower_firing_speed = app.initial_htower_firing_speed;
        intervalFrames = App.FPS/tower_firing_speed;
        init_damage = app.initial_htower_damage;
        tower_damage = app.initial_htower_damage;
    }
    @Override
    public void setImage(){
        if(rangelvl>=2 &&damagelvl>=2 &&speedlvl>=2  ){
            level = 2;
            image = app.imageMap.get("htower2");
        }else if(rangelvl>=1 &&damagelvl>=1 &&speedlvl>=1 ){
            level =1;
            image = app.imageMap.get("htower1");
        }else{
            level =0;
            image = app.imageMap.get("htower0");
        }

    }


    @Override
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
                app.fireballList.add(new HugeFireball(app, new PVector(x,y), tower_damage, target));
            }




        }



    }



}
