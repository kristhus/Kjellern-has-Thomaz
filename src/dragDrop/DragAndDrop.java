package dragDrop;

import interfaces.Drawable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class DragAndDrop extends JPanel implements Drawable {
	
	private Color bg;
	private Color bgHover;
	private Color bgSelected;
	private Color fg;
	private Color fgHover;
	private Color fgSelected;
	
	private Border border;
	private Border borderHover;
	private Border borderSelected;
	
	private boolean selected;
	private boolean hover;
	
	//THEMES
	public static final int DEFAULT = 0;
	public static final int AQUATIC = 1;
	
	/**
	 * Constructor for the default theme
	 */
	public DragAndDrop() { 
		setTheme(DEFAULT);
	}
	
	public DragAndDrop(Icon ic) {
		JLabel imageLabel = new JLabel();
		imageLabel.setIcon(ic);
	}
	
	public DragAndDrop(int THEME) {
		setTheme(THEME);
		
	}
	
	
	public void setTheme(int THEME) {
		switch(THEME) {
		case DEFAULT:
			bg = Color.WHITE;
			bgHover = new Color(255, 255, 240);
			bgSelected = new Color(255, 240, 220);
			fg = Color.black;
			fgSelected = fgHover = fg;
			border = BorderFactory.createDashedBorder(Color.GRAY);
			borderHover = borderSelected = border;
			break;
		case AQUATIC:
			bg = Color.white;
			bgHover = new Color(0, 200, 255);
			bgSelected = new Color(0, 150, 255);
			fg = Color.black;
			fgSelected = fgHover = Color.white;
			border = BorderFactory.createDashedBorder(new Color(0, 100, 160));
			borderHover = borderSelected = border;
			break;
		}
		setSelected(selected);
	}
	
	public boolean intersects(Rectangle bounds) {
		Rectangle rect = new Rectangle(getX(), getY(), getWidth(), getHeight());
		return bounds.intersects(rect);
	}
	public boolean intersects(Point point) {
		if((point.getX() >= getX() && point.getX() <= getX()+getWidth()) &&
				point.getY() >= getY() && point.getY() <= getY()+getHeight()) {
			return true;
		}
		
		return false;
	}
	
	
	public void setSelected(boolean selected) {
		this.selected = selected;
		if(selected) {
			setBackground(bgSelected);
			setForeground(fgSelected);
			setBorder(borderSelected);
		} else {
			setBackground(bg);
			setForeground(fg);
			setBorder(border);
		}
		System.out.println(selected);
	}
	public boolean isSelected() {
		return selected;
	}
	
	/**
	 * If the component isHover while isSelected is in effect, selected takes priority over hover
	 * @param hover the mouse has entered the boundaries of the component
	 */
	public void setHover(boolean hover) {
		this.hover = hover;
		if(hover && !selected) {
			setBackground(bgHover);
			setForeground(fgHover);
			setBorder(borderHover);
		} else if (!hover && !selected) {
			setBackground(bg);
			setForeground(fg);
			setBorder(border);
		}
	}
	public boolean isHover() {
		return hover;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		//super.paintComponent(g);
	}

	@Override
	public void update(double dt) {
		// TODO Auto-generated method stub
		
	}
	
	
}
