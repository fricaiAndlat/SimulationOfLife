package de.diavololoop.gl.gui;

import org.lwjgl.glfw.GLFW;

import de.diavololoop.gl.GLColor;
import de.diavololoop.gl.GLLookAndFeel;
import de.diavololoop.gl.decorations.GLBackground;

public class GLTextField extends GLLabel{
	
	
	
	protected int cursorPos = 0;
	protected GLBackground cursor;
	protected long startBlinkingTime;
	protected long blinkingTime = 700;
	protected boolean isBlinking = false;
	

	public GLTextField(int width) {
		super("");
		
		this.setAttrib(new GLAttrib(GLLookAndFeel.getCurrent().getSubSetting("textfield")));
		

		preferedHeight = getTextFont().maxHeight + getTextFont().maxDescent + getInsets()*2 + getBorder().getThickness()*2;
		this.setExpand(true, false);
		
		cursor = new GLBackground(x + textOffsetX, y + getInsets(), 1, getTextFont().maxHeight+getTextFont().maxDescent, GLColor.BLACK);

		recalcAll();
	}
	
	@Override
	public void onFocusGet(){
		super.onFocusGet();
		startBlinkingTime = System.currentTimeMillis();
		isBlinking = true;
	}
	
	@Override
	public void onKeyPressed(int key, boolean isShiftDown, boolean isControlDown, boolean isAltDown){
		super.onKeyPressed(key, isShiftDown, isControlDown, isAltDown);
		
		if(key == GLFW.GLFW_KEY_BACKSPACE){
			if(isSelected){
				String start = text.substring(0, startSelectionIndex);
				String end = text.substring(stopSelectionIndex);
				
				setText(start + end);
				
				isSelected = false;
				cursorPos = startSelectionIndex;
			}else{
				if(cursorPos > 0){
					String start = text.substring(0, cursorPos-1);
					
					String end = text.substring(cursorPos);
					
					setText(start + end);
				
				
				
					--cursorPos;
					
					if(hasClick){
						cursor.setPosition(this.x + textOffsetX + getTextFont_click().getWidth(text.substring(0, cursorPos)), this.y + getInsets());
					}else if(hasFocus){
						cursor.setPosition(this.x + textOffsetX + getTextFont_focus().getWidth(text.substring(0, cursorPos)), this.y + getInsets());
					}else if(hasHover){
						cursor.setPosition(this.x + textOffsetX + getTextFont_hover().getWidth(text.substring(0, cursorPos)), this.y + getInsets());
					}else{
						cursor.setPosition(this.x + textOffsetX + getTextFont().getWidth(text.substring(0, cursorPos)), this.y + getInsets());
					}
				}
			}
			
		}else if(key == GLFW.GLFW_KEY_DELETE){
			if(isSelected){
				String start = text.substring(0, startSelectionIndex);
				String end = text.substring(stopSelectionIndex);
				
				setText(start + end);
				
				isSelected = false;
				cursorPos = startSelectionIndex;
			}else{
				if(cursorPos < text.length()){
					String start = text.substring(0, cursorPos);
						
					String end = text.substring(cursorPos+1);
						
					setText(start + end);
		
				}
			}
			
		}else if(key == GLFW.GLFW_KEY_LEFT){
			
			if(cursorPos > 0){
				--cursorPos;
			}
			
		}else if(key == GLFW.GLFW_KEY_RIGHT){
			
			if(cursorPos <= text.length()){
				++cursorPos;
			}
			
		}else if(key == GLFW.GLFW_KEY_A && isControlDown){
			
			startSelectionIndex = 0;
			stopSelectionIndex = text.length();
			paintSelection();
			
		}else if(key == GLFW.GLFW_KEY_X && isControlDown){
			
			String start = text.substring(0, startSelectionIndex);
			String end = text.substring(stopSelectionIndex);
			
			setText(start + end);
			
			isSelected = false;
			cursorPos = startSelectionIndex;
			
		}else if(key == GLFW.GLFW_KEY_V && isControlDown){
			String mid = GLComponent.restoreStringFromClipboard();
			String start, end;
			
			if(isSelected){
				start = text.substring(0, startSelectionIndex);
				end = text.substring(stopSelectionIndex);
				cursorPos = startSelectionIndex + mid.length();
			}else{
				start = text.substring(0, cursorPos);
				end = text.substring(cursorPos);
				cursorPos += mid.length();
			}

			setText(start + mid + end);
			isSelected = false;
			
			startSelectionIndex = stopSelectionIndex;
			
		}
	}
	@Override
	public void onKeyPressed(char ch){
		super.onKeyPressed(ch);
		String c = Character.toString(ch);
		
		if(this.isSelected){
			String start = text.substring(0, startSelectionIndex);
			String end = text.substring(stopSelectionIndex);
			
			setText(start + c + end);
			
			cursorPos = startSelectionIndex;
		}else{
			String start = text.substring(0, cursorPos);
			String end = text.substring(cursorPos);
			
			setText(start + c + end);
		}
		
		
		
		
		if(!c.isEmpty()){
			++cursorPos;
			
			if(hasClick){
				cursor.setPosition(this.x + textOffsetX + getTextFont_click().getWidth(text.substring(0, cursorPos)), this.y + getInsets());
			}else if(hasFocus){
				cursor.setPosition(this.x + textOffsetX + getTextFont_focus().getWidth(text.substring(0, cursorPos)), this.y + getInsets());
			}else if(hasHover){
				cursor.setPosition(this.x + textOffsetX + getTextFont_hover().getWidth(text.substring(0, cursorPos)), this.y + getInsets());
			}else{
				cursor.setPosition(this.x + textOffsetX + getTextFont().getWidth(text.substring(0, cursorPos)), this.y + getInsets());
			}
		}
	}
	
	@Override
	public void onFocusLost(){
		super.onFocusLost();
		isBlinking = false;
	}
	
	@Override
	public void onMousePressed(int x, int y){
		super.onMousePressed(x, y);
		
		cursorPos = this.getTextPosition(x);


		if(hasClick){
			cursor.setPosition(this.x + textOffsetX + getTextFont_click().getWidth(text.substring(0, cursorPos)), this.y + getInsets());
		}else if(hasFocus){
			cursor.setPosition(this.x + textOffsetX + getTextFont_focus().getWidth(text.substring(0, cursorPos)), this.y + getInsets());
		}else if(hasHover){
			cursor.setPosition(this.x + textOffsetX + getTextFont_hover().getWidth(text.substring(0, cursorPos)), this.y + getInsets());
		}else{
			cursor.setPosition(this.x + textOffsetX + getTextFont().getWidth(text.substring(0, cursorPos)), this.y + getInsets());
		}
	}
	
	@Override
	public void reCalculatePosition(){
		super.reCalculatePosition();
		if(cursor != null){
			
			if(hasClick){
				cursor.setPosition(this.x + textOffsetX + getTextFont_click().getWidth(text.substring(0, cursorPos)), this.y + getInsets());
			}else if(hasFocus){
				cursor.setPosition(this.x + textOffsetX + getTextFont_focus().getWidth(text.substring(0, cursorPos)), this.y + getInsets());
			}else if(hasHover){
				cursor.setPosition(this.x + textOffsetX + getTextFont_hover().getWidth(text.substring(0, cursorPos)), this.y + getInsets());
			}else{
				cursor.setPosition(this.x + textOffsetX + getTextFont().getWidth(text.substring(0, cursorPos)), this.y + getInsets());
			}
			
		}
		
		
	}
	
	@Override
	public void drawExtra(){
		super.drawExtra();
		
		if(startSelectionIndex==stopSelectionIndex && isBlinking && ((int)((System.currentTimeMillis()-startBlinkingTime)/blinkingTime))%2 == 0){
			cursor.renderBackground(screenWidth, screenHeight);
		}
		
	}

}
