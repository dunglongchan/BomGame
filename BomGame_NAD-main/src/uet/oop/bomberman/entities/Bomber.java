package uet.oop.bomberman.entities;

import com.sun.javaws.IconUtil;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.Animation;
import uet.oop.bomberman.graphics.SpriteSheet;

public class Bomber extends Entity {
    public static boolean isIsMove = false;
    public Bomb bomb= new Bomb();
    public int hasplated = 0;



    private final Sprite [] frameleft = {Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2};
    private final Sprite [] frameright = {Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2};
    private final Sprite [] frameup = {Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2};
    private final Sprite [] framedown = {Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2};

    public static boolean isMoveLeft, isMoveRight, isMoveUp, isMoveDown, isPlantBomb;
    public Animation a = new Animation (frameleft, 4);
    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }
    KeyCode keyCode;
    public double stepup = 2;
    public double stepdown = 2;
    public double stepleft = 2;
    public double stepright = 2;

    public int xbomb=1;
    public int ybomb =1;


    public void input() {
        BombermanGame.scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == keyCode.LEFT || keyEvent.getCode() == keyCode.A ) {
                isMoveLeft = true;
            }
            if (keyEvent.getCode() == keyCode.RIGHT || keyEvent.getCode() == keyCode.D ) {
                isMoveRight = true;
            }
            if (keyEvent.getCode() == keyCode.UP || keyEvent.getCode() == keyCode.W) {
                isMoveUp = true;
            }
            if (keyEvent.getCode() == keyCode.DOWN || keyEvent.getCode() == keyCode.S ) {
                isMoveDown = true;
            }
            if (keyEvent.getCode() == keyCode.SPACE || keyEvent.getCode() == keyCode.ENTER ) {
                isPlantBomb = true;
            }
        }
        );
        BombermanGame.scene.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == keyCode.LEFT || keyEvent.getCode() == keyCode.A) {
                this.img = Sprite.player_left.getFxImage();
                isMoveLeft = false;
            }
            if (keyEvent.getCode() == keyCode.RIGHT || keyEvent.getCode() == keyCode.D) {
                this.img = Sprite.player_right.getFxImage();
                isMoveRight = false;
            }
            if (keyEvent.getCode() == keyCode.UP || keyEvent.getCode() == keyCode.W) {
                this.img = Sprite.player_up.getFxImage();
                isMoveUp = false;
            }
            if (keyEvent.getCode() == keyCode.DOWN || keyEvent.getCode() == keyCode.S) {
                this.img = Sprite.player_down.getFxImage();
                isMoveDown = false;
            }
            if (keyEvent.getCode() == keyCode.SPACE || keyEvent.getCode() == keyCode.ENTER) {
                xbomb = this.x/Sprite.SCALED_SIZE;
                ybomb=this.y/ Sprite.SCALED_SIZE;
                bomb.setIsexplode(false);
                isPlantBomb = false;
                hasplated++;
            }
        }
        );
    }

    public void PlantBomb(){
        if(isPlantBomb) {
            bomb = new Bomb(this.x/Sprite.SCALED_SIZE,y/Sprite.SCALED_SIZE,Sprite.bomb.getFxImage());
            BombermanGame.stillObjects.add(bomb);
            bomb.setIsexplode(false);
        }
    }
    public void explode(){
        int xx=xbomb;
        int yy = ybomb;
        Flame flameR = new Flame(xx+1,yy,Sprite.explosion_horizontal_right_last.getFxImage());
        Flame flameL = new Flame(xx-1,yy,Sprite.explosion_horizontal_left_last.getFxImage());
        Flame flameU = new Flame(xx,yy-1,Sprite.explosion_vertical_top_last.getFxImage());
        Flame flameD = new Flame(xx,yy+1,Sprite.explosion_vertical_down_last1.getFxImage());
        Flame flameC = new Flame(xx,yy,Sprite.bomb_exploded1.getFxImage());
        BombermanGame.stillObjects.add(flameC);
        BombermanGame.stillObjects.add(flameD);
        BombermanGame.stillObjects.add(flameL);
        BombermanGame.stillObjects.add(flameR);
        BombermanGame.stillObjects.add(flameU);
    }

    public void exploding() {
        if(hasplated>0){
            if (bomb.isexplode) {
                explode();
                bomb.isexplode=false;
            } else {
                if (bomb.time2explode > 50) {
                    bomb.time2explode--;
                    bomb.isexplode = false;
                    System.out.println(bomb.time2explode);
                    System.out.println(bomb.isexplode);
                } else if (bomb.time2explode <= 50 && bomb.time2explode > 0) {
                    bomb.isexplode = true;
                    bomb.time2explode--;
                    System.out.println(bomb.time2explode);
                    System.out.println(bomb.isexplode);
                } else {
                    bomb.isexplode = false;
                }
            }
        }
    }
    public void move() {
        isIsMove = false;
        if (isMoveDown) {
                a.setFrames(framedown);
                this.y+= stepdown;
                stepright =2;
                stepleft =2;
                stepup =2;
                isIsMove = true;
        } else if (isMoveUp) {
                a.setFrames(frameup);
                this.y-= stepup;
                stepleft =2;
                stepright = 2;
                stepdown =2;
                isIsMove = true;

        } else if (isMoveLeft) {
                a.setFrames(frameleft);
                this.x-= stepleft;
                stepright =2;
                stepdown =2;
                stepup =2;
                isIsMove = true;
        } else if (isMoveRight) {
                a.setFrames(frameright);
                this.x+= stepright;
                stepleft =2 ;
                stepup =2;
                stepdown =2;
                isIsMove = true;
        }

    }
    @Override
    public void update() {
        input();
        if (this.x < 32) this.x = 32;
        if (this.x > 960 - 32) this.x = 960 - 32;
        if (this.y < 32) this.y = 32;
        if (this.y > 384 - 32) this.y = 384 - 32;
        move();
        for (Entity st : BombermanGame.stillObjects) {

                if (check(st) && isMoveRight) {
                    stepright = 0;
                    stepup = 2;
                    stepdown = 2;
                    stepleft = 2;
                } else if (check(st) && isMoveLeft) {
                    stepright = 2;
                    stepup = 2;
                    stepdown = 2;
                    stepleft = 0;

                } else if (check(st) && isMoveUp) {
                    stepright = 2;
                    stepup = 0;
                    stepdown = 2;
                    stepleft = 2;

                } else if (check(st) && isMoveDown) {
                    stepright = 2;
                    stepup = 2;
                    stepdown = 0;
                    stepleft = 2;

                }
        }
        if(isIsMove) {
            a.update();
            this.img = a.getFrame().getFxImage();
        }
        PlantBomb();
        exploding();
    }
}