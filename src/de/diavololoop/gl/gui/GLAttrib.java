package de.diavololoop.gl.gui;

import de.diavololoop.gl.GLColor;
import de.diavololoop.gl.decorations.GLBackground;
import de.diavololoop.gl.decorations.GLCurvedBackground;
import de.diavololoop.gl.decorations.GLInvert;
import de.diavololoop.gl.decorations.border.GLBorder;
import de.diavololoop.gl.decorations.border.GLEmptyBorder;
import de.diavololoop.gl.decorations.border.GLLineBorder;
import de.diavololoop.gl.text.GLFont;
import de.diavololoop.util.yml.Setting;

public class GLAttrib {
	
	private int insets;
	private GLInvert selection;
	
	private GLColor textColor;
	private GLFont textFont;
	private GLBackground outerBackground;
	private GLBackground innerBackground;
	private GLBorder border;
	private boolean isOpaque;
	
	private GLColor hover_textColor;
	private GLFont hover_textFont;
	private GLBackground hover_outerBackground;
	private GLBackground hover_innerBackground;
	private GLBorder hover_border;
	private boolean hover_isOpaque;
	
	private GLColor click_textColor;
	private GLFont click_textFont;
	private GLBackground click_outerBackground;
	private GLBackground click_innerBackground;
	private GLBorder click_border;
	private boolean click_isOpaque;
	
	private GLColor focus_textColor;
	private GLFont focus_textFont;
	private GLBackground focus_outerBackground;
	private GLBackground focus_innerBackground;
	private GLBorder focus_border;
	private boolean focus_isOpaque;

	public int getInsets(){
		return insets;
	}
	public GLInvert getSelection(){
		return selection;
	}
	
	//NORMAL
	public GLColor getTextColor(){
		return textColor;
	}
	public GLFont getTextFont(){
		return textFont;
	}
	public GLBackground getOuterBackground(){
		return outerBackground;
	}
	public GLBackground getInnerBackground(){
		return innerBackground;
	}
	public GLBorder getBorder(){
		return border;
	}
	public boolean isOpaque(){
		return isOpaque;
	}
	
	//HOVER
	public GLColor getTextColor_hover(){
		return hover_textColor;
	}
	public GLFont getTextFont_hover(){
		return hover_textFont;
	}
	public GLBackground getOuterBackground_hover(){
		return hover_outerBackground;
	}
	public GLBackground getInnerBackground_hover(){
		return hover_innerBackground;
	}
	public GLBorder getBorder_hover(){
		return hover_border;
	}
	public boolean isOpaque_hover(){
		return hover_isOpaque;
	}
	
	//CLICK
	public GLColor getTextColor_click(){
		return click_textColor;
	}
	public GLFont getTextFont_click(){
		return click_textFont;
	}
	public GLBackground getOuterBackground_click(){
		return click_outerBackground;
	}
	public GLBackground getInnerBackground_click(){
		return click_innerBackground;
	}
	public GLBorder getBorder_click(){
		return click_border;
	}
	public boolean isOpaque_click(){
		return click_isOpaque;
	}
	
	//FOCUS
	public GLColor getTextColor_focus(){
		return focus_textColor;
	}
	public GLFont getTextFont_focus(){
		return focus_textFont;
	}
	public GLBackground getOuterBackground_focus(){
		return focus_outerBackground;
	}
	public GLBackground getInnerBackground_focus(){
		return focus_innerBackground;
	}
	public GLBorder getBorder_focus(){
		return focus_border;
	}
	public boolean isOpaque_focus(){
		return focus_isOpaque;
	}
	
	public GLAttrib(Setting setting){
		insets = setting.getInt("insets");
		selection = new GLInvert(0, 0, 0, 0, readColorRGB(setting.getSubSetting("selection")));
		
		isOpaque = setting.getBoolean("background.use");
		innerBackground = readBackground(setting.getSubSetting("background-inner"));
		outerBackground = readBackground(setting.getSubSetting("background-outer"));
		textColor = readColorRGBA(setting.getSubSetting("text.color"));
		textFont = readFont(setting.getSubSetting("text"));
		border = readBorder(setting.getSubSetting("border"));
		
		hover_isOpaque = setting.getBoolean("hover.background.use");
		hover_innerBackground = readBackground(setting.getSubSetting("hover.background-inner"));
		hover_outerBackground = readBackground(setting.getSubSetting("hover.background-outer"));
		hover_textColor = readColorRGBA(setting.getSubSetting("hover.text.color"));
		hover_textFont = readFont(setting.getSubSetting("hover.text"));
		hover_border = readBorder(setting.getSubSetting("hover.border"));
		
		click_isOpaque = setting.getBoolean("click.background.use");
		click_innerBackground = readBackground(setting.getSubSetting("click.background-inner"));
		click_outerBackground = readBackground(setting.getSubSetting("click.background-outer"));
		click_textColor = readColorRGBA(setting.getSubSetting("click.text.color"));
		click_textFont = readFont(setting.getSubSetting("click.text"));
		click_border = readBorder(setting.getSubSetting("click.border"));
		
		focus_isOpaque = setting.getBoolean("focus.background.use");
		focus_innerBackground = readBackground(setting.getSubSetting("focus.background-inner"));
		focus_outerBackground = readBackground(setting.getSubSetting("focus.background-outer"));
		focus_textColor = readColorRGBA(setting.getSubSetting("focus.text.color"));
		focus_textFont = readFont(setting.getSubSetting("focus.text"));
		focus_border = readBorder(setting.getSubSetting("focus.border"));
	}
	
	private GLColor readColorRGB(Setting setting){
		return new GLColor(
				(float)setting.getDouble("r"),
				(float)setting.getDouble("g"),
				(float)setting.getDouble("b"));
	}
	
	private GLColor readColorRGBA(Setting setting){
		return new GLColor(
				(float)setting.getDouble("r"),
				(float)setting.getDouble("g"),
				(float)setting.getDouble("b"),
				(float)setting.getDouble("a"));
	}
	private GLBackground readBackground(Setting setting){
		String style = setting.getString("style");
		GLColor color = readColorRGBA(setting.getSubSetting("color"));
		
		if(style.equalsIgnoreCase("plain")){
			return new GLBackground(0, 0, 0, 0, color);
		}else if(style.equalsIgnoreCase("curved")){
			return new GLCurvedBackground(color);
		}
		return null;
	}
	
	private GLFont readFont(Setting setting){
		String style = setting.getString("style");
		String fontName = setting.getString("font");
		int styleID = java.awt.Font.PLAIN;
		
		if(style.equalsIgnoreCase("plain")){
			styleID = java.awt.Font.PLAIN;
		}else if(style.equalsIgnoreCase("bold")){
			styleID = java.awt.Font.BOLD;
		}else if(style.equalsIgnoreCase("italic")){
			styleID = java.awt.Font.ITALIC;
		}else if(style.equalsIgnoreCase("italic-bold")){
			styleID = java.awt.Font.ITALIC | java.awt.Font.BOLD;
		}else if(style.equalsIgnoreCase("bold-italic")){
			styleID = java.awt.Font.ITALIC | java.awt.Font.BOLD;
		}
		
		if(fontName.equalsIgnoreCase("pre-dialog")){
			fontName = java.awt.Font.DIALOG;
		}else if(fontName.equalsIgnoreCase("pre-serif")){
			fontName = java.awt.Font.SERIF;
		}else if(fontName.equalsIgnoreCase("pre-sansserif")){
			fontName = java.awt.Font.SANS_SERIF;
		}else if(fontName.equalsIgnoreCase("pre-monospaced")){
			fontName = java.awt.Font.MONOSPACED;
		}else if(fontName.equalsIgnoreCase("pre-dialoginput")){
			fontName = java.awt.Font.DIALOG_INPUT;
		}
		
		
		return GLFont.createFont(fontName, styleID, setting.getInt("size"));
	}
	private GLBorder readBorder(Setting setting){
		String type = setting.getString("type");
		int thickness = setting.getInt("thickness");
		
		if(type.equalsIgnoreCase("empty")){
			GLEmptyBorder border = new GLEmptyBorder();
			border.setThickness(thickness);
			return border;
		}else if(type.equalsIgnoreCase("line")){
			GLLineBorder border = new GLLineBorder();
			border.setThickness(thickness);
			border.setColor(readColorRGBA(setting.getSubSetting("color")));
			return border;
		}
		
		return null;
	}

}

