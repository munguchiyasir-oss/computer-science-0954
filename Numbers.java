     import java.util.Scanner;
       public class Numbers {
       public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        double num1,num2,num3,num4,num5;

        System.out.print("Enter first number:");
        num1=input.nextDouble();

        System.out.print("Enter second number:");
        num2=input.nextDouble();

        System.out.print("Enter third number:");
        num3=input.nextDouble();

        System.out.print("Enter fourth number:");
        num4=input.nextDouble();

        System.out.print("Enter fifth number:");
        num5=input.nextDouble();
        double average = (num1 + num2 + num3 + num4 + num5)/5;
        System.out.printf("Average=%.3f%n",average);
    }
}