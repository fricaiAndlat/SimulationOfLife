package de.diavololoop.gl.text;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import de.diavololoop.gl.GLColor;
import de.diavololoop.gl.GLFrame;

public class GLText {

	private static boolean IS_INITIALISED = false;
	private static int TEXTRENDER_PROGRAM;
	
	protected final String content;
	protected GLFont font;
	
	protected FloatBuffer dataBuffer;
	protected int dataBufferID;
	
	private boolean isFreed;
	
	public GLText(String content, int size){
		this.content = content;
		this.font = GLFont.createFont(size);
		
		init();
		createDataBuffer();
	}
	
	public GLText(String content, GLFont font){
		this.content = content;
		this.font = font;
		
		init();
		createDataBuffer();
	}
	
	private void init(){
		if(IS_INITIALISED){
			return;
		}
		
		String vert=
	            "#version 330                                             \n"+
	            "                                                         \n"+
	            "in vec2 position;                                        \n"+
	            "in vec2 tex_corner;                                      \n"+
	            "                                                         \n"+
	            "out vec2 tex_coord;                                      \n"+
	            "                                                         \n"+
	            "uniform vec2 offset;                                     \n"+
	            "uniform vec2 window_size;                                \n"+
	            "                                                         \n"+
	            "void main(){                                             \n"+
	            "    gl_Position = vec4(2*((offset + position)*window_size)-1, 0.5f, 1.0f);   \n"+
	            "    tex_coord   = tex_corner;                            \n"+
	            "}                                                        \n";
		String frag=
	            "#version 330                                             \n"+
	            "                                                         \n"+
	            "in vec2 tex_coord;                                       \n"+
	            "                                                         \n"+
	            "out vec4 out_color;                                      \n"+
	            "                                                         \n"+
	            "uniform sampler2D textureIn;                             \n"+
	            "uniform vec4 text_color;                                 \n"+
	            "                                                         \n"+
	            "void main(){                                             \n"+
	            "    out_color = texture(textureIn,tex_coord) * text_color;\n"+
	            "}                                                        \n";
		TEXTRENDER_PROGRAM = GLFrame.createShaderProgramme(new int[]{
		        GL20.GL_VERTEX_SHADER, GL20.GL_FRAGMENT_SHADER
		}, new String[]{
		        vert, frag
		});
		
		IS_INITIALISED = true;
	}
	
	private void createDataBuffer(){
		dataBuffer = BufferUtils.createFloatBuffer(content.length()*4*4);
		
		int progress = 0;
		
		for(int i = 0; i < content.length(); ++i){
			GLFont.GlyphMetric metric = font.getGlyphMetric(Character.toString(content.charAt(i)));
			
			dataBuffer.put(progress);
			dataBuffer.put(metric.height);
			dataBuffer.put(metric.uvx1);
			dataBuffer.put(metric.uvy1);
			
			dataBuffer.put(progress);
			dataBuffer.put(0 - metric.offsetY);
			dataBuffer.put(metric.uvx1);
			dataBuffer.put(metric.uvy2);
			
			dataBuffer.put(progress + metric.width);
			dataBuffer.put(0 - metric.offsetY);
			dataBuffer.put(metric.uvx2);
			dataBuffer.put(metric.uvy2);
			
			dataBuffer.put(progress + metric.width);
			dataBuffer.put(metric.height);
			dataBuffer.put(metric.uvx2);
			dataBuffer.put(metric.uvy1);
			
			progress += metric.width + metric.offsetX;
		}
		
		dataBuffer.flip();
		
		dataBufferID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, dataBufferID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, dataBuffer, GL15.GL_STATIC_DRAW);
        
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public void renderText(int x, int y, GLColor color, int width, int height){
		
		if(isFreed){
			throw new IllegalStateException("tried to render an already freed GLText");
		}
		
		//set alpha blending
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		//start actual drawing
        GL20.glUseProgram(TEXTRENDER_PROGRAM);
        
        //use charTexture
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTexture().textureID);
        int tex=GL20.glGetUniformLocation(TEXTRENDER_PROGRAM, "textureIn");
        GL20.glUniform1i(tex, 0);
        
        //set render offset
        int posOffset = GL20.glGetUniformLocation(TEXTRENDER_PROGRAM, "offset");
        GL20.glUniform2f(posOffset, x, y);
        
        //set window size
        int posWindowSize = GL20.glGetUniformLocation(TEXTRENDER_PROGRAM, "window_size");
        GL20.glUniform2f(posWindowSize, 1f/width, 1f/height);
        
        //set text color
        int posTextColor = GL20.glGetUniformLocation(TEXTRENDER_PROGRAM, "text_color");
        GL20.glUniform4f(posTextColor, color.r, color.g, color.b, color.a);
        
        //give buffer data
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, dataBufferID);
		
        int position=GL20.glGetAttribLocation(TEXTRENDER_PROGRAM, "position");
        GL20.glEnableVertexAttribArray(position);
        GL20.glVertexAttribPointer(position, 2, GL11.GL_FLOAT, false, 4*4, 0);
        
        int corner=GL20.glGetAttribLocation(TEXTRENDER_PROGRAM, "tex_corner");
        GL20.glEnableVertexAttribArray(corner);
        GL20.glVertexAttribPointer(corner, 2, GL11.GL_FLOAT, false, 4*4, 2*4);
        
        GL11.glDrawArrays(GL11.GL_QUADS, 0, content.length()*4);
        GL20.glDisableVertexAttribArray(0);
        
        	
	}
	
	public void free(){
		if(isFreed){
			throw new IllegalStateException("tried to free an already freed GLText");
		}
		
		GL15.glDeleteBuffers(dataBufferID);
		isFreed = true;
	}
	
	@Override
	public void finalize(){
		if(!isFreed){
			IllegalStateException e = new IllegalStateException("finalize was invoked without freeing the OpenGL VBO");
			e.printStackTrace();
			throw e;
		}
	}
	

}
