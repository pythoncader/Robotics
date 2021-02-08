package Practice;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class WritingImages {
    public static void main(String[] args) {
        //Loading the OpenCV core library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        //Instantiating the imagecodecs class
        Imgcodecs imageCodecs = new Imgcodecs();

        //Reading the Image from the file and storing it in to a Matrix object
        String file ="C:/users/mike/desktop/myfile.png";
        Mat matrix = imageCodecs.imread(file);

        System.out.println("Image Loaded ..........");
        String file2 = "C:/users/mike/desktop/myfile-resaved.png";

        //Writing the image
        imageCodecs.imwrite(file2, matrix);
        System.out.println("Image Saved ............");
    }
}