package ToDoList;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class TodoList {
    static Scanner input = new Scanner(System.in);
    // ANSI color codes
    static final String RESET = "\u001B[0m";
    static final String RED = "\u001B[31m";
    static final String GREEN = "\u001B[32m";
    static final String YELLOW = "\u001B[33m";
    static final String BLUE = "\u001B[34m";
    static final String PURPLE = "\u001B[35m";
    static final String CYAN = "\u001B[36m";

    static void displayBanner() {
        clearScreen();  // Clear screen before displaying banner
        System.out.println(PURPLE + """
            ╔════════════════════════════════════════╗
            ║     _____         _        _     _     ║
            ║    |_   _|__   __| | ___  | |   (_)    ║
            ║      | |/ _ \\ / _` |/ _ \\ | |   | |    ║
            ║      | | (_) | (_| | (_) || |___| |    ║
            ║      |_|\\___/ \\__,_|\\___/ |_____|_|    ║
            ║                                        ║
            ║          Your Task Manager            ║
            ╚════════════════════════════════════════╝
            """ + RESET);
    }

    // Clear screen method
    static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void displayMenu() {
        System.out.println(YELLOW + """
            ┌──────────── Menu ────────────┐
            │ 1. 📋 View TodoList          │
            │ 2. ➕ Add Task               │
            │ 3. ✅ Complete Task          │
            │ 4. ❌ Remove Task            │
            │ 5. 🏷️  Add Priority         │
            │ 6. 📅 Add Due Date          │
            │ 7. 📊 View Statistics        │
            │ 8. 🚪 Exit                   │
            └────────────────────────────────┘
            """ + RESET);
    }

    static String command() {
        while (true) {
            System.out.print(BLUE + "Enter your choice (1-8): " + RESET);
            String userChoice = input.nextLine().trim();  // Added trim() to handle whitespace
            if (validateCommand(userChoice)) {
                System.out.println(GREEN + "Selected option: " + userChoice + RESET);
                return userChoice;
            }
            System.out.println(RED + "❌ Invalid option! Please enter a number between 1 and 8." + RESET);
        }
    }

    static boolean validateCommand(String userChoice) {
        return userChoice.matches("[1-8]");
    }

    static void addTask(ArrayList<Task> tasks) {
        while (true) {
            System.out.print(BLUE + "Enter task description (or 'quit' to return): " + RESET);
            String description = input.nextLine().trim();

            if (description.equalsIgnoreCase("quit")) {
                break;
            }

            if (description.isEmpty()) {
                System.out.println(RED + "❌ Task description cannot be empty!" + RESET);
                continue;
            }

            Task newTask = new Task(description);
            tasks.add(newTask);
            System.out.println(GREEN + "✨ Task added successfully!" + RESET);
            displayTasks(tasks);
        }
    }

    static void displayTasks(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println(YELLOW + "📝 Your todo list is empty!" + RESET);
            return;
        }

        System.out.println(PURPLE + """
            ┌────────────────── Your Tasks ──────────────────┐""" + RESET);

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            String status = task.isCompleted() ? "✅" : "⭕";
            String priority = task.getPriority() != null ? "[" + task.getPriority() + "]" : "";
            String dueDate = task.getDueDate() != null ? "📅 " + task.getDueDate() : "";

            System.out.printf("%s%d. %s %s %s %s%s\n",
                    CYAN,
                    (i + 1),
                    status,
                    task.getDescription(),
                    priority,
                    dueDate,
                    RESET);
        }

        System.out.println(PURPLE + "└───────────────────────────────────────────────┘" + RESET);
    }

    static void completeTask(ArrayList<Task> tasks) {
        while (true) {
            displayTasks(tasks);
            if (tasks.isEmpty()) {
                System.out.println(YELLOW + "No tasks to complete! Add some tasks first." + RESET);
                return;
            }

            System.out.print(BLUE + "Enter task number to mark as complete (or 'quit' to return): " + RESET);
            String userInput = input.nextLine().trim(); // Changed variable name to userInput

            if (userInput.equalsIgnoreCase("quit")) {
                break;
            }

            try {
                int taskNum = Integer.parseInt(userInput) - 1; // Changed variable to userInput
                if (taskNum >= 0 && taskNum < tasks.size()) {
                    Task task = tasks.get(taskNum);
                    if (!task.isCompleted()) {
                        task.setCompleted(true);
                        System.out.println(GREEN + "🎉 Task marked as complete!" + RESET);
                    } else {
                        System.out.println(YELLOW + "📌 Task is already completed!" + RESET);
                    }
                } else {
                    System.out.println(RED + "❌ Invalid task number! Please enter a number between 1 and " + tasks.size() + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "❌ Invalid input! Please enter a valid number." + RESET);
            }
        }
    }


    static void setPriority(ArrayList<Task> tasks) {
        while (true) {
            displayTasks(tasks);
            if (tasks.isEmpty()) {
                System.out.println(YELLOW + "No tasks to set priority! Add some tasks first." + RESET);
                return;
            }

            System.out.print(BLUE + "Enter task number to set priority (or 'quit' to return): " + RESET);
            String userInput = input.nextLine().trim();  // Changed variable name to userInput

            if (userInput.equalsIgnoreCase("quit")) {
                break;
            }

            try {
                int taskNum = Integer.parseInt(userInput) - 1;  // Updated to use userInput
                if (taskNum >= 0 && taskNum < tasks.size()) {
                    System.out.print(BLUE + "Enter priority (HIGH, MEDIUM, LOW): " + RESET);
                    String priorityInput = input.nextLine().trim().toUpperCase();  // Changed variable name to priorityInput
                    if (priorityInput.matches("HIGH|MEDIUM|LOW")) {
                        tasks.get(taskNum).setPriority(priorityInput);
                        System.out.println(GREEN + "🏷️ Priority set successfully!" + RESET);
                    } else {
                        System.out.println(RED + "❌ Invalid priority level! Please enter HIGH, MEDIUM, or LOW." + RESET);
                    }
                } else {
                    System.out.println(RED + "❌ Invalid task number! Please enter a number between 1 and " + tasks.size() + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "❌ Invalid input! Please enter a valid number." + RESET);
            }
        }
    }


    static void setDueDate(ArrayList<Task> tasks) {
        while (true) {
            displayTasks(tasks);
            if (tasks.isEmpty()) {
                System.out.println(YELLOW + "No tasks to set due date! Add some tasks first." + RESET);
                return;
            }

            System.out.print(BLUE + "Enter task number to set due date (or 'quit' to return): " + RESET);
            String userInput = input.nextLine().trim();

            if (userInput.equalsIgnoreCase("quit")) {
                break;
            }

            try {
                int taskNum = Integer.parseInt(userInput) - 1;
                if (taskNum >= 0 && taskNum < tasks.size()) {
                    System.out.print(BLUE + "Enter due date (YYYY-MM-DD): " + RESET);
                    String dueDate = input.nextLine().trim();

                    try {
                        // Validate date format and parse it
                        LocalDate.parse(dueDate);
                        tasks.get(taskNum).setDueDate(dueDate);
                        System.out.println(GREEN + "📅 Due date set successfully!" + RESET);
                    } catch (DateTimeParseException e) {
                        System.out.println(RED + "❌ Invalid date format! Please use YYYY-MM-DD format." + RESET);
                    }
                } else {
                    System.out.println(RED + "❌ Invalid task number! Please enter a number between 1 and " + tasks.size() + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "❌ Invalid input! Please enter a valid number." + RESET);
            }
        }
    }

    static void displayStatistics(ArrayList<Task> tasks) {
        try {
            int totalTasks = tasks.size();
            int completedTasks = (int) tasks.stream().filter(Task::isCompleted).count();
            int pendingTasks = totalTasks - completedTasks;

            System.out.println(PURPLE + """
                ┌─────────── Statistics ───────────┐""" + RESET);
            System.out.printf("%s📊 Total Tasks: %d%s\n", CYAN, totalTasks, RESET);
            System.out.printf("%s✅ Completed: %d%s\n", GREEN, completedTasks, RESET);
            System.out.printf("%s⭕ Pending: %d%s\n", YELLOW, pendingTasks, RESET);
            if (totalTasks > 0) {
                int completionRate = (completedTasks * 100) / totalTasks;
                System.out.printf("%s📈 Completion Rate: %d%%%s\n", BLUE, completionRate, RESET);

                // Add priority distribution
                long highPriority = tasks.stream().filter(t -> "HIGH".equals(t.getPriority())).count();
                long mediumPriority = tasks.stream().filter(t -> "MEDIUM".equals(t.getPriority())).count();
                long lowPriority = tasks.stream().filter(t -> "LOW".equals(t.getPriority())).count();

                System.out.printf("%s🏷️ Priority Distribution:%s\n", PURPLE, RESET);
                System.out.printf("  %s⚡ High: %d%s\n", RED, highPriority, RESET);
                System.out.printf("  %s📍 Medium: %d%s\n", YELLOW, mediumPriority, RESET);
                System.out.printf("  %s🔽 Low: %d%s\n", GREEN, lowPriority, RESET);
            }
            System.out.println(PURPLE + """
                └─────────────────────────────────┘""" + RESET);
        } catch (Exception e) {
            System.out.println(RED + "❌ Error calculating statistics!" + RESET);
        }
    }
    static void removeTask(ArrayList<Task> tasks) {
        while (true) {
            displayTasks(tasks);
            if (tasks.isEmpty()) {
                System.out.println(YELLOW + "No tasks to remove! Add some tasks first." + RESET);
                return;
            }

            System.out.print(BLUE + "Enter task number to remove (or 'quit' to return): " + RESET);
            String userInput = input.nextLine().trim();  // Changed variable name to userInput

            if (userInput.equalsIgnoreCase("quit")) {
                break;
            }

            try {
                int taskNum = Integer.parseInt(userInput) - 1;  // Updated to use userInput
                if (taskNum >= 0 && taskNum < tasks.size()) {
                    Task removedTask = tasks.remove(taskNum);
                    System.out.println(GREEN + "🗑️ Task '" + removedTask.getDescription() + "' removed successfully!" + RESET);
                } else {
                    System.out.println(RED + "❌ Invalid task number! Please enter a number between 1 and " + tasks.size() + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "❌ Invalid input! Please enter a valid number." + RESET);
            }
        }
    }


    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<>();



        displayBanner();

        while (true) {
            try {
                displayMenu();
                String choice = command();

                switch (choice) {
                    case "1" -> displayTasks(tasks);
                    case "2" -> addTask(tasks);
                    case "3" -> completeTask(tasks);
                    case "4" -> removeTask(tasks);
                    case "5" -> setPriority(tasks);
                    case "6" -> setDueDate(tasks);
                    case "7" -> displayStatistics(tasks);
                    case "8" -> {
                        System.out.println(YELLOW + """
                            ╔══════════════════════════╗
                            ║   👋 Thanks for using    ║
                            ║      TodoList App!       ║
                            ╚══════════════════════════╝
                            """ + RESET);
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println(RED + "❌ An unexpected error occurred! Please try again." + RESET);
            }
        }
    }
}