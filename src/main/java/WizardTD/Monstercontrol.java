package WizardTD;

import java.util.List;

public class Monstercontrol {

    App app;
    MonsterType type;
    int hp;
    float speed;
    float armour;
    int mana_gained_on_kill;
    int quantity;
    double duration;

    int count = 0;




    public List<List<Point>> paths;




    public Monstercontrol(App app, MonsterType type, int hp, float speed, float armour, int mana_gained_on_kill, int quantity, List<List<Point>> paths){
        this.app = app;
        this.type = type;
        this.hp = hp;
        this.speed = speed;
        this.armour = armour;
        this.mana_gained_on_kill = mana_gained_on_kill;
        this.quantity = quantity;
        this.paths = paths;


    }

    public Monster getMonster(){
        int index = app.random.nextInt(paths.size());
        List<Point> path =  paths.get(index);
        return new Monster(app, type, hp, speed, armour, mana_gained_on_kill, path);
    }

    public void setDuration(double duration){
        this.duration = duration/this.quantity ;


    }
    public void count(){
        if(app.pause){
            return;
        }
        if(count == 0 ||(count >= duration && quantity >0)){

            app.monsterList.add(getMonster());
            quantity -= 1;
            count =0;
        }
        count +=app.gameSpeed ;

    }

}
