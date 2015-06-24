package shapeCreator;

import graphics.MainFrame;
import graphics.ParticleCanvas;
import interfaces.Drawable;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import listeners.CanvasMouseListener;
import particleEngine.ColorChooser;;

public class CreatorCanvas extends JPanel implements Drawable{
	
	private ColorChooser cc;
	private ShapePanel sp;
	
	private int mouseX;
	private int mouseY;
	
	public CreatorCanvas() {
		cc = new ColorChooser();
		sp = new ShapePanel();
		
		BorderLayout bl = new BorderLayout();
		setLayout(bl);
		add(cc, BorderLayout.NORTH);
		JScrollPane scrollPane = new JScrollPane(sp);
		//scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane, BorderLayout.WEST);
		add(new ParticleCanvas(), BorderLayout.CENTER);
		addMouseMotionListener(MainFrame.getRightPanel().getMouseListener());
		revalidate();
	}
	
	
	
	@Override
	public void draw(Graphics g) {
		for(Component c : getComponents()) {
			if(c instanceof Drawable){
				((Drawable) c).draw(getGraphics());  // I want the children to have access to the entire graphics of this un
			}
		}
		sp.draw(g);
	}


	@Override
	public void update(double dt) {
		// TODO Auto-generated method stub
		for(Component c : getComponents()) {
			if(c instanceof Drawable) {
				((Drawable)c).update (dt);
			}
		}
		sp.update(dt);
		
	}

}
