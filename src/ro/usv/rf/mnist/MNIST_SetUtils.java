package ro.usv.rf.mnist;

import ro.usv.rf.classifiers.Classifier_KNN;
import ro.usv.rf.learningsets.SupervisedLearningSet;
import ro.usv.rf.utils.DistanceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class MNIST_SetUtils {

	public static double[][] convertToMatrixListOfDigitData(List<DigitData> lstDigitData,
															int[] iClass, int nMax) {
		final int ind[] = new int[]{-1};
		return lstDigitData.stream()  // Stream<DigitData>
				.limit(nMax)
				.map(digObj -> {
					iClass[++ind[0]] = digObj.getLabel() + 1;
					return digObj.getData();
				})   // Stream<double[]>
				.toArray(double[][]::new);
	}


	public static SupervisedLearningSet convertMNIST_SupervisedSet(String filePrefix, int nMax) {
		String[] classNames = {"", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		/*
		String[] classNames = IntStream.range(0, 11)  //->int
				.mapToObj(i-> i==0?"":""+(i-1))  //Stream<String>
				.toArray(String[]::new);
		*/
		List<DigitData> imageSet = MNIST_FileUtil.loadImageData(filePrefix);
		if (nMax <= 0)
			nMax = imageSet.size();

		int[] iClass = new int[nMax];
		System.out.println("Start conversion to double[][]");
		double[][] Ximag = convertToMatrixListOfDigitData(imageSet, iClass, nMax);
		System.out.println("Start conversion to SupervisedLearningSet");
		return new SupervisedLearningSet(Ximag, iClass, classNames);
	}

	public static SupervisedLearningSet convert_MNIST_SupervisedSet(String filePrefix, int nMax) {
		String[] classNames = {"", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		Object tab[] = MNIST_FileUtil.loadImageSet(filePrefix, nMax);
		int[] iClass = (int[]) tab[1];
		IntStream.range(0, iClass.length).forEach(i -> iClass[i]++);
		return new SupervisedLearningSet((double[][]) tab[0], iClass, classNames);
	}

	public static void afisDurataExecutie(long startTime, int N) {
		long duration = System.currentTimeMillis() - startTime;
		System.out.printf("Exec time for %d patterns %.3f s\nExec time  per pattern"
						+ " %.6f ms\n\n",
				N, (float) (duration / 1000.), (float) (duration / (double) N));
	}

	public static Void printErrors(SupervisedLearningSet set, int[] iClassCalculated) {
		printErrors(set, iClassCalculated, 0);
		return null;
	}

	public static void printErrors(SupervisedLearningSet set, int[] iClassCalculated, int nrMax) {
		int nPatterns = set.getIClass().length;
		int nr = 0;
		if (nrMax < 1)
			nrMax = Integer.MAX_VALUE;
		DigitData.printData = false;
		List<DigitData> printList = new ArrayList<DigitData>();
		for (int i = 0; i < nPatterns; i++) {
			if (set.getIClass()[i] != iClassCalculated[i]) {
				printList.add(new DigitData(set.getX()[i], set.getIClass()[i] - 1,
						"Bad classification in class" + (iClassCalculated[i] - 1) + " "));
				System.out.println();
				if (++nr == 3) {  // display max 3 images on row
					System.out.println(DigitData.toStringNImgNearby(printList, 0, nr - 1));
					printList.clear();
					nr = 0;
				}
				if (--nrMax <= 0)
					break;
			}
		}
		if (nr > 0)
			System.out.println(DigitData.toStringNImgNearby(printList, 0, nr - 1));

	}


//    Accuracy of classification by 1NN the test set=92.25%
//    Nb. of correct classified patterns = 1845 of 2000


	public static void demo(int nbPatternsInTrainingSet, int nbPatternsInTestSet,
							BiFunction<SupervisedLearningSet, int[], Void> display, int k) {
		String filePrefixTest = "mnist\\t10k";
		String filePrefixTrain = "mnist\\train";
		System.out.println("Start");
		SupervisedLearningSet trainingSet = convert_MNIST_SupervisedSet(filePrefixTrain, nbPatternsInTrainingSet);
		Classifier_KNN cls = new Classifier_KNN(k, DistanceUtils::distEuclid);
		cls.train(trainingSet);
		SupervisedLearningSet testSet10k = convert_MNIST_SupervisedSet(filePrefixTest, nbPatternsInTestSet);
		testSet10k.doSameClassIndexAs(trainingSet);
		System.out.printf("Training set with %d patterns%nTest set with %d patterns%n",
				nbPatternsInTrainingSet, nbPatternsInTestSet);
		double[] z = testSet10k.getX()[nbPatternsInTestSet - 1];
		System.out.println(new DigitData(testSet10k.getX()[nbPatternsInTestSet - 1],
				testSet10k.getIClass()[nbPatternsInTestSet - 1] - 1,
				"Forma z"));
		System.out.println("Clasificare forma z:");
		int indiceClassPredictedForZ = cls.predict(z);
		String classNameForZ = testSet10k.getClassNames()[indiceClassPredictedForZ];
		System.out.println("indiceClassPredictedForZ=" + indiceClassPredictedForZ + "\n" +
				"classNameForZ=" + classNameForZ+ "\n" );
		long startTest = System.currentTimeMillis();
		double accuracy = cls.evaluateAccuracy(testSet10k, false);  //execute predict()
		afisDurataExecutie(startTest, testSet10k.getN());
		System.out.println("Accuracy of classification by 1NN the test set="+ accuracy*100 + "%");
		System.out.println("Nb. of correct classified patterns = " + (int)(testSet10k.getN()*accuracy)
				+ " of " + testSet10k.getN() + "\nClassification error =" + (1 - accuracy)*100 + "%");
		display.apply(testSet10k, cls.getiClassCalculated());

	}

	public static void main(String[] args) {
			demo(600, 100, MNIST_SetUtils::printErrors,  1);
	}
}