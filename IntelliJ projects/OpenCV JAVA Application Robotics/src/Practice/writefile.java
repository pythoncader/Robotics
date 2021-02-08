package Practice;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class writefile {
    public static void main( String[] args ) throws IOException {
        //Input File
        File input = new File("C:/Users/Mike/Desktop/Creative Cloud Files/giftidea.png");


        //Reading the image
        BufferedImage image = ImageIO.read(input);

        //Saving the image with a different name
        File output = new File("C:/Users/Mike/Desktop/myfile.png");
        ImageIO.write(image, "png", output);

        System.out.println("image Saved");
    }
}
