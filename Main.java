class Library {
       public  void read() {
        System.out.println("Reading a book from the  library");
    }
}
class Book extends Library {
   public  void borrow() {
        System.out.println("Book Borrowed");
    }
}
public class Main {
    public static void main(String[] args) {
        Book b = new Book();
        b.read(); //inherited from Library
        b.borrow(); //method in Book
    }
}

    
