class Vehicle {
    public void go() {
System.out.println("The vehicle begin moving");
    }
}
class Car extends Vehicle{
    public void go(){
        System.out.println("The car begin moving");
    }
}
class Bicycle extends Vehicle{
    public void go(){
        System.out.println("The bicycle is moving");
    }
}
class Boat extends Vehicle{
    public void go(){
System.out.println("The boat begin moving");
}
}
public class Polymorphism{
    public static void main(String[] args) {
        Car car = new Car();
        Bicycle bicycle = new Bicycle();
        Boat boat = new Boat();
        Vehicle [] racer = {car,bicycle,boat};
        car.go();
        bicycle.go();
        boat.go();

    }
}