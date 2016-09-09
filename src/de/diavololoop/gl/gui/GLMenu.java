package de.diavololoop.gl.gui;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import de.diavololoop.gl.GLFrame;
import de.diavololoop.gl.GLTexture;

public class GLMenu{
	
	static private int PROGRAM;
	static private boolean IS_PROGRAM_INITIALISED = false;
	
	GLComponent comp;
	
	FloatBuffer buffer;
	int bufferID;
	
	IntBuffer argumentBuffer;
	int argumentBufferID;
	
	GLTexture texture;
	
	boolean isFinished = false;
	GLComponent lastHovered;
	GLComponent currentFocus;
	int mouseX, mouseY;

	public GLMenu(){
		
		if(!IS_PROGRAM_INITIALISED){
			IS_PROGRAM_INITIALISED = true;
			
			String vert=
		            "#version 330                                \n"+
		            "in vec2 position;                           \n"+
		            "in vec2 size;                               \n"+
		            "in vec2 tex_corner;                         \n"+
		            "in int is_hover;                            \n"+
		            "out vec2 tex_coord;                         \n"+
		            "void main(){                                \n"+
		            "    gl_Position= vec4(position*2-vec2(1.0f, 1.0f), 0.5f, 1.0f);    \n"+
		            "    if(is_hover!=0){                        \n"+
		            "       tex_coord=tex_corner-vec2(0, size.y);                \n"+
		            "    }else{                                  \n"+
		            "       tex_coord=tex_corner;                \n"+
		            "    }                                       \n"+
		            "}                                           \n";
			String frag=
		            "#version 330                                \n"+
		            "out vec4 out_color;                         \n"+
		            "in vec2 tex_coord;                          \n"+
		            "uniform sampler2D textureIn;                \n"+
		            "void main(){                                \n"+
		            "    out_color=vec4(texture(textureIn,tex_coord).rgb, 1.0f);             \n"+
		            "}                                           \n";

			PROGRAM = GLFrame.createShaderProgramme(new int[]{
			        GL20.GL_VERTEX_SHADER, GL20.GL_FRAGMENT_SHADER
			}, new String[]{
			        vert, frag
			});
			
			
		}
		
		GLStackPane pane = new GLStackPane(10, 10, GLStackPane.VERTICAL, 10);
		pane.setAlignment(GLComponent.CENTER, GLComponent.TOP);
		pane.setExpand(true, false);
		
		GLComponent c = new GLSpacer(200, 100);
		pane.add(c);
		
		GLButton b = new GLButton("Singleplayer");
		b.setAction(() -> System.out.println("singlePlayer"));
		pane.add(b);
		
		b = new GLButton("Multiplayer");
		b.setAction(() -> System.out.println("Multiplayer"));
		pane.add(b);
		
		b = new GLButton("Minecraft Realms");
		b.setAction(() -> System.out.println("Minecraft Realms"));
		pane.add(b);
		
		pane.add(new GLTextField(20));
		
		c = new GLSpacer(200, 30);
		pane.add(c);
		
		GLStackPane s = new GLStackPane(10, 10, GLStackPane.HORIZONTAL, 10);
		s.setAlignment(GLComponent.LEFT, GLComponent.CENTER);
		s.setExpand(true, true);
		
		GLTexturedComponent t = new GLTexturedComponent(40, 40);
		t.setTexture(0, 0.125f);
		//t.setAction(() -> System.out.println("welt"));
		s.add(t);
		
		b = new GLButton("Options...");
		b.setAction(() -> System.out.println("Options"));
		s.add(b);
		
		b = new GLButton("Quit Game");
		b.setAction(() -> System.out.println("quit"));
		s.add(b);
		
		c = new GLSpacer(40, 40);
		pane.add(c);
		
		GLLabel l = new GLLabel("Hallo, ich bin ein text");
		pane.add(l);
		

		pane.add(s);
		
		pane.reCalculatePosition();
		
		comp = pane;
		comp.setElementID(0);
		
		try {
			texture = new GLTexture(ImageIO.read(new File("res\\menu.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void finish() {
		if(isFinished){
			throw new IllegalStateException("this menu is already finished");
		}
		
		buffer = BufferUtils.createFloatBuffer(comp.getChildLength() * 24);
		argumentBuffer = BufferUtils.createIntBuffer(comp.getChildLength()*4);
		
		comp.paint(buffer, texture);
		buffer.flip();
		
		for(int i = 0; i < argumentBuffer.capacity(); ++i){
			argumentBuffer.put(0);
		}
		argumentBuffer.flip();
		
		bufferID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        
        argumentBufferID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, argumentBufferID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, argumentBuffer, GL15.GL_DYNAMIC_DRAW);
        
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		isFinished = true;
	}

	public void setSize(int width, int height){
		comp.setScreenSize(width, height);
		comp.setSize(width, height);
		comp.reCalculatePosition();

	}
	
	public void paint(){
		if(!isFinished){
			throw new IllegalStateException("cant draw a non finished menu");
		}
		
		//drawing extras like texts
        comp.drawExtra();
		
		// retrieve draw data
		comp.paint(buffer, texture);
		buffer.flip();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        
        //set alpha blending
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        //start actual drawing
        GL20.glUseProgram(PROGRAM);
        
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.textureID);
        int tex=GL20.glGetUniformLocation(PROGRAM, "textureIn");
        GL20.glUniform1i(tex, 0);
        
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
		
        int position=GL20.glGetAttribLocation(PROGRAM, "position");
        GL20.glEnableVertexAttribArray(position);
        GL20.glVertexAttribPointer(position, 2, GL11.GL_FLOAT, false, 6*4, 0);
        
        int corner=GL20.glGetAttribLocation(PROGRAM, "tex_corner");
        GL20.glEnableVertexAttribArray(corner);
        GL20.glVertexAttribPointer(corner, 2, GL11.GL_FLOAT, false, 6*4, 2*4);
        
        int size=GL20.glGetAttribLocation(PROGRAM, "size");
        GL20.glEnableVertexAttribArray(size);
        GL20.glVertexAttribPointer(size, 2, GL11.GL_FLOAT, false, 6*4, 4*4);
        
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, argumentBufferID);
        int isHover=GL20.glGetAttribLocation(PROGRAM, "is_hover");
        GL20.glEnableVertexAttribArray(isHover);
        GL20.glVertexAttribPointer(isHover, 1, GL11.GL_INT, false, 0, 0);
        
        GL11.glDrawArrays(GL11.GL_QUADS, 0, comp.getChildLength()*4);
        GL20.glDisableVertexAttribArray(0);
        
		
		
	}
	
	public void mouseMoved(int x, int y){
		mouseX = x;
		mouseY = y;
		
		if(currentFocus != null){
			currentFocus.onMouseMove(x, y);
		}
		
		GLComponent currentHover = comp.getComponentFromPosition(x, y);

		if(currentHover == lastHovered){
			return;
		}
		
		if(currentHover != null){
			currentHover.onMouseEnter();
		}
		if(lastHovered != null){
			lastHovered.onMouseLeave();
		}
		
		lastHovered = currentHover;
		
		int currentHoverID = -1;
		if(currentHover != null){
			currentHoverID = currentHover.elementID;
		}
		
		
		for(int i = 0; i < argumentBuffer.capacity(); ++i){
			if(i / 4 == currentHoverID){
				argumentBuffer.put(1);
			}else{
				argumentBuffer.put(0);
			}
		}

		argumentBuffer.flip();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, argumentBufferID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, argumentBuffer, GL15.GL_DYNAMIC_DRAW);
	}

	public void mouseEvent(int button, boolean press, int mods) {
		GLComponent component = comp.getComponentFromPosition(mouseX, mouseY);
		
		
		
		if(press){
			if(component != null){
				component.onMousePressed(mouseX, mouseY);
				
			}
			requestFocus(component);
		}else{
			if(component != null){
				component.onMouseReleased();
			}
			if(currentFocus != null && currentFocus!=component){
				currentFocus.onMouseReleased();
			}
		}
		
	}
	
	public void keyEvent(int key, boolean press, int mods) {
		if(currentFocus != null){
		
			if(press){
				currentFocus.onKeyPressed(key, (mods&GLFW.GLFW_MOD_SHIFT)!=0, (mods&GLFW.GLFW_MOD_CONTROL)!=0, (mods&GLFW.GLFW_MOD_ALT)!=0);
			}else{
				currentFocus.onKeyReleased(key, (mods&GLFW.GLFW_MOD_SHIFT)!=0, (mods&GLFW.GLFW_MOD_CONTROL)!=0, (mods&GLFW.GLFW_MOD_ALT)!=0);
			}
		}
		
	}
	public void keyEvent(char c) {
		if(currentFocus != null){
			currentFocus.onKeyPressed(c);
		}
		
	}
	
	public void requestFocus(GLComponent comp){
		if(comp == currentFocus){
			return;
		}
		
		if(currentFocus != null){
			currentFocus.onFocusLost();
		}
		
		if(comp != null){
			comp.onFocusGet();
		}
		
		currentFocus = comp;
	}

	

	

}
