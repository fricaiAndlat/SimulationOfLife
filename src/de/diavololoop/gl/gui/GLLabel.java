package de.diavololoop.gl.gui;

import org.lwjgl.glfw.GLFW;

import de.diavololoop.gl.GLLookAndFeel;
import de.diavololoop.gl.text.GLText;

public class GLLabel extends GLComponent{
	
	protected String text;
	private GLText glText;
	private GLText glText_hover;
	private GLText glText_focus;
	private GLText glText_click;
	
	
	private boolean isSelectable = true;

	
	protected boolean isSelecting = false;
	protected boolean isSelected = false;
	private int startSelectionX;
	private int stopSelectionX;
	private String selectedText;
	protected int startSelectionIndex;
	protected int stopSelectionIndex;
	
	protected int textOffsetX;
	protected int textOffsetY;
	
	protected int textAlignX = LEFT;
	protected int textAlignY = CENTER;
	
	
	public GLLabel(String text){
		super(10, 10);
		
		this.setAttrib(new GLAttrib(GLLookAndFeel.getCurrent().getSubSetting("label")));		
		
		this.text = text;
		
		this.glText = new GLText(text, getTextFont());
		this.glText_hover = new GLText(text, getTextFont_hover());
		this.glText_click = new GLText(text, getTextFont_click());
		this.glText_focus = new GLText(text, getTextFont_focus());
		
		reCalculatePosition();
	
		//textOffsetX = getInsets();
		
		setExpand(true, false);
		
		recalcAll();
	}
	

	@Override
	public void onMousePressed(int x, int y){
		super.onMousePressed(x, y);
		if(!isSelectable){
			return;
		}
		isSelecting = true;
		startSelectionX = x;
		stopSelectionX = x;
		updateSelection();
	}
	
	@Override
	public void onMouseReleased(){
		super.onMouseReleased();
		isSelecting = false;
		if(isSelectable){
			isSelected = true;
		}
	}
	
	@Override
	public void onMouseMove(int x, int y){
		super.onMouseMove(x, y);
		if(!isSelectable){
			return;
		}
		if(isSelecting){
			stopSelectionX = x;
			updateSelection();
		}
	}

	@Override
	public void onFocusLost(){
		super.onFocusLost();
		isSelecting = false;
		isSelected = false;
	}
	
	@Override
	public void onKeyPressed(int key, boolean isShiftDown, boolean isControlDown, boolean isAltDown){
		super.onKeyPressed(key, isShiftDown, isControlDown, isAltDown);
		
		if(!isShiftDown && isControlDown && !isAltDown && key == GLFW.GLFW_KEY_C && isSelected){
			GLComponent.storeStringInClipboard(selectedText);
		}
	}
	
	protected void paintSelection(){
		if(startSelectionIndex > stopSelectionIndex){
			int temp = startSelectionIndex;
			startSelectionIndex = stopSelectionIndex;
			stopSelectionIndex = temp;
		}
		
		selectedText = text.substring(startSelectionIndex, stopSelectionIndex);
		
		int roundedStartSelectionX = getTextFont().getWidth(text.substring(0, startSelectionIndex));
		int roundedLength = getTextFont().getWidth(selectedText);
		
		
		
		selection.setPosition(roundedStartSelectionX + this.x +this.textOffsetX, this.y + textOffsetY - getTextFont().maxDescent);	
		selection.setSize(roundedLength, getTextFont().maxHeight);	
	}
	
	private void updateSelection() {
		startSelectionIndex = getTextPosition(startSelectionX);
		stopSelectionIndex = getTextPosition(stopSelectionX);
		
		paintSelection();
	}
	
	protected int getTextPosition(int x){
		int remaining = x - this.x - this.textOffsetX - 5;
		for(int i = 0; i < text.length(); ++i){
			if(remaining < 0){
				return i;
			}
			
			remaining -= getTextFont().getWidth(Character.toString(text.charAt(i)));
			
		}
		if(text.isEmpty()){
			return 0;
		}
		return text.length();
	}
	
	public void setText(String text){
		this.text = text;
		
		glText.free();
		glText = new GLText(text, getTextFont());
		
		glText_hover.free();
		glText_hover = new GLText(text, getTextFont_hover());
		
		glText_click.free();
		glText_click = new GLText(text, getTextFont_click());
		
		glText_focus.free();
		glText_focus = new GLText(text, getTextFont_focus());
		
		isSelected = false;
		isSelecting = false;
	}
	
	public String getText(){
		return text;
	}
	
	public String getSelected(){
		if(isSelected){
			return selectedText;
		}
		return "";
	}

	
	public void setSelectable(boolean selectable){
		this.isSelectable = selectable;
	}
	
	
	@Override
	public void drawExtra(){
		super.drawExtra();
		
		if(hasClick){
			glText_click.renderText(x+textOffsetX, y+textOffsetY, getTextColor_click(), screenWidth, screenHeight);
		}else if(hasFocus){
			glText_focus.renderText(x+textOffsetX, y+textOffsetY, getTextColor_focus(), screenWidth, screenHeight);
		}else if(hasHover){
			glText_hover.renderText(x+textOffsetX, y+textOffsetY, getTextColor_hover(), screenWidth, screenHeight);
		}else{
			glText.renderText(x+textOffsetX, y+textOffsetY, getTextColor(), screenWidth, screenHeight);
		}
		
		
		if(isSelecting || isSelected){
			selection.renderBackground(screenWidth, screenHeight);
		}
	}
	
	@Override
	public void reCalculatePosition(){
		super.reCalculatePosition();
		
		if(textAlignX == LEFT){
			textOffsetX = getInsets();
		}else if(textAlignX == CENTER){
			textOffsetX = getInsets() + (width-getTextFont().getWidth(text)) / 2;
		}else if(textAlignX == RIGHT){
			textOffsetX = width - getTextFont().getWidth(text) - getInsets();
		}
		
		if(textAlignY == TOP){
			textOffsetY = height - getTextFont().maxAscent;
		}else if(textAlignY == CENTER){
			textOffsetY = (height-getTextFont().maxAscent) / 2;
		}else if(textAlignY == BOTTOM){
			textOffsetY = 0;
		}
		
		this.preferedHeight = getTextFont().maxHeight + getTextFont().maxDescent + getInsets()*2 + getBorder().getThickness()*2;
		this.preferedWidth = getTextFont().getWidth(text) + getInsets()*2 + getBorder().getThickness()*2;

	}

	
	

}
