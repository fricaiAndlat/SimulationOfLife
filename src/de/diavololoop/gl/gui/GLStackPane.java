package de.diavololoop.gl.gui;

public class GLStackPane extends GLPane{
	
	public final static int HORIZONTAL = 0;
	public final static int VERTICAL = 1;
	
	protected int gap;
	protected int orientation;
	
	public GLStackPane(int maxWidth, int maxHeight, int orientation, int gap){
		super(maxWidth, maxHeight);
		this.orientation = orientation;
		this.gap = gap;
	}
	
	@Override
	protected void reCalculatePosition(){
		super.reCalculatePosition();
		
		int offset = 0;
		preferedWidth  = getPreferedWidth();
		preferedHeight = getPreferedHeight();

		if(orientation == HORIZONTAL){
			
			if(xAlign == LEFT){
				for(GLComponent child: childs){
					child.setPosition(offset+x, y);
					child.setSize(child.getPreferedWidth(), preferedHeight);
					offset += child.getPreferedWidth()+gap;
				}
			}else if(xAlign == RIGHT){
				for(GLComponent child: childs){
					offset += child.getPreferedWidth();
					child.setPosition(x+width-offset, y);
					child.setSize(child.getPreferedWidth(), preferedHeight);
					offset += gap;
				}
			}else{
				throw new IllegalStateException("no proper align");
			}

		}else if(orientation == VERTICAL){
			
			if(yAlign == BOTTOM){
				for(GLComponent child: childs){
					child.setPosition(x, y+offset);
					child.setSize(preferedWidth, child.getPreferedHeight());
					offset += child.getPreferedHeight()+gap;
				}
			}else if(yAlign == TOP){
				for(GLComponent child: childs){
					offset += child.getPreferedHeight();
					child.setPosition(x, y+height-offset);
					child.setSize(preferedWidth, child.getPreferedHeight());
					offset += gap;
					
				}
			}else{
				throw new IllegalStateException("no proper align");
			}

		}
		
	}
	
	@Override
	public int getPreferedWidth(){
		if(orientation == HORIZONTAL){
			int w = 0;
			for(GLComponent child: childs){
				w += child.getPreferedWidth();
			}
			if(childs.size()>0){
				w += (childs.size()-1)*gap;
			}
			return w;
		}else if(orientation == VERTICAL){
			if(expandHorizontal && this.parent==null){
				return this.outerWidth;
			}
			int w = 0;
			for(GLComponent child: childs){
				int childWidth = child.getPreferedWidth();
				if(childWidth > w){
					w = childWidth;
				}
			}
			return w;
		}
		throw new IllegalStateException("orientation is not specified");
	}
	
	@Override
	public int getPreferedHeight(){
		if(orientation == HORIZONTAL){			
			if(expandVertical && this.parent==null){
				return this.outerHeight;
			}
			int h = 0;
			for(GLComponent child: childs){
				int childHeight = child.getPreferedHeight();
				if(childHeight > h){
					h = childHeight;
				}
			}
			return h;
		}else if(orientation == VERTICAL){
			
			int h = 0;
			for(GLComponent child: childs){
				h += child.getPreferedHeight();
			}
			if(childs.size()>0){
				h += (childs.size()-1)*gap;
			}			
			return h;
		}
		throw new IllegalStateException("orientation is not specified");
	}
	
	@Override
	public void add(GLComponent component){
		super.add(component);
		
		reCalculatePosition();
	}
	


}
