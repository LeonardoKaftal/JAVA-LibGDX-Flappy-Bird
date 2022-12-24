package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture backgroundTexture;
	private Texture playerTexture;
	private Texture upTubeTexture;
	private Texture downTubeTexture;
	private Player player;
	private BitmapFont font;
	private final Array<Rectangle> upTubeList = new Array<>();
	private final Array<Rectangle> downTubeList = new Array<>();


	@Override
	public void create () {
		batch = new SpriteBatch();
		backgroundTexture = new Texture(Gdx.files.internal("background.png"));
		playerTexture = new Texture(Gdx.files.internal("bird.png"));
		downTubeTexture = new Texture(Gdx.files.internal("down_tube.png"));
		upTubeTexture = new Texture(Gdx.files.internal("up_tube.png"));
		player = new Player(0, Gdx.graphics.getHeight() / 2f - Player.HEIGHT / 2f);
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(2);
		generateTube();
	}

	public void generateTube() {
		final int tubeWidth = 100;
		final int tubeGap = 250;
		final int tubeDistance = 300;

		for (int i = 1; i * tubeDistance < Gdx.graphics.getWidth(); i++) {
			final int upTubeHeight = MathUtils.random(100,450);
			final int downTubeHeight = Gdx.graphics.getHeight() - upTubeHeight - tubeGap;

			upTubeList.add(new Rectangle(i * tubeDistance,0,tubeWidth,upTubeHeight));
			downTubeList.add(new Rectangle(i * tubeDistance,Gdx.graphics.getHeight() - downTubeHeight,tubeWidth,downTubeHeight));
		}
	}

	public void drawTube() {
		final int tubeSpeed = 200;

		for (int i = 0; i < upTubeList.size; i++) {
			Rectangle upTube = upTubeList.get(i);
			Rectangle downTube = downTubeList.get(i);


			upTube.x -= tubeSpeed * Gdx.graphics.getDeltaTime();
			downTube.x -= tubeSpeed * Gdx.graphics.getDeltaTime();

			// respawn tube
			if (upTube.x < 0 - upTube.width - 5) {
				final int tubeGap = 250;
				final int upTubeHeight = MathUtils.random(100,450);
				final int downTubeHeight = Gdx.graphics.getHeight() - upTubeHeight - tubeGap;
				upTube.x = Gdx.graphics.getWidth();
				upTube.height = upTubeHeight;
				downTube.x = Gdx.graphics.getWidth();
				downTube.height = downTubeHeight;
				downTube.y = Gdx.graphics.getHeight() - downTubeHeight;
				player.score++;
			}

			batch.draw(upTubeTexture,upTube.x,upTube.y,upTube.width,upTube.height);
			batch.draw(downTubeTexture,downTube.x,downTube.y,downTube.width,downTube.height);
		}
	}

	public void checkGameOver() {
		for (int i = 0; i < upTubeList.size; i++) {
			Rectangle upTube = upTubeList.get(i);
			Rectangle downTube = downTubeList.get(i);

			// up tube collision
			if (player.y >= 0 && player.y < upTube.height - 10 && player.x >= upTube.x - upTube.width ) Gdx.app.exit();
			// down tube collision
			if (player.y <= Gdx.graphics.getHeight() && player.y > Gdx.graphics.getHeight() - downTube.height - 100 &&
					player.x >= upTube.x - upTube.width ) Gdx.app.exit();
		}
	}

	@Override
	public void render () {
		checkGameOver();
		player.addGravity();
		batch.begin();
		batch.draw(backgroundTexture,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.draw(playerTexture,player.x,player.y,Player.WIDTH,Player.HEIGHT);
		drawTube();
		font.draw(batch,"Score: " + player.score,10,Gdx.graphics.getHeight() - 10 );
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		backgroundTexture.dispose();
		upTubeTexture.dispose();
		downTubeTexture.dispose();
	}
}
