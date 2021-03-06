package reader;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Reader {

	
	//Delete resources when not needed (use recycling, and null objects to reallocate mem)
	
	// All settings should be accessed from this class through some kind of readable file
	// 		These settings include simple stuff like isSeedOn, various color options etc. 
	
	public BufferedImage readImage(String path) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path));
		} catch(IOException e) {
			//Visual representation of error loading img
			System.out.println("ERROR");
		}
		return img;
	}
	
	public ImageIcon resizeIcon(ImageIcon icon, int w, int h) {
		Image img = icon.getImage();
		BufferedImage bImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics g = bImg.createGraphics();
		g.drawImage(img, 0, 0, w, h, null, null);
		return new ImageIcon(bImg);
		
	}
	
	public ImageIcon readGif(String path) {
		ImageIcon img = null;
		img = new ImageIcon(getClass().getResource(path));
		return img;
	}
	
	public void readDocument(String path) {
		
	}
	
	public void saveDocument() {
		
	}
	
}
