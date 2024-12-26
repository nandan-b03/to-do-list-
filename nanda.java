import java.io.*;
import java.util.*;

public class TaskManagementSystem {
    private static final String FILE_NAME = "tasks.txt";

    // Task class
    static class Task {
        private int id;
        private String name;
        private String description;
        private boolean isCompleted;

        public Task(int id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.isCompleted = false;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public boolean isCompleted() {
            return isCompleted;
        }

        public void markAsCompleted() {
            isCompleted = true;
        }

        @Override
        public String toString() {
            return "ID: " + id + "\nName: " + name + "\nDescription: " + description +
                   "\nStatus: " + (isCompleted ? "Completed" : "Pending") + "\n";
        }
    }

    // Task manager
    static class TaskManager {
        private List<Task> tasks;

        public TaskManager() {
            tasks = new ArrayList<>();
            loadTasksFromFile();
        }

        public void addTask(String name, String description) {
            int id = tasks.size() + 1;
            Task task = new Task(id, name, description);
            tasks.add(task);
            saveTasksToFile();
            System.out.println("Task added successfully!");
        }

        public void viewTasks() {
            if (tasks.isEmpty()) {
                System.out.println("No tasks available.");
                return;
            }

            for (Task task : tasks) {
                System.out.println(task);
            }
        }

        public void deleteTask(int id) {
            Task taskToRemove = null;
            for (Task task : tasks) {
                if (task.getId() == id) {
                    taskToRemove = task;
                    break;
                }
            }

            if (taskToRemove != null) {
                tasks.remove(taskToRemove);
                saveTasksToFile();
                System.out.println("Task deleted successfully!");
            } else {
                System.out.println("Task not found.");
            }
        }

        public void markTaskAsCompleted(int id) {
            for (Task task : tasks) {
                if (task.getId() == id) {
                    task.markAsCompleted();
                    saveTasksToFile();
                    System.out.println("Task marked as completed!");
                    return;
                }
            }
            System.out.println("Task not found.");
        }

        private void saveTasksToFile() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                for (Task task : tasks) {
                    writer.write(task.getId() + "," + task.getName() + "," +
                                 task.getDescription() + "," + task.isCompleted());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error saving tasks: " + e.getMessage());
            }
        }

        private void loadTasksFromFile() {
            tasks.clear();
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String description = parts[2];
                    boolean isCompleted = Boolean.parseBoolean(parts[3]);

                    Task task = new Task(id, name, description);
                    if (isCompleted) {
                        task.markAsCompleted();
                    }
                    tasks.add(task);
                }
            } catch (IOException e) {
                System.out.println("Error loading tasks: " + e.getMessage());
            }
        }
    }

    // Main application
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();

        while (true) {
            System.out.println("\nTask Management System");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Delete Task");
            System.out.println("4. Mark Task as Completed");
            System.out.println("5. Exit");
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
                    System.out.print("Enter task name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter task description: ");
                    String description = scanner.nextLine();
                    taskManager.addTask(name, description);
                    break;

                case 2:
                    taskManager.viewTasks();
                    break;

                case 3:
                    System.out.print("Enter task ID to delete: ");
                    int idToDelete = Integer.parseInt(scanner.nextLine());
                    taskManager.deleteTask(idToDelete);
                    break;

                case 4:
                    System.out.print("Enter task ID to mark as completed: ");
                    int idToMark = Integer.parseInt(scanner.nextLine());
                    taskManager.markTaskAsCompleted(idToMark);
                    break;

                case 5:
                    System.out.println("Exiting the application. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
