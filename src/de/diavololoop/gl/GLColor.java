package de.diavololoop.gl;

public class GLColor {
	
	public final static GLColor BLACK = new GLColor(0, 0, 0);
	public final static GLColor BLUE = new GLColor(0, 0, 1);
	public final static GLColor CYAN = new GLColor(0, 1, 1);
	public final static GLColor DARK_GRAY = new GLColor(0.25f, 0.25f, 0.25f);
	public final static GLColor GRAY = new GLColor(0.5f, 0.5f, 0.5f);
	public final static GLColor GREEN = new GLColor(0, 1, 0);
	public final static GLColor LIGHT_GRAY = new GLColor(0.75f, 0.75f, 0.75f);
	public final static GLColor MAGENTA = new GLColor(1, 0, 1);
	public final static GLColor ORANGE = new GLColor(1, 0.5f, 0);
	public final static GLColor PINK = new GLColor(1, 0.5f, 0);
	public final static GLColor RED = new GLColor(1, 0, 0);
	public final static GLColor WHITE = new GLColor(1, 1, 1);
	public final static GLColor YELLOW = new GLColor(1, 1, 0);
	
	public final static GLColor TRANSPARENT = new GLColor(0, 0, 0, 0);
	
	public final float a;
	public final float r;
	public final float g;
	public final float b;
	
	public GLColor(float r, float g, float b){
		this(r, g, b, 1);
	}
	
	public GLColor(float r, float g, float b, float a){
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
}
