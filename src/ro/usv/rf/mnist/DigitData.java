package ro.usv.rf.mnist;

import java.util.Arrays;
import java.util.List;

import static java.lang.Math.min;

/**
 * Holds data for a digit as well as its label.
 * https://bitbucket.org/tobias_hill/mnist-example/src/Article/src/main/java/com/tailworks/ml/mnistexample/
 */
public class DigitData {

    private double[] data; // pattern: x =( x, x2,  ...,  xp)
    private int label;     // class of pattern
    
    
    public static boolean printData=true;
    private String extraText=null;

    public DigitData(double[] data, int label) {
        this.data = data;
        this.label = label;
    }
    public DigitData(double[] data, int label, String textSuplimentar) {
        this.data = data;
        this.label = label;
        this.extraText = textSuplimentar;
    }

    public double[] getData() {
        return data;
    }

    public int getLabel() {
        return label;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        String line = " ---------------------------- ";
        if(extraText!=null)
        	sb.append(extraText+"\n");
        sb.append(" Label: ").append(label).append("                     "); //counted spaces
        if(printData)
                sb.append("\nData: \n").append(toStringData()); //.append(Arrays.toString(data)); 
        sb.append("\n").append(line);
        int cnt = 0;
        for (int r = 0; r < 28; r++) {
            sb.append("\n|");
            for (int c = 0; c < 28; c++) {
                sb.append(toChar(data[cnt++]));
            }
            sb.append("|");
        }

        sb.append("\n").append(line).append("\n");
        return sb.toString();
    }

    /**
     * Simply converts gray-scale to an ascii-shade
     * Autor: Tobias Hill
     */
    private char toChar(double val) {
        return " .:-=+*#%@".charAt(min((int) (val * 10), 9));
    }

/*
 * Generates a String care that represents as matrix the data[] 
 */
    public String toStringData() {
    	final StringBuilder sb = new StringBuilder();
        int cnt = 0;
        for (int r = 0; r < 28; r++) {
            for (int c = 0; c < 28; c++) {
                sb.append(String.format("%3.1f ", data[cnt++]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    /*
     * useful for setting label(iclasa) = 0 for test pattterns
     */
    public void setLabel(int label) {
		this.label = label;
	}

		/*
     * Create a new DigitData object with  features equals
     * with the absolute value of the difference between the feature arguments
     * Useful for explaining the distance values between the patterns 
     * Autor:PSG, feb. 2021    
     */
        public static DigitData getDif(DigitData img1, DigitData img2) {
    	int p = img1.getData().length;
    	double[] difdata = new double[p];
    	for(int j=0; j<p; j++)
    		difdata[j] = Math.abs ( img1.getData()[j] - img2.getData()[j]);   
    	return new DigitData(difdata, p);
    }
        
    
    
    /*
     * Creates a new String that contains 2 nearby images
     * s1 and s2 are the String conversion of two DigitData objects 
     * Autor:PSG, feb. 2021    
     */
    private static String toString2ImgNEarbyStrings(String s1, String s2) {
    	String[] rowsS1 = s1.split("\n");
    	String[] rowsS2 = s2.split("\n");
    	StringBuilder sb = new StringBuilder();
    	for(int i=0; i< rowsS1.length; i++)
    		sb.append(rowsS1[i]).append("  ").append(rowsS2[i]).append("\n");
    	return sb.toString();
    }
    /*
     * Creates a new String that contains 2 nearby images
     * (img1 and img2). Useful for printing two images and difference
     * Autor:PSG, feb. 2021    
     */
    public static String toString2ImgNearby(DigitData img1, DigitData img2) {
    	return toString2ImgNEarbyStrings(img1.toString(), img2.toString() );   
    }
    
    /*
     * Creates a new String that contains 3 nearby images
     * (img1, img2 and img3)
     * Autor:PSG, feb. 2021    
     */
    public static String toString3ImgNearby(DigitData img1, DigitData img2, DigitData img3) {

    	return toString2ImgNEarbyStrings (
    			    toString2ImgNEarbyStrings(img1.toString(), img2.toString() ),
    			    img3.toString()
    			    );
    }

    /*
     * Creates a new String that contains n nearby images
     * taken from the list, starting with istart and ending with ifinal (inclusive)
     * Autor:PSG, feb. 2021    
     */
    public static String toStringNImgNearby(List<DigitData> set, int istart, int ifinal) {
    		String rez = set.get(istart).toString();
    		if (ifinal<0)
    			ifinal = set.size()-1;
    		for(int i=istart+1; i<=ifinal; i++) 
    			    rez = toString2ImgNEarbyStrings(rez, set.get(i).toString());
    	    return rez;
    }

}
