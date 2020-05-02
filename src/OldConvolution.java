import java.awt.image.BufferedImage;
import java.util.Arrays;

public class OldConvolution {
    public static void main(String[] args) {
        double[][] img = {{0, 0, 0, 0, 0, 0, 0}, {0, 0, 45, 45, 45, 0, 0}, {0, 45, 0, 0, 0, 45, 0}, {0, 45, 0, 0, 0, 45, 0}, {0, 0, 45, 45, 45, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0}};
        double[][] boxFilter = new double[3][3];
        for (int i = 0; i < boxFilter.length; i++) { //filling in the boxFilter
            for (int j = 0; j < boxFilter.length; j++) {
                boxFilter[i][j] = 1.0 / 9;
            }
        }
        double[][] gaussFilter = {{0, 0.25, 0}, {0.25, 0.5, 0.25}, {0, 0.25, 0}};

        double[][] filter = gaussFilter;

        double[][] newImg = convolve(img, filter);

        printArray(img, filter, newImg);

        // Convert the image int[][] arrays (original and blurred to BufferedImage objects
        BufferedImage in = ImageFiltering.arrayToBufferedImageGray(img);
        BufferedImage out = ImageFiltering.arrayToBufferedImageGray(newImg);
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
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                for (int k = 0; k < filter.length; k++) {
                    for (int y = 0; y < filter.length; y++) {
                        int x = (int) (filter[k][y] * img[i + k][j + y]);
                        newImg[i][j] += x;
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