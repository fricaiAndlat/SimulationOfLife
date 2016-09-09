package de.diavololoop.gl.gui;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.nio.FloatBuffer;

import de.diavololoop.gl.GLColor;
import de.diavololoop.gl.GLTexture;
import de.diavololoop.gl.decorations.GLBackground;
import de.diavololoop.gl.decorations.GLInvert;
import de.diavololoop.gl.decorations.border.GLBorder;
import de.diavololoop.gl.text.GLFont;

public abstract class GLComponent {
	
	public final static int TOP = 0;
	public final static int BOTTOM = 1;
	public final static int CENTER = 2;
	public final static int LEFT = 3;
	public final static int RIGHT = 4;
	
	
	protected int screenWidth;
	protected int screenHeight;
	
	//the actually drawing rectangle
	protected int width;
	protected int height;
	protected int x;
	protected int y;
	
	//the outer  coordinates. 
	protected int componentX;
	protected int componentY;
	
	//maximum size. can be ignored by the container
	protected int outerWidth;
	protected int outerHeight;
	
	//maximum size. can be ignored by the container
	protected int preferedWidth;
	protected int preferedHeight;
	
	protected boolean expandVertical;
	protected boolean expandHorizontal;
	
	
	protected int xAlign = GLComponent.CENTER;
	protected int yAlign = GLComponent.CENTER;
	
	
	protected GLAttrib attrib;
	
	protected GLComponent parent;
	
	protected int elementID = -1;

	protected GLInvert selection;
	private int insets;
	
	private GLBackground outerBackground;
	protected GLBackground innerBackground;
	private GLBorder border;
	public boolean isOpaque;
	
	protected boolean hasFocus;
	protected boolean hasHover;
	protected boolean hasClick;
	
	protected boolean focusable;
	
	public GLComponent(int preferedWidth, int preferedHeight){
		this.preferedWidth = preferedWidth;
		this.preferedHeight = preferedHeight;
	}
	
	public void setAttrib(GLAttrib attrib){
		this.attrib = attrib;
		
		insets = attrib.getInsets();
		selection = attrib.getSelection();
		
		setNormal();
	}
	
	public void setParent(GLComponent comp){
		parent = comp;
	}
	public void recalcAll(){
		if(parent == null){
			reCalculatePosition();
		}else{
			parent.recalcAll();
		}
	}
	
	public int getInsets(){
		return insets;
	}
	
	public GLBorder getBorder(){
		return border;
	}
	
	public GLFont getTextFont(){
		return attrib.getTextFont();
	}
	public GLFont getTextFont_hover(){
		return attrib.getTextFont_hover();
	}
	public GLFont getTextFont_click(){
		return attrib.getTextFont_click();
	}
	public GLFont getTextFont_focus(){
		return attrib.getTextFont_focus();
	}
	
	public GLColor getTextColor(){
		return attrib.getTextColor();
	}
	public GLColor getTextColor_hover(){
		return attrib.getTextColor_hover();
	}
	public GLColor getTextColor_click(){
		return attrib.getTextColor_click();
	}
	public GLColor getTextColor_focus(){
		return attrib.getTextColor_focus();
	}
	
	public void setAlignment(int xAlign, int yAlign){
		if(xAlign == TOP || xAlign == BOTTOM || yAlign == LEFT || yAlign == RIGHT){
			throw new IllegalArgumentException("illegal alignment");
		}
		
		this.xAlign = xAlign;
		this.yAlign = yAlign;
	}
	
	public void setExpand(boolean expandHori, boolean expandVert){
		this.expandHorizontal = expandHori;
		this.expandVertical = expandVert;
		recalcAll();
	}
	public void setPosition(int x, int y){
		this.componentX = x;
		this.componentY = y;
		reCalculatePosition();
	}
	public void setSize(int width, int height){
		this.outerWidth = width;
		this.outerHeight = height;
		reCalculatePosition();
	}
	
	public void setOpaque(boolean opaque){
		this.isOpaque = opaque;
	}
	
	public void setBackgroundColor(GLColor outer, GLColor inner){
		outerBackground.setColor(outer);
		innerBackground.setColor(inner);
	}
	
	public int getPreferedWidth(){
		return preferedWidth;
	}
	public int getPreferedHeight(){
		return preferedHeight;
	}

	protected void reCalculatePosition(){
		int offsetX = 0;
		int offsetY = 0;
		
		width = outerWidth;
		height = outerHeight;
		
		if(!expandHorizontal){
			width  = getPreferedWidth();
			
			if(xAlign == LEFT){
				offsetX = 0;
			}else if(xAlign == CENTER){
				offsetX = (outerWidth - width) / 2;
			}else if(xAlign == CENTER){
				offsetX = outerWidth - width;
			}
		}
		if(!expandVertical){
			height = getPreferedHeight();
			
			if(yAlign == TOP){
				offsetY = outerHeight - height;
			}else if(yAlign == CENTER){
				offsetY = (outerHeight - height) / 2;
			}else if(yAlign == BOTTOM){
				offsetY = 0;
			}
		}
		
		x = componentX + offsetX;
		y = componentY + offsetY;
		
		if(innerBackground != null){
			attrib.getInnerBackground().setPosition(x, y);
			attrib.getInnerBackground().setSize(width, height);
			
			attrib.getInnerBackground_focus().setPosition(x, y);
			attrib.getInnerBackground_focus().setSize(width, height);
			
			attrib.getInnerBackground_click().setPosition(x, y);
			attrib.getInnerBackground_click().setSize(width, height);
			
			attrib.getInnerBackground_hover().setPosition(x, y);
			attrib.getInnerBackground_hover().setSize(width, height);
		}
		
		if(outerBackground != null){
			attrib.getOuterBackground().setPosition(componentX, componentY);
			attrib.getOuterBackground().setSize(outerWidth, outerHeight);
			
			attrib.getOuterBackground_focus().setPosition(componentX, componentY);
			attrib.getOuterBackground_focus().setSize(outerWidth, outerHeight);
			
			attrib.getOuterBackground_click().setPosition(componentX, componentY);
			attrib.getOuterBackground_click().setSize(outerWidth, outerHeight);
			
			attrib.getOuterBackground_hover().setPosition(componentX, componentY);
			attrib.getOuterBackground_hover().setSize(outerWidth, outerHeight);
		}

		if(border != null){
			attrib.getBorder().setRectangle(x, y, width, height);
			attrib.getBorder_focus().setRectangle(x, y, width, height);
			attrib.getBorder_click().setRectangle(x, y, width, height);
			attrib.getBorder_hover().setRectangle(x, y, width, height);
		}
	}
	

	
	public void setScreenSize(int screenWidth, int screenHeight){
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		reCalculatePosition();
	}


	
	public void onMouseEnter(){
		hasHover = true;
		setHovered();
	}
	public void onMouseLeave(){	
		hasHover = false;
		if(hasFocus){
			setFocused();
		}else{
			setNormal();
		}
	}
	public void onMousePressed(int x, int y){
		hasClick = true;
		setClicked();
	}
	public void onMouseReleased(){	
		hasClick = false;
		if(hasFocus && focusable){
			setFocused();
			hasFocus = true;
		}else{
			setHovered();
			hasHover = true;
		}
	}
	public void onFocusLost(){
		hasFocus = false;
		setNormal();
	}
	public void onFocusGet(){
		if(!focusable){
			return;
		}
		hasFocus = true;
		setFocused();
	}
	public void onMouseMove(int x, int y){
	}
	public void onKeyPressed(int key, boolean isShiftDown, boolean isControlDown, boolean isAltDown){
	}
	public void onKeyReleased(int key, boolean isShiftDown, boolean isControlDown, boolean isAltDown){
	}
	public void onKeyPressed(char c){
	}
	
	private void setHovered(){
		if(attrib==null){
			return;
		}
		outerBackground = attrib.getOuterBackground_hover();
		innerBackground = attrib.getInnerBackground_hover();
		border = attrib.getBorder_hover();
		isOpaque = attrib.isOpaque_hover();
	}
	private void setClicked(){
		if(attrib==null){
			return;
		}
		outerBackground = attrib.getOuterBackground_click();
		innerBackground = attrib.getInnerBackground_click();
		border = attrib.getBorder_click();
		isOpaque = attrib.isOpaque_click();
	}
	private void setFocused(){
		if(attrib==null){
			return;
		}
		outerBackground = attrib.getOuterBackground_focus();
		innerBackground = attrib.getInnerBackground_focus();
		border = attrib.getBorder_focus();
		isOpaque = attrib.isOpaque_focus();
	}
	private void setNormal(){
		if(attrib==null){
			return;
		}
		outerBackground = attrib.getOuterBackground();
		innerBackground = attrib.getInnerBackground();
		border = attrib.getBorder();
		isOpaque = attrib.isOpaque();
	}
	
	
	public void paint(FloatBuffer buffer, GLTexture texture){
		
	}
	
	public int setElementID(int startID){
		elementID = -1;
		return 0;
	}
	
	public int getElementIDFromPosition(int posx, int posy){
		if(posx<x || posx>=x+width || posy<y || posy>=y+height){
			return -1;
		}
		return elementID;
	}
	
	public GLComponent getComponentFromPosition(int posx, int posy){
		if(posx<x || posx>=x+width || posy<y || posy>=y+height){
			return null;
		}
		return this;
	}
	
	public int getChildLength(){
		return 0;
	}
	
	public void drawExtra(){
		if(isOpaque){
			outerBackground.renderBackground(screenWidth, screenHeight);
			innerBackground.renderBackground(screenWidth, screenHeight);
		}
		if(border != null){
			border.renderBorder(screenWidth, screenHeight);
		}
	}
	
	
	public static void storeStringInClipboard(String text){
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
	}
	
	public static String restoreStringFromClipboard(){
		try {
			return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		} catch (HeadlessException | UnsupportedFlavorException | IOException e) {
			return "";
		}
	}

}
