package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;


public class MyGdxGame extends ApplicationAdapter {
	ModelBatch modelBatch;
	public Model model;
	public ModelInstance instance;
	public PerspectiveCamera cam;
	public Environment environment;
	public StringBuilder stringBuilder;
	public Label label;
	public Stage stage;

	public BitmapFont font;
	float aXnow;
	float aYnow;
	float aZnow;

	long startTime;
	boolean accelerometerAvail;
	final float [] startPos = {150f, -9f, 0f};
	@Override
	public void create () {
		accelerometerAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
		cam = new PerspectiveCamera(67,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		cam.position.set(startPos[0],startPos[1],startPos[2]);
		cam.lookAt(0,0,0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();

		modelBatch = new ModelBatch();

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, 10f, 10f, 20f));

		ModelBuilder modelBuilder = new ModelBuilder();
		model = modelBuilder.createBox(100f, 100f, 100f,
				new Material(ColorAttribute.createDiffuse(Color.GRAY)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
		instance = new ModelInstance(model);
		instance.transform.setToRotation(Vector3.Z, 120);

		startTime = System.currentTimeMillis();

		font = new BitmapFont();
		font.getData().setScale(2f);
		label = new Label(" ", new Label.LabelStyle(font, Color.WHITE));
		stage = new Stage();
		stage.addActor(label);
	}

	@Override
	public void render () {

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(cam);
		modelBatch.render(instance);
		modelBatch.end();

		long time = System.currentTimeMillis()-startTime;

		stringBuilder = new StringBuilder();
		stringBuilder.append("FPS: ").append(Gdx.graphics.getFramesPerSecond());
		stringBuilder.append("| Game Time: |").append(time);
		if(accelerometerAvail){
			aXnow = Gdx.input.getAccelerometerX();
			aYnow = Gdx.input.getAccelerometerY();
			aZnow = Gdx.input.getAccelerometerZ();
			stringBuilder.append("| X: ").append(aXnow);
			stringBuilder.append("| Y: ").append(aYnow);
			stringBuilder.append("| Z: ").append(aZnow);
		}
		label.setText(stringBuilder);
		stage.draw();
	}

	@Override
	public void dispose () {
		model.dispose();
		modelBatch.dispose();
	}
/*	public String move (){
		aXnow = Gdx.input.getAccelerometerX();
		aYnow = Gdx.input.getAccelerometerY();
		aZnow = Gdx.input.getAccelerometerZ();

		float aXbefore = aXnow;
		float aYbefore = aYnow;
		float AZbefore = aZnow;
		if(aXnow-AZbefore>10){
		}
	}	*/
}
