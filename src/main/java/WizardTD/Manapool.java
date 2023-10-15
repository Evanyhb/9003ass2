package WizardTD;

public class Manapool {
    App app;
    float mana_cap;
    float mana;
    float mana_gain_per_second;

    float tower_cost;
    float htower_cost;
    float spell_cost;
    float cost_increase_per_use;
    float cap_multiplier;
    float mana_gained_multiplier;
    float curr_gained_multiplier;
    int count;
    int timeSpeed = 1;
    int pause =0;


    public Manapool(App app){
        this.app = app;
        mana_cap = app.initial_mana_cap;
        mana = app.initial_mana;
        mana_gain_per_second = app.initial_mana_gained_per_second;
        tower_cost = app.tower_cost;
        htower_cost = app.htower_cost;
        spell_cost = app.mana_pool_spell_initial_cost;
        cost_increase_per_use = app.mana_pool_spell_cost_increase_per_use;
        cap_multiplier = app.mana_pool_spell_cap_multiplier;
        mana_gained_multiplier = app.mana_pool_spell_mana_gained_multiplier;
        curr_gained_multiplier = 1;


    }



    private void addmana(float num){
        mana += num;
        if(mana > mana_cap){
            mana = mana_cap;
        }
    }


    public void count(){
        if(app.pause){
            return;
        }
        count += app.gameSpeed;
        if(count >= 60){
            addmana(mana_gain_per_second);
            count = 0;
        }
    }

    public void spell(){
        if(use(spell_cost)){
            spell_cost += cost_increase_per_use;
            mana_cap = mana_cap * cap_multiplier;
            curr_gained_multiplier += mana_gained_multiplier-1;
        }

    }

    public boolean use(float mana){
        if(this.mana >= mana){
            this.mana -= mana;
            return true;
        }else {
            return false;
        }
    }

    public boolean affordable(float mana){
        if(this.mana >= mana){
            return true;
        }else {
            return false;
        }
    }


    public boolean buildTower(){
        return use(tower_cost);
    }
    public boolean buildhTower(){
        return use(htower_cost);
    }



    public int getMax(){
        return (int)mana_cap;
    }
    public int getCurrent(){
        return (int) mana;
    }

    public void killMonster(int mana){
        addmana(mana * curr_gained_multiplier);
    }




}
