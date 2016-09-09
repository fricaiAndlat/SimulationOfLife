package de.diavololoop.gl.decorations;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import de.diavololoop.gl.GLColor;
import de.diavololoop.gl.GLFrame;

public class GLBackground {
	
	private static boolean IS_INITIALISED = false;
	private static int BACKGROUNDRENDER_PROGRAM;
	
	protected static int BUFFER_ID;
	
	protected GLColor color;
	
	protected int x, y;
	protected int width, height;
	
	public GLBackground(int x, int y, int width, int height, GLColor color){
		init();
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.color = color;
	}
	
	public void setSize(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	public void setColor(GLColor color){
		this.color = color;
	}
	
	private void init(){
		if(IS_INITIALISED){
			return;
		}
		
		String vert=
	            "#version 330                                             \n"+
	            "                                                         \n"+
	            "in vec2 position;                                        \n"+
	            "                                                         \n"+
	            "uniform vec2 offset;                                     \n"+
	            "uniform vec2 size;                                       \n"+
	            "                                                         \n"+
	            "void main(){                                             \n"+
	            "    gl_Position = vec4((2*(offset+size*position))-1, 0.5f, 1.0f);   \n"+
	            "}                                                        \n";
		String frag=
	            "#version 330                                             \n"+
	            "                                                         \n"+
	            "out vec4 out_color;                                      \n"+
	            "                                                         \n"+
	            "uniform vec4 background_color;                           \n"+
	            "                                                         \n"+
	            "void main(){                                             \n"+
	            "    out_color = background_color;                        \n"+
	            "}                                                        \n";
		BACKGROUNDRENDER_PROGRAM = GLFrame.createShaderProgramme(new int[]{
		        GL20.GL_VERTEX_SHADER, GL20.GL_FRAGMENT_SHADER
		}, new String[]{
		        vert, frag
		});
		
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
		
		BUFFER_ID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, BUFFER_ID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		IS_INITIALISED = true;
	}
	public void renderBackground(int windowWidth, int windowHeight){
		renderBackground(windowWidth, windowHeight, true);
	}
	
	public void renderBackground(int windowWidth, int windowHeight, boolean resetBlending){
		
		if(resetBlending){
			//set alpha blending
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}

		//start actual drawing
        GL20.glUseProgram(BACKGROUNDRENDER_PROGRAM);
        
        //set size
        int posSize = GL20.glGetUniformLocation(BACKGROUNDRENDER_PROGRAM, "size");
        GL20.glUniform2f(posSize, (float)width/windowWidth, (float)height/windowHeight);
        
        //set offset (position)
        int posOffset = GL20.glGetUniformLocation(BACKGROUNDRENDER_PROGRAM, "offset");
        GL20.glUniform2f(posOffset, (float)x/windowWidth, (float)y/windowHeight);
        
        //set text color
        int posBackgroundColor = GL20.glGetUniformLocation(BACKGROUNDRENDER_PROGRAM, "background_color");
        GL20.glUniform4f(posBackgroundColor, color.r, color.g, color.b, color.a);
        
        //give buffer data
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, BUFFER_ID);
		
        //give "position" (its a basic squad)
        int position=GL20.glGetAttribLocation(BACKGROUNDRENDER_PROGRAM, "position");
        GL20.glEnableVertexAttribArray(position);
        GL20.glVertexAttribPointer(position, 2, GL11.GL_FLOAT, false, 0, 0);
        
        GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);
        GL20.glDisableVertexAttribArray(0);	
	}
	
	public String toString(){
		return String.format("GLBackground[r=%f, g=%f, b=%f, a=%f]", color.r, color.g, color.b, color.a);
	}

	
}
