import java.util.Arrays;

public class Convolution {
    public static void main(String[] args) {
        int [][] img = {{0,0,0,0,0,0,0}, {0,0,45,45,45,0,0}, {0,45,0,0,0,45,0}, {0,45,0,0,0,45,0}, {0,0,45,45,45,0,0},
                {0,0,0,0,0,0,0},{0,0,0,0,0,0,0}};
        double [][] filter = {{1.0/9, 1.0/9, 1.0/9}, {1.0/9, 1.0/9, 1.0/9}, {1.0/9, 1.0/9, 1.0/9}};
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

               /* newImg [i][j] = (int) ((filter [0][0] * img [i][j]) + (filter [0][1] * img [i][j+1]) +
                        (filter [0][2] * img [i][j+2]) + (filter [1][0] * img [i+1][j]) +
                        (filter [1][1] * img [i+1][j+1]) + (filter [1][2] * img [i+1][j+2]) +
                        (filter [2][2] * img [i+2][j+2]) + (filter [2][1] * img [i+2][j+1]) +
                        (filter [2][0] * img [i+2][j]) );
                */
            }

            }
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
