package de.diavololoop.gl;

import java.io.File;
import java.io.IOException;

import de.diavololoop.util.yml.Setting;

public class GLLookAndFeel {
	
	private static GLLookAndFeel CURRENT;
	
	private Setting settings;
	
	static{
		File f = new File("res/LaF.yml");
		if(f.exists() && f.isFile()){
			try {
				
				Setting setting = Setting.read(f);
				new GLLookAndFeel(setting).setCurrent();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
						
		}
		
		if(CURRENT == null){
			System.out.println("created new LaF file");
			createBasicLaF().setCurrent();
			try {
				CURRENT.settings.write(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		
	}
	
	public GLLookAndFeel(Setting settings){
		this.settings = settings;
	}
	
	public void setCurrent(){
		CURRENT = this;
	}
	public static Setting getCurrent(){
		return CURRENT.settings;
	}
	
	public static double getDouble(String path){
		return CURRENT.settings.getDouble(path);
	}
	public static int getInt(String path){
		return CURRENT.settings.getInt(path);
	}
	
	public static double[] getDoubleArray(String path){
		return CURRENT.settings.getDoubleArray(path);
	}
	
	public static String getString(String path){
		return CURRENT.settings.getString(path);		
	}
	
	public static Boolean getBoolean(String path){
		return CURRENT.settings.getBoolean(path);		
	}
	
	public static GLLookAndFeel createBasicLaF(){
		Setting setting = new Setting();
		
		setting.createInt("version", 1);

		/*
		 * LABEL START 
		 */
		
		setting.createInt("label.insets", 2);
		
		setting.createDouble("label.selection.r", 0.2);
		setting.createDouble("label.selection.g", 0.6);
		setting.createDouble("label.selection.b", 1.0);
		
		setting.createBoolean("label.background.use", false);
		
		setting.createString("label.background-inner.style", "plain");
		setting.createDouble("label.background-inner.color.r", 0.93);
		setting.createDouble("label.background-inner.color.g", 0.93);
		setting.createDouble("label.background-inner.color.b", 0.93);
		setting.createDouble("label.background-inner.color.a", 1);
		
		setting.createString("label.background-outer.style", "plain");
		setting.createDouble("label.background-outer.color.r", 1);
		setting.createDouble("label.background-outer.color.g", 1);
		setting.createDouble("label.background-outer.color.b", 1);
		setting.createDouble("label.background-outer.color.a", 0);
		
		setting.createString("label.text.font", "OpenSans-Regular");
		setting.createInt("label.text.size", 15);
		setting.createDouble("label.text.color.r", 0.2);
		setting.createDouble("label.text.color.g", 0.2);
		setting.createDouble("label.text.color.b", 0.2);
		setting.createDouble("label.text.color.a", 1);
		
		setting.createString("label.border.type", "empty");
		setting.createInt("label.border.thickness", 0);
		
		//HOVER
		
		setting.createBoolean("label.hover.background.use", false);
		
		setting.createString("label.hover.background-inner.style", "plain");
		setting.createDouble("label.hover.background-inner.color.r", 0.93);
		setting.createDouble("label.hover.background-inner.color.g", 0.93);
		setting.createDouble("label.hover.background-inner.color.b", 0.93);
		setting.createDouble("label.hover.background-inner.color.a", 1);
		
		setting.createString("label.hover.background-outer.style", "plain");
		setting.createDouble("label.hover.background-outer.color.r", 1);
		setting.createDouble("label.hover.background-outer.color.g", 1);
		setting.createDouble("label.hover.background-outer.color.b", 1);
		setting.createDouble("label.hover.background-outer.color.a", 0);
		
		setting.createString("label.hover.text.font", "OpenSans-Regular");
		setting.createInt("label.hover.text.size", 15);
		setting.createDouble("label.hover.text.color.r", 0.2);
		setting.createDouble("label.hover.text.color.g", 0.2);
		setting.createDouble("label.hover.text.color.b", 0.2);
		setting.createDouble("label.hover.text.color.a", 1);
		
		setting.createString("label.hover.border.type", "empty");
		setting.createInt("label.hover.border.thickness", 0);
		
		//CLICK
		
		setting.createBoolean("label.click.background.use", false);
		
		setting.createString("label.click.background-inner.style", "plain");
		setting.createDouble("label.click.background-inner.color.r", 0.93);
		setting.createDouble("label.click.background-inner.color.g", 0.93);
		setting.createDouble("label.click.background-inner.color.b", 0.93);
		setting.createDouble("label.click.background-inner.color.a", 1);
		
		setting.createString("label.click.background-outer.style", "plain");
		setting.createDouble("label.click.background-outer.color.r", 1);
		setting.createDouble("label.click.background-outer.color.g", 1);
		setting.createDouble("label.click.background-outer.color.b", 1);
		setting.createDouble("label.click.background-outer.color.a", 0);
		
		setting.createString("label.click.text.font", "OpenSans-Regular");
		setting.createInt("label.click.text.size", 15);
		setting.createDouble("label.click.text.color.r", 0.2);
		setting.createDouble("label.click.text.color.g", 0.2);
		setting.createDouble("label.click.text.color.b", 0.2);
		setting.createDouble("label.click.text.color.a", 1);
		
		setting.createString("label.click.border.type", "empty");
		setting.createInt("label.click.border.thickness", 0);
		
		// focus
		
		setting.createBoolean("label.focus.background.use", false);
		
		setting.createString("label.focus.background-inner.style", "plain");
		setting.createDouble("label.focus.background-inner.color.r", 0.93);
		setting.createDouble("label.focus.background-inner.color.g", 0.93);
		setting.createDouble("label.focus.background-inner.color.b", 0.93);
		setting.createDouble("label.focus.background-inner.color.a", 1);
		
		setting.createString("label.focus.background-outer.style", "plain");
		setting.createDouble("label.focus.background-outer.color.r", 1);
		setting.createDouble("label.focus.background-outer.color.g", 1);
		setting.createDouble("label.focus.background-outer.color.b", 1);
		setting.createDouble("label.focus.background-outer.color.a", 0);
		
		setting.createString("label.focus.text.font", "OpenSans-Regular");
		setting.createInt("label.focus.text.size", 15);
		setting.createDouble("label.focus.text.color.r", 0.2);
		setting.createDouble("label.focus.text.color.g", 0.2);
		setting.createDouble("label.focus.text.color.b", 0.2);
		setting.createDouble("label.focus.text.color.a", 1);
		
		setting.createString("label.focus.border.type", "empty");
		setting.createInt("label.focus.border.thickness", 0);
		
		/*
		 * PANEL START 
		 */
		
		setting.createInt("panel.insets", 2);
		
		setting.createDouble("panel.selection.r", 0.2);
		setting.createDouble("panel.selection.g", 0.6);
		setting.createDouble("panel.selection.b", 1.0);
		
		setting.createBoolean("panel.background.use", true);
		
		setting.createString("panel.background-inner.style", "plain");
		setting.createDouble("panel.background-inner.color.r", 0.93);
		setting.createDouble("panel.background-inner.color.g", 0.93);
		setting.createDouble("panel.background-inner.color.b", 0.93);
		setting.createDouble("panel.background-inner.color.a", 1);
		
		setting.createString("panel.background-outer.style", "plain");
		setting.createDouble("panel.background-outer.color.r", 0.93);
		setting.createDouble("panel.background-outer.color.g", 0.93);
		setting.createDouble("panel.background-outer.color.b", 0.93);
		setting.createDouble("panel.background-outer.color.a", 1);
		
		setting.createString("panel.text.font", "OpenSans-Regular");
		setting.createInt("panel.text.size", 15);
		setting.createDouble("panel.text.color.r", 0.2);
		setting.createDouble("panel.text.color.g", 0.2);
		setting.createDouble("panel.text.color.b", 0.2);
		setting.createDouble("panel.text.color.a", 1);
		
		setting.createString("panel.border.type", "empty");
		setting.createInt("panel.border.thickness", 0);
		
		//HOVER
		
		setting.createBoolean("panel.hover.background.use", true);
		
		setting.createString("panel.hover.background-inner.style", "plain");
		setting.createDouble("panel.hover.background-inner.color.r", 0.93);
		setting.createDouble("panel.hover.background-inner.color.g", 0.93);
		setting.createDouble("panel.hover.background-inner.color.b", 0.93);
		setting.createDouble("panel.hover.background-inner.color.a", 1);
		
		setting.createString("panel.hover.background-outer.style", "plain");
		setting.createDouble("panel.hover.background-outer.color.r", 0.93);
		setting.createDouble("panel.hover.background-outer.color.g", 0.93);
		setting.createDouble("panel.hover.background-outer.color.b", 0.93);
		setting.createDouble("panel.hover.background-outer.color.a", 1);
		
		setting.createString("panel.hover.text.font", "OpenSans-Regular");
		setting.createInt("panel.hover.text.size", 15);
		setting.createDouble("panel.hover.text.color.r", 0.2);
		setting.createDouble("panel.hover.text.color.g", 0.2);
		setting.createDouble("panel.hover.text.color.b", 0.2);
		setting.createDouble("panel.hover.text.color.a", 1);
		
		setting.createString("panel.hover.border.type", "empty");
		setting.createInt("panel.hover.border.thickness", 0);
		
		//CLICK
		
		setting.createBoolean("panel.click.background.use", true);
		
		setting.createString("panel.click.background-inner.style", "plain");
		setting.createDouble("panel.click.background-inner.color.r", 0.93);
		setting.createDouble("panel.click.background-inner.color.g", 0.93);
		setting.createDouble("panel.click.background-inner.color.b", 0.93);
		setting.createDouble("panel.click.background-inner.color.a", 1);
		
		setting.createString("panel.click.background-outer.style", "plain");
		setting.createDouble("panel.click.background-outer.color.r", 0.93);
		setting.createDouble("panel.click.background-outer.color.g", 0.93);
		setting.createDouble("panel.click.background-outer.color.b", 0.93);
		setting.createDouble("panel.click.background-outer.color.a", 1);
		
		setting.createString("panel.click.text.font", "OpenSans-Regular");
		setting.createInt("panel.click.text.size", 15);
		setting.createDouble("panel.click.text.color.r", 0.2);
		setting.createDouble("panel.click.text.color.g", 0.2);
		setting.createDouble("panel.click.text.color.b", 0.2);
		setting.createDouble("panel.click.text.color.a", 1);
		
		setting.createString("panel.click.border.type", "empty");
		setting.createInt("panel.click.border.thickness", 0);
		
		// focus
		
		setting.createBoolean("panel.focus.background.use", true);
		
		setting.createString("panel.focus.background-inner.style", "plain");
		setting.createDouble("panel.focus.background-inner.color.r", 0.93);
		setting.createDouble("panel.focus.background-inner.color.g", 0.93);
		setting.createDouble("panel.focus.background-inner.color.b", 0.93);
		setting.createDouble("panel.focus.background-inner.color.a", 1);
		
		setting.createString("panel.focus.background-outer.style", "plain");
		setting.createDouble("panel.focus.background-outer.color.r", 0.93);
		setting.createDouble("panel.focus.background-outer.color.g", 0.93);
		setting.createDouble("panel.focus.background-outer.color.b", 0.93);
		setting.createDouble("panel.focus.background-outer.color.a", 1);
		
		setting.createString("panel.focus.text.font", "OpenSans-Regular");
		setting.createInt("panel.focus.text.size", 15);
		setting.createDouble("panel.focus.text.color.r", 0.2);
		setting.createDouble("panel.focus.text.color.g", 0.2);
		setting.createDouble("panel.focus.text.color.b", 0.2);
		setting.createDouble("panel.focus.text.color.a", 1);
		
		setting.createString("panel.focus.border.type", "empty");
		setting.createInt("panel.focus.border.thickness", 0);
		
		/*
		 * BUTTON START 
		 */
		
		setting.createInt("button.insets", 10);
		
		setting.createDouble("button.selection.r", 0.2);
		setting.createDouble("button.selection.g", 0.6);
		setting.createDouble("button.selection.b", 1.0);
		
		setting.createBoolean("button.background.use", true);
		
		setting.createString("button.background-inner.style", "curved");
		setting.createDouble("button.background-inner.color.r", 0.72);
		setting.createDouble("button.background-inner.color.g", 0.8125);
		setting.createDouble("button.background-inner.color.b", 0.895);
		setting.createDouble("button.background-inner.color.a", 1);
		
		setting.createString("button.background-outer.style", "plain");
		setting.createDouble("button.background-outer.color.r", 1);
		setting.createDouble("button.background-outer.color.g", 1);
		setting.createDouble("button.background-outer.color.b", 1);
		setting.createDouble("button.background-outer.color.a", 0);
		
		setting.createString("button.text.font", "OpenSans-Regular");
		setting.createString("button.text.style", "plain");
		setting.createInt("button.text.size", 18);
		setting.createDouble("button.text.color.r", 0.2);
		setting.createDouble("button.text.color.g", 0.2);
		setting.createDouble("button.text.color.b", 0.2);
		setting.createDouble("button.text.color.a", 1);
		
		setting.createString("button.border.type", "line");
		setting.createInt("button.border.thickness", 1);
		setting.createDouble("button.border.color.r", 0.477);
		setting.createDouble("button.border.color.g", 0.54);
		setting.createDouble("button.border.color.b", 0.6);
		setting.createDouble("button.border.color.a", 1);
		
		//HOVER
		
		setting.createBoolean("button.hover.background.use", true);
		
		setting.createString("button.hover.background-inner.style", "curved");
		setting.createDouble("button.hover.background-inner.color.r", 0.72);
		setting.createDouble("button.hover.background-inner.color.g", 0.8125);
		setting.createDouble("button.hover.background-inner.color.b", 0.895);
		setting.createDouble("button.hover.background-inner.color.a", 1);
		
		setting.createString("button.hover.background-outer.style", "plain");
		setting.createDouble("button.hover.background-outer.color.r", 1);
		setting.createDouble("button.hover.background-outer.color.g", 1);
		setting.createDouble("button.hover.background-outer.color.b", 1);
		setting.createDouble("button.hover.background-outer.color.a", 0);
		
		setting.createString("button.hover.text.font", "OpenSans-Regular");
		setting.createString("button.hover.text.style", "bold");
		setting.createInt("button.hover.text.size", 18);
		setting.createDouble("button.hover.text.color.r", 0.2);
		setting.createDouble("button.hover.text.color.g", 0.2);
		setting.createDouble("button.hover.text.color.b", 0.2);
		setting.createDouble("button.hover.text.color.a", 1);
		
		setting.createString("button.hover.border.type", "line");
		setting.createInt("button.hover.border.thickness", 3);
		setting.createDouble("button.hover.border.color.r", 0.477);
		setting.createDouble("button.hover.border.color.g", 0.54);
		setting.createDouble("button.hover.border.color.b", 0.6);
		setting.createDouble("button.hover.border.color.a", 1);
		
		//CLICK
		
		setting.createBoolean("button.click.background.use", true);
		
		setting.createString("button.click.background-inner.style", "plain");
		setting.createDouble("button.click.background-inner.color.r", 0.72);
		setting.createDouble("button.click.background-inner.color.g", 0.8125);
		setting.createDouble("button.click.background-inner.color.b", 0.895);
		setting.createDouble("button.click.background-inner.color.a", 1);
		
		setting.createString("button.click.background-outer.style", "plain");
		setting.createDouble("button.click.background-outer.color.r", 1);
		setting.createDouble("button.click.background-outer.color.g", 1);
		setting.createDouble("button.click.background-outer.color.b", 1);
		setting.createDouble("button.click.background-outer.color.a", 0);
		
		setting.createString("button.click.text.font", "OpenSans-Regular");
		setting.createString("button.click.text.style", "plain");
		setting.createInt("button.click.text.size", 18);
		setting.createDouble("button.click.text.color.r", 0.2);
		setting.createDouble("button.click.text.color.g", 0.2);
		setting.createDouble("button.click.text.color.b", 0.2);
		setting.createDouble("button.click.text.color.a", 1);
		
		setting.createString("button.click.border.type", "line");
		setting.createInt("button.click.border.thickness", 3);
		setting.createDouble("button.click.border.color.r", 0.477);
		setting.createDouble("button.click.border.color.g", 0.54);
		setting.createDouble("button.click.border.color.b", 0.6);
		setting.createDouble("button.click.border.color.a", 1);
		
		// focus
		
		setting.createBoolean("button.focus.background.use", true);
		
		setting.createString("button.focus.background-inner.style", "plain");
		setting.createDouble("button.focus.background-inner.color.r", 0.72);
		setting.createDouble("button.focus.background-inner.color.g", 0.8125);
		setting.createDouble("button.focus.background-inner.color.b", 0.895);
		setting.createDouble("button.focus.background-inner.color.a", 1);
		
		setting.createString("button.focus.background-outer.style", "plain");
		setting.createDouble("button.focus.background-outer.color.r", 1);
		setting.createDouble("button.focus.background-outer.color.g", 1);
		setting.createDouble("button.focus.background-outer.color.b", 1);
		setting.createDouble("button.focus.background-outer.color.a", 0);
		
		setting.createString("button.focus.text.font", "OpenSans-Bold");
		setting.createString("button.focus.text.style", "bold");
		setting.createInt("button.focus.text.size", 18);
		setting.createDouble("button.focus.text.color.r", 0.2);
		setting.createDouble("button.focus.text.color.g", 0.2);
		setting.createDouble("button.focus.text.color.b", 0.2);
		setting.createDouble("button.focus.text.color.a", 1);
		
		setting.createString("button.focus.border.type", "line");
		setting.createInt("button.focus.border.thickness", 3);
		setting.createDouble("button.focus.border.color.r", 0.477);
		setting.createDouble("button.focus.border.color.g", 0.54);
		setting.createDouble("button.focus.border.color.b", 0.6);
		setting.createDouble("button.focus.border.color.a", 1);
		
		/*
		 * TEXTFIELD START 
		 */
		
		setting.createInt("textfield.insets", 4);
		
		setting.createDouble("textfield.selection.r", 0.2);
		setting.createDouble("textfield.selection.g", 0.6);
		setting.createDouble("textfield.selection.b", 1.0);
		
		setting.createBoolean("textfield.background.use", true);
		
		setting.createString("textfield.background-inner.style", "plain");
		setting.createDouble("textfield.background-inner.color.r", 1);
		setting.createDouble("textfield.background-inner.color.g", 1);
		setting.createDouble("textfield.background-inner.color.b", 1);
		setting.createDouble("textfield.background-inner.color.a", 1);
		
		setting.createString("textfield.background-outer.style", "plain");
		setting.createDouble("textfield.background-outer.color.r", 1);
		setting.createDouble("textfield.background-outer.color.g", 1);
		setting.createDouble("textfield.background-outer.color.b", 1);
		setting.createDouble("textfield.background-outer.color.a", 0);
		
		setting.createString("textfield.text.font", "OpenSans-Regular");
		setting.createInt("textfield.text.size", 15);
		setting.createDouble("textfield.text.color.r", 0.2);
		setting.createDouble("textfield.text.color.g", 0.2);
		setting.createDouble("textfield.text.color.b", 0.2);
		setting.createDouble("textfield.text.color.a", 1);
		
		setting.createString("textfield.border.type", "line");
		setting.createInt("textfield.border.thickness", 1);
		setting.createDouble("textfield.border.color.r", 0.477);
		setting.createDouble("textfield.border.color.g", 0.54);
		setting.createDouble("textfield.border.color.b", 0.6);
		setting.createDouble("textfield.border.color.a", 1);
		
		//HOVER
		
		setting.createBoolean("textfield.hover.background.use", true);
		
		setting.createString("textfield.hover.background-inner.style", "plain");
		setting.createDouble("textfield.hover.background-inner.color.r", 1);
		setting.createDouble("textfield.hover.background-inner.color.g", 1);
		setting.createDouble("textfield.hover.background-inner.color.b", 1);
		setting.createDouble("textfield.hover.background-inner.color.a", 1);
		
		setting.createString("textfield.hover.background-outer.style", "plain");
		setting.createDouble("textfield.hover.background-outer.color.r", 1);
		setting.createDouble("textfield.hover.background-outer.color.g", 1);
		setting.createDouble("textfield.hover.background-outer.color.b", 1);
		setting.createDouble("textfield.hover.background-outer.color.a", 0);
		
		setting.createString("textfield.hover.text.font", "OpenSans-Regular");
		setting.createInt("textfield.hover.text.size", 15);
		setting.createDouble("textfield.hover.text.color.r", 0.2);
		setting.createDouble("textfield.hover.text.color.g", 0.2);
		setting.createDouble("textfield.hover.text.color.b", 0.2);
		setting.createDouble("textfield.hover.text.color.a", 1);
		
		setting.createString("textfield.hover.border.type", "line");
		setting.createInt("textfield.hover.border.thickness", 1);
		setting.createDouble("textfield.hover.border.color.r", 0.477);
		setting.createDouble("textfield.hover.border.color.g", 0.54);
		setting.createDouble("textfield.hover.border.color.b", 0.6);
		setting.createDouble("textfield.hover.border.color.a", 1);
		
		//CLICK
		
		setting.createBoolean("textfield.click.background.use", true);
		
		setting.createString("textfield.click.background-inner.style", "plain");
		setting.createDouble("textfield.click.background-inner.color.r", 1);
		setting.createDouble("textfield.click.background-inner.color.g", 1);
		setting.createDouble("textfield.click.background-inner.color.b", 1);
		setting.createDouble("textfield.click.background-inner.color.a", 1);
		
		setting.createString("textfield.click.background-outer.style", "plain");
		setting.createDouble("textfield.click.background-outer.color.r", 1);
		setting.createDouble("textfield.click.background-outer.color.g", 1);
		setting.createDouble("textfield.click.background-outer.color.b", 1);
		setting.createDouble("textfield.click.background-outer.color.a", 0);
		
		setting.createString("textfield.click.text.font", "OpenSans-Regular");
		setting.createInt("textfield.click.text.size", 15);
		setting.createDouble("textfield.click.text.color.r", 0.2);
		setting.createDouble("textfield.click.text.color.g", 0.2);
		setting.createDouble("textfield.click.text.color.b", 0.2);
		setting.createDouble("textfield.click.text.color.a", 1);
		
		setting.createString("textfield.click.border.type", "line");
		setting.createInt("textfield.click.border.thickness", 1);
		setting.createDouble("textfield.click.border.color.r", 0.477);
		setting.createDouble("textfield.click.border.color.g", 0.54);
		setting.createDouble("textfield.click.border.color.b", 0.6);
		setting.createDouble("textfield.click.border.color.a", 1);
		
		// focus
		
		setting.createBoolean("textfield.focus.background.use", true);
		
		setting.createString("textfield.focus.background-inner.style", "plain");
		setting.createDouble("textfield.focus.background-inner.color.r", 1);
		setting.createDouble("textfield.focus.background-inner.color.g", 1);
		setting.createDouble("textfield.focus.background-inner.color.b", 1);
		setting.createDouble("textfield.focus.background-inner.color.a", 1);
		
		setting.createString("textfield.focus.background-outer.style", "plain");
		setting.createDouble("textfield.focus.background-outer.color.r", 1);
		setting.createDouble("textfield.focus.background-outer.color.g", 1);
		setting.createDouble("textfield.focus.background-outer.color.b", 1);
		setting.createDouble("textfield.focus.background-outer.color.a", 0);
		
		setting.createString("textfield.focus.text.font", "OpenSans-Regular");
		setting.createInt("textfield.focus.text.size", 15);
		setting.createDouble("textfield.focus.text.color.r", 0.2);
		setting.createDouble("textfield.focus.text.color.g", 0.2);
		setting.createDouble("textfield.focus.text.color.b", 0.2);
		setting.createDouble("textfield.focus.text.color.a", 1);
		
		setting.createString("textfield.focus.border.type", "line");
		setting.createInt("textfield.focus.border.thickness", 1);
		setting.createDouble("textfield.focus.border.color.r", 0.477);
		setting.createDouble("textfield.focus.border.color.g", 0.54);
		setting.createDouble("textfield.focus.border.color.b", 0.6);
		setting.createDouble("textfield.focus.border.color.a", 1);
		
		
		return new GLLookAndFeel(setting);
	}
	
	

}
