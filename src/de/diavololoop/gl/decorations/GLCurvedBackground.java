package de.diavololoop.gl.decorations;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import de.diavololoop.gl.GLColor;
import de.diavololoop.gl.GLFrame;

public class GLCurvedBackground extends GLBackground{
	
	private static boolean IS_INITIALISED = false;
	private static int BACKGROUNDRENDER_PROGRAM;
	
	public GLCurvedBackground(GLColor color){
		super(0, 0, 0, 0, color);
		
		ownInit();
	}

	private void ownInit() {
		if(IS_INITIALISED){
			return;
		}
		
		String vert=
	            "#version 330                                             \n"+
	            "                                                         \n"+
	            "in vec2 position;                                        \n"+
	            "                                                         \n"+
	            "out vec2 interpolated_position;                          \n"+
	            "                                                         \n"+
	            "uniform vec2 offset;                                     \n"+
	            "uniform vec2 size;                                       \n"+
	            "                                                         \n"+
	            "void main(){                                             \n"+
	            "    interpolated_position = position;                    \n"+
	            "    gl_Position = vec4((2*(offset+size*position))-1, 0.5f, 1.0f);   \n"+
	            "}                                                        \n";
		String frag=
	            "#version 330                                             \n"+
	            "                                                         \n"+
	            "in vec2 interpolated_position;                           \n"+
	            "                                                         \n"+
	            "out vec4 out_color;                                      \n"+
	            "                                                         \n"+
	            "uniform vec4 background_color;                           \n"+
	            "                                                         \n"+
	            "void main(){                                             \n"+
	            "    float factor = 0.25f-sqrt(pow(interpolated_position.y-0.7f, 2))/2.0f;   \n"+
	            "    out_color = background_color + vec4(1, 1, 1, 0)*factor;\n"+
	            "}                                                        \n";
		BACKGROUNDRENDER_PROGRAM = GLFrame.createShaderProgramme(new int[]{
		        GL20.GL_VERTEX_SHADER, GL20.GL_FRAGMENT_SHADER
		}, new String[]{
		        vert, frag
		});
		
		IS_INITIALISED = true;
		
	}
	
	public void renderBackground(int windowWidth, int windowHeight){
		
		
		//set alpha blending
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

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

}
