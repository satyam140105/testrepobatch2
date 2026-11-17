import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeManagement {
    private static final List<Employee> employees = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Employee Management System ===");
            System.out.println("1. Add employee");
            System.out.println("2. Display all employees");
            System.out.println("3. Search employee by ID");
            System.out.println("4. Update employee details");
            System.out.println("5. Delete employee");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    addEmployee();
                    break;
                case "2":
                    displayEmployees();
                    break;
                case "3":
                    searchEmployee();
                    break;
                case "4":
                    updateEmployee();
                    break;
                case "5":
                    deleteEmployee();
                    break;
                case "6":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose a number from 1 to 6.");
            }
        }

        scanner.close();
    }

    private static void addEmployee() {
        System.out.print("Enter employee ID: ");
        String id = scanner.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("Employee ID cannot be empty.");
            return;
        }
        if (findEmployeeById(id) != null) {
            System.out.println("An employee with that ID already exists.");
            return;
        }

        String name = readRequiredText("Enter employee name: ", "Name");
        if (name == null) return;

        String department = readRequiredText("Enter department: ", "Department");
        if (department == null) return;

        double salary = readSalary();
        employees.add(new Employee(id, name, department, salary));
        System.out.println("Employee added successfully.");
    }

    private static String readRequiredText(String prompt, String fieldName) {
        System.out.print(prompt);
        String value = scanner.nextLine().trim();
        if (value.isEmpty()) {
            System.out.println(fieldName + " cannot be empty.");
            return null;
        }
        return value;
    }

    private static double readSalary() {
        while (true) {
            System.out.print("Enter salary: ");
            String input = scanner.nextLine().trim();
            try {
                double salary = Double.parseDouble(input);
                if (Double.isFinite(salary) && salary >= 0) {
                    return salary;
                }
            } catch (NumberFormatException ignored) {
                // Prompt again for invalid numeric input.
            }
            System.out.println("Please enter a valid, non-negative salary.");
        }
    }

    private static void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees have been added yet.");
            return;
        }

        System.out.println("\nEmployee details:");
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    private static void searchEmployee() {
        System.out.print("Enter employee ID to search: ");
        String id = scanner.nextLine().trim();
        Employee employee = findEmployeeById(id);

        if (employee == null) {
            System.out.println("No employee found with ID " + id + ".");
        } else {
            System.out.println("Employee found:");
            System.out.println(employee);
        }
    }

    private static void updateEmployee() {
        System.out.print("Enter the ID of the employee to update: ");
        String id = scanner.nextLine().trim();
        Employee employee = findEmployeeById(id);

        if (employee == null) {
            System.out.println("No employee found with ID " + id + ".");
            return;
        }

        System.out.println("Current details: " + employee);
        System.out.println("Enter new details (leave a field blank to keep its current value).");

        System.out.print("New name [" + employee.name + "]: ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) {
            employee.name = name;
        }

        System.out.print("New department [" + employee.department + "]: ");
        String department = scanner.nextLine().trim();
        if (!department.isEmpty()) {
            employee.department = department;
        }

        System.out.print("New salary [" + String.format("%.2f", employee.salary) + "]: ");
        String salaryInput = scanner.nextLine().trim();
        if (!salaryInput.isEmpty()) {
            try {
                double salary = Double.parseDouble(salaryInput);
                if (!Double.isFinite(salary) || salary < 0) {
                    System.out.println("Salary must be a valid, non-negative number. Salary was not changed.");
                } else {
                    employee.salary = salary;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid salary. Salary was not changed.");
            }
        }

        System.out.println("Employee details updated: " + employee);
    }

    private static void deleteEmployee() {
        System.out.print("Enter the ID of the employee to delete: ");
        String id = scanner.nextLine().trim();
        Employee employee = findEmployeeById(id);

        if (employee == null) {
            System.out.println("No employee found with ID " + id + ".");
            return;
        }

        employees.remove(employee);
        System.out.println("Employee " + id + " deleted successfully.");
    }

    private static Employee findEmployeeById(String id) {
        for (Employee employee : employees) {
            if (employee.id.equalsIgnoreCase(id)) {
                return employee;
            }
        }
        return null;
    }

    private static class Employee {
        private final String id;
        private String name;
        private String department;
        private double salary;

        private Employee(String id, String name, String department, double salary) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        @Override
        public String toString() {
            return String.format("ID: %s | Name: %s | Department: %s | Salary: %.2f",
                    id, name, department, salary);
        }
    }
}
