/*what is muilti-threading in java? Multi-threading in Java is a programming technique that 
 allows multiple threads to run concurrently within a single program. 
A thread is a lightweight unit of execution that can run independently, allowing for
 parallelism and improved performance in applications. Multi-threading enables developers 
to create responsive and efficient programs by performing multiple tasks simultaneously, 
 such as handling user input, processing data, or performing background operations.*/
 /*advantages of multi-threading in java
  Improved performance: Multi-threading allows for concurrent execution of tasks, 
which can
faster execution and better utilization of system resources.
Responsiveness: Multi-threaded applications can remain responsive to user input
saving time and providing a better user experience.
Resource sharing: Threads within the same process can share resources such as memory,

disadvantages of multi-threading in java
  Increased complexity: Writing and managing multi-threaded code can be more complex than 
  single-threaded code, leading to potential issues such as race conditions and deadlocks.
  synchronization overhead: Proper synchronization is required to avoid data inconsistencies,
  high resource consumption: Creating and managing multiple threads can consume more system 
  resources,
  difficult debugging: Debugging multi-threaded applications can be challenging due to the
  non-deterministic nature of thread execution and potential race conditions.
*/
 //examle of multi-threading in java

/*public class Multithread {
    public static void main(String[] args) {
        MyThread thread1 = new MyThread();
        MyThread thread2 = new MyThread();

        thread1.setName("Thread 1");
        thread2.setName("Thread 2");

        thread1.start();
        thread2.start();
    }
}
class MyThread extends Thread {
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(getName() + " :" + i);
        }
    }
}*/

/*Another simple example of multi-threading in Java */
class AnotherExample {
    public static void main(String[] args) {
        Runnable task = () -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Task executed by " + Thread.currentThread().getName() + " :" + i);
            }
        };

        Thread thread1 = new Thread(task, "Thread 1");
        Thread thread2 = new Thread(task, "Thread 2");

        thread1.start();
        thread2.start();
    }
}