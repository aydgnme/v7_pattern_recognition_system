package ro.usv.rf.labs;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.IntStream;

import javax.security.auth.x500.X500Principal;
import javax.swing.event.ListDataEvent;

import ro.usv.rf.utils.DataUtils;
import ro.usv.rf.utils.FileUtils1;
import ro.usv.rf.utils.Pattern;
import ro.usv.rf.utils.StatisticsUtils;

public class Lab2 {


	public static void main(String[] args) {
		double[][] patternSet = FileUtils1.readMatrixFromFileStream("inm.txt");
		System.out.println("Matrix pattern Set:");
		DataUtils.printMatrix(patternSet);
		int numberOfPatterns = patternSet.length;
		int numberOfFeatures = patternSet[0].length;
		
		for (int j=0; j<numberOfFeatures; j++)
		{
			double[] feature = new double[numberOfPatterns];
			for (int i=0; i<numberOfPatterns; i++)
			{
				feature[i] = patternSet[i][j];
			}
			System.out.println("Feature average is:" + StatisticsUtils.calculateFeatureAverage(feature));
		}
		
		Map<Pattern, Double> patternsMap = StatisticsUtils.getPatternsMapFromInitialSet(patternSet);
		
		// ----------------------- Results -----------------------
		double[] weightedAverages = StatisticsUtils.calculateWeightedAverages(patternsMap, numberOfFeatures);
		System.out.println("The array of features averages:" + Arrays.toString(weightedAverages));	
		
		double[][] reducedSet = StatisticsUtils.getPatterns(patternsMap);
		double[]   weights    = StatisticsUtils.getPatternWeigths(patternsMap);
		System.out.println("\n*** Reduced pattern set with patterns' weigths");
		DataUtils.printPatternsAndWeigthsSet(reducedSet, weights);
		DataUtils.printMeansAndStandardDeviations( reducedSet, weights);
		
		System.out.println("\n*** Normalized Set");
		double[][] xNormalized = DataUtils.normalizeSet(reducedSet);
		DataUtils.printPatternsAndWeigthsSet(xNormalized, weights);
		DataUtils.printMeansAndStandardDeviations( xNormalized, weights);

		System.out.println("\n*** Auto-Scaled Set");
		double[][] xAuto = DataUtils.autoScaleSet(reducedSet, weights);
		DataUtils.printPatternsAndWeigthsSet(xAuto, weights);
		DataUtils.printMeansAndStandardDeviations( xAuto, weights);
	}


}
