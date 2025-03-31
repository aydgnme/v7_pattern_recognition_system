/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.usv.rf.classifiers;

import ro.usv.rf.utils.DataUtils;

public class ClassClassifier extends AbstractClassifier {



    @Override
    public void training() {

        w = new double[M][p + 1];
        double []miu = new double[M];

        for (int k = 0; k < M; k++) {
            miu[k] = 0;
            for (int j = 0; j < p + 1; j++) {
                w[k][j] = 0;
            }
        }
        for (int i = 0; i < n; i++) {
            int k = iClass[i] - 1;
            for (int j = 0; j < p; j++) {
                w[k][j] += X[i][j] * f[i];
            }
            miu[k] += f[i];
        }

        for (int k = 0; k < M; k++) {
            for (int j = 0; j < p; j++) {
                w[k][j] /= miu[k];
                w[k][p] -= w[k][j] * w[k][j] / 2;
            }
        }
        DataUtils.printMatrix(w);
    }
    
    
    //
    // Returns the predicted index of the class 
    // to which the pattern x might belong.
    // Predict only for linear models of classifiers
    //
    @Override
    public int predict(double[] x){
        // The function has 2 bugs. ...TODO: find them
        int kmax=-1;
        double psik, psimax= Double.NEGATIVE_INFINITY;
        for(int k=0; k<M; k++){
            psik =w[k][p];
            for(int j=0; j<p; j++)
                psik += x[j] * w[k][j];
            if (psik>psimax){
                psimax = psik;
                kmax = k;
            }
        }
        return kmax+1;
    }

	@Override
	public String toString() {
		return "ClassClassifier []";
	}
    

}
