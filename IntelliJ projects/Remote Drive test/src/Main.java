import java.util.Scanner;

public class Main {
    public static void drive(double vertical, double horizontal, double turn){
        //Turn value is positive on both left and negative on both right. It goes with its side. When the right wheels are going forward, then the left wheels will be going backward and vice versa
        //Vertical values are always positive because all of the wheels need to be the same forward or backward.
        //Horizontal values differ because the opposite wheels on a diagonal (e.g. front_left and back_right) need to be moving in the same direction so they are both positive and the other two are negative
        double front_left_power = vertical + horizontal + turn; //always positive vertical, horizontal positive because this wheel goes forward relative to the other two
        double front_right_power = vertical - horizontal - turn; //always positive vertical, horizontal negative because this wheel will need to go backward relative to the other two
        double back_left_power = vertical - horizontal + turn; //always positive vertical, horizontal negative because this wheel needs to go backward relative to the other two
        double back_right_power = vertical + horizontal - turn; //always positive vertical, horizontal positive because this wheel needs to go forward relative to the other two
        System.out.println("Front left power: "+front_left_power);
        System.out.println("Front right power: "+front_right_power);
        System.out.println("Back left power: "+back_left_power);
        System.out.println("Back right power: "+back_right_power);

        if (front_left_power > 0 && front_right_power > 0 && back_left_power > 0 && back_right_power > 0){
            System.out.println("\nMoving forward\n");
        }
        if (front_left_power < 0 && front_right_power < 0 && back_left_power < 0 && back_right_power < 0){
            System.out.println("\nMoving backward\n");
        }
        if (front_left_power < 0 && front_right_power > 0 && back_left_power > 0 && back_right_power < 0){
            System.out.println("\nMoving left\n");
        }
        if (front_left_power > 0 && front_right_power < 0 && back_left_power < 0 && back_right_power > 0){
            System.out.println("\nMoving right\n");
        }
    }
    public static void main(String[] args) {
	// write your code here
        Scanner input = new Scanner(System.in);
        double vertical;
        double horizontal;
        double turn;
        while (true) {
            System.out.println("Please put in the vertical value (-1 : 1):");
            vertical = input.nextDouble();
            System.out.println("Please put in the horizontal value (-1 : 1):");
            horizontal = input.nextDouble();
            System.out.println("Please put in the turn value (-1 : 1)");
            turn = input.nextDouble();
            drive(vertical, horizontal, turn);
            //The middle is 0
        }

    }
}
