package ro.usv.rf.labs;

import java.security.AlgorithmConstraints;
import java.util.Arrays;

import ro.usv.rf.classifiers.Classifier_1NN;
import ro.usv.rf.learningsets.SupervisedLearningSet;
import ro.usv.rf.learningsets.UnsupervisedLearningSet;
import ro.usv.rf.utils.DistanceUtils;

public class Lab4 {

	   public static void demoUnsupervisedLearningSet() {
			
			UnsupervisedLearningSet setU = new UnsupervisedLearningSet("file.txt");
			System.out.println(setU);
			//System.out.println(setU.getF().length);
		}
	
	   public static void demoSupervisedLearningSet() {
	    	
			double [][] x = new double[][] {
				{1,5}, {5,2}, {3, 5}, {3, 3.5} };
	     	SupervisedLearningSet setSuperv1 = new SupervisedLearningSet(x, new int[] {1,2,1,2});
	     	System.out.println("Set 1 - X[][] is provided and iClass[]:\n"+ setSuperv1);

	    	SupervisedLearningSet setSuperv2 = new SupervisedLearningSet("file.txt", null);
			System.out.println("\nSet 2 - file.txt with numeric class:\n"+ setSuperv2);

		    String[] numeClase = new String[] {"", "A", "B", "C"};
			SupervisedLearningSet setSuperv3 = new SupervisedLearningSet("testexam_numeric.txt", numeClase);
			System.out.println("\nSet 3 - testexam_numeric.txt + classNames[M+1]:\n"+ setSuperv3);
			
			SupervisedLearningSet setSuperv4 = new SupervisedLearningSet("testexam_strings.txt");
			System.out.println("\nSet 4 - file.txt with strings as class labels:\n"+ setSuperv4);
		
			setSuperv4.printMeansAndStandardDeviations();
			setSuperv4.normalize();
			System.out.println("\nSet 4 - normalized:\n"+ setSuperv4);
			setSuperv4.printMeansAndStandardDeviations();
			setSuperv4.autoScale();
			System.out.println("\nSet 4 - auto-scaled:\n"+ setSuperv4);
			setSuperv4.printMeansAndStandardDeviations();

	   }

	   public static void demoClassifier_1NN() {
		    String[] numeClase = new String[] {"", "A", "B", "C"};
			SupervisedLearningSet setSuperv3 = new SupervisedLearningSet("testexam_numeric.txt", numeClase);

			Classifier_1NN classif_1NN_CityBlock = new Classifier_1NN(DistanceUtils::distCityBlock);
			setSuperv3.getF()[3]=2;  // to prove ambiguities solution
			classif_1NN_CityBlock.train(setSuperv3);
			int cls1 = classif_1NN_CityBlock.predict(new double[] {2,4}); 
			System.out.println("class index ( dist CityBlock)="+ cls1 +
					                        " <"+ setSuperv3.getClassNames()[cls1] +">");
			
			Classifier_1NN classif_1NN_Euclid = new Classifier_1NN();  //Euclidian Distance
			classif_1NN_Euclid.train(setSuperv3);
			int cls2 = classif_1NN_Euclid.predict(new double[] {2,4});
			System.out.println("class index  ( dist. Euclidian)=" + cls2 +
                                " <"+ setSuperv3.getClassNames()[cls2] +">");
			
			classif_1NN_Euclid.setDebug(false);
			double[][] testSet = new double[][] {	{2,	4}, {4,	2}, {10, 5}, {5,5}, {6,10}, {5,12} };
			Classifier_1NN.classifyAndDisplayResult(classif_1NN_Euclid, setSuperv3.getClassNames(), testSet);
			Classifier_1NN.classifyAndDisplayResult(classif_1NN_Euclid, setSuperv3.getClassNames(), setSuperv3.getX());
            
            System.out.println("\nSelf test pt. setSuperv3)");
			classif_1NN_Euclid.evaluateAccuracy( true );  // self-evaluation
            
			System.out.println("\nSetul de 6 forme de test");
            classif_1NN_Euclid.evaluateAccuracy(testSet, new int[] {2,1,1,1,3,3}, true );   // test with other patterns
			           
            SupervisedLearningSet setSuperv4 = new SupervisedLearningSet("testexam_plus_A.txt", numeClase);
            classif_1NN_Euclid.train(setSuperv4);
            System.out.println("\nSelf test pt. setSuperv4 (plus A)");
            classif_1NN_Euclid.evaluateAccuracy(true);
            
            // to check RunTime exceptions:
			//classif_1NN_CityBlock.train((double[][])null);   //no data set provided
			//classif_1NN_CityBlock.train(new double[][] {});  //no patterns in data set
			//classif_1NN_CityBlock.train(new double[1][0]);   //patterns with no features 
			//classif_1NN_CityBlock.train(new double[1][1]);   //no supervised learning set (M=0)
            

	   }

	public static void main(String[] args) {

		String separatorLine = "-------------------------------------------------------------";
		// demo existing code
		demoUnsupervisedLearningSet();
		System.out.println(separatorLine);
		demoSupervisedLearningSet();
		System.out.println(separatorLine);
		demoClassifier_1NN();
	}

}
