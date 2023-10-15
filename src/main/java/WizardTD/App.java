package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.MouseEvent;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.util.*;
import java.util.List;

public class App extends PApplet {

    public static final int CELLSIZE = 32;
    public static final int SIDEBAR = 120;
    public static final int TOPBAR = 40;
    public static final int BOARD_WIDTH = 20;
    public static int WIDTH = CELLSIZE*BOARD_WIDTH+SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH*CELLSIZE+TOPBAR;
    public static final int FPS = 60;
    public String configPath;
    public Random random = new Random();
	// Feel free to add any additional methods or attributes you want. Please put classes in different files.
    public Map<String,PImage> imageMap = new HashMap<>();
    public Gamelayout layout;
    int waveIndex;
    public List<Wave> waves = new ArrayList<>();

    public List<Monster> monsterList = new ArrayList<>();

    public List<Tower> towerList = new ArrayList<>();

    public List<Fireball> fireballList = new ArrayList<>();

    public List<Integer> activeButton = new ArrayList<>();

    Iterator<?extends Drawable> iterator;

    public Manapool manapool;

    public static int buttonSize = 40;

    private int onButton;

    public int gameSpeed;
    public int timeSpeed;

    public boolean pause;

    public int result;



    public float initial_tower_range;
    public float initial_tower_firing_speed;
    public int initial_tower_damage;
    public float initial_mana;
    public float initial_mana_cap;
    public float initial_mana_gained_per_second;
    public float tower_cost;
    public float mana_pool_spell_initial_cost;
    public float mana_pool_spell_cost_increase_per_use;
    public float mana_pool_spell_cap_multiplier;
    public float mana_pool_spell_mana_gained_multiplier;

    public float htower_cost;

    public float initial_htower_range;
    public float initial_htower_firing_speed;
    public int initial_htower_damage;







    public App() {
        this.configPath = "config.json";
    }



    /**
     * Initialise the setting of the window size.
     */
	@Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
     */
	@Override
    public void setup() {
        frameRate(FPS);
        background(132,115,74);


        imageMap = new HashMap<>();

        waveIndex = 0;

        waves = new ArrayList<>();

        monsterList = new ArrayList<>();

        towerList = new ArrayList<>();

        fireballList = new ArrayList<>();

        activeButton = new ArrayList<>();

        gameSpeed = 1;

        timeSpeed = 1;

        pause = false;

        result = 0;

        // Load images during setup
		// Eg:
        // loadImage("src/main/resources/WizardTD/tower0.png");
        // loadImage("src/main/resources/WizardTD/tower1.png");
        // loadImage("src/main/resources/WizardTD/tower2.png");
        try{
            imageMap.put("tower0",loadImage("src/main/resources/WizardTD/tower0.png"));
            imageMap.put("tower1",loadImage("src/main/resources/WizardTD/tower1.png"));
            imageMap.put("tower2",loadImage("src/main/resources/WizardTD/tower2.png"));
            imageMap.put("beetle",loadImage("src/main/resources/WizardTD/beetle.png"));
            imageMap.put("fireball",loadImage("src/main/resources/WizardTD/fireball.png"));
            imageMap.put("grass",loadImage("src/main/resources/WizardTD/grass.png"));
            imageMap.put("gremlin",loadImage("src/main/resources/WizardTD/gremlin.png"));
            imageMap.put("gremlin1",loadImage("src/main/resources/WizardTD/gremlin1.png"));
            imageMap.put("gremlin2",loadImage("src/main/resources/WizardTD/gremlin2.png"));
            imageMap.put("gremlin3",loadImage("src/main/resources/WizardTD/gremlin3.png"));
            imageMap.put("gremlin4",loadImage("src/main/resources/WizardTD/gremlin4.png"));
            imageMap.put("gremlin5",loadImage("src/main/resources/WizardTD/gremlin5.png"));
            imageMap.put("path0",loadImage("src/main/resources/WizardTD/path0.png"));
            imageMap.put("path1",loadImage("src/main/resources/WizardTD/path1.png"));
            imageMap.put("path2",loadImage("src/main/resources/WizardTD/path2.png"));
            imageMap.put("path3",loadImage("src/main/resources/WizardTD/path3.png"));
            imageMap.put("shrub",loadImage("src/main/resources/WizardTD/shrub.png"));
            imageMap.put("worm",loadImage("src/main/resources/WizardTD/worm.png"));
            imageMap.put("wizard_house",loadImage("src/main/resources/WizardTD/wizard_house.png"));
            imageMap.put("htower0",loadImage("src/main/resources/WizardTD/htower0.png"));
            imageMap.put("htower1",loadImage("src/main/resources/WizardTD/htower1.png"));
            imageMap.put("htower2",loadImage("src/main/resources/WizardTD/htower2.png"));
        }catch (Exception e){
            System.out.println("cannot load images");
            e.printStackTrace();
            throw e;
        }




        // load JSON file
        String jsonFilename = "config.json";
        JSONObject json = loadConfig(jsonFilename);


        String layoutFilename = json.getString("layout");
        layout = new Gamelayout(this, layoutFilename);
        List<List<Point>> paths = layout.findpath();

        initial_tower_range = json.getFloat("initial_tower_range");
        initial_tower_firing_speed = json.getFloat("initial_tower_firing_speed");
        initial_tower_damage = json.getInt("initial_tower_damage");
        initial_mana = json.getFloat("initial_mana");
        initial_mana_cap = json.getFloat("initial_mana_cap");
        initial_mana_gained_per_second = json.getFloat("initial_mana_gained_per_second");
        tower_cost = json.getFloat("tower_cost");
        mana_pool_spell_initial_cost = json.getFloat("mana_pool_spell_initial_cost");
        mana_pool_spell_cost_increase_per_use = json.getFloat("mana_pool_spell_cost_increase_per_use");
        mana_pool_spell_cap_multiplier = json.getFloat("mana_pool_spell_cap_multiplier");
        mana_pool_spell_mana_gained_multiplier = json.getFloat("mana_pool_spell_mana_gained_multiplier");
        htower_cost = json.getFloat("htower_cost");
        initial_htower_range = json.getFloat("initial_htower_range");
        initial_htower_firing_speed = json.getFloat("initial_htower_firing_speed");
        initial_htower_damage = json.getInt("initial_htower_damage");


        JSONArray jsonWaves = json.getJSONArray("waves");
        for (int i=0; i<jsonWaves.size(); i++){
            JSONObject jsonWave = jsonWaves.getJSONObject(i);
            double duration = jsonWave.getDouble("duration");
            double pre_wave_pause = jsonWave.getDouble("pre_wave_pause");
            JSONArray jsonMonsters = jsonWave.getJSONArray("monsters");
            List<Monstercontrol> monstercontrols = new ArrayList<>();
            for(int j=0; j < jsonMonsters.size(); j++){
                JSONObject jsonMonster = jsonMonsters.getJSONObject(j);
                String typeMonster = jsonMonster.getString("type");
                MonsterType type;
                try{
                    type = MonsterType.fromString(typeMonster);
                }catch (IllegalArgumentException e){
                    System.out.println(typeMonster);
                    throw e;
                }
                int hp = jsonMonster.getInt("hp");
                float speed = jsonMonster.getFloat("speed");
                float armour = jsonMonster.getFloat("armour");
                int mana_gained_on_kill = jsonMonster.getInt("mana_gained_on_kill");
                int quantity = jsonMonster.getInt("quantity");
                monstercontrols.add(new Monstercontrol(this, type, hp, speed, armour, mana_gained_on_kill, quantity, paths));


            }
            waves.add(new Wave(this, duration, pre_wave_pause, monstercontrols));

        }
        manapool = new Manapool(this);


    }



    /**
     * Receive key pressed signal from the keyboard.
     */
	@Override
    public void keyPressed(){
        
    }

    /**
     * Receive key released signal from the keyboard.
     */
	@Override
    public void keyReleased(){
        switch (Character.toLowerCase(key)) {
            case 't':
                if (activeButton.contains(2)){
                    activeButton.remove((Integer) 2);
                }else {
                    activeButton.add(2);

                }
                break;
            case 'm':
                manapool.spell();
                break;
            case '1':
                if (activeButton.contains(3)){
                    activeButton.remove((Integer) 3);
                }else {
                    activeButton.add(3);

                }
                break;
            case '2':
                if (activeButton.contains(4)){
                    activeButton.remove((Integer) 4);
                }else {
                    activeButton.add(4);

                }
                break;
            case '3':
                if (activeButton.contains(5)){
                    activeButton.remove((Integer) 5);
                }else {
                    activeButton.add(5);

                }
                break;
            case 'p':
                if (activeButton.contains(1)){
                    activeButton.remove((Integer) 1);
                }else {
                    activeButton.add(1);
                    userPause();

                }
                break;
            case 'f':
                if (activeButton.contains(0)){
                    activeButton.remove((Integer) 0);
                }else {
                    activeButton.add(0);
                    doubleSpeed();
                }
                break;
            case 'r':
                setup();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(mouseX>0 && mouseX < CELLSIZE*BOARD_WIDTH&& mouseY > TOPBAR && mouseY<HEIGHT ){
            if(activeButton.contains(2)){
                layout.buildTower(mouseX,mouseY);
            }
            if(activeButton.contains(7)){
                layout.buildhTower(mouseX,mouseY);
            }
            if(activeButton.contains(3)){
                Tower tower = layout.getTower(mouseX, mouseY);
                if(tower != null){
                    tower.updateRange();
                }


            }
            if(activeButton.contains(4)){
                Tower tower = layout.getTower(mouseX, mouseY);
                if(tower != null){
                    tower.updateSpeed();
                }

            }
            if(activeButton.contains(5)){
                Tower tower = layout.getTower(mouseX, mouseY);
                if(tower != null){
                    tower.updateDamage();
                }

            }


        }
        if (onButton == -1){
            return;
        }
        if (activeButton.contains(onButton)){
            activeButton.remove((Integer) onButton);
        }else {
            activeButton.add(onButton);

        }
        if(onButton == 6){
            manapool.spell();
            activeButton.remove((Integer) onButton);

        }
        if(onButton == 0){
            doubleSpeed();

        }
        if(onButton == 1){
            userPause();
        }



    }

    /*@Override
    public void mouseDraggd'wed(MouseEvent e) {

    }*/

    /**
     * Draw all elements in the game by current frame.
     */
    boolean a = true;

	@Override
    public void draw() {
        if(pause){
            gameSpeed = 0;
        }else {
            gameSpeed = timeSpeed;
        }
        checkwin();
        switch (result){
            case 1:
                drawWin();
                return;
            case 2:
                drawLost();
                return;
        }

        onButton = -1;

        layout.draw();
        if(waveIndex<waves.size()){
            waves.get(waveIndex).count();
        } else if (a) {
            System.out.println(monsterList.size());
            a = false;
        }

        drawList(monsterList);
        drawList(towerList);
        drawList(fireballList);

        layout.drawWz();
        drawUI();
        manapool.count();



    }

    public static void main(String[] args) {
        PApplet.main("WizardTD.App");
    }

    /**
     * Source: https://stackoverflow.com/questions/37758061/rotate-a-buffered-image-in-java
     * @param pimg The image to be rotated
     * @param angle between 0 and 360 degrees
     * @return the new rotated image
     */
    public PImage rotateImageByDegrees(PImage pimg, double angle) {
        BufferedImage img = (BufferedImage) pimg.getNative();
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        PImage result = this.createImage(newWidth, newHeight, ARGB);
        //BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        BufferedImage rotated = (BufferedImage) result.getNative();
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                result.set(i, j, rotated.getRGB(i, j));
            }
        }

        return result;
    }


    public JSONObject loadConfig(String jsonFilename){
        JSONObject json = null;

        try {
            // use loadJSONObject() method to load JSON
            json = loadJSONObject(jsonFilename);

            // get data from JSON object

        } catch (Exception e) {
            e.printStackTrace();
            println("Error loading JSON file: " + jsonFilename);
        }
        return json;
    }


    private void drawUI(){



        drawTowerRange();
        noStroke();
        fill(132,115,74);
        rect(0,0,WIDTH,TOPBAR);
        rect(CELLSIZE * BOARD_WIDTH, TOPBAR,SIDEBAR,HEIGHT-TOPBAR);
        stroke(0);
        strokeWeight(1);

        if(waveIndex< waves.size()){
            fill(0,0,0);  // Set the fill color to white
            if(waves.get(waveIndex).isStart()){
                if(waveIndex+1 < waves.size()){
                    text("Wave: " + (waveIndex + 2), 10, 25);
                    text("Start: " + Math.round((waves.get(waveIndex).getremain() + waves.get(waveIndex+1).getPre())/FPS) + "s", 60, 25);
                }
            }else{
                text("Wave: " + (waveIndex + 1), 10, 25);
                text("Start: " + Math.round( (waves.get(waveIndex).timeTostart())/FPS) + "s", 60, 25);
            }

        }

        int buttonY = TOPBAR + 10;  // Start drawing buttons 10 pixels below the top bar
        String[] buttons = {"FF", "P", "T", "U1", "U2", "U3", "M", "HT"};
        String[] descriptions = {"2x Speed", "Pause", "Build Tower", "Upgrade Range", "Upgrade Speed", "Upgrade Damage", "Mana Pool Cost:", "Build HugeTower"};

        for (int i = 0; i < buttons.length; i++) {
            if((mouseX >= CELLSIZE * BOARD_WIDTH + 10 && mouseX <= CELLSIZE * BOARD_WIDTH + 10 + buttonSize && mouseY >= buttonY && mouseY <= buttonY + buttonSize)) {
                onButton = i;
                if(buttons[i] =="T" || buttons[i] =="M"||buttons[i] =="HT" ){
                    fill(255,255,255);
                    rect(CELLSIZE * BOARD_WIDTH -45 , buttonY, 40, buttonSize/2);
                    fill(0,0,0);
                    text("Cost: ", CELLSIZE * BOARD_WIDTH -45 , buttonY+10);
                    if(buttons[i] =="M"){
                        text((int)manapool.spell_cost, CELLSIZE * BOARD_WIDTH -45 , buttonY+20);
                    }else if(buttons[i] =="T"){
                        text((int)manapool.tower_cost, CELLSIZE * BOARD_WIDTH -45 , buttonY+20);
                    }else {
                        //todo
                        text("", CELLSIZE * BOARD_WIDTH -45 , buttonY+20);
                    }
                }
                fill(192, 192, 192);
            }else{
                fill(132,115,74);
            }
            if(activeButton.contains(i)){
                fill(255,255,0);
            }
            rect(CELLSIZE * BOARD_WIDTH + 10, buttonY, buttonSize, buttonSize);

            // Draw button Name
            fill(0,0,0);
            textSize(10);
            text(buttons[i], CELLSIZE * BOARD_WIDTH + 15, buttonY + (float) buttonSize / 2 + 5);
            // Draw description label
            textSize(CELLSIZE / 2 - 5);
            if(buttons[i] =="FF" || buttons[i] =="P"){
                text(descriptions[i], CELLSIZE * BOARD_WIDTH + 10 + buttonSize + 5, buttonY + (float) buttonSize / 4 + 5);
            } else if (buttons[i] == "M") {
                String[] strings = descriptions[i].split(" ");
                text(strings[0]+" " +strings[1], CELLSIZE * BOARD_WIDTH + 10 + buttonSize + 5, buttonY + (float) buttonSize / 4 + 5);
                text(strings[2] + (int)manapool.spell_cost , CELLSIZE * BOARD_WIDTH + 10 + buttonSize + 5, buttonY + (float) (3 * buttonSize) / 4 + 5);
            }else {
                String[] splitDescription = descriptions[i].split(" ");
                text(splitDescription[0], CELLSIZE * BOARD_WIDTH + 10 + buttonSize + 5, buttonY + (float) buttonSize / 4 + 5);
                text(splitDescription[1], CELLSIZE * BOARD_WIDTH + 10 + buttonSize + 5, buttonY + (float) (3 * buttonSize) / 4 + 5);
            }
            buttonY += buttonSize + 10;
        }
        drawCost();




        // draw mana pool
        fill(0,0,0);
        text("MANA:", 320,25);

        fill(255,255,255);
        int manapoollen = 200;
        rect(360,10, manapoollen,20);
        fill(0,255,255);
        rect(360,10,  manapool.mana/manapool.mana_cap*manapoollen,20);
        fill(0,0,0);

        text((int)manapool.mana + "/" + (int)manapool.mana_cap, 440,25);

    }

    private void drawTowerRange(){
        if(mouseX>0 && mouseX < CELLSIZE*BOARD_WIDTH&& mouseY > TOPBAR && mouseY<HEIGHT ){
            Tower tower = layout.getTower(mouseX,mouseY);
            if(tower != null){
                stroke(255,255,0);
                strokeWeight(1);
                noFill();
                ellipse(tower.x+CELLSIZE/2, tower.y+CELLSIZE/2, tower.tower_range*2, tower.tower_range*2);
            }


        }
    }

    private void drawCost(){
        String[] strings = new String[]{"Range: ","Speed: ", "Damage: "};
        if(mouseX>0 && mouseX < CELLSIZE*BOARD_WIDTH&& mouseY > TOPBAR && mouseY<HEIGHT ){
            Tower tower = layout.getTower(mouseX,mouseY);
            boolean selectany = false;
            int[] costs = new int[]{0,0,0};
            if(tower != null){
                if(activeButton.contains(3)){
                    costs[0] = tower.rangelvl*10 + 20;
                    selectany = true;
                }
                if(activeButton.contains(4)){
                    costs[1] = tower.speedlvl*10 + 20;
                    selectany = true;
                }
                if(activeButton.contains(5)){
                    costs[2] = tower.damagelvl*10 + 20;
                    selectany = true;
                }
                if(selectany){
                    int total = 0;
                    int costY = TOPBAR+500;
                    fill(255,255,255);
                    rect(CELLSIZE * BOARD_WIDTH+20, costY,SIDEBAR-40,15);
                    fill(0,0,0);
                    text("Upgrade Cost", CELLSIZE * BOARD_WIDTH+22, costY+12);

                    for(int i=0; i<3; i++){
                        if(costs[i] == 0){
                            continue;
                        }
                        costY += 15;
                        fill(255,255,255);
                        rect(CELLSIZE * BOARD_WIDTH+20, costY,SIDEBAR-40,15);
                        fill(0,0,0);
                        text(strings[i] + costs[i], CELLSIZE * BOARD_WIDTH+22, costY+12);
                        total += costs[i];
                    }
                    costY += 15;
                    fill(255,255,255);
                    rect(CELLSIZE * BOARD_WIDTH+20, costY,SIDEBAR-40,15);
                    fill(0,0,0);
                    text("Total: " + total, CELLSIZE * BOARD_WIDTH+22, costY+12);

                }
            }
        }
    }
    public void checkwin(){
        boolean isover = true;
        for(Wave wave: waves){
            if(!wave.isOver()){
                isover = false;
            }
        }

        if(isover&& monsterList.isEmpty()){
            result = 1;
        }


    }


    public void drawWin(){
        fill(0,0,0);
        rect(0,0,WIDTH, HEIGHT);
        fill(0, 255, 0);
        textSize(60);
        text("YOU WIN", width/2 - 120, height / 2 - 20);
        fill(255); // White color for "Press 'R' to restart"
        textSize(30);
        text("Press 'R' to restart", width / 2-120, height / 2 + 30); // Slightly below center
    }

    public void drawLost(){
        fill(0,0,0);
        rect(0,0,WIDTH, HEIGHT);
        fill(255, 0, 0);
        textSize(60);
        text("YOU LOST", width/2 - 120, height / 2 - 20);
        fill(255); // White color for "Press 'R' to restart"
        textSize(30);
        text("Press 'R' to restart", width / 2-120, height / 2 + 30); // Slightly below center

    }
    private void drawList(List<? extends Drawable> list){
        iterator = list.listIterator();
        while(iterator.hasNext()){
            Drawable item = iterator.next();
            item.draw();
        }

    }




    public void doubleSpeed() {
        if(timeSpeed == 1){
            timeSpeed =2;
        }else if (timeSpeed ==2){
            timeSpeed =1;
        }

    }

    public void userPause() {
        pause = !pause;

    }











}
