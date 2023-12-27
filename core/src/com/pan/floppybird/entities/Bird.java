package com.pan.floppybird.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.pan.floppybird.screens.GameScreen.GRAVEDAD;

public class Bird {
    private Texture imagen;
    private Game game;
    private SpriteBatch batch;
    private float x, y;
    private float width, height;

    private float dx, dy;

    public Bird(Game game, SpriteBatch batch) {
        this.imagen = new Texture("bird.png");
        width = imagen.getWidth();
        height = imagen.getHeight();
        x = 512 / 2 - (width / 2);
        y = 288 / 2 - (height / 2);
        this.game = game;
        this.batch = batch;
    }

    public void render() {
        batch.begin();
        batch.draw(imagen, x, y);
        batch.end();
    }

    public void update(float dt) {
        dy = dy + GRAVEDAD * dt;
        y = y -  dy;
    }



    public boolean collides(Pipe pipe) {
        float birdRight = x + width;
        float birdTop = y + height;

        float pipeRight = pipe.getRight();
        float pipeTop = pipe.getTop();
        if (y > pipe.getBottom() && y < pipeTop && birdRight > pipe.getLeft() && x < pipeRight){
            System.out.println("Colisión detectada!");
            return true;
            }
        return false;
    }


    public float getTop() {
        return y + height;
    }

    public float getBottom() {
        return y;
    }

    public void jump() {dy = -5;}

    public void dispose() {imagen.dispose();}
}
