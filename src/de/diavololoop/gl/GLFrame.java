package de.diavololoop.gl;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import de.diavololoop.gl.gui.GLMenu;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;


public class GLFrame{

	private static boolean isInitialised = false;
	
	// The window handle
	private long windowID;
	private int width = 800;
	private int height = 700;
	
	private GLMenu menu;
	
	public GLFrame(){
		Thread t = new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					
					
					init();
					
					// Create the window
					windowID = glfwCreateWindow(width, height, "Hello World!", NULL, NULL);
					if ( windowID == NULL )
						throw new RuntimeException("Failed to create the GLFW window");

					// Setup a key callback. It will be called every time a key is pressed, repeated or released.
					glfwSetKeyCallback(windowID, (window, key, scancode, action, mods) -> {
						if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE ){
							glfwSetWindowShouldClose(window, true); // We will detect this in our rendering loop
						}
						if(menu != null){
							menu.keyEvent(key, action == GLFW_PRESS, mods);
						}
					});
					
					// setup a mouse callback
					glfwSetCursorPosCallback(windowID, (window, xpos, ypos) ->{
						if(menu != null){
							menu.mouseMoved((int)xpos, height-(int)ypos);
						}
					});
					// setup a key callback
					GLFW.glfwSetMouseButtonCallback(windowID, (window, button, action, mods) -> {
						if(menu != null){
							menu.mouseEvent(button, action == GLFW_PRESS, mods);
						}
					});
					GLFW.glfwSetCharCallback(windowID, (window, codepoint) -> {
						if(menu != null){
							char[] chars = Character.toChars(codepoint);
							if(chars.length > 0){
								menu.keyEvent(chars[0]);
							}
						}
					});
					
					GLFW.glfwSetFramebufferSizeCallback(windowID, (window, newWidth, newHeight)->{
						if(menu != null){
							width = newWidth;
							height = newHeight;
							
							GL11.glViewport(0, 0, width, height);
							menu.setSize(width, height);
						}
					});

					// Get the resolution of the primary monitor
					GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
					// Center our window
					glfwSetWindowPos(
						windowID,
						(vidmode.width() - width) / 2,
						(vidmode.height() - height) / 2
					);

					// Make the OpenGL context current
					glfwMakeContextCurrent(windowID);
					// Enable v-sync
					glfwSwapInterval(1);

					// Make the window visible
					glfwShowWindow(windowID);

					loop();

					

					// Free the window callbacks and destroy the window
					glfwFreeCallbacks(windowID);
					glfwDestroyWindow(windowID);
				} catch(Exception e){
					e.printStackTrace();
				}finally {
					// Terminate GLFW and free the error callback
					glfwTerminate();
					glfwSetErrorCallback(null).free();
				}
				
			}
			
		});
		t.start();
			
	}

	private static void init() {
		if(isInitialised){
			return;
		}
		isInitialised = true;
		
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure our window
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		
	}

	private void loop() {
		// Make the OpenGL context current
		glfwMakeContextCurrent(windowID);
		
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();
		
		System.out.println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));
		System.out.println("GL_SHADING_LANGUAGE_VERSION version: " + GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION));

		// Set the clear color
		glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
		
		
		menu = new GLMenu();
		menu.setSize(width, height);
		menu.finish();
		
		//GLFW.glfwSetWindowSizeCallback(windowID, cbfun);
		
		
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while ( !glfwWindowShouldClose(windowID) ) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

			
			//http://forum.staticvoidgames.com/t/lwjgl-tutorial-part-7-vector-and-matrix-math/47
			
			
			menu.paint();
			
			//------------------------------
			
			glfwSwapBuffers(windowID); // swap the color buffers

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}
	}
	
	public static int createShader(int shadertype,String shaderString){
        int shader = GL20.glCreateShader(shadertype);
        GL20.glShaderSource(shader, shaderString);
        GL20.glCompileShader(shader);
        int status = GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS);
        if (status == GL11.GL_FALSE){
            
            String error=GL20.glGetShaderInfoLog(shader);
            
            String ShaderTypeString = null;
            switch(shadertype){
            case GL20.GL_VERTEX_SHADER: ShaderTypeString = "vertex"; break;
            case GL32.GL_GEOMETRY_SHADER: ShaderTypeString = "geometry"; break;
            case GL20.GL_FRAGMENT_SHADER: ShaderTypeString = "fragment"; break;
            }
            
            System.err.println( "Compile failure in " + ShaderTypeString + " shader:\n" + error);
        }
        return shader;
    }
	
	public static int createShaderProgramme(int[] shaders){
        int program = GL20.glCreateProgram();
        for (int i = 0; i < shaders.length; i++) {
            GL20.glAttachShader(program, shaders[i]);
        }
        GL20.glLinkProgram(program);
        
        // validate linking
     	if (GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
     		throw new RuntimeException("could not link shader. Reason: " + GL20.glGetProgramInfoLog(program, 1000));
     	}
      
     	// perform general validation that the program is usable
     	GL20.glValidateProgram(program);
      
     	if (GL20.glGetProgrami(program, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE){
     		throw new RuntimeException("could not validate shader. Reason: " + GL20.glGetProgramInfoLog(program, 1000));            
     	}
        return program;
    }
	
	 public static int createShaderProgramme(String vert, String frag){
         int[] shaderids = new int[2];
         
         shaderids[0] = createShader(GL20.GL_VERTEX_SHADER, vert);
         shaderids[1] = createShader(GL20.GL_FRAGMENT_SHADER, frag);

         return createShaderProgramme(shaderids);
     }
	 
	 public static int createShaderProgramme(int[] shadertypes, String[] shaders){
         int[] shaderids = new int[shaders.length];
         for (int i = 0; i < shaderids.length; i++) {
             shaderids[i] = createShader(shadertypes[i], shaders[i]);
         }
         return createShaderProgramme(shaderids);
     }

	public static void main(String[] args) {

		new GLFrame();
		
		
	}

}