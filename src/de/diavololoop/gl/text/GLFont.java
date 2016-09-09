package de.diavololoop.gl.text;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;


import org.lwjgl.opengl.GL11;

import de.diavololoop.gl.GLTexture;

public class GLFont {
	
	private final static HashMap<String, GLFont> FONTLIST = new HashMap<String, GLFont>();
	private final static BufferedImage GRAPHICS_GETTER = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
	private final static String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789������������������������������������۴`^� �@�<>|,.-_:;#'+*~�`^?\\!\"�$%&/()=��{[]}";
	
	private static boolean IS_INITIALISED = false;
	
	
	
	
	private HashMap<String, GlyphMetric> fontMetric = new HashMap<String, GlyphMetric>();
	private BufferedImage charMap;
	
	private GLTexture charMapTexture;
	
	public final int maxDescent;
	public final int maxAscent;
	public final int maxHeight;
	
	
	protected GLFont(String name, int type, int size){		
		init();
		
		java.awt.Font font = new java.awt.Font(name, type, size);
		
		java.awt.FontMetrics metric = GRAPHICS_GETTER.getGraphics().getFontMetrics(font);
		
		maxDescent = metric.getMaxDescent();
		maxAscent = metric.getMaxAscent();
				
		int maxWidth = 0;
		for(char c: CHARS.toCharArray()){
			int width = metric.charWidth(c);
			if(width > maxWidth){
				maxWidth = width;
			}
		}
		
		maxHeight = metric.getMaxAscent() + metric.getMaxDescent();
		
		int glyphSize = maxWidth>maxHeight ? maxWidth:maxHeight;
		int glyphBorderLength = (int)Math.ceil(Math.sqrt(CHARS.length()));
		
		charMap = new BufferedImage(glyphSize*glyphBorderLength, glyphSize*glyphBorderLength, BufferedImage.TYPE_INT_ARGB);
		Graphics g = charMap.getGraphics();
		
		g.setFont(font);
		g.setColor(Color.WHITE);
		
		for(int i = 0; i < CHARS.length(); ++i){
			String current = Character.toString(CHARS.charAt(i));
			
			float uvSize = 1f/glyphBorderLength;
			int x = i%glyphBorderLength;
			int y = i/glyphBorderLength;
			int charWidth = metric.stringWidth(current);	
			
			fontMetric.put(current, new GlyphMetric(
					charWidth, maxHeight, 0, metric.getMaxDescent(),
					uvSize*x, uvSize*x + (float)charWidth/charMap.getWidth(), 1-uvSize*y, 1-uvSize*(y+1)));
			
			g.drawString(current, x*glyphSize, metric.getMaxAscent() + y*glyphSize);
		}
		
		
		charMapTexture = new GLTexture(charMap, GL11.GL_NEAREST);
		
		
	}
	
	private void init(){
		if(IS_INITIALISED){
			return;
		}
		
		File f = new File("res/fonts/");
		
		for(File dir: f.listFiles()){
			if(dir.isDirectory()){
				loadFontDirectory(dir);
			}
		}
		
		
		
		IS_INITIALISED = true;
	}
	
	private void loadFontDirectory(File dir){
		for(File file: dir.listFiles()){
			if(file.isFile() && file.getName().endsWith(".ttf")){
				
				try {
					String fontName = file.getName().substring(0, file.getName().length()-4);
					FileInputStream input = new FileInputStream(file);
					java.awt.Font font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, input);
					input.close();
					
					//IMPORTED_FONTS.put(fontName, font);
					
					GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont( font );
					
					System.out.println("loaded font: "+fontName);
					
				} catch (FontFormatException | IOException e) {
					e.printStackTrace();
				}
				
			}
		}
			
	}
	
	public GlyphMetric getGlyphMetric(String c){
		return fontMetric.get(c);
	}
	
	public GLTexture getTexture(){
		return charMapTexture;
	}
	
	public int getWidth(String string){
		int sum = 0;
		
		for(char c: string.toCharArray()){
			GlyphMetric metric = fontMetric.get(Character.toString(c));
			sum += metric.width + metric.offsetX;
		}
		
		return sum;
	}
	
	public int getHeight(String string){
		int height = 0;
		
		for(char c: string.toCharArray()){
			GlyphMetric metric = fontMetric.get(Character.toString(c));
			if(metric.height > height){
				height = metric.height;
			}
		}
		return height;
	}
	
	public static GLFont createFont(String name, int type, int size){
		GLFont alreadyCreated = FONTLIST.get(name+size);
		
		if(alreadyCreated != null){
			return alreadyCreated;
		}
		
		GLFont newlyCreated = new GLFont(name, type, size);
		FONTLIST.put(name+size, newlyCreated);
		
		return newlyCreated;
	}
	
	public static GLFont createFont(int size){
		return createFont(GRAPHICS_GETTER.getGraphics().getFont().getFontName(), java.awt.Font.PLAIN, size);
	}
	public static GLFont createFont(String name, int size){
		return createFont(name, java.awt.Font.PLAIN, size);
	}
	
	class GlyphMetric{
		public final int width;
		public final int height;
		public final int offsetX;
		public final int offsetY;
		
		public final float uvx1;
		public final float uvx2;
		
		public final float uvy1;
		public final float uvy2;
		
		public GlyphMetric(int width, int height, int offsetX, int offsetY, float uvx1, float uvx2, float uvy1, float uvy2){
			this.width = width;
			this.height = height;
			this.offsetX = offsetX;
			this.offsetY = offsetY;
			
			this.uvx1 = uvx1;
			this.uvx2 = uvx2;
			this.uvy1 = uvy1;
			this.uvy2 = uvy2;
		}
	}
	
}
