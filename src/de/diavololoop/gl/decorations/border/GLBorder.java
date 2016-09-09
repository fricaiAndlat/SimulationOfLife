package de.diavololoop.gl.decorations.border;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

public abstract class GLBorder {
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected int thickness = 1;
	
	private static int SQUAD_BUFFER_ID;
	private static boolean IS_INITIALISED = false;
	
	public GLBorder(){
		if(!isInitialised()){
			init();
		}
		if(!IS_INITIALISED){
			FloatBuffer buffer = BufferUtils.createFloatBuffer(2*4);
			buffer.put(0);
			buffer.put(1);
			
			buffer.put(0);
			buffer.put(0);
			
			buffer.put(1);
			buffer.put(0);
			
			buffer.put(1);
			buffer.put(1);
			buffer.flip();
			
			SQUAD_BUFFER_ID = GL15.glGenBuffers();
	        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, SQUAD_BUFFER_ID);
	        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	        
	        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		}
	}
	
	public abstract int getThickness();
	public abstract void setThickness(int valuse);
	
	public void setRectangle(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	protected int getSquadBufferID(){
		return SQUAD_BUFFER_ID;
	}
	
	protected abstract boolean isInitialised();
	protected abstract void init();
	
	public abstract void renderBorder(int width, int height);
	
	
}
