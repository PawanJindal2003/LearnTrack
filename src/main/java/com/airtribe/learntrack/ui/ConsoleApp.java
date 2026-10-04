package com.airtribe.learntrack.ui;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Person;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;

import java.util.List;
import java.util.Scanner;

/**
 * Console UI for LearnTrack. Business logic stays in service classes.
 */
public class ConsoleApp {

    private final Scanner scanner = new Scanner(System.in);

    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    public ConsoleApp(
            StudentService studentService,
            CourseService courseService,
            EnrollmentService enrollmentService
    ) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }

    public static void main(String[] args) {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentRepository enrollmentRepository = new EnrollmentRepository();

        StudentService studentService = new StudentService(studentRepository);
        CourseService courseService = new CourseService(courseRepository);
        EnrollmentService enrollmentService = new EnrollmentService(
                studentRepository,
                courseRepository,
                enrollmentRepository
        );

        ConsoleApp app = new ConsoleApp(studentService, courseService, enrollmentService);
        app.run();
    }

    public void run() {
        boolean running = true;

        while (running) {
            displayMainMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    studentMenu();
                    break;
                case 2:
                    courseMenu();
                    break;
                case 3:
                    enrollmentMenu();
                    break;
                case 4:
                    running = false;
                    System.out.println("Exiting LearnTrack...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    private void displayMainMenu() {
        System.out.println("\n===== LearnTrack =====");
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Enrollment Management");
        System.out.println("4. Exit");
    }

    private void studentMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\n===== Student Management =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student By ID");
            System.out.println("4. Update Student");
            System.out.println("5. Deactivate Student");
            System.out.println("6. Activate Student");
            System.out.println("7. View Student Display Name");
            System.out.println("8. Back");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deactivateStudent();
                    break;
                case 6:
                    activateStudent();
                    break;
                case 7:
                    showStudentDisplayName();
                    break;
                case 8:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void addStudent() {
        try {
            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine();

            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            System.out.print("Enter batch: ");
            String batch = scanner.nextLine();

            System.out.println(studentService.addStudent(firstName, lastName, email, batch));
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewStudents() {
        List<Student> students = studentService.listStudents();

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.println("--- Students List ---");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private void searchStudent() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();

        try {
            Student student = studentService.searchStudentById(id);
            System.out.println(student);
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateStudent() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();

        try {
            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine();

            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            System.out.print("Enter batch: ");
            String batch = scanner.nextLine();

            System.out.println(
                    studentService.updateStudent(id, firstName, lastName, email, batch)
            );
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deactivateStudent() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();

        try {
            System.out.println(studentService.deactivateStudent(id));
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private void activateStudent() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();

        try {
            System.out.println(studentService.activateStudent(id));
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showStudentDisplayName() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();

        try {
            Student student = studentService.getActiveStudent(id);
            Person person = student;
            person.getDisplayName();
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private void courseMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\n===== Course Management =====");
            System.out.println("1. Add Course");
            System.out.println("2. View All Courses");
            System.out.println("3. Activate Course");
            System.out.println("4. Deactivate Course");
            System.out.println("5. Back");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    viewCourses();
                    break;
                case 3:
                    activateCourse();
                    break;
                case 4:
                    deactivateCourse();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void addCourse() {
        try {
            System.out.print("Enter course name: ");
            String courseName = scanner.nextLine();

            System.out.print("Enter description: ");
            String description = scanner.nextLine();

            int duration = readInt("Enter duration in weeks: ");

            System.out.println(courseService.addCourse(courseName, description, duration));
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewCourses() {
        List<Course> courses = courseService.getAllCourses();

        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }

        for (Course course : courses) {
            System.out.println(course);
        }
    }

    private void activateCourse() {
        System.out.print("Enter course ID: ");
        String id = scanner.nextLine();

        try {
            System.out.println(courseService.activateCourse(id));
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deactivateCourse() {
        System.out.print("Enter course ID: ");
        String id = scanner.nextLine();

        try {
            System.out.println(courseService.deactivateCourse(id));
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void enrollmentMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\n===== Enrollment Management =====");
            System.out.println("1. Enroll Student");
            System.out.println("2. View Student Enrollments");
            System.out.println("3. Complete Enrollment");
            System.out.println("4. Cancel Enrollment");
            System.out.println("5. Back");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    enrollStudent();
                    break;
                case 2:
                    viewStudentEnrollments();
                    break;
                case 3:
                    completeEnrollment();
                    break;
                case 4:
                    cancelEnrollment();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void enrollStudent() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();

        System.out.print("Enter course ID: ");
        String courseId = scanner.nextLine();

        try {
            System.out.println(enrollmentService.enrollStudent(studentId, courseId));
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewStudentEnrollments() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();

        try {
            List<Enrollment> enrollments = enrollmentService.getStudentEnrollments(studentId);

            if (enrollments.isEmpty()) {
                System.out.println("No enrollments found for this student.");
                return;
            }

            for (Enrollment enrollment : enrollments) {
                System.out.println(enrollment);
            }
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private void completeEnrollment() {
        System.out.print("Enter enrollment ID: ");
        String enrollmentId = scanner.nextLine();

        try {
            System.out.println(enrollmentService.completeEnrollment(enrollmentId));
        } catch (EntityNotFoundException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private void cancelEnrollment() {
        System.out.print("Enter enrollment ID: ");
        String enrollmentId = scanner.nextLine();

        try {
            System.out.println(enrollmentService.cancelEnrollment(enrollmentId));
        } catch (EntityNotFoundException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
