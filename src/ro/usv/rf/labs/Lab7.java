package ro.usv.rf.labs;

import java.util.Arrays;
import ro.usv.rf.classifiers.ClassClassifier;
import ro.usv.rf.classifiers.Classifier_KNN;
import ro.usv.rf.learningsets.SupervisedLearningSet;
import ro.usv.rf.utils.FileUtils1;
import ro.usv.rf.utils.SetUtils;

public class Lab7
{
	public static void main(String args[])
	{
		System.out.println("Lab 7 - Classifier - Problem 1");
		
		// Problem 1 - Part A
		FileUtils1.setinputFileValuesSeparator(" ");
		SupervisedLearningSet setLab = new SupervisedLearningSet("fileLab7PunctA");

		ClassClassifier clsLab = new ClassClassifier();
		clsLab.train(setLab);
		
		// Test predictions
		int clasa = clsLab.predict(new double[]{1, 3});
		System.out.println("Prediction for [1, 3]: " + setLab.getClassNames()[clasa]);

		clasa = clsLab.predict(new double[]{4, 5});
		System.out.println("Prediction for [4, 5]: " + setLab.getClassNames()[clasa]);

		clasa = clsLab.predict(new double[]{0, 0});
		System.out.println("Prediction for [0, 0]: " + setLab.getClassNames()[clasa]);

		// Problem 1 - Part B
		System.out.println("\nProblem 1 - Part B: County Data Classification");
		FileUtils1.setinputFileValuesSeparator(",");
		SupervisedLearningSet setLabCounty = new SupervisedLearningSet("county_data.txt");
		ClassClassifier clsLabCountry = new ClassClassifier();
		clsLabCountry.train(setLabCounty);
		int clasaCountry = clsLabCountry.predict(new double[]{24.20, 45.87});
		System.out.println("Prediction for county data [24.20, 45.87]: " + setLabCounty.getClassNames()[clasaCountry]);

		System.out.println("\nLab 7 - Classifier - Problem 2");
		
		// Problem 2 - Part A: Read Iris Dataset
		System.out.println("Problem 2 - Part A: Reading Iris Dataset");
		FileUtils1.setinputFileValuesSeparator(",");
		SupervisedLearningSet irisSet = new SupervisedLearningSet("lab7_iris.csv");
		
		// Problem 2 - Part B: Split into Training and Evaluation Sets
		System.out.println("Problem 2 - Part B: Splitting Dataset");
		SupervisedLearningSet[] sets = SetUtils.splitSet(irisSet, 15);
		SupervisedLearningSet trainSet = sets[0];
		SupervisedLearningSet testSet = sets[1];
		
		// Problem 2 - Part C: Train and Evaluate Classifier
		System.out.println("Problem 2 - Part C: Training and Evaluating Classifier");
		ClassClassifier clsIris = new ClassClassifier();
		clsIris.train(trainSet);
		double accuracy = clsIris.evaluateAccuracy(testSet, false);
		System.out.println("Accuracy without scaling: " + accuracy);

		int[] labels = clsIris.predict(testSet.getX());
		System.out.println("Predicted labels: " + Arrays.toString(labels));

		// Problem 2 - Part D: Auto-scaling and Re-evaluation
		System.out.println("\nProblem 2 - Part D: Auto-scaling and Re-evaluation");
		FileUtils1.setinputFileValuesSeparator(",");
		SupervisedLearningSet irisSetS = new SupervisedLearningSet("lab7_iris.csv");
		irisSetS.autoScale();
		
		SupervisedLearningSet[] setsS = SetUtils.splitSet(irisSetS, 15);
		SupervisedLearningSet trainSetS = setsS[0];
		SupervisedLearningSet testSetS = setsS[1];

		ClassClassifier clsIrisS = new ClassClassifier();
		clsIrisS.train(trainSetS);
		double scaledAccuracy = clsIrisS.evaluateAccuracy(testSetS, false);
		System.out.println("Accuracy with auto-scaling: " + scaledAccuracy);

		// Problem 3 - using the above training and evaluation sets, apply KNN algorithm to the evaluation set and study the error for different values of k (1,3,5,7,9,11,13,15). Which is the optimal value for K ? Compare the accuracy of the kNN and Class classifiers.
		System.out.println("\nProblem 3: Applying KNN Algorithm and Comparing Classifiers v1");

		int[] kValues = {1, 3, 5, 7, 9, 11, 13, 15};
		
		System.out.println("Testing KNN with different k values:");
		double bestAccuracy = 0;
		int optimalK = 0;
		
		for (int k : kValues) {
			Classifier_KNN knn = new Classifier_KNN(k);
			knn.train(trainSet);
			double knnAccuracy = knn.evaluateAccuracy(testSet, false);
			System.out.printf("k = %d: Accuracy = %.4f\n", k, knnAccuracy);
			
			if (knnAccuracy > bestAccuracy) {
				bestAccuracy = knnAccuracy;
				optimalK = k;
			}
		}
		
		System.out.printf("\nOptimal k value: %d (Accuracy: %.4f)\n", optimalK, bestAccuracy);
		System.out.printf("Class Classifier Accuracy: %.4f\n", accuracy);
		
		if (bestAccuracy > accuracy) {
			System.out.println("KNN classifier performed better than Class classifier");
		} else if (bestAccuracy < accuracy) {
			System.out.println("Class classifier performed better than KNN classifier");
		} else {
			System.out.println("Both classifiers performed equally well");
		}


		// Problem 3 - using the above training and evaluation sets, apply KNN algorithm to the evaluation set and study the error for different values of k (1,3,5,7,9,11,13,15). Which is the optimal value for K ? Compare the accuracy of the kNN and Class classifiers.
		System.out.println("\nProblem 3: Applying KNN Algorithm and Comparing Classifiers v2");
		int[] kValues1 = {1, 3, 5, 7, 9, 11, 13, 15};
		Classifier_KNN knn = new Classifier_KNN(kValues1.length);
		knn.train(trainSet);
		knn.evaluateAccuracy(testSet, true);
	}
}
