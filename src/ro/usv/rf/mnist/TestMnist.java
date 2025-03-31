package ro.usv.rf.mnist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ro.usv.rf.utils.DistanceUtils;



public class TestMnist {

	public static void printOneCipher(List<DigitData> imageSet) {
		int afis=0, i=0;
		while(afis<10 && i<imageSet.size()) {
			DigitData cipherImage = imageSet.get(i++);
			if( cipherImage.getLabel() != afis) 
				continue;
			System.out.println(imageSet.get(i-1));
			afis++;
		}
	}
	
	
	
public static void main(String[] args) {
	String filePrefix = "mnist\\t10k";
	List<DigitData>  imageSet = MNIST_FileUtil.loadImageData(filePrefix);
	//printOneCipher(setImagini);
		
	int[] toate = new int[10];
	for(int i=0; i<10; i++)
		toate[i] = i;
	DigitData.printData = false;
	//List<DigitData> exemple10 = extractSubset( setImagini, toate, 1, false);
	//System.out.println(DigitData.toStringNImgNearby(exemple10, 0, -1));
	
	int[] claseDeInteres = new int[] {1,3,4,6,8};
	List<DigitData> subset = extractSubset( imageSet, claseDeInteres, 1, false);
	//System.out.println(DigitData.toString2ImgNearby(subset.get(0), subset.get(1)));
	System.out.println(DigitData.toStringNImgNearby(subset, 0, -1));
	
	int M = claseDeInteres.length;
	
	double d, dist[][] =new double [M][M];
	
	for(int i=0; i< subset.size(); i++) 
		for(int j=i; j< subset.size(); j++) {
			DigitData img1 = subset.get(i),
					  img2 = subset.get(j);
			dist[i][j] = dist[j][i] = getImageEuclidianDistance(img1, img2);
			System.out.println("Distance = " + dist[i][j]);	
			// System.out.println(  DigitData.toString2ImgAlaturi(img1, img2));
			//System.out.println(  DigitData.toString3ImgAlaturi(img1, img2, 
            //        DigitData.dif(img1, img2)));
	    }
		printMatrix(dist, "Distance matrix " + Arrays.toString(claseDeInteres));
		System.out.println("\n******************** Compare unknown image ******************");
	    test(imageSet, subset, 3, claseDeInteres);

}

	public static List<DigitData> extractSubset(List<DigitData> imageSet, int[] classes, 
			                                            int classPatternsNr, boolean print){
		List<DigitData> subset = new ArrayList<>();
		int classNr = classes.length;
		int doneNr=0;
		int[] nrFormeInClasa = new int[10] ;
		int i;
		for(i=0; i<10; i++) 
			nrFormeInClasa[i] = classPatternsNr;
		for(i=0; i<classes.length; i++) 
			nrFormeInClasa[ classes[i] ]= 0;
		i=0;
		while( doneNr < classNr && i < imageSet.size()) {
			DigitData imgCrt = imageSet.get(i++);
			int currentlass = imgCrt.getLabel();
			if(currentlass != classes [ doneNr] )  //for filling the list in class order
				continue;
			if(nrFormeInClasa [ currentlass] >= classPatternsNr ) 
				        continue;
			subset.add( imgCrt );
			if(++ nrFormeInClasa [currentlass] == classPatternsNr )
				doneNr++;
			if(print)
				System.out.println(imageSet.get(i-1));
		}
		return subset;
		
	}

	public static void printMatrix(double[][] mat, String text) {
		System.out.println("\n"+text);
		for(double[] currentLine: mat) {
			for( double currentElement: currentLine)
				System.out.print(String.format("%5.2f  ", currentElement));
			System.out.println();
		}
		
	}
	
	private static void test(List<DigitData> bigSet, List<DigitData> E, int iclass, 
			                  int[] followedClasses)  {
		DigitData imgtest = null;
		// se alege o imagine de test din clasa iclasa
		for(int i = bigSet.size()-1; i>=0; i-- ) {
			imgtest = bigSet.get(i);
			if(imgtest.getLabel() == iclass) 
				break;
		}
		imgtest.setLabel(0);  
		System.out.println(iclass + " " + imgtest);
		
		double dist[][] =new double [1][E.size()];
		int i=0;
		for(DigitData img: E) {
				System.out.println("Distance = " + (dist[0][i++] = getImageEuclidianDistance(imgtest, img)));	
				// System.out.println(  DigitData.toString2ImgAlaturi(imgtest, img));
				System.out.println(  DigitData.toString3ImgNearby(imgtest, img, 
						                                           DigitData.getDif(img, imgtest)));
				
		}
		printMatrix(dist, "DistanceMatrix " + Arrays.toString(followedClasses));
	}
	
	public static double getImageEuclidianDistance(DigitData img1, DigitData img2)  {
		return DistanceUtils.distEuclid(img1.getData(), img2.getData());
	}

}
