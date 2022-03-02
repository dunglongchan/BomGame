package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Ballom extends  Entity {
    int min = x - 2 * Sprite.SCALED_SIZE;
    int max = x + 2 * Sprite.SCALED_SIZE;
    boolean moveleft = true;
    boolean moveright = false;

    public Ballom(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {


        if (x == min) {
            moveright = true;
            moveleft = false;
        } else if (x == max) {
            moveleft = true;
            moveright = false;
        }
        if (moveleft == true && moveright == false) {
            x--;
            this.img = Sprite.balloom_left1.getFxImage();
        } else if (moveleft == false && moveright == true) {
            x++;
            this.img = Sprite.balloom_right1.getFxImage();
        } else if (x == max && moveright == false && moveleft == true) {
            x--;
            this.img = Sprite.balloom_left1.getFxImage();
        }
    }
}
