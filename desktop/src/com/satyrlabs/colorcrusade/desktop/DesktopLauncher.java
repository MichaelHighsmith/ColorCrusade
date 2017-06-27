package com.satyrlabs.colorcrusade.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.satyrlabs.colorcrusade.ColorCrusade;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = ColorCrusade.WIDTH;
		config.height = ColorCrusade.HEIGHT;
		config.title = ColorCrusade.TITLE;
		new LwjglApplication(new ColorCrusade(), config);
	}
}
