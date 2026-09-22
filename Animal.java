public class Animal {
    public void Sound() {
        System.out.println("Animal makes a sound");
    }
//subclass
   class Dog extends Animal {
    @Override
    public void Sound() {
        System.out.println("Dog barks");
    }
}
    class Cat extends Animal {
    @Override
    public void Sound() {
        System.out.println("Cat meows");
    }
}
public class Animal {
    public static void main(String[] args) {
        Animal myAnimal = new Animal();
        Animal myDog = new Dog();
        Animal myCat = new Cat();

        myAnimal.Sound(); // Output: Animal makes a sound
        myDog.Sound();    // Output: Dog barks
        myCat.Sound();    // Output: Cat meows
    }
}