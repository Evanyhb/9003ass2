package WizardTD;

import java.util.List;

public class Wave {
    App app;
    double pre_wave_pause;
    double duration;

    List<Monstercontrol> monstercontrols;

    int count = 0;

    public Wave(App app, double duration, double pre_wave_pause, List<Monstercontrol> monstercontrols){
        this.app = app;
        this.pre_wave_pause = pre_wave_pause * App.FPS;
        this.duration = duration * App.FPS;
        this.monstercontrols = monstercontrols;
        for(Monstercontrol monstercontrol: monstercontrols){
            monstercontrol.setDuration(duration * App.FPS);

        }


    }



    public void count(){
        if(app.pause){
            return;
        }
        count += app.gameSpeed;

        if(count>pre_wave_pause && count <= pre_wave_pause+ duration){
            for(Monstercontrol monstercontrol: monstercontrols){
                monstercontrol.count();
            }
        }else if (count > pre_wave_pause+ duration){
            app.waveIndex += 1;
        }




    }
    public double getPre(){
        return this.pre_wave_pause;
    }

    public double getremain(){
        return (this.pre_wave_pause + this.duration - this.count);
    }

    public boolean isStart(){
        return count>pre_wave_pause;
    }

    public double timeTostart(){
        return (this.pre_wave_pause - count);
    }

    public boolean isOver(){

        for(Monstercontrol monstercontrol: monstercontrols){
            if(!(monstercontrol.quantity == 0)){
                return false;
            }

        }
        return true;

    }



}
