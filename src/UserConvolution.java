import java.util.Arrays;
import java.util.Scanner;

public class UserConvolution {
    public static void main(String[] args) {
        Scanner lulu = new Scanner(System.in);
        int filterSize;
        int filterType;
        int[][] img = {{0, 0, 0, 0, 0, 0, 0}, {0, 0, 45, 45, 45, 0, 0}, {0, 45, 0, 0, 0, 45, 0}, {0, 45, 0, 0, 0, 45, 0}, {0, 0, 45, 45, 45, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0}}; //how to fill in these arrays?


        System.out.println("Please enter the size of your filter (it must be an odd number): ");
        filterSize = lulu.nextInt();
        double[][] filter = new double[filterSize][filterSize];
        System.out.println("Press 1 for Box Filter, press 2 for Gauss Filter. ");
        filterType = lulu.nextInt();
        //  if (filterType == 1) {
        //    filter = boxFilter;
        for (int i = 0; i < filter.length; i++) {
            for (int j = 0; j < filter.length; j++) {
                filter[i][j] = 1.0 / 9;
                //{{1.0/9, 1.0/9, 1.0/9}, {1.0/9, 1.0/9, 1.0/9}, {1.0/9, 1.0/9, 1.0/9}};
            }
        }

        // else if (filterType == 2) {
        //   double[][] gaussFilter={{0, 0.25, 0}, {0.25, 0.5, 0.25},{0, 0.25, 0}};

        int[][] newImg = convolve(img, filter);

        printArray(newImg);

    }


    public static int[][] convolve (int[][] img, double [][] filter){
        int rows = img.length;
        int cols = img[0].length;
        int [][] newImg = new int [rows -2][cols-2];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                for (int k = 0; k <filter.length; k++) {
                    for (int y = 0; y < filter.length; y++){
                        int x = (int) (filter [k][y] * img [i+k][j+y]);
                        newImg[i][j] += x;
                    }
                }

            }

        }
        return newImg;
    }

    public static void printArray (int [][] newImg){
        System.out.println("Convolved image: ");
        for (int c = 0; c < newImg.length; c++) {
            System.out.println(Arrays.toString(newImg[c]));
        }
    }
}

