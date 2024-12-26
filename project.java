import java.util.ArrayList;
import java.util.Scanner;

public class TodoListApp {

    private static ArrayList<String> tasks = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- To-Do List Menu ---");
            System.out.println("1. View Tasks");
            System.out.println("2. Add Task");
            System.out.println("3. Update Task");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                scanner.next();
            }
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewTasks();
                    break;
                case 2:
                    addTask(scanner);
                    break;
                case 3:
                    updateTask(scanner);
                    break;
                case 4:
                    deleteTask(scanner);
                    break;
                case 5:
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please choose a number between 1 and 5.");
            }

        } while (choice != 5);

        scanner.close();
    }

    private static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Your to-do list is empty.");
        } else {
            System.out.println("\n--- Your Tasks ---");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    private static void addTask(Scanner scanner) {
        System.out.print("Enter the task to add: ");
        scanner.nextLine(); // Consume the leftover newline
        String task = scanner.nextLine();
        tasks.add(task);
        System.out.println("Task added successfully.");
    }

    private static void updateTask(Scanner scanner) {
        viewTasks();
        if (!tasks.isEmpty()) {
            System.out.print("Enter the task number to update: ");
            int taskNumber = scanner.nextInt();
            if (taskNumber > 0 && taskNumber <= tasks.size()) {
                System.out.print("Enter the updated task: ");
                scanner.nextLine(); // Consume the leftover newline
                String updatedTask = scanner.nextLine();
                tasks.set(taskNumber - 1, updatedTask);
                System.out.println("Task updated successfully.");
            } else {
                System.out.println("Invalid task number.");
            }
        }
    }

    private static void deleteTask(Scanner scanner) {
        viewTasks();
        if (!tasks.isEmpty()) {
            System.out.print("Enter the task number to delete: ");
            int taskNumber = scanner.nextInt();
            if (taskNumber > 0 && taskNumber <= tasks.size()) {
                tasks.remove(taskNumber - 1);
                System.out.println("Task deleted successfully.");
            } else {
                System.out.println("Invalid task number.");
            }
        }
    }
}
