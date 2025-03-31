# Pattern Recognition System - Lab 7

This project implements various pattern recognition classifiers and algorithms for the Pattern Recognition course at USV.

## Project Structure

```
src/
├── ro/
│   └── usv/
│       └── rf/
│           ├── classifiers/
│           │   ├── ClassClassifier.java
│           │   └── Classifier_KNN.java
│           ├── learningsets/
│           │   └── SupervisedLearningSet.java
│           ├── utils/
│           │   ├── FileUtils1.java
│           │   └── SetUtils.java
│           └── labs/
│               └── Lab7.java
```

## Features

The project implements the following classifiers and algorithms:

1. Class Classifier (Minimum Distance Classifier)
2. K-Nearest Neighbors (KNN) Classifier
3. Data Preprocessing:
   - Auto-scaling
   - Training/Test set splitting

## Lab 7 Implementation

Lab 7 focuses on implementing and comparing different classifiers. The implementation includes:

### Problem 1
- Part A: Basic classification using ClassClassifier
- Part B: County data classification

### Problem 2
- Part A: Reading and processing Iris dataset
- Part B: Splitting dataset into training and evaluation sets
- Part C: Training and evaluating ClassClassifier
- Part D: Auto-scaling and re-evaluation

### Problem 3
- Implementation of KNN classifier
- Testing with different k values (1,3,5,7,9,11,13,15)
- Comparison between KNN and Class classifiers
- Finding optimal k value

## Input Files

The project uses the following input files:
- `fileLab7PunctA`: Basic classification data
- `county_data.txt`: County classification data
- `lab7_iris.csv`: Iris dataset

## Usage

1. Compile the project:
```bash
javac src/ro/usv/rf/labs/Lab7.java
```

2. Run the program:
```bash
java -cp src ro.usv.rf.labs.Lab7
```

## Output

The program will output:
- Classification results for different test cases
- Accuracy measurements for different classifiers
- Comparison between KNN and Class classifiers
- Optimal k value for KNN classifier

## Dependencies

- Java JDK 8 or higher
- No external dependencies required

## Notes

- The program uses space and comma as separators for different input files
- Auto-scaling is applied to normalize the data
- Training/test split ratio is 85/15 