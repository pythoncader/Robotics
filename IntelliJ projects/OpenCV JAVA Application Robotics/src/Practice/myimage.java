package Practice;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class myimage{
    public static void imshow(Mat image, String extension){
        //Loading the OpenCV core library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME );

        //Encoding the image
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(extension, image, matOfByte);

        //Storing the encoded Mat in a byte array
        byte[] byteArray = matOfByte.toArray();

        //Preparing the Buffered Image
        InputStream in = new ByteArrayInputStream(byteArray);
        BufferedImage bufImage = null;
        try {
            bufImage = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Instantiate JFrame
        JFrame frame = new JFrame();

        //Set Content to the JFrame2
        frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
        frame.pack();
        frame.setVisible(true);

        System.out.println("Image Loaded.. Press Enter to close");
        try {
            System.in.read();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}