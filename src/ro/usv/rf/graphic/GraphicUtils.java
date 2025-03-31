package ro.usv.rf.graphic;
import ro.usv.rf.utils.FileUtils1;
import ro.usv.rf.utils.StatisticsUtils;
import java.awt.Color;
import java.awt.Graphics;
import java.rmi.server.RMIClassLoader;
import java.util.Arrays;

import javax.naming.InitialContext;

import ro.usv.rf.learningsets.SupervisedLearningSet;

public class GraphicUtils {
	DrawingPanel graficDrawingPanel;
	Graphics g;
	double ax, bx, ay, by;
	int XMAX = 800; 
	int YMAX = 600;
	Color colorPatternPoint = Color.BLUE;
	Color[] colors = new Color[] {Color.BLACK, Color.BLUE, Color.GREEN, Color.RED, Color.CYAN,
			Color.MAGENTA, Color.ORANGE, Color.PINK, Color.YELLOW, Color.GRAY};
	int nColors = colors.length;
	int radiusPatternPoint=8;
	
	public GraphicUtils(int xMAX, int yMAX) {
		super();
		XMAX = xMAX;
		YMAX = yMAX;
	}	
	
	public GraphicUtils() {
	}	

	
	private static double getCoefficientA(double vmin, double vmax, int VMAX) {
		return (double)VMAX/(vmax - vmin);
	}
	
	public void initXOY(double xmin, double xmax, double ymin, double ymax) {
		graficDrawingPanel = new DrawingPanel(XMAX, YMAX);
		g = graficDrawingPanel.getGraphics();
		int VMAX = Math.min(XMAX, YMAX);  //pt. a avea aceeasi scara pe ox si oy
		ax = getCoefficientA(xmin, xmax, VMAX);
		bx = - ax * xmin;
		ay = getCoefficientA(ymin, ymax, VMAX);
		by = - ay * ymin;
		g.drawRect(1, 1, XMAX-2, YMAX-2);
	}

	public void fillPatternPoint( double[] z) {
		int x,y;
		g.fillOval(x=(int)(ax*z[0]+bx)- radiusPatternPoint, y = YMAX - (int)(ay*z[1]+by) - radiusPatternPoint, 
		           radiusPatternPoint, radiusPatternPoint);
		System.out.println(x+ ", "+ y);
	}

	public void drawPatternPoint( double[] z) {
		int x,y;
		g.drawOval(x=(int)(ax*z[0]+bx)- radiusPatternPoint, y = YMAX - (int)(ay*z[1]+by) - radiusPatternPoint, 
		           radiusPatternPoint, radiusPatternPoint);
		System.out.println(x+ ", "+ y);
	}

	public void drawGraphicXOY(SupervisedLearningSet set, boolean minFromZero) {
		double[][] X = set.getX();
		int iClass[] = set.getIClass();
		double[][] minMax = StatisticsUtils.calculateMinMax(X);
		System.out.println(set);
		System.out.println(Arrays.deepToString(minMax));
        if(minFromZero)
        	initXOY(0, minMax[1][0]*1.2, 0, minMax[1][1]*1.2);
        else {
            initXOY(minMax[0][0]>0?minMax[0][0]*0.9: minMax[0][0]*1.2, 
            		minMax[1][0]<0?minMax[1][0]*0.9: minMax[1][0]*1.2,
            		minMax[0][1]>0?minMax[0][1]*0.9: minMax[0][1]*1.2, 
            	    minMax[1][1]<0?minMax[1][1]*0.9: minMax[1][1]*1.2);

		}
        for(int i=0; i<set.getN(); i++) {
        	g.setColor(iClass[i]> nColors-2? colors[ nColors-1]: colors[ iClass[i] ]);
        	if(iClass[i]==0)
        		drawPatternPoint(X[i]);
        	else
        	    fillPatternPoint(X[i]);
        }
	}
	
	public static void main(String[] args) {
		SupervisedLearningSet tstExamStr = new SupervisedLearningSet("testexam_strings_plus_test.txt");
		//FileUtils1.setinputFileValuesSeparator(","); // by default is white spaces
		//SupervisedLearningSet tstExamStr = new SupervisedLearningSet("county_data.txt");
		new GraphicUtils().	drawGraphicXOY(tstExamStr, true);	
		
		
		
	}


}
