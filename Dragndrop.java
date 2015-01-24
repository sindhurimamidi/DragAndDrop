package dragpack;

import java.awt.event.KeyEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.RepaintManager;


class ImagePanel extends JComponent {
    private Image image;
    public ImagePanel(Image image) {
        this.image = image;
    }
    @Override
    protected void paintComponent(Graphics g) {
    	ImageIcon background = new ImageIcon("src/body.jpg");
    	System.out.println("In paintComp");
        g.drawImage(background.getImage(), 20, 20, null);
    }
}

public class Dragndrop extends JLabel implements MouseMotionListener, ActionListener, MouseListener{ 
	
  static int imageWidth = 300, imageHeight = 300;
  int imageX, imageY;
  int image1X=100, image1Y=100;	
  BufferedImage imagename;
  int startX = 1, startY = 1;
  int finalX, finalY, finalx1, finaly1;
  boolean inDrag = false;
  boolean finalstage = false;

  public Dragndrop(BufferedImage imagename,int startX,int startY,int finalX,int finalY,int finalx1,int finaly1) {
	this.imagename = imagename ;
	this.startX = startX; //assigning local variable = constructor variable.
	this.startY = startY;
	this.finalX = finalX;
	this.finalY = finalY;
	this.finalx1 = finalx1;
	this.finaly1 = finaly1;
    addMouseMotionListener(this);
    addMouseListener(this);
  }
  
  public void mousePressed(MouseEvent e)  {
	Point p = e.getPoint();
	System.out.println("mousePressed at " + p);
		if (p.x < finalX  && p.y < finalY)
	{
		inDrag = true;
	}
  }
  
  public void mouseReleased(MouseEvent e) {
	  	Point p = e.getPoint();
	  	System.out.println("mouseReleased at " + p);
	   	if (!this.finalstage) {
		   if ((p.x > finalX && p.x < finalx1) && (p.y > finalY && p.y < finaly1) ){
			   imageX = (finalX+finalx1)/2;
			   imageY = (finalY+finaly1)/2;
			   repaint();
			   this.finalstage = true;
		   }
		   else
		   {
			   imageX = startX;  //e.getX();
			   imageY = startY; //e.getY();
			   repaint();	    
		   }	
	   	}	
  }

  public void mouseDragged(MouseEvent e) {
   Point p = e.getPoint();
   if(!this.finalstage) {
	imageX = e.getX();
    imageY = e.getY();
    repaint();
   }
  
  }
  
  public void mouseMoved(MouseEvent e) {
  }
  
  public void actionPerformed(ActionEvent e) {

  }
  
  public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    System.out.println("imageX=" + imageX + "imageY=" + imageY);
    g2.drawImage(imagename, imageX, imageY, this);
  }

  public static void main(String[] args) throws IOException {
	  
    JFrame frame = new JFrame("DragImage"); 
    frame.setBackground(Color.WHITE);
    //create a button
    JButton btn = new JButton("Exit");
    btn.setBounds(300,700,200,60);
	btn.setToolTipText("button");
	btn.addActionListener(new ActionListener() {
		 @Override
		 public void actionPerformed(ActionEvent event){
			 System.exit(0);
		 }
	 });
	//adding button to panel
	frame.add(btn);
    String BODY = "src/body.png";
    String IMG_P1 = "src/p1.png";
    String IMG_P2 = "src/p2.png";
    String IMG_P3 = "src/p3.png";
    String IMG_P4 = "src/p4.png";
    String IMG_P5 = "src/p5.png";
    BufferedImage body = null;
    BufferedImage p1 = null;
    BufferedImage p2 = null;
    BufferedImage p3 = null;
    BufferedImage p4 = null;
    BufferedImage p5 = null;
    
    try {
    	//Read the image
        body = ImageIO.read(new File(BODY)); //reading image
    } catch (IOException e) {
        e.printStackTrace();
    } 
    try{
    	p1 = ImageIO.read(new File(IMG_P1));
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    try{
    	p2 = ImageIO.read(new File(IMG_P2));
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    try {
    	//Read the image
        p3 = ImageIO.read(new File(IMG_P3)); //reading image
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    try{
    	p4 = ImageIO.read(new File(IMG_P4));
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    try{
    	p5 = ImageIO.read(new File(IMG_P5));
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    //Create layeredpane and add jlabel
    JLayeredPane layeredPane = new JLayeredPane();
    layeredPane.setPreferredSize(new Dimension(1000, 1000));
    Dragndrop d1 = new Dragndrop(p1,0,100,525,0,690,340); //pink mouth (sx,sy,fx,fy,fx1,fy1) s-start,f-final
    Dragndrop d2 = new Dragndrop(p2,0,100,550,200,690,300); //pink 
    Dragndrop d3 = new Dragndrop(p3,0,100,490,0,690,300); // each component y starts with 0 green
    Dragndrop d4 = new Dragndrop(p4,0,100,580,0,600,220); //yellow 
    Dragndrop d5 = new Dragndrop(p5,0,100,550,0,680,65); //red
    Dragndrop bdy = new Dragndrop(body,0,0,0,0,0,0);
    
    layeredPane.add(bdy,new Integer(0));
    layeredPane.add(d1,new Integer(1));
    layeredPane.add(d2,new Integer(2));
    layeredPane.add(d3,new Integer(3));
    layeredPane.add(d4,new Integer(4));
    layeredPane.add(d5,new Integer(5));
    layeredPane.setOpaque(false);
     //After adding the label, set it's dimensions manually.
    bdy.setBounds( 500, 50,  
            3000,
            3000 );
    
    d1.setBounds( 0,0,  
            3000,
            3000 );
    d2.setBounds( 0,100,  
            3000,
            3000 );
    d3.setBounds( 0,200,  
    		3000  /*length*/,
    		3000 /*breadth*/ ); 
    d4.setBounds( 0,300,  
                3000,
                3000 );
    d5.setBounds( 0,400,  
                3000,
                3000 );
        
    frame.add(layeredPane);
    frame.setSize(900, 800);
    
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    
    System.out.println("in try block : " + layeredPane.getComponentCountInLayer(0) +"and" +layeredPane.getComponentCountInLayer(1));
    
  }

@Override
public void mouseClicked(MouseEvent arg0) {	
}

@Override
public void mouseEntered(MouseEvent arg0) {	
}

@Override
public void mouseExited(MouseEvent arg0) {	
}
}
