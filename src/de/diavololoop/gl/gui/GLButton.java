package de.diavololoop.gl.gui;

import de.diavololoop.gl.GLLookAndFeel;
public class GLButton extends GLLabel{

	protected Runnable action;
	
	public GLButton(String string) {
		super(string);
		
		this.setAttrib(new GLAttrib(GLLookAndFeel.getCurrent().getSubSetting("button")));
		setText(string);
		reCalculatePosition();
		
		
		this.setExpand(true, true);
		
		textAlignX = CENTER;
		textAlignY = CENTER;
	}
	
	public void setAction(Runnable action){
		this.action = action;
		
	}

	
	@Override
	public void onMousePressed(int x, int y){
		super.onMousePressed(x, y);
		if(action != null){
			action.run();
		}
	}
		


}
