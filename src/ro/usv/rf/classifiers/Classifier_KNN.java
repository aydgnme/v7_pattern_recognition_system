package ro.usv.rf.classifiers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import ro.usv.rf.utils.DistanceUtils;
import ro.usv.rf.utils.IDistance;
import ro.usv.rf.utils.Neighbour;

public class Classifier_KNN extends AbstractClassifier {
	
	IDistance d;
	int k;
	private boolean debug = true;
	
	public Classifier_KNN(int k, IDistance d) {
		super();
		this.d = d;
		this.k = k;
	}

	public Classifier_KNN(int k) {
		this(k, DistanceUtils::distEuclid);
	}


	@Override
	public void training() {
		if(M==0)
			throw new RuntimeException("train(): No supervised learning set provided (M=0)");
		// all rest were done in super.train(X,F,iClass)
	}

	//TODO: implement predict method
	@Override
	public int predict(double[] z) {
		PriorityQueue<Neighbour> pq = new PriorityQueue<Neighbour>(k);

		
		Map<Integer,Integer> classOccurenceMap = new HashMap<Integer,Integer>();


		return 1;

	}
		
		

	
    static public void classifyAndDisplayResult(AbstractClassifier classifier, String[] classNames, double[][] testSet) {
    	System.out.println("\nPatterns class:"+ Arrays.deepToString(testSet) + ":");
    	Arrays.stream(classifier.predict(testSet))
    	.mapToObj(k-> (classNames==null ? k :classNames[k]) +" ")
        .forEach(System.out::print);
    }

    public void setDebug(boolean debug) {
		this.debug = debug;
	}

	@Override
	public String toString() {
		return "Classifier_KNN [k=" + k + "]";
	}

}
