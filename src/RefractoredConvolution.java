/**********************************************************************
 * @file RefractoredConvolution.java
 * @brief This program implements the use of methods and arrays to convolve images.
 * @author Alexa Brown
 * @date April 5, 2019
 **********************************************************************/
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class RefractoredConvolution {
    public static void main(String[] args) {

        double[][] filter;

        //
        // Create a 7x7 image of a white circle
        //
        double[][] img = {{0, 0, 0, 0, 0, 0, 0},
                {0, 0, 245, 245, 245, 0, 0},
                {0, 245, 0, 0, 0, 245, 0},
                {0, 245, 0, 0, 0, 245, 0},
                {0, 0, 245, 245, 245, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}};

        int imgHeight = img.length;
        int imgWidth = img[0].length;

        //
        // Define two smoothing filters: a box filter and a Gaussian filter
        //
        double[][] boxFilter = new double[3][3];
        for (int i = 0; i < boxFilter.length; i++) { //filling in the boxFilter
            for (int j = 0; j < boxFilter.length; j++) {
                boxFilter[i][j] = 1.0 / 9;
            }
        }
        double[][] gaussFilter = {{0, 0.25, 0}, {0.25, 0.5, 0.25}, {0, 0.25, 0}};


        // Choose the gaussFilter for the code below
        filter = gaussFilter;
        int filterHeight = filter.length;
        int filterWidth = filter[0].length;

        //
        // Create an array to hold the resulting image
        //
        double[][] newImage = new double[imgHeight - 2 * (filterHeight / 2)][imgWidth - 2 * (filterWidth / 2)];


        double[][] newImg = convolve(img, filter);

        printArray(img, filter, newImg);


        BufferedImage in = ImageFiltering.arrayToBufferedImageGray(img);
        BufferedImage out = ImageFiltering.arrayToBufferedImageGray(newImage);
        // Display the resulting image
        ImageFiltering.displayImage(out);

        // Save the original image and the blurred image to JPG files
        ImageFiltering.save(in, "in.jpg");
        ImageFiltering.save(out, "out.jpg");
    }

    public static double[][] convolve(double[][] img, double[][] filter) {
        int rows = img.length;
        int cols = img[0].length;
        double[][] newImg = new double[rows - 2][cols - 2];
        for (int i = 0; i < newImg.length; i++) {
            for (int j = 0; j < newImg[0].length; j++) {
                for (int k = 0; k < filter.length; k++) {
                    for (int y = 0; y < filter[0].length; y++) {
                        newImg[i][j] = (newImg[i][j]) + (filter[k][y] * img[i + k][j + y]);
                    }
                }
            }
        }
        return newImg;
    }

    public static void printArray(double[][] img, double[][] filter, double[][] newImg) {

        System.out.println("Image: ");
        for (int a = 0; a < img.length; a++) {
            System.out.println(Arrays.toString(img[a]));
        }
        System.out.println();
        System.out.println("Filter: ");
        for (int b = 0; b < filter.length; b++) {
            System.out.println(Arrays.toString(filter[b]));
        }
        System.out.println();
        System.out.println("Convolved image: ");
        for (int c = 0; c < newImg.length; c++) {
            System.out.println(Arrays.toString(newImg[c]));
        }
    }
}
