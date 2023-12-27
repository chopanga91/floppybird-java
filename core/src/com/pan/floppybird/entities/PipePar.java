package com.pan.floppybird.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pan.floppybird.entities.Pipe;

import static com.pan.floppybird.screens.GameScreen.VIRTUAL_WIDTH;

public class PipePar {
    private static final int GAP_HEIGHT = 120;
    private float x;
    private float y;
    private boolean remove;

    public Pipe upperPipe;
    public Pipe lowerPipe;

    public PipePar(float y) {
        this.x = VIRTUAL_WIDTH + 140;
        this.y = y;

        this.upperPipe = new Pipe("top", this.y);
        this.lowerPipe = new Pipe("bottom", this.y - Pipe.PIPE_HEIGHT - GAP_HEIGHT);

        // Whether this pipe pair is ready to be removed from the scene
        this.remove = false;
    }

    public void update(float dt) {
        // Remove the pipe from the scene if it's beyond the left edge of the screen,
        // else move it from right to left
        if (this.x > -Pipe.PIPE_WIDTH) {
            this.x -= Pipe.PIPE_SPEED * dt;
            this.lowerPipe.setX(this.x);
            this.upperPipe.setX(this.x);
        } else {
            this.remove = true;
        }
    }

    public void render(SpriteBatch batch) {
        lowerPipe.render(batch);
        upperPipe.render(batch);

    }

    public boolean shouldRemove() {
        return upperPipe.x + Pipe.PIPE_WIDTH < 0; //
    }
    public void dispose() {
        upperPipe.dispose();
        lowerPipe.dispose();
    }

}
