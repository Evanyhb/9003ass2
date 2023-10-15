package WizardTD;


import processing.core.PImage;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Gamelayout {
    private App app;
    public Tile[][] staticMatrix = new Tile[App.BOARD_WIDTH][App.BOARD_WIDTH];
    public int[] wizardHouse = null;
    public Coordinate wizardHouseC;


    public PImage house;
    public List<Path> spawnPoint = new ArrayList<>();
    public List<Coordinate> spawnCoordinate = new ArrayList<>();


    class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point toPoint(){
            return new Point(x*App.CELLSIZE, y*App.CELLSIZE+App.TOPBAR);
        }

        @Override
        public String toString() {
            return "Coordinate{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }




    public Gamelayout(App app, String filename){
        this.app = app;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            int y =0;
            while ((line = br.readLine()) != null && y<App.BOARD_WIDTH) {
                for(int x=0; x< App.BOARD_WIDTH;x++ ){
                    char letter;
                    if (!(x <line.length())){
                        letter = ' ';
                    }else {
                        letter = line.charAt(x);
                    }

                    if(letter=='S'){
                        staticMatrix[y][x] = new Tile(this.app, Tile.tiletype.SHRUB,x*App.CELLSIZE, y*App.CELLSIZE+App.TOPBAR);

                    } else if (letter==' ') {
                        staticMatrix[y][x] = new Tile(this.app, Tile.tiletype.GRASS,x*App.CELLSIZE, y*App.CELLSIZE+App.TOPBAR);

                    } else if (letter=='X') {
                        Path path = new Path(this.app,x*App.CELLSIZE, y*App.CELLSIZE+App.TOPBAR);
                        staticMatrix[y][x] = path;
                        if(x==0 || y ==0 || x== (App.BOARD_WIDTH -1) || y == (App.BOARD_WIDTH -1)){
                            spawnPoint.add(path);
                            spawnCoordinate.add(new Coordinate(x,y));
                        }
                    } else if (letter=='W') {
                        if (wizardHouse ==null) {
                            wizardHouseC = new Coordinate(x,y);
                            wizardHouse = new int[]{x*App.CELLSIZE-8, y*App.CELLSIZE+App.TOPBAR-8};

                        }
                        staticMatrix[y][x] = new Tile(this.app, Tile.tiletype.PATH,x*App.CELLSIZE, y*App.CELLSIZE+App.TOPBAR);
                    }
                }
                y++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        setneighbors();

        house = app.imageMap.get("wizard_house");




    }

    public void setneighbors(){
        for(int y=1; y< App.BOARD_WIDTH-1; y++){
            for(int x=1; x< App.BOARD_WIDTH-1; x++){
                if (staticMatrix[y][x].type == Tile.tiletype.PATH){
                    if (staticMatrix[y-1][x].type == Tile.tiletype.PATH){
                        staticMatrix[y][x].setNeighbors(Path.Location.UP);
                        staticMatrix[y-1][x].setNeighbors(Path.Location.DOWN);
                    }
                    if (staticMatrix[y+1][x].type == Tile.tiletype.PATH){
                        staticMatrix[y][x].setNeighbors(Path.Location.DOWN);
                        staticMatrix[y+1][x].setNeighbors(Path.Location.UP);
                    }
                    if (staticMatrix[y][x-1].type == Tile.tiletype.PATH){
                        staticMatrix[y][x].setNeighbors(Path.Location.LEFT);
                        staticMatrix[y][x-1].setNeighbors(Path.Location.RIGHT);
                    }
                    if (staticMatrix[y][x+1].type == Tile.tiletype.PATH){
                        staticMatrix[y][x].setNeighbors(Path.Location.RIGHT);
                        staticMatrix[y][x+1].setNeighbors(Path.Location.LEFT);
                    }
                }

            }
        }
        for(int y=0; y<App.BOARD_WIDTH; y++ ){
            for(int x=0;x<App.BOARD_WIDTH; x++ ){
                staticMatrix[y][x].setImage();
            }
        }


    }




    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public List<Coordinate> findShortestPath(Tile[][] matrix, Coordinate start, Coordinate end) {
        int n = matrix.length;
        boolean[][] visited = new boolean[n][n];
        Queue<List<Coordinate>> queue = new LinkedList<>();
        queue.add(Collections.singletonList(start));
        visited[start.y][start.x] = true;

        while (!queue.isEmpty()) {
            List<Coordinate> path = queue.poll();
            Coordinate current = path.get(path.size() - 1);

            if (current.x == end.x && current.y == end.y) {
                return path; // Found the shortest path
            }

            for (int[] direction : DIRECTIONS) {
                int newX = current.x + direction[0];
                int newY = current.y + direction[1];

                if (isValid(newX, newY, n) && matrix[newY][newX].type == Tile.tiletype.PATH && !visited[newY][newX]) {
                    visited[newY][newX] = true;
                    List<Coordinate> newPath = new ArrayList<>(path);
                    newPath.add(new Coordinate(newX, newY));
                    queue.add(newPath);
                }
            }
        }


        return Collections.emptyList(); // No path found
    }

    private boolean isValid(int x, int y, int n) {
        return x >= 0 && x < n && y >= 0 && y < n;
    }


    public List<List<Point>> findpath(){
        List<List<Point>> result = new ArrayList<>();
        for(Coordinate point : spawnCoordinate){
            List<Point> temp = new ArrayList<>();
            List<Coordinate> cpath = findShortestPath(staticMatrix, point, wizardHouseC);
            Coordinate coordinate = cpath.get(0);
            if(coordinate.x ==0){
                temp.add(new Coordinate(coordinate.x-1,coordinate.y).toPoint());
            } else if (coordinate.x ==App.BOARD_WIDTH-1) {
                temp.add(new Coordinate(coordinate.x+1,coordinate.y).toPoint());
            }else if (coordinate.y ==0) {
                temp.add(new Coordinate(coordinate.x,coordinate.y-1).toPoint());
            }else {
                temp.add(new Coordinate(coordinate.x,coordinate.y+1).toPoint());
            }


            for(Coordinate coord: cpath){

                temp.add(coord.toPoint());
            }
            result.add(temp);
        }
        return result;
    }




    public void buildTower(int x, int y){
        int index_x = (int) x/App.CELLSIZE;
        int index_y = (int) (y- App.TOPBAR)/App.CELLSIZE;
        if(staticMatrix[index_y][index_x].type == Tile.tiletype.GRASS ){
            Tower tower = new Tower(app, Tile.tiletype.Tower, index_x*App.CELLSIZE, index_y*App.CELLSIZE+App.TOPBAR);
            if(app.manapool.buildTower()){
                staticMatrix[index_y][index_x] = tower;
                app.towerList.add( tower);
            }else {
            }

        }else {
        }
    }
    public void buildhTower(int x, int y){
        int index_x = (int) x/App.CELLSIZE;
        int index_y = (int) (y- App.TOPBAR)/App.CELLSIZE;
        if(staticMatrix[index_y][index_x].type == Tile.tiletype.GRASS ){
            Tower tower = new Hugetower(app, Tile.tiletype.Tower, index_x*App.CELLSIZE, index_y*App.CELLSIZE+App.TOPBAR);
            if(app.manapool.buildhTower()){
                staticMatrix[index_y][index_x] = tower;
                app.towerList.add( tower);
            }else {
            }

        }else {
        }
    }

    public Tower getTower(int x, int y){
        int index_x =  x/App.CELLSIZE;
        int index_y =  (y- App.TOPBAR)/App.CELLSIZE;
        if(staticMatrix[index_y][index_x].type == Tile.tiletype.Tower ){
            return (Tower) staticMatrix[index_y][index_x];
        }else {
            return null;
        }
    }


    public void draw(){
        for(int y=0; y<App.BOARD_WIDTH; y++ ){
            for(int x=0;x<App.BOARD_WIDTH; x++ ){
                staticMatrix[y][x].draw();
            }
        }

    }

    public void drawWz(){
        app.image(house, wizardHouse[0], wizardHouse[1]);

    }

}
