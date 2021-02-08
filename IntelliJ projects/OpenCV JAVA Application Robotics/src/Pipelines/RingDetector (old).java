package Pipelines;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.highgui.HighGui;


import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import static org.opencv.imgproc.Imgproc.cvtColor;

public class RingDetector {
    public static void main(String[] args) {
        int runtime = 0;
        String[] myfiles = {"Pipelines/files/skystonesmall.jpg", "Pipelines/files/skystonepic.jpg", "Pipelines/files/skystonestack.jpg", "Pipelines/files/skystonestack2.jpg", "Pipelines/files/skystonestack3.jpg"};
        for (String i : myfiles) {
            runtime++;

            //Loading the OpenCV core library
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            String filepath = "C:/users/mike/desktop/OpenCV java application/src/" + i;
            Mat src = Imgcodecs.imread(filepath);
            int y1 = 0;
            int y2 = 0;
            int x1 = 0;
            int x2 = 0;
            switch (runtime) { //set ROI values based on which image it is
                case (1):
                    y1 = 107;
                    y2 = 276;
                    x1 = 301;
                    x2 = 558;
                    break;
                case (2):
                    y1 = 73;
                    y2 = 388;
                    x1 = 240;
                    x2 = 640;
                    break;
                case (3):
                    y1 = 55;
                    y2 = 472;
                    x1 = 106;
                    x2 = 628;
                    break;
                case (4):
                    y1 = 7;
                    y2 = 720;
                    x1 = 312;
                    x2 = 799;
                    break;
                case (5):
                    y1 = 11;
                    y2 = 720;
                    x1 = 166;
                    x2 = 1251;
                    break;
            }
            int[] myheight = getheight(src, y1, y2, x1, x2);
            System.out.println(myheight[0]+" and percentage: "+myheight[1]);
        }
        System.exit(0);
    }
public static int[] getheight(Mat src, int y1, int y2, int x1, int x2){
        //Loading the OpenCV core library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat hsv = new Mat();
        Mat blurred = new Mat();
        Imgproc.blur(src, blurred, new Size(8, 8));
        cvtColor(blurred, hsv, Imgproc.COLOR_BGR2HSV);
        HighGui.imshow("RGB image", src);
        HighGui.imshow("HSV image", hsv);
        HighGui.waitKey();


        Mat dst = new Mat();
        int u_h = 35;
        int u_s = 255;
        int u_v = 255;
        int l_h = 20;
        int l_s = 150;
        int l_v = 155;
        Scalar l_b = new Scalar(l_h, l_s, l_v);
        Scalar u_b = new Scalar(u_h, u_s, u_v);
        Mat mask = new Mat();
        Core.inRange(hsv, l_b, u_b, mask);
        Core.bitwise_and(src, src, dst, mask);
        HighGui.imshow("result image:", dst);
        HighGui.waitKey();


        Mat crop;
        //crop = mask.submat(y1, x1, y2, x2);
        Rect rectCrop = new Rect(x1, y1, x2 - x1, y2 - y1);
        Imgproc.cvtColor(mask, mask, Imgproc.COLOR_GRAY2BGR);
        crop = new Mat(mask, rectCrop);

        Mat myblankframe = mask.clone(); //Clone from the original image
        myblankframe.setTo(new Scalar(0, 0, 0)); //This sets the whole image to white, it is R,G,B value

    /*Mat smallblankframe= myblankframe.submat(new Rect(20,20, crop.cols(), crop.rows()) );
    crop.copyTo(smallblankframe);*/


    /*
    int size = 30;
    Mat b = new Mat(size*3, size*3, CvType.CV_8UC1, new Scalar(0));
    Mat a = new Mat(size, size, CvType.CV_8UC1, new Scalar(255));

    Mat bSubmat = b.submat(a.rows(), a.rows()*2, a.cols(), a.cols()*2);
    a.copyTo(bSubmat);
    Imgcodecs.imwrite("myfile.png", b);
    */


        crop.copyTo(myblankframe.rowRange(y1, y2).colRange(x1, x2));
        Imgproc.cvtColor(myblankframe, myblankframe, COLOR_BGR2GRAY);
        Rect containingrect = Imgproc.boundingRect(myblankframe);
        Point startPoint = new Point(containingrect.x, containingrect.y);
        Point endingPoint = new Point(containingrect.x + containingrect.width, containingrect.y + containingrect.height);


        Imgproc.rectangle(src, startPoint, endingPoint, new Scalar(0, 255, 0), 2);


        HighGui.imshow("Final mask", myblankframe);
        HighGui.imshow("original image", src);
        HighGui.imshow("ROI", crop);
        HighGui.waitKey();
        HighGui.destroyAllWindows();
        System.out.println("Height is "+containingrect.height+" pixels");
        float floatpercent = (float)containingrect.height/src.rows()*100;
        int percentage = (int)floatpercent;
        System.out.println("Percent of image height: "+percentage+"%");
    return new int[]{containingrect.height, percentage};

    }
}