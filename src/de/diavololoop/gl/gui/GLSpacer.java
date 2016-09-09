package de.diavololoop.gl.gui;

import java.nio.FloatBuffer;

import de.diavololoop.gl.GLTexture;

public class GLSpacer extends GLComponent{

	public GLSpacer(int maxWidth, int maxHeight) {
		super(maxWidth, maxHeight);
	}
	
	@Override
	public void paint(FloatBuffer buffer, GLTexture texture){	
	}
	
	@Override
	public int getChildLength(){
		return 0;
	}
	
	@Override
	public int setElementID(int startID){
		return 0;
	}
	
	@Override
	public int getElementIDFromPosition(int posx, int posy){
		return -1;
	}
}
