package MyClasses;

public class Animal {
    public static void main(String[] args){
        System.out.println("This is a module. Do not run it independently.");
    }

    String animal_type;
    int animal_weight;
    String animal_name;

    public Animal(String animaltype, int animalweight, String animalname){
        animal_type = animaltype;
        animal_weight = animalweight;
        animal_name = animalname;
    }
    public String getname(){
        return animal_name;
    }
    public int getweight(){
        return animal_weight;
    }
    public String gettype(){
        return animal_type;
    }

    public void print(){
        System.out.println("The animal is a "+animal_type+" whose name is "+getname()+" and weighs "+getweight()+" pounds!");
    }
}
