import java.util.*;

public class StudentGradesSystem {

    // Student class
    static class Student {
        private int id;
        private String name;
        private List<Double> grades;

        public Student(int id, String name) {
            this.id = id;
            this.name = name;
            this.grades = new ArrayList<>();
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public List<Double> getGrades() {
            return grades;
        }

        public void addGrade(double grade) {
            grades.add(grade);
        }

        public double calculateAverage() {
            if (grades.isEmpty()) {
                return 0.0;
            }
            double sum = 0;
            for (double grade : grades) {
                sum += grade;
            }
            return sum / grades.size();
        }

        @Override
        public String toString() {
            return "ID: " + id + ", Name: " + name + ", Grades: " + grades + ", Average: " + String.format("%.2f", calculateAverage());
        }
    }

    // Grades Manager
    static class GradesManager {
        private List<Student> students;

        public GradesManager() {
            students = new ArrayList<>();
        }

        public void addStudent(String name) {
            int id = students.size() + 1;
            Student student = new Student(id, name);
            students.add(student);
            System.out.println("Student added successfully: " + name);
        }

        public void addGradeToStudent(int id, double grade) {
            Student student = findStudentById(id);
            if (student == null) {
                System.out.println("Student not found.");
                return;
            }
            student.addGrade(grade);
            System.out.println("Grade added successfully to " + student.getName());
        }

        public void viewAllStudents() {
            if (students.isEmpty()) {
                System.out.println("No students available.");
                return;
            }
            for (Student student : students) {
                System.out.println(student);
            }
        }

        private Student findStudentById(int id) {
            for (Student student : students) {
                if (student.getId() == id) {
                    return student;
                }
            }
            return null;
        }
    }

    // Main application
    public static void main(String[] args) {
        GradesManager manager = new GradesManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nStudent Grades Management System");
            System.out.println("1. Add Student");
            System.out.println("2. Add Grade to Student");
            System.out.println("3. View All Students");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    manager.addStudent(name);
                    break;

                case 2:
                    System.out.print("Enter student ID: ");
                    int id;
                    try {
                        id = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID. Please enter a number.");
                        break;
                    }
                    System.out.print("Enter grade: ");
                    double grade;
                    try {
                        grade = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid grade. Please enter a valid number.");
                        break;
                    }
                    manager.addGradeToStudent(id, grade);
                    break;

                case 3:
                    manager.viewAllStudents();
                    break;

                case 4:
                    System.out.println("Exiting the program. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
