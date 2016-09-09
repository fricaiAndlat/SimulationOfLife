package de.diavololoop.gl.decorations.border;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import de.diavololoop.gl.GLColor;
import de.diavololoop.gl.GLFrame;

public class GLLineBorder extends GLBorder{

	private static int BORDERRENDER_PROGRAM;
	private static boolean IS_INITIALISED;
	
	private GLColor color = GLColor.BLACK;
	
	public GLLineBorder(){
		this(1);
	}
	
	public GLLineBorder(int thickness){
		this.thickness = thickness;
	}
	
	public void setColor(GLColor color){
		this.color = color;
	}
	
	@Override
	protected boolean isInitialised() {
		return IS_INITIALISED;
	}

	@Override
	protected void init() {
		if(IS_INITIALISED){
			return;
		}
		
		String vert=
	            "#version 330                                             \n"+
	            "                                                         \n"+
	            "in vec2 position;                                        \n"+
	            "                                                         \n"+
	            "out vec2 real_position;                                  \n"+
	            "                                                         \n"+
	            "uniform vec2 offset;                                     \n"+
	            "uniform vec2 size;                                       \n"+
	            "uniform vec2 window_size;                                \n"+
	            "                                                         \n"+
	            "void main(){                                             \n"+
	            "    real_position = size*position;                       \n"+
	            "    gl_Position = vec4(2*(real_position*window_size+offset)-1, 0.5f, 1);   \n"+
	            "}                                                        \n";
		String frag=
	            "#version 330                                                                            \n"+
	            "                                                                                        \n"+
	            "in vec2 real_position;                                                                  \n"+
	            "                                                                                        \n"+
	            "uniform vec4 border_color;                                                              \n"+
	            "uniform vec2 size;                                                                      \n"+
	            "uniform int border_thickness;                                                           \n"+
	            "                                                                                        \n"+
	            "void main(){                                                                            \n"+
	            "    if(real_position.x <= border_thickness || real_position.y <= border_thickness){     \n"+
	            "        gl_FragColor = border_color;                                                    \n"+
	            "    }                                                                                   \n"+
	            "    vec2 invert_real_position = size - real_position;                                   \n"+
	            "    if(invert_real_position.x <= border_thickness || invert_real_position.y <= border_thickness){       \n"+
	            "        gl_FragColor = border_color;                                                    \n"+
	            "    }                                                                                   \n"+
	            "}                                                                                       \n";
		
		BORDERRENDER_PROGRAM = GLFrame.createShaderProgramme(new int[]{
		        GL20.GL_VERTEX_SHADER, GL20.GL_FRAGMENT_SHADER
		}, new String[]{
		        vert, frag
		});
		
		IS_INITIALISED = true;
		
	}

	@Override
	public void renderBorder(int windowWidth, int windowHeight) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	

		//start actual drawing
	    GL20.glUseProgram(BORDERRENDER_PROGRAM);
	    
	    //set size
	    int posSize = GL20.glGetUniformLocation(BORDERRENDER_PROGRAM, "size");
	    GL20.glUniform2f(posSize, (float)width, (float)height);
	    
	    //set windowsize
	    int posWindowSize = GL20.glGetUniformLocation(BORDERRENDER_PROGRAM, "window_size");
	    GL20.glUniform2f(posWindowSize, 1f/windowWidth, 1f/windowHeight);
	    
	    //set offset (position)
	    int posOffset = GL20.glGetUniformLocation(BORDERRENDER_PROGRAM, "offset");
	    GL20.glUniform2f(posOffset, (float)x/windowWidth, (float)y/windowHeight);
	    
	    //set border color
	    int posBorderColor = GL20.glGetUniformLocation(BORDERRENDER_PROGRAM, "border_color");
	    GL20.glUniform4f(posBorderColor, color.r, color.g, color.b, color.a);
	    
	    //set border thickness
	    int posBorderThickness = GL20.glGetUniformLocation(BORDERRENDER_PROGRAM, "border_thickness");
	    GL20.glUniform1i(posBorderThickness, thickness);
	    
	    //give buffer data
	    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, getSquadBufferID());
		
	    //give "position" (its a basic squad)
	    int position=GL20.glGetAttribLocation(BORDERRENDER_PROGRAM, "position");
	    GL20.glEnableVertexAttribArray(position);
	    GL20.glVertexAttribPointer(position, 2, GL11.GL_FLOAT, false, 0, 0);
	    
	    GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);
	    GL20.glDisableVertexAttribArray(0);
		
	}

	@Override
	public int getThickness() {
		return thickness;
	}
	
	@Override
	public void setThickness(int value){
		this.thickness = value;
	}
	
}
