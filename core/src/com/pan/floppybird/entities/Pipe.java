package com.pan.floppybird.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import org.w3c.dom.Text;

public class Pipe {
    public static final int PIPE_HEIGHT = 288;
    public static final int PIPE_WIDTH = 70;
    final Texture PIPE_IMAGE = new Texture("pipe.png");
    final int PIPE_SCROLL = -60;

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float x, y;
    public int width, height;
    private String orientation;
    public static final int PIPE_SPEED = 60;




    public Pipe(String orientation, float y) {
        this.x = 650;
        this.y = y;
        this.width = PIPE_WIDTH;
        this.height = PIPE_HEIGHT;
        this.orientation = orientation;
    }
    public void update(float dt) {
    }


    public void render(SpriteBatch batch) {
        float flipY = (orientation.equals("top")) ? -1 : 1;
        float offsetY = (orientation.equals("top")) ? 0 : -PIPE_HEIGHT;
        batch.begin();
        batch.draw(PIPE_IMAGE, x, y + offsetY, PIPE_WIDTH, PIPE_HEIGHT * flipY);
        batch.end();
    }
    public void dispose() {
        PIPE_IMAGE.dispose();

    }

        public float getTop() {
                return y ;
        }
        public float getBottom() {
                return y - height;
        }

        public float getLeft() {
            return x;
        }

        public float getRight() {
            return x + width;
        }

        // Otros métodos y propiedades de la clase Pipe
    }


