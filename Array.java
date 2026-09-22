public class Array {
    public static void main(String[] args) {

        int[] marks = {70, 80, 65,90, 85};

        int total = 0;
        
        for (int i = 0; i < marks.length; i++) {
            total += marks[i];
        }
                double average = (double) total / marks.length;
        System.out.println("Average marks: " + average);
    
        
        }
    }