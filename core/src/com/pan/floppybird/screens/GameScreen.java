package com.pan.floppybird.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.pan.floppybird.entities.Bird;
import com.pan.floppybird.FloppyBird;
import com.pan.floppybird.entities.PipePar;

import java.util.Iterator;

import static com.pan.floppybird.entities.Pipe.PIPE_HEIGHT;

public class GameScreen implements Screen {

    public static final int VIRTUAL_WIDTH = 512;
    public static final int VIRTUAL_HEIGHT = 288;
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;

    final FloppyBird game;

    public final static int GRAVEDAD = 20;
    public Texture background;
    private SpriteBatch batch;
    public Texture ground;
    public Bird bird;
    OrthographicCamera camera;

    private float spawnTimer = 0; //timer para spawnear pipes  SPAWN = APARECER

    //Infinite Scrolling "ilusion"
    float backgroundScroll = 0;
    float groundScroll = 0;
    int BACKGROUND_SCROLL_SPEED = 30; // LOS DIFERENTES RATES DAN LA NOCION DE PROFUNDIDAD O ILUSION DE MOVIMIENTO
    int GROUND_SCROLL_SPEED = 60;
    int BACKGROUND_LOOPING_POINT = 413;

    private Array<PipePar> pipePairs = new Array<>();
    private float lastY = -PIPE_HEIGHT + MathUtils.random(80) + 20;
    boolean scrolling = true;


    public GameScreen(final FloppyBird game) {

        this.game = game;
        batch = new SpriteBatch();
        bird = new Bird(game, batch);
        background = new Texture("background.png");
        ground = new Texture("ground.png");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

    }



    public void render(float delta) {

        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        limpiarPantalla(dt);
        renderFondoyBird();
        renderPipes();
        game.batch.setProjectionMatrix(camera.combined);
        camera.update();
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) bird.jump();

    }

    private void limpiarPantalla(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Gdx.app.log("GameScreen FPS", (1/dt) + "");

    }

    private void renderFondoyBird() {

        game.batch.begin();
        game.batch.draw(background, -backgroundScroll, 0);//O 10
        game.batch.draw(ground, -groundScroll, 0);
        bird.render();
        game.batch.end();

    }

    private void renderPipes() {

        Iterator<PipePar> iter2 = pipePairs.iterator();
        while (iter2.hasNext()) {
            PipePar pipe = iter2.next();
            pipe.render(batch);
        }
    }

    private void update(float dt) {
        if (scrolling){
            moverFondoySuelo(dt);
            bird.update(dt);
            spawnTimer += dt;
            generarPipes();
            updatePipes(dt);
        }
        else {
            volverAempezar();
        }
    }

    private void volverAempezar() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    private void updatePipes(float dt) {
        Iterator<PipePar> iter = pipePairs.iterator();
        while (iter.hasNext()) {
            PipePar pipe = iter.next();
            pipe.update(dt);
            detectarColisiones(iter, pipe);
        }
    }

    private void detectarColisiones(Iterator<PipePar> iter, PipePar pipe) {
        if (bird.collides(pipe.lowerPipe) || bird.collides(pipe.upperPipe)) scrolling = false;
        if (pipe.shouldRemove()) iter.remove();
    }


    private void generarPipes() {

        if (spawnTimer > 4) {
            float y = MathUtils.random(450, 680);
            Gdx.app.log("Spawn", "Generated y: " + y);
            pipePairs.add(new PipePar(y));
            spawnTimer = 0;
        }
    }

    private void moverFondoySuelo(float dt) {

        backgroundScroll = (backgroundScroll + BACKGROUND_SCROLL_SPEED * dt) % BACKGROUND_LOOPING_POINT;
        groundScroll = (groundScroll + GROUND_SCROLL_SPEED * dt) % 512;
    }


    public void show() {
    }
    @Override
    public void resize(int width, int height) {
        // Adjust the virtual width and height based on the window's aspect ratio
        float aspectRatio = (float) width / height;
        int newWidth = VIRTUAL_HEIGHT * MathUtils.round(aspectRatio);
        camera.setToOrtho(false, newWidth, VIRTUAL_HEIGHT);
    }
    public void pause() {

    }
    public void resume() {
    }
    public void hide() {
    }
    public void dispose() {
        batch.dispose();
        background.dispose();
        ground.dispose();
        bird.dispose();
        for (PipePar pair : pipePairs) {
            pair.dispose();
        }
    }

}
