package Practice;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class DisplayingImagesUsingSwings {
    public static void main(String[] args){
        //Loading the OpenCV core library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        //Reading the Image from the file and storing it in to a Matrix object
        String filepath = "C:/users/Mike/desktop/myfile.png";
        Mat image = Imgcodecs.imread(filepath);
        Mat grayscale = Imgcodecs.imread(filepath, Imgcodecs.IMREAD_GRAYSCALE); //reading the same image in grayscale

        myimage.imshow(image, ".png");
        myimage.imshow(grayscale, ".png");
        System.exit(1);
    }
}