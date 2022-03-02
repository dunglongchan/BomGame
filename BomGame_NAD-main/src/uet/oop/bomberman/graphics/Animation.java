package uet.oop.bomberman.graphics;

import java.util.ArrayList;


public class Animation {
    private int currentFrame;
    private int delay;
    private Sprite [] frames;
    private int numFrames;
    private int count;
    private boolean aniDone = false;

    public Animation(Sprite[] frames, int delay) {
        this.frames = frames;
        this.numFrames = frames.length;
        this.delay = delay;
    }
    public Sprite getFrame () {
        return this.frames[currentFrame];
    }
    public void setFrames (Sprite[] s) {
        this.frames = s;
    }
    public void update() {
        if(delay == -1) return;
        count++;
        if(count == delay) {
            currentFrame++;
            count = 0;
        }
        if(currentFrame == numFrames) {
            aniDone = true;
            currentFrame = 0;
        }
    }
    public boolean aniDone() {
        return aniDone;
    }
}