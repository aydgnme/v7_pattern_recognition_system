package ro.usv.rf.labs;

import ro.usv.rf.graphic.PatternImage;
import ro.usv.rf.mnist.MNIST_SetUtils;

public class Lab6 {
	

	public static void main(String[] args) {
		
		//int read = 150;
		//MNIST_SetUtils.demo(600, 100, MNIST_SetUtils::printErrors, 5);
		


		PatternImage.demo("t10k", 30);  // the name of MNIST set: train or t10k
		                               // 30 patterns will be drawn
		
	}
}