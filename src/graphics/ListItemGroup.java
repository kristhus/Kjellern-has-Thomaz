package graphics;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import interfaces.Drawable;

public class ListItemGroup extends JPanel implements Drawable{

	
	private int maxComponentWidth;
	private int maxComponentHeight;
	
	private GridBagLayout gbl = new GridBagLayout();
	private SpringLayout spl = new SpringLayout();
	private GridLayout gl = new GridLayout();
	
	private boolean flowHorizontal = false;
	private boolean flowVertical = true;
	
	private ArrayList<ListItem> components = new ArrayList<ListItem>();
	
	public ListItem get(int i){
		if(components.size() <= i)
			return components.get(i);
		else
			return null;
	}
	
	private void updateMaxSize() {
		int tmpW = maxComponentWidth;
		int tmpH = maxComponentHeight;
		for(ListItem item : components) {
			maxComponentWidth = Math.max(item.getWidth(), maxComponentWidth);
			maxComponentHeight = Math.max(item.getHeight(), maxComponentHeight);
		}
		if(maxComponentHeight != tmpH || maxComponentWidth != tmpW) {
			for(ListItem item : components) {
				item.getLabel().setBounds(0,0,maxComponentWidth, maxComponentHeight);
			}
		}
	}
	
	
	public void addC(ListItem i) {
		if(i != null) {
			components.add(i);
			updateMaxSize();
			add(i);
		}
	}
	public void addC(ListItem i, GridBagConstraints c) {
		setLayout(gbl);
		if(i != null) {
			components.add(i);
			updateMaxSize();
			add(i, c);
		}
	}
	
	public void addC(ListItem i, GridLayout gl) {
		if(i != null) {
			components.add(i);
			updateMaxSize();
			add(i);
		}
		int row = 0;
		int col = 0;
		if(flowHorizontal)
			this.gl = new GridLayout(0, sizeC());
		else
			this.gl = new GridLayout(sizeC(), 0);
		setLayout(this.gl);
	}
	
	public void addC(ListItem i, SpringLayout spl) {
		setLayout(spl);
		if(i != null) {
			components.add(i);
			updateMaxSize();
			add(i);
		}
	}
	
	public ListItem removeC(int i) {
		ListItem tmp = null;
		if(components.size() <= i) {
			tmp = components.remove(i);
			remove(tmp);
			updateMaxSize();
		}
		return tmp;
	}
	
	public int sizeC() {
		return components.size();
	}
	
	public boolean containsC(ListItem item) {
		return components.contains(item);
	}

	public void setGridLayoutFlowHorizontal(boolean flowHorizontal) {
		this.flowHorizontal = flowHorizontal;
		flowVertical = !flowHorizontal;
		updateMaxSize();
	}
	public void setGridLayoutFlowLeft(boolean flowVertical) {
		this.flowVertical = flowVertical;
		flowHorizontal = !flowVertical;
		updateMaxSize();
	}
	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		repaint();
		for(ListItem item : components) {
			item.draw(g);
		}
	}

	@Override
	public void update(double dt) {
		// TODO Auto-generated method stub
		for(ListItem item : components) {
			item.update(dt);
		}
		updateMaxSize();
	}
	
}
