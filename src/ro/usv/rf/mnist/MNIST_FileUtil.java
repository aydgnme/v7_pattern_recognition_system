package ro.usv.rf.mnist;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import static java.lang.String.format;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads the Mnist dataset and put it in a List of DigitData
 *
 * Autor: Tobias Hill, dec. 2018
 * https://bitbucket.org/tobias_hill/mnist-example/src/Article/src/main/java/com/tailworks/ml/mnistexample/
 *
 */
public class MNIST_FileUtil {

    private final static int LABEL_FILE_MAGIC_INT = 2049;
    private final static int IMG_FILE_MAGIC_INT = 2051;

    public static List<DigitData> loadImageData(String filePrefix) {
        List<DigitData> images = null;
        String imgFileName = "mnist/" + filePrefix + "-images.idx3-ubyte";
        String lblFileName = "mnist/" + filePrefix + "-labels.idx1-ubyte";
        try (
                DataInputStream imageIS = new DataInputStream(new FileInputStream(imgFileName));
                DataInputStream labelIS = new DataInputStream(new FileInputStream(lblFileName));        ) {
            if (imageIS.readInt() != IMG_FILE_MAGIC_INT)
                throw new IOException("Unknown file format for " + imgFileName);

            if (labelIS.readInt() != LABEL_FILE_MAGIC_INT)
                throw new IOException("Unknown file format for " + lblFileName);

            int nImages = imageIS.readInt();
            int nLabels = labelIS.readInt();

            if (nImages != nLabels)
                throw new IOException(format("File %s and %s contains data for different number of images", imgFileName, lblFileName));

            images = new ArrayList<>(nImages);

            int rows = imageIS.readInt();
            int cols = imageIS.readInt();

            byte[] data = new byte[rows * cols];


            for (int i = 0; i < nImages; i++) {
                double[] img = new double[rows * cols];
                imageIS.read(data, 0, data.length);
                for (int d = 0; d < img.length; d++)
                    img[d] =  (data[d] & 255) / 255.0;

                images.add(new DigitData(img, labelIS.readByte()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return images;
    }

    /**
     * Loads the Mnist dataset and put image data in a double[][] matrix
     * and image labels in int[] iClass array
     *
     * Slightly modified by PSG in March 2021 after the original loadImageData() written by
     * Author: Tobias Hill, dec. 2018
     * https://bitbucket.org/tobias_hill/mnist-example/src/Article/src/main/java/com/tailworks/ml/mnistexample/
     *
     */
    public static Object[] loadImageSet(String filePrefix, int nMax) {
        String imgFileName = "mnist/" + filePrefix + "-images.idx3-ubyte";
        String lblFileName = "mnist/" + filePrefix + "-labels.idx1-ubyte";
        double[][] X = null;
        int[] iClass = null;
        try (
                DataInputStream imageIS = new DataInputStream(new FileInputStream(imgFileName));
                DataInputStream labelIS = new DataInputStream(new FileInputStream(lblFileName));        ) {
            if (imageIS.readInt() != IMG_FILE_MAGIC_INT)
                throw new IOException("Unknown file format for " + imgFileName);

            if (labelIS.readInt() != LABEL_FILE_MAGIC_INT)
                throw new IOException("Unknown file format for " + lblFileName);

            int nImages = imageIS.readInt();
            int nLabels = labelIS.readInt();

            if (nImages != nLabels)
                throw new IOException(format("File %s and %s contains data for different number of images", imgFileName, lblFileName));
            if(nMax > 0)
                nImages = Math.min(nImages, nMax);
            int rows = imageIS.readInt();
            int cols = imageIS.readInt();
            int p = rows * cols;

            X = new double[ nImages ][ p ];
            iClass = new int[ nImages ];

            byte[] data = new byte[rows * cols];


            for (int i = 0; i < nImages; i++) {
                X[i] = new double[ p ];
                imageIS.read(data, 0, data.length);
                for (int j = 0; j < p; j++)
                    X[i][j] =  (data[j] & 255) / 255.0;
                iClass [ i ] = labelIS.readByte();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Object[] {X, iClass};
    }

}
