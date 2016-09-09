package de.diavololoop.gl.gui;

import java.nio.FloatBuffer;
import java.util.List;

import de.diavololoop.gl.GLTexture;

public abstract class GLContainer extends GLComponent{
	
	public GLContainer(int maxHeight, int maxWidth) {
		super(maxHeight, maxWidth);
	}
	public abstract List<GLComponent> getAllComponents();
	public abstract void add(GLComponent component);
	

	
	@Override
	public void setScreenSize(int screenWidth, int screenHeight){
		super.setScreenSize(screenWidth, screenHeight);
		
		for(GLComponent comp: getAllComponents()){
			comp.setScreenSize(screenWidth, screenHeight);
		}
	}

	@Override
	public void paint(FloatBuffer buffer, GLTexture texture){
		for(GLComponent comp: getAllComponents()){
			comp.paint(buffer, texture);
		}
	}
	
	@Override
	public void drawExtra(){
		super.drawExtra();
		for(GLComponent comp: getAllComponents()){
			comp.drawExtra();
		}
	}
	
	@Override
	public int getChildLength(){
		int result = 0;
		
		for(GLComponent comp: getAllComponents()){
			result += comp.getChildLength();
		}
		
		return result;
	}
	
	@Override
	public int setElementID(int startID){
		int givenIDs = 0;
		
		for(GLComponent comp: getAllComponents()){
			givenIDs += comp.setElementID(startID+givenIDs);
		}
		
		return givenIDs;
		
	}
	
	@Override
	public int getElementIDFromPosition(int posx, int posy){
		
		for(GLComponent comp: getAllComponents()){
			int result = comp.getElementIDFromPosition(posx, posy);
			if(result != -1){
				return result;
			}
		}
		return -1;
	}
	
	@Override
	public GLComponent getComponentFromPosition(int posx, int posy){
		
		for(GLComponent comp: getAllComponents()){
			GLComponent result = comp.getComponentFromPosition(posx, posy);
			if(result != null){
				return result;
			}
		}
		return null;
	}


}
