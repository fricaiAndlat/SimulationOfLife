package de.diavololoop.gl.decorations.border;

public class GLEmptyBorder extends GLBorder{

	private int thickness = 0;
	
	@Override
	protected boolean isInitialised() {
		//no need to be initialised
		return true;
	}

	@Override
	protected void init() {
		//no need to be initialised
	}

	@Override
	public void renderBorder(int width, int height) {
		//dont render anything
	}
	
	@Override
	public void setThickness(int value){
		this.thickness = value;
	}

	@Override
	public int getThickness() {
		return thickness;
	}

}
