package shapeCreator;

import graphics.ParticleCanvas;
import interfaces.Drawable;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ParticleEngine.ColorChooser;

public class CreatorCanvas extends JPanel implements Drawable{
	
	private ColorChooser cc;
	private ShapePanel sp;
	
	public CreatorCanvas() {
		cc = new ColorChooser();
		sp = new ShapePanel();
		
		BorderLayout bl = new BorderLayout();
		setLayout(bl);
		add(cc, BorderLayout.NORTH);
		JScrollPane scrollPane = new JScrollPane(sp);
		//scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane, BorderLayout.WEST);
		add(new ParticleCanvas());
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	@Override
	public void draw(Graphics g) {
		repaint();
	}

	@Override
	public void update(long dt) {
		// TODO Auto-generated method stub
		
	}

}
