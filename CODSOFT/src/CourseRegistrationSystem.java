import java.util.*;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private int enrolledStudents;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = 0;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public int getAvailableSlots() {
        return capacity - enrolledStudents;
    }

    public boolean registerStudent() {
        if (enrolledStudents < capacity) {
            enrolledStudents++;
            return true;
        }
        return false;
    }

    public void dropStudent() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
        }
    }

    public void displayCourseDetails() {
        System.out.println("Course Code: " + courseCode);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Schedule: " + schedule);
        System.out.println("Available Slots: " + getAvailableSlots());
        System.out.println("-----------------------------");
    }
}

class Student {
    private String studentID;
    private String name;
    private List<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentID() {
        return studentID;
    }

    public void registerCourse(Course course) {
        if (registeredCourses.contains(course)) {
            System.out.println("You are already registered for this course.");
            return;
        }
        if (course.registerStudent()) {
            registeredCourses.add(course);
            System.out.println("Successfully registered for course: " + course.getCourseCode());
        } else {
            System.out.println("Registration failed! Course is full.");
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            course.dropStudent();
            registeredCourses.remove(course);
            System.out.println("Successfully dropped course: " + course.getCourseCode());
        } else {
            System.out.println("You are not registered for this course.");
        }
    }

    public void displayRegisteredCourses() {
        if (registeredCourses.isEmpty()) {
            System.out.println("You have not registered for any courses yet.");
            return;
        }
        System.out.println("Registered Courses:");
        for (Course course : registeredCourses) {
            System.out.println("- " + course.getCourseCode() + " (" + course.getAvailableSlots() + " slots left)");
        }
    }
}

public class CourseRegistrationSystem {
    private List<Course> courseDatabase;
    private List<Student> studentDatabase;
    private Scanner scanner;

    public CourseRegistrationSystem() {
        courseDatabase = new ArrayList<>();
        studentDatabase = new ArrayList<>();
        scanner = new Scanner(System.in);
        initializeCourses();
    }

    private void initializeCourses() {
        courseDatabase.add(new Course("CS101", "Introduction to Computer Science", "Basics of CS", 3, "Mon 10 AM"));
        courseDatabase.add(new Course("MA101", "Calculus I", "Basic Calculus", 2, "Wed 2 PM"));
        courseDatabase.add(new Course("PH101", "Physics I", "Introduction to Physics", 2, "Fri 9 AM"));
        courseDatabase.add(new Course("EN101", "English Composition", "Academic Writing", 2, "Tue 11 AM"));
    }

    private Student findStudent(String studentID) {
        for (Student student : studentDatabase) {
            if (student.getStudentID().equals(studentID)) {
                return student;
            }
        }
        return null;
    }

    private Course findCourse(String courseCode) {
        for (Course course : courseDatabase) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                return course;
            }
        }
        return null;
    }

    public void start() {
        System.out.println("===== Student Course Registration System =====");

        while (true) {
            System.out.println("\n1. Register Student\n2. View Courses\n3. Register for Course");
            System.out.println("4. Drop Course\n5. View Registered Courses\n6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    registerStudent();
                    break;
                case 2:
                    viewCourses();
                    break;
                case 3:
                    registerForCourse();
                    break;
                case 4:
                    dropCourse();
                    break;
                case 5:
                    viewStudentCourses();
                    break;
                case 6:
                    System.out.println("Thank you for using the Course Registration System. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void registerStudent() {
        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine();
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();

        if (findStudent(studentID) != null) {
            System.out.println("Student ID already exists!");
            return;
        }

        studentDatabase.add(new Student(studentID, name));
        System.out.println("Student registered successfully.");
    }

    private void viewCourses() {
        System.out.println("\nAvailable Courses:");
        for (Course course : courseDatabase) {
            course.displayCourseDetails();
        }
    }

    private void registerForCourse() {
        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine();
        Student student = findStudent(studentID);

        if (student == null) {
            System.out.println("Student not found. Please register first.");
            return;
        }

        viewCourses();
        System.out.print("Enter Course Code to Register: ");
        String courseCode = scanner.nextLine();
        Course course = findCourse(courseCode);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        student.registerCourse(course);
    }

    private void dropCourse() {
        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine();
        Student student = findStudent(studentID);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        student.displayRegisteredCourses();
        System.out.print("Enter Course Code to Drop: ");
        String courseCode = scanner.nextLine();
        Course course = findCourse(courseCode);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        student.dropCourse(course);
    }

    private void viewStudentCourses() {
        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine();
        Student student = findStudent(studentID);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        student.displayRegisteredCourses();
    }

    public static void main(String[] args) {
        CourseRegistrationSystem system = new CourseRegistrationSystem();
        system.start();
    }
}

