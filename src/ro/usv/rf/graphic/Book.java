package ro.usv.rf.graphic;

import java.awt.*;
import java.awt.Graphics;

/*
 * class for book draw
 */
public class Book {
	
	/*
	 * Draws a BJP textbook at the given x/y position.    
	 */
	public static void drawBook(Graphics g, int x, int y, int size) {
        g.setColor(Color.CYAN);            // cyan background
        g.fillRect(x, y, size, size);
        
        g.setColor(Color.WHITE);           // white "bjp" text
        g.drawString("BJP", x + size/2, y + size/5);

        g.setColor(new Color(191, 118, 73));
        for (int i = 0; i < 10; i++) {     // orange "bricks"
            g.fillRect(x,                  // x
                       y + size/10 * i,    // y
                       size/10 * (i + 1),  // width
                       size/10 - 1);       // height
        }
    }

	

    /*
     * test main method
     */
    public static void main(String[] args) {
    	int xmax=800, ymax=600;
        DrawingPanel panel = new DrawingPanel(xmax,ymax);
        panel.setBackground(Color.WHITE);
        Graphics g = panel.getGraphics();

        g.setColor(Color.CYAN);            // cyan background
        g.fillRect(20, 35, 100, 100);

        g.setColor(Color.WHITE);           // white "bjp" text
        g.drawString("BJP", 70, 55);

        g.setColor(new Color(191, 118, 73));
        g.setColor(Color.BLUE);
        int r=20;
        for(int x=0; x<xmax-r; x+=2*r)
        	for(int y=0; y<ymax-r; y+=2*r)
        		g.fillOval(x+1, y+1, r, r);
        /*
        drawBook(g,  20, 35, 100);
        panel.sleep(500);
        drawBook(g, 150, 70,  60);
        panel.sleep(500);
        drawBook(g, 300, 10, 200);
        panel.sleep(500);
        DrawingPanel p = new DrawingPanel(100, 100);
        Graphics g1 = p.getGraphics();
        g1.setColor(Color.GREEN);

        Polygon poly = new Polygon();
        poly.addPoint(10, 90);
        poly.addPoint(50, 10);
        poly.addPoint(90, 90);
        //g.fillPolygon(poly);
        //g1.fillPolygon(poly);
*/
    }
}
