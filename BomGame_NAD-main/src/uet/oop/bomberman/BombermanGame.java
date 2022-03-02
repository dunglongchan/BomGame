package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private GraphicsContext gc;
    private Canvas canvas;
    public static Scene scene;
    private static int time = 0;

    private List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    private static String [][] map;
    private static List<Entity> grass = new ArrayList<>();
    private static  List<Entity> item = new ArrayList<>();


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Ten game
        stage.setTitle("BombermanGame 2.0");

        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        createMap();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
                setTime();
            }
        };
        timer.start();

        Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);

    }
    public static String[][] getMap(){
        return map;
    }
    public static Scene getScene(){
        return scene;
    }
    public static List getStillObject() {
        return stillObjects;
    }

    private void setTime() {
        if (time < 20) {
            time++;
        } else {
            time/=3;
        }
    }

    public static int getTime() {
        return time;

    }

    public void createMap() throws IOException {
        File f = new File("res/levels/Level1.txt");
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String l = br.readLine();
        String[] a = l.split(" ");
        int level = Integer.parseInt(a[0]);
        int h = Integer.parseInt(a[1]);
        int w = Integer.parseInt(a[2]);
        map = new String [HEIGHT][WIDTH];
        for (int row = 0 ; row < HEIGHT ; row++) {
            String line = br.readLine();
            String[] take = line.split("");
            for (int col = 0 ; col < WIDTH ; col++) {
                map[row][col] = take[col];
            }
        }

        for (int row = 0 ; row < HEIGHT ; row++) {
            for ( int col = 0 ; col < WIDTH ; col++) {
                Entity object;
                Entity obj;
                Entity it;
                String t = map[row][col];
                obj = new Grass(col, row, Sprite.grass.getFxImage());
                grass.add(obj);
                if ( t.equals("#")) {
                    object = new Wall(col, row, Sprite.wall.getFxImage());
                    stillObjects.add(object);
                }
                else if (t.equals("*")){
                    object = new Brick(col, row, Sprite.brick.getFxImage());
                    stillObjects.add(object);
                }
                else if (t.equals("x")){
                    object = new Portal(col, row, Sprite.portal.getFxImage());
                    stillObjects.add(object);

                }
                else if (t.equals("1")){
                    object = new Ballom(col, row, Sprite.balloom_left2.getFxImage());
                    stillObjects.add(object);

                }
                else if (t.equals("2")){
                    object = new Oneal(col, row, Sprite.oneal_left2.getFxImage());
                    stillObjects.add(object);
                }
                else if (t.equals("f")) {
                    it = new Flame(col, row, Sprite.powerup_flames.getFxImage());
                    item.add(it);
                }


            }
        }
    }


    public void update()  {
        for (Entity entity : entities) {
            entity.update();
        }
        for (Entity stillObject : stillObjects) {
            stillObject.update();
        }
    }

    public void render() {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            grass.forEach(g -> g.render(gc));
            item.forEach(g -> g.render(gc));
            stillObjects.forEach(g -> g.render(gc));
            entities.forEach(g -> g.render(gc));
    }

}
