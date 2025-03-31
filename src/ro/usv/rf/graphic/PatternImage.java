package ro.usv.rf.graphic;

import java.awt.Color;
import java.awt.Graphics;
import java.security.PublicKey;

import javax.imageio.ImageTranscoder;

import ro.usv.rf.learningsets.SupervisedLearningSet;
import ro.usv.rf.mnist.MNIST_SetUtils;

public class PatternImage {
	private DrawingPanel panel;   // the panel where the image(s) will be drawn
	private int imgNumRows;       // the image number of rows
	private int imgNumCols;       // the image number of columns
	private int pixelImageWidth;  // the graphic width of the square that will draw a pixel from image
	private Graphics g;           // will be get from the DrawingPanel
	
    
	public PatternImage(DrawingPanel panel, int imgNumRows, int imgNumCols, int pixelImageWidth) {
		super();
		this.panel = panel;
		this.imgNumRows = imgNumRows;
		this.imgNumCols = imgNumCols;
		this.pixelImageWidth = pixelImageWidth;
		g = panel.getGraphics();
	}

	/*
	 * Draws a grey image at panel coordinates x and y
	 */
	public void drawGreyImage(double [] z, int x, int y)  {
		int ind= 0;
		for(int i=0; i<imgNumRows; i++)
			for(int j=0; j < imgNumCols; j++) {
				int nivelGri = (int)( 255 * (1 - z[ind++]));
				g.setColor(new Color(nivelGri, nivelGri, nivelGri )  );
				g.fillRect(x + j*pixelImageWidth, y + i*pixelImageWidth, 
						         pixelImageWidth, pixelImageWidth);	
			}
	}

	/*
	 * Draws a grey image at panel coordinates x and y
	 * in a contour rectangle with color c (BLUE as default)
	 * and if a Legend is provided, write the legend under the rectangle
	 * in the same color c as rectangle
	 * 
	 */
	public void drawPatternImage(double [] z, int x, int y, String legend, Color c)  {
		c = c==null ? Color.BLUE : c;
		g.setColor(c);
		g.drawRect(x, y,(imgNumCols+2) * pixelImageWidth, (imgNumRows+2)*pixelImageWidth);

		drawGreyImage(z, x+1, y+1);
	    
		if(legend!=null) {
			g.setColor(c );
			g.drawString(legend, x, y + pixelImageWidth*imgNumRows+30);
	    }
	}

	/*
	 * A helper method that draws an image in a rectangle, and as legend writes
	 * under image the 'patternClassName'
	 * The rectangle and legend is have the color c
	 */
	public void drawPatternImageAndClass(double [] z, String patternClassName, int x, int y, Color c) {
		drawPatternImage(z, x, y, "Class="+patternClassName, c);
	}
	
	/*
	 * A helper method that draws an image in a rectangle, and as legend writes
	 * under image the 'patternClassName'
	 * The rectangle and legend have the color BLUE
	 */
	public void drawPatternImage(double [] z, int x, int y, String legend) {
		drawPatternImage(z, x, y, legend, Color.BLUE);
	}

	/*
	 * A helper method that draws an image in a rectangle of color BLUE
	 * without any legend wrote under the image
	 */
	public void drawPatternImage(double [] z, int x, int y) {
		drawPatternImage(z, x, y, null, null);
	}
	
	/* 
	 * Drawing a collection of grey images
	 *   - drawImageSet( SupervisedearnigSet set,        patternsNumber, x0, y0, gap)
	 *   - drawImageArray( x[][], iClas[], classNames[], patternsNumber, x0, y0, gap)
	 */
	public void drawImageSet(SupervisedLearningSet set, int nb, int x0, int y0, int gap, Color[] tabColors ) {
		drawImageArray(set.getX(), set.getIClass(), set.getClassNames(), nb, x0, y0, gap, tabColors);
	}
	
	public void drawImageArray(double[][] X, int[] iClass, String[] classNames, 
			int nb, int x0, int y0, int gap, Color[] tabColors )	{
		
		int imageWidth = imgNumCols * pixelImageWidth + 2 + gap;  // +2 is for rectangle contour
		int colHeight  = imgNumRows * pixelImageWidth + 2 + gap;  // +2 is for rectangle contour
		int nbImagesPerRow = (panel.getWidth() -x0) / (imgNumCols*pixelImageWidth + gap);
		System.out.println("img width="+imageWidth + " colH=" + colHeight + " nb/row=" + nbImagesPerRow);
		
		int y = y0 -colHeight;
		for (int i=0; i < nb; i++) {
			if( i % nbImagesPerRow == 0)
				y += colHeight;
			System.out.println("i="+i+ " y=" + y);
			Color c= tabColors==null? null: tabColors [ Math.min(i, tabColors.length-1)];
			drawPatternImage (X[i], x0 + (i % nbImagesPerRow) * imageWidth , 
					                y , classNames [ iClass [i] ],c);
		}
	}
	
	
	public Graphics getG() {
		return g;
	}


	public void setPanel(DrawingPanel panel) {
		this.panel = panel;
		g = panel.getGraphics();
	}

	
	
	/*
	 * Test the methods of the PatternImage class
	 */
	public static void demo(String nameSet, int nbPatterns ) {
		String filePrefixTrain = ""+nameSet;
		int nbPatternsInSet = 600;
		System.out.println("Start");
		SupervisedLearningSet trainingSet = MNIST_SetUtils.convert_MNIST_SupervisedSet(filePrefixTrain, nbPatternsInSet);
		
		// The first Panel
		
		DrawingPanel panel = new DrawingPanel(1200, 1600);
		PatternImage drawImgs = new PatternImage(panel, 28, 28, 5);
	    //drawImgs.drawPatternImage(trainingSet.getX()[0], 5*(28*5+50)+50, 380+50);		
		int gap = 48;
		int width = 28*5 + 2 + gap;  // +2 is for rectangle contour
		drawImgs.drawPatternImage(trainingSet.getX()[499], gap/2, gap/3, "Test-500", Color.MAGENTA); 
		
		int y1 =  gap/3 + width + 30;
		Graphics g = drawImgs.getG();
		g.setColor(Color.BLUE);
		g.drawString("The first "+ nbPatterns + " patterns of " + nameSet + " set",
				                                             gap/2, y1);
		g.setColor(Color.BLACK);
		g.drawLine(gap/2, y1 +5 , gap/2 + 6* width - gap, y1+5);
		
		drawImgs.drawPatternImage(trainingSet.getX()[99], gap/2+ 5*width, gap/2, "Pattern 100"); 
		drawImgs.drawImageSet(trainingSet, nbPatterns, gap/2, gap/3+ width+50, gap, null);
		
		// the second Panel
		DrawingPanel panel2 = new DrawingPanel(1200, 1600);
		drawImgs.setPanel(panel2);
		g = drawImgs.getG(); // for the second panell
		y1 = gap;
		Color[] patternColors = new Color[nbPatterns];
		for(int i=0; i < nbPatterns; i++)
			if( trainingSet.getIClass()[i] - 1 >=5)
				patternColors[i] = Color.BLUE;
			else 
				patternColors[i] = Color.RED;		
				
		g.setColor(Color.BLUE);
		g.drawString("The first "+ nbPatterns + " patterns of " + nameSet + " set, colored in red if iClass[i]<5 "+
		              " and blue the others", gap/2, y1/2-5);
		g.setColor(new Color(250, 172, 190));
		g.drawLine(gap/2, y1/2 , gap/2 + 6* width - gap, y1/2);
		drawImgs.drawImageSet(trainingSet, nbPatterns, gap/2, y1, gap, patternColors);
	}
}
