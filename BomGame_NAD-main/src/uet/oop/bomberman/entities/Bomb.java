package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.Sprite;


public class Bomb extends Entity{
    public int time2explode=120;
    public boolean isexplode=false;

    public Bomb(){
        time2explode=120;
        isexplode=false;
    }

    public void setTime2explode(int time2explode) {
        this.time2explode = time2explode;
    }

    public void setIsexplode(boolean isexplode) {
        this.isexplode = isexplode;
    }

    @Override
    public void setImg(Image img) {
        super.setImg(img);
    }

    @Override
    public void setX(int x) {
        super.setX(x);
    }

    @Override
    public void setY(int y) {
        super.setY(y);
    }

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {

    }
}

