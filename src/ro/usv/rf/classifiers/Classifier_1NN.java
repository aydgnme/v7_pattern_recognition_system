package ro.usv.rf.classifiers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import ro.usv.rf.utils.DistanceMatrixTriangle;
import ro.usv.rf.utils.DistanceUtils;
import ro.usv.rf.utils.IDistance;

public class Classifier_1NN extends AbstractClassifier {
	
	IDistance d;
	private boolean debug = true;
	private DistanceMatrixTriangle mdt; // used for self-test only
	
	public Classifier_1NN(IDistance d) {
		super();
		this.d = d;
	}

	public Classifier_1NN() {
		this(DistanceUtils::distEuclid);
	}


	@Override
	public void training() {
		if(M==0)
			throw new RuntimeException("train(): No supervised learning set provided (M=0)");
		mdt = null; // will be calculated for self test only
		// all rest were done in super.train(X,F,iClass)
	}

	public int[] predict ( double[][] testSet) {
		if(testSet != X )
			return super.predict(testSet);
		// self test
		if( mdt == null)  // to avoid multiple calc. for same set
			mdt = new DistanceMatrixTriangle(X, d);
		int[] iClassCalculated = new int[n];
		for( int i=0; i<n; i++) {
			iClassCalculated[i] = predict(i);
		}	
		return iClassCalculated;
	}
	
	// predict used obly for self test
	private int predict(int iz) {
		int idmin = IntStream.range(0, n)
				.filter(i -> i!=iz)
				.reduce(-1, (iprec,icrt)-> iprec ==-1? icrt : 
					                 (mdt.getDistance(iz,iprec) < mdt.getDistance(iz, icrt) ? iprec : icrt));
				
		return iClass[ idmin ];
	}
	
	//TODO: implement predict method
	@Override
	public int predict(double[] z) {
		int ind[]=new int[] {-1,-1};   // ind[0]=index of current pattern
		                              //  ind[1]=index of pattern situated at minimum distance
	    double dmin= Arrays.stream(X)
				.mapToDouble(x -> d.calculateDistance(x, z) )
				.reduce(Double.MAX_VALUE, 
						(dprec, dcrt)-> { ind[0]++;
						                  if(dcrt <= dprec) { 
						                	        ind[1] = ind[0]; 
						                	        return dcrt;
						                  } 
						                  return dprec;});
	    if(ind[1]<0 || ind[1]>=n)
	    	System.out.println("X="+X+ " n="+ n+"p="+p + " ind="+Arrays.toString(ind)+ " z="+ Arrays.toString(z));
	    return iClass[ ind[1] ];
	}
	
    static public void classifyAndDisplayResult(AbstractClassifier classifier, String[] classNames, double[][] testSet) {
    	System.out.println("\nPatterns class:"+ Arrays.deepToString(testSet) + ":");
    	Arrays.stream(classifier.predict(testSet))                 //IntStream
    	.mapToObj(k-> (classNames==null ? k :classNames[k]) +" ")  //Stream<String>
        .forEach(System.out::print);
    }

       public void setDebug(boolean debug) {
		this.debug = debug;
	}

	@Override
	public String toString() {
		return "Classifier_1NN []";
	}


}
