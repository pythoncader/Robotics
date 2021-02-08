package Practice;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Thresholding {
    public static void main(String[] args) {
        // Loading the OpenCV core library
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

        // Reading the Image from the file and storing it in to a Matrix object
        String filepath ="C:/Users/Mike/Desktop/OpenCV java application/src/thresh_input.jpg";
        Mat source = Imgcodecs.imread(filepath);

        // Creating an empty matrix to store the result
        Mat destination = new Mat();
        Imgproc.threshold(source, destination, 200, 0, Imgproc.THRESH_BINARY); //thresh is the threshold. If the pixel value (black and white) is more than this, then it is set to the max value
        try {
            myimage.imshow(destination, ".jpg");
        }catch (Exception e){
            //do nothing
        }
        // Writing the image
        Imgcodecs.imwrite("C:/Users/Mike/Desktop/OpenCV java application/src/thresh_trunc.jpg", destination);

        System.out.println("Image Processed");
        System.exit(1);
    }
}
