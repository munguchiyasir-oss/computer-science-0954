import java.sql.*;
import java.util.Scanner;

public class SmartCampusApp {
    // This creates a local file named 'campus.db' automatically in your project folder
    private static final String DB_URL = "jdbc:sqlite:campus.db";
    private static final Scanner scanner = new Scanner(System.in);
    
    // Global session variables
    private static String currentUserId = null;
    private static String currentUserRole = null;
    private static String currentName = null;

    public static void main(String[] args ) {
    
        System.out.println("Initializing Smart Campus System...");
        initializeDatabase();
        seedMockData();

        while (true) {
            if (currentUserId == null) {
                showLoginMenu();
            } else {
                showDashboardByRole();
            }
        }
    }

    // --- DATABASE SETUP ---
    private static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            
            // 1. Create Users Table
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "user_id TEXT PRIMARY KEY, username TEXT UNIQUE, password TEXT, role TEXT);");

            // 2. Create Courses Table
            stmt.execute("CREATE TABLE IF NOT EXISTS courses (" +
                    "course_code TEXT PRIMARY KEY, course_name TEXT, credits INTEGER);");

            // 3. Create Enrollments Table (Handles Marks & Grades)
            stmt.execute("CREATE TABLE IF NOT EXISTS enrollments (" +
                    "student_id TEXT, course_code TEXT, coursework REAL DEFAULT 0, " +
                    "exam REAL DEFAULT 0, total REAL DEFAULT 0, grade TEXT DEFAULT 'F', " +
                    "PRIMARY KEY (student_id, course_code));");

        } catch (SQLException e) {
            System.out.println("Database initialization error: " + e.getMessage());
        }
    }

    private static void seedMockData() {
        // Inserts baseline dummy entries safely if tables are completely clean
        String insertUser = "INSERT OR IGNORE INTO users VALUES (?, ?, ?, ?);";
        String insertCourse = "INSERT OR IGNORE INTO courses VALUES (?, ?, ?);";
        String insertEnroll = "INSERT OR IGNORE INTO enrollments (student_id, course_code) VALUES (?, ?);";

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            try (PreparedStatement pstmt = conn.prepareStatement(insertUser)) {
                pstmt.setString(1, "STU101"); pstmt.setString(2, "alice"); pstmt.setString(3, "password"); pstmt.setString(4, "STUDENT"); pstmt.executeUpdate();
                pstmt.setString(1, "LEC202"); pstmt.setString(2, "bob"); pstmt.setString(3, "password"); pstmt.setString(4, "LECTURER"); pstmt.executeUpdate();
                pstmt.setString(1, "ADM303"); pstmt.setString(2, "admin"); pstmt.setString(3, "admin123"); pstmt.setString(4, "ADMIN"); pstmt.executeUpdate();
            }
            try (PreparedStatement pstmt = conn.prepareStatement(insertCourse)) {
                pstmt.setString(1, "CS101"); pstmt.setString(2, "Introduction to Java"); pstmt.setInt(3, 4); pstmt.executeUpdate();
                pstmt.setString(1, "DB201"); pstmt.setString(2, "Database Systems"); pstmt.setInt(3, 3); pstmt.executeUpdate();
            }
            try (PreparedStatement pstmt = conn.prepareStatement(insertEnroll)) {
                pstmt.setString(1, "STU101"); pstmt.setString(2, "CS101"); pstmt.executeUpdate();
                pstmt.setString(1, "STU101"); pstmt.setString(2, "DB201"); pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error seeding database data: " + e.getMessage());
        }
    }

    // --- AUTHENTICATION ENGINE ---
    private static void showLoginMenu() {
        System.out.println("\n=== SMART CAMPUS LOGIN ===");
        System.out.print("Enter Username: ");
        String user = scanner.nextLine().trim();
        System.out.print("Enter Password: ");
        String pass = scanner.nextLine().trim();

        String query = "SELECT * FROM users WHERE username = ? AND password = ?;";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, user);
            pstmt.setString(2, pass);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                currentUserId = rs.getString("user_id");
                currentUserRole = rs.getString("role");
                currentName = rs.getString("username");
                System.out.println("\nLogin Successful! Welcome, " + currentName + " (" + currentUserRole + ").");
            } else {
                System.out.println("\nInvalid username or password. Try again.");
            }
        } catch (SQLException e) {
            System.out.println("Authentication Error: " + e.getMessage());
        }
    }

    // --- CORE ROUTING NAVIGATION ---
    private static void showDashboardByRole() {
        System.out.println("\n--- " + currentUserRole + " PORTAL DASHBOARD ---");
        switch (currentUserRole) {
            case "ADMIN":
                System.out.println("1. Manage student & Accounts\n2.Manage courses & Semesters\n3. Manage Programs & Departments\n4.Manage Lecturer & Assignments\n5.Generate Reports\n6. Logout");
                int adminChoice = getIntegerInput();
                if (adminChoice == 1) adminRegisterStudent();
                else if (adminChoice == 2) manageCoursesAndSemesters();
                else if (adminChoice == 3) manageProgramsAndDepartments();
                else if (adminChoice == 4) manageLecturersAndAssignments();
                else if (adminChoice == 5) generateSystemReports();
                else logout();
                break;

            case "LECTURER":
                System.out.println("1. View Assigned Courses");
                System.out.println("2. View Registered Students");
                System.out.println("3. Record Attendance");
                System.out.println("4. Enter Coursework & Marks");
                System.out.println("5. Enter Examination Marks");
                System.out.println("6. View Student performance");
                System.out.println("7. Generate course reports");
                System.out.println("8. Logout");
                int lecChoice = getIntegerInput();
                if (lecChoice == 1) lecturerViewAssignedCourses();
                else if (lecChoice == 2) lecturerViewRegisteredStudents();
                else if (lecChoice == 3) lecturerRecordAttendance();
                else if (lecChoice == 4) lecturerInputCourseworkMarks();
                else if (lecChoice == 5) lecturerInputExaminationMarks();
                else if (lecChoice == 6) lecturerViewStudentPerformance();
                else if (lecChoice == 7) lecturerGenerateCourseReports();
                else logout();
                break;

                

                

            case "STUDENT":
                System.out.println("1. Login to view to the system");
                System.out.println("2. View profile");
                System.out.println("3. Register for courses");
                System.out.println("4. View Registered Courses");
                System.out.println("5. View Attendance");
                System.out.println("6. View Results");
                System.out.println("7. View GPA");
                System.out.println("8. Download/View Transcript Report Card");
                int stuChoice = getIntegerInput();
                if (stuChoice == 1) studentLogin();
                else if (stuChoice == 2) studentViewProfile();
                else if (stuChoice == 3) studentRegisterCourses();
                else if (stuChoice == 4) studentViewRegisteredCourses();
                else if (stuChoice == 5) studentViewAttendance();
                else if (stuChoice == 6) studentViewResults();
                else if (stuChoice == 7) studentViewGPA();
                else if (stuChoice == 8) studentViewReport();
            
                else logout();
                break;
        }
    }
    private static void lecturerViewAssignedCourses() {
        System.out.println("\n--- View Assigned Courses ---");
    }
    private static void lecturerViewRegisteredStudents() {
        System.out.println("\n--- View Registered Students ---");
    }
    private static void lecturerRecordAttendance() {
        System.out.println("\n--- Record Attendance ---");
    }
    private static void lecturerInputCourseworkMarks() {
        System.out.println("\n--- Enter Coursework Marks ---");
    }
    private static void lecturerInputExaminationMarks() {
        System.out.println("\n--- Enter Examination Marks ---");
    }
    private static void lecturerViewStudentPerformance() {
        System.out.println("\n--- View Student Performance ---");
    }
    private static void lecturerGenerateCourseReports() {
        System.out.println("\n--- Generate Course Reports ---");
    }
    private static void studentLogin() {
        System.out.println("\n--- Student Login ---");
    }
    private static void studentViewProfile() {
        System.out.println("\n--- View Profile ---");
    }
    private static void studentRegisterCourses() {
        System.out.println("\n--- Register for Courses ---");
    }
    private static void studentViewRegisteredCourses() {
        System.out.println("\n--- View Registered Courses ---");
    }
    private static void studentViewAttendance() {
        System.out.println("\n--- View Attendance ---");
    }
    private static void studentViewResults() {
        System.out.println("\n--- View Results ---");
    }
    private static void studentViewGPA() {
        System.out.println("\n--- View GPA ---");
    }
    
    

    // --- CORE BUSINESS OPERATIONS ---
    
    // Admin Option 1: Account Creation
    private static void adminRegisterStudent() {
        System.out.print("Enter New ID (e.g., STU105): ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter Student Username: ");
        String user = scanner.nextLine().trim();
        System.out.print("Set Password: ");
        String pass = scanner.nextLine().trim();

        String query = "INSERT INTO users VALUES (?, ?, ?, 'STUDENT');";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
            pstmt.setString(2, user);
            pstmt.setString(3, pass);
            pstmt.executeUpdate();
            System.out.println("Student Profile added successfully to Central Registry.");
        } catch (SQLException e) {
            System.out.println("Error saving new user record: " + e.getMessage());
        }
    }

    // Admin Option 2: View Courses
    private static void adminViewCourses() {
        System.out.println("\nAvailable Registered Course Catalog Units:");
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM courses;")) {
            while (rs.next()) {
                System.out.printf("- [%s] %s (%d Credit Hours)\n", 
                        rs.getString("course_code"), rs.getString("course_name"), rs.getInt("credits"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching course data: " + e.getMessage());
        }
    }

    // Lecturer Option 1: Automatic Calculations (Main Objective 8)
    private static void lecturerInputMarks() {
        String sId;
        String cCode;
        System.out.print("Enter target Student ID (e.g., STU101): ");
        sId = scanner.nextLine().trim();

        System.out.print("Enter target Course Code (e.g., CS101): ");
        cCode = scanner.nextLine().trim();


        System.out.print("Enter Coursework Mark (out of 100): ");
        double cw = scanner.nextDouble();

        System.out.print("Enter Examination Mark (out of 100): ");
        double exam = scanner.nextDouble();

        scanner.nextLine(); // Clear buffer

        // Automated Logic Split Rule (40% Coursework + 60% Exam)
        double total = (cw * 0.4) + (exam * 0.6);
        String grade;
        if (total >= 80) grade = "A";
        else if (total >= 70) grade = "B";
        else if (total >= 60) grade = "C";
        else if (total >= 50) grade = "D";
        else grade = "F";

        String updateQuery = "UPDATE enrollments SET coursework = ?, exam = ?, total = ?, grade = ? " +
                             "WHERE student_id = ? AND course_code = ?;";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setDouble(1, cw);
            pstmt.setDouble(2, exam);
            pstmt.setDouble(3, total);
            pstmt.setString(4, grade);
            pstmt.setString(5, sId);
            pstmt.setString(6, cCode);
            
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.printf("Successfully updated database. Total Calculated Score: %.1f Grade: %s\n", total, grade);
            } else {
                System.out.println("No matching enrollment entry found for that specific student and course ID combined.");
            }
        } catch (SQLException e) {
            System.out.println("Database processing exception: " + e.getMessage());
        }
    }

    // Student Option 1: View Progress Transcript Card
     private static void studentViewReport() {
    System.out.println("\n===============================================");
    System.out.println("      ACADEMIC TRANSCRIPT REPORT CARD          ");
System.out.println("===============================================");
System.out.println("Student Reference Account: " + currentUserId);
System.out.println("Student Legal Name: " + currentName);
System.out.println("-----------------------------------------------");

String query = "SELECT e.course_code, c.course_name, e.coursework, e.exam, e.total, e.grade" +"FROM enrollments e "
 +"JOIN courses c ON e.course_code = c.course_code " +"WHERE e.student_id = ?;";


try (Connection conn = DriverManager.getConnection(DB_URL);

PreparedStatement pstmt = conn.prepareStatement(query)) {
    
    pstmt.setString(1, currentUserId);

   try (ResultSet rs = pstmt.executeQuery()) {
    System.out.printf("%-10s %-22s %-6s %-6s %-6s %-5s\n", "CODE", "COURSE NAME", "CW", "EXAM", "TOT", "GRADE");
    System.out.println("---------------------------------------");

    while (rs.next())  {
    
        System.out.printf("%-10s %-22s %-6.1f %-6.1f %-6.1f %-5s\n",rs.getString("course_code"),
        rs.getString("course_name"),
        rs.getDouble("coursework"),
        rs.getDouble("exam"),
        rs.getDouble("total"),
        rs.getString("grade") 
    );
}
} 
} catch (SQLException e) {
    System.out.println("Error creating printable view format: " + e.getMessage());

}
System.out.println("===============================================");
}

private static void logout() {
    System.out.println("Logging out session securely...");
currentUserId = null;
currentUserRole = null;
currentName = null;
}
//Admin Option 2: Manage Courses & Semesters
private static void manageCoursesAndSemesters() {
    System.out.println("\n== COURSES & SEMESTERS MANAGEMENT function ==");
    System.out.println("1. Add New Course Unit");
    System.out.println("2. Configure Accademic Year & Semester");
    System.out.println("3. Select administrative action");
    int choice = getIntegerInput();
    if (choice==1) {
    System.out.print("Enter New Course Code (e.g., CS102): ");
    String code = scanner.nextLine().trim();
        System.out.print("Enter Course Title: ");
        String name = scanner.nextLine().trim();

    String query = "INSERT INTO IGNORE courses (course_code, course_name) VALUES (?, ?);";
    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, code);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
            System.out.println("Successfully; Course unit " + name + "initialized globall.");
        } catch (SQLException e) {
            System.out.println("Database Error:: " + e.getMessage());
        }
    } else if (choice==2) {
        System.out.print("Set Academic Year (e.g., 2025/2026): ");
        String academicYear = scanner.nextLine().trim();
        System.out.print("Set Semester (e.g., Fall, Spring): ");
        String semester = scanner.nextLine().trim();
        System.out.println("System Configuration Updated: Current operational Academic Year: " + academicYear + " Semester: " + semester);
    }
}
// Admin Option 3: Manage Programs & Departments
private static void manageProgramsAndDepartments() {
System.out.println("\n== PROGRAMS & DEPARTMENTS MANAGEMENT function ==");
System.out.println("1. Add New Program");
System.out.println("2. Add New Department");
System.out.println("3. Select administrative action");
int choice = getIntegerInput();
if (choice==1) {
    System.out.println("Enter New Department Name: ");
    String deptName = scanner.nextLine().trim();
    System.out.println("Success:Department " + deptName + " added to the system.");
} else if (choice==2) {
    System.out.println("Enter New Program Name: ");
    String progName = scanner.nextLine().trim();
    System.out.println("Success:Program " + progName + " added to the system.");
}
}

// Admin Option 4: Manage Lecturers & Assignments
private static void manageLecturersAndAssignments() {
System.out.println("\n== LECTURERS & ASSIGNMENTS MANAGEMENT function ==");
System.out.println("1. Add New Lecturer");
System.out.println("2. Assign Lecturer to Course");
System.out.println("3. Select administrative action");
int choice = getIntegerInput();
if (choice==1) {
    System.out.println("Enter New Lecturer Name: ");
    String lecName = scanner.nextLine().trim();
    System.out.println("Success:Lecturer " + lecName + " added to the system.");
    String user =scanner.nextLine().trim();
    System.out.println("Set Security Password for Lecturer: ");
    String pass = scanner.nextLine().trim();
    String query = "INSERT INTO users VALUES (?, ?, ?, 'LECTURER');";
    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, "LEC" + System.currentTimeMillis()); // Unique ID
            pstmt.setString(2, user);
            pstmt.setString(3, pass);
            pstmt.executeUpdate();
            System.out.println("Lecturer account created successfully.");
            System.out.println("Lecturer accout created successfully.");
        } catch (SQLException e) {
            System.out.println("Database Error:: " + e.getMessage());
         }
        }
}
        // Admin Option 5: Generate System Reports
    private static void generateSystemReports() {
        System.out.println("\n== SYSTEM REPORTS GENERATION function ==");
        String query = "SELECT COUNT(*) FROM users;";
    }
        private static int getIntegerInput() {
        try {
            int val =scanner.nextInt();
            return val;
        } catch (Exception e) {
            scanner.nextLine(); // Clear buffer
            return -1;
        }
        }
    }
    

        
    


