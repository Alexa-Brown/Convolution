/**********************************************************************
 * @file ImageFiltering.java
 * @brief This program implements the use of methods and arrays to filter images.
 * @author Alexa Brown
 * @date April 5, 2019
 **********************************************************************/

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

// ***************************************************************************
// This class implements a few methods useful for reading in,
// converting BufferedImages to int[][] arrays, and saving JPG files.
// ***************************************************************************
public class ImageFiltering {

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // Method save(out, filename) saves the given image (out) to a file
    // (filename)
    // -----------------------------------------------------------------------
    public static void save(BufferedImage out, String filename) {
        File outfile = new File(filename);
        try {
            ImageIO.write(out, "jpg", outfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // Method arrayToBufferedImageGray(imageArray) converts an int[][] array
    // to a BufferedImage object. It returns the BufferedImage object.
    // -----------------------------------------------------------------------
    public static BufferedImage arrayToBufferedImageGray(double[][] imageArray) {
        BufferedImage out = new BufferedImage(imageArray.length, imageArray[0].length,
                BufferedImage.TYPE_BYTE_GRAY);

        for (int i = 0; i < imageArray.length; i++) {
            for (int j = 0; j < imageArray[i].length; j++) {
                // rgb is just (gray, gray, gray) as an int
                int rgb = ((int) imageArray[i][j] << 16 | (int) imageArray[i][j] << 8 |
                        (int) imageArray[i][j]);
                out.setRGB(i, j, rgb);
            }
        }
        return out;
    }

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // Method arrayToBufferedImageRGB(red, green, blue) creates and returns
    // a BufferedImage object constructed from the red, green, and blue arrays.
    // -----------------------------------------------------------------------
    public static BufferedImage arrayToBufferedImageRGB(int[][] red, int[][] green, int[][] blue) {
        BufferedImage out = new BufferedImage(red.length, red[0].length,
                BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < red.length; i++) {
            for (int j = 0; j < red[i].length; j++) {
                int rgb = ((int) red[i][j] << 16 | (int) green[i][j] << 8 |
                        (int) blue[i][j]);
                out.setRGB(i, j, rgb);
            }
        }
        return out;
    }

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // Method bufferedImageToArray(image, channel) takes in a Buffered image
    // as input and a string ("RED", "GREEN", "BLUE") and returns an int[][]
    // array of the corresponding color channel.
    // -----------------------------------------------------------------------
    public static double[][] bufferedImageToArray(BufferedImage image, String channel) {
        int w = image.getWidth();
        int h = image.getHeight();
        double[][] pixels = new double[w][h];

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (channel.toUpperCase().equals("BLUE")) {
                    pixels[i][j] = (int) image.getRGB(i, j) & 0xff;
                } else if (channel.toUpperCase().equals("GREEN")) {
                    pixels[i][j] = (int) (image.getRGB(i, j) >> 8) & 0xff;
                } else {
                    pixels[i][j] = (int) (image.getRGB(i, j) >> 16) & 0xff;
                }
            }
        }
        return pixels;
    }

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // Method getImage(filename) takes in a URL in filename and then reads
    // the image from the Web. Your computer must be connected to the internet
    // for this method to work.
    // If an image is not found, then getImage() returns null.
    // -----------------------------------------------------------------------
    public static BufferedImage getImage(String filename) {
        if (filename == null) throw new IllegalArgumentException();
        BufferedImage image = null;

        // from a file or URL
        try {
            URL url = new URL(filename);
            image = ImageIO.read(url);
            return image;
        } catch (IOException e) {
            // ignore
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // Method displayImage(img) takes in a BufferedImage object img and
    // displays it in a window using Jframe.
    // -----------------------------------------------------------------------
    public static void displayImage(BufferedImage img) {
        ImageIcon icon = new ImageIcon(img);
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(img.getWidth(), img.getHeight());
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //
    // Sample main method that reads in an image from the web, gets
    // its height and width and then saves the image to the hard drive.
    //

    public static double[][] convolve(double[][] img, double[][] filter) {
        int rows = img.length;
        int cols = img[0].length;
        double [][] newImg = new double[rows - 2][cols - 2];
        for (int i = 0; i < newImg.length; i++) {
            for (int j = 0; j < newImg[i].length; j++) {

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

    public static void main(String[] args) {

        BufferedImage inimage, outimage;
        double[][] gray;
        double[][] red, green, blue;

        String filename;
        //filename = "http://csweb.cs.wfu.edu/~pauca/csc111/DSC_9259.JPG";
        filename = "https://www.mountainphotography.com/images/xl/20170821-Red-Mountain-from-Brown-Mountain.jpg";

        // Read in the image and verify that it worked
        inimage = getImage(filename);
        if (inimage == null) {
            System.out.println("Could not read image");
            System.exit(0);
        }

        // Print the image width and height
        System.out.println("input image width: " + inimage.getWidth());
        System.out.println("input image height: " + inimage.getHeight());

        //
        // Extract the RGB channels from inimage and use then to convert
        // to grayscale
        //
        red = bufferedImageToArray(inimage, "RED");
        green = bufferedImageToArray(inimage, "GREEN");
        blue = bufferedImageToArray(inimage, "BLUE");
        // Create the grayscale image
        gray = new double[inimage.getWidth()][inimage.getHeight()];

        double[][] gaussFilter = {{0, 0.25, 0}, {0.25, 0.5, 0.25}, {0, 0.25, 0}};
        double[][] boxFilter = {{1.0 / 9, 1.0 / 9, 1.0 / 9}, {1.0 / 9, 1.0 / 9, 1.0 / 9}, {1.0 / 9, 1.0 / 9, 1.0 / 9}};
        double[][] newred = convolve(red, gaussFilter);
        double[][] newgreen = convolve(green, boxFilter);
        double[][] newblue = convolve(blue, boxFilter);
        convolve(gray, gaussFilter);

        for (int i = 0; i < gray.length; i++) {
            for (int j = 0; j < gray[i].length; j++) {
                // Formula is gray = B * 0.07 + G * 0.72 + R * 0.21
                gray[i][j] = (int) (0.07 * blue[i][j] + 0.72 * green[i][j] + 0.21 * red[i][j]);
            }
        }

        // Compute the average of the image intensity
        double avg = 0;
        for (int i = 0; i < gray.length; i++) {
            for (int j = 0; j < gray[i].length; j++) {
                avg += gray[i][j];
            }
        }
        avg = avg / (gray.length * gray[0].length);
        System.out.printf("The average intensity is %.2f\n", avg);

        outimage = arrayToBufferedImageGray(gray);
        //outimage = arrayToBufferedImageRGB(newred, newgreen, newblue);
        // can use to change the picture's colors instead of just transferring to gray

        // Display the image and save also to a new file
        displayImage(inimage);
        displayImage(outimage);
        save(outimage, "image.jpg");
    }

}
