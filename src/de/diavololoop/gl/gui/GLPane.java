package de.diavololoop.gl.gui;

import java.util.ArrayList;
import java.util.List;

import de.diavololoop.gl.GLLookAndFeel;

public class GLPane extends GLContainer{

	public GLPane(int maxHeight, int maxWidth) {
		super(maxHeight, maxWidth);
		
		this.setAttrib(new GLAttrib(GLLookAndFeel.getCurrent().getSubSetting("panel")));
	}

	ArrayList<GLComponent> childs = new ArrayList<GLComponent>();
	
	@Override
	public List<GLComponent> getAllComponents() {
		return childs;
	}
	
	@Override
	public void add(GLComponent component){
		childs.add(component);
		component.parent = this;
	}
	
	
	
}
