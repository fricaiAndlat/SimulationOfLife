package de.diavololoop.gl.gui;

import java.nio.FloatBuffer;

import de.diavololoop.gl.GLTexture;

public class GLTexturedComponent extends GLComponent{

	protected float textureX;
	protected float textureY;
	
	public GLTexturedComponent(int preferedWidth, int preferedHeight) {
		super(preferedWidth, preferedHeight);
	}
	
	@Override
	public int getChildLength(){
		return 1;
	}
	
	@Override
	public int setElementID(int startID){
		this.elementID = startID;
		return 1;
	}
	
	@Override
	public void paint(FloatBuffer buffer, GLTexture texture){
		
		buffer.put((float)x/screenWidth);
		buffer.put((float)y/screenHeight);
		buffer.put(textureX);
		buffer.put(textureY);
		buffer.put((float)width/texture.width);
		buffer.put((float)height/texture.height);
		
		buffer.put((float)(x+width)/screenWidth);
		buffer.put((float)y/screenHeight);
		buffer.put(textureX + (float)width/texture.width);
		buffer.put(textureY);
		buffer.put((float)width/texture.width);
		buffer.put((float)height/texture.height);
		
		buffer.put((float)(x+width)/screenWidth);
		buffer.put((float)(y+height)/screenHeight);
		buffer.put(textureX + (float)width/texture.width);
		buffer.put(textureY + (float)height/texture.height);
		buffer.put((float)width/texture.width);
		buffer.put((float)height/texture.height);
		
		buffer.put((float)x/screenWidth);
		buffer.put((float)(y+height)/screenHeight);
		buffer.put(textureX);
		buffer.put(textureY + (float)height/texture.height);
		buffer.put((float)width/texture.width);
		buffer.put((float)height/texture.height);
		
	
		
	}
	
	public void setTexture(float textureX, float textureY){
		this.textureX = textureX;
		this.textureY = textureY;
		reCalculatePosition();
	}
	

}
