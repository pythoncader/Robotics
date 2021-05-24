import MyClasses.Animal;
import MyClasses.Math;
import Games.Guessing;

public class Main {

    public static void main(String[] args) { //String[] args are the arguments that you pass when you start the program.
	    System.out.println("my arguments: ");
		for (String arg : args) { //prints out the arguments of the function
			System.out.println(arg);
		} //this is the same thing as saying (for arg in args) in Python
		/*for (int i = 0; i < args.length; i++) { //prints out the arguments of the function
			System.out.println(args[i]);
		}*/

	    Math myobject = new Math(5);
	    System.out.println(myobject.squareit());

	    Animal mydog = new Animal("Dog", 8, "Zipper");
	    System.out.println("I have a "+mydog.gettype()+" whose name is "+mydog.getname()+" and weighs "+mydog.getweight()+" pounds!");
	    mydog.print();

	    System.out.println("Your best score: "+Guessing.playgame());
    }

}
