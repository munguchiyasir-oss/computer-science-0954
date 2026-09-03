public class EvenOrOdd {
    public static void main(String[] args) {
        int number = 24;

        // If a number divided by 2 has remainder 0, it is even 
        if (number % 2 ==0) {

            System.out.println(number + "is an even number.");
        } else { 
            System.out.println(number + "is an odd number");
        }
    }
}