package MyClasses;

import static java.lang.Integer.parseInt;

public class Math {
    public static void main(String[] args){
        System.out.println("This is a module. Do not run it independently.");
    }

    int mynum;
    public Math(int mynumber){
        mynum = mynumber;
    }
    public int squareit() {
        int myvar = 0;
        for (int i = mynum; i < mynum * mynum + 1; i = i + mynum) {
            myvar = i;
        }
        return myvar;
    }
}
