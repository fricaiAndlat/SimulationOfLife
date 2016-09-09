package de.diavololoop.gl.decorations;

import org.lwjgl.opengl.GL11;

import de.diavololoop.gl.GLColor;

public class GLInvert extends GLBackground{

	private GLColor color;
	
	public GLInvert(int x, int y, int width, int height, GLColor color) {
		super(x, y, width, height, GLColor.WHITE);
		
		this.color = color;
		
	}
	
	public void renderBackground(int windowWidth, int windowHeight){
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ZERO);
		super.renderBackground(windowWidth, windowHeight, false);
		
		this.setColor(color);
		GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_DST_COLOR);
		super.renderBackground(windowWidth, windowHeight, false);
		this.setColor(GLColor.WHITE);
	}

}
