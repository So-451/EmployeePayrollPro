package com.payroll;

import com.payroll.models.Developer;
import com.payroll.models.Employee;
import com.payroll.models.Leave;
import com.payroll.models.Manager;
import com.payroll.services.EmployeeService;
import com.payroll.services.LeaveService;
import com.payroll.services.PayrollService;
import com.payroll.utils.PayrollConstants;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Main class for Employee Payroll Management System
 * Provides a menu-driven interface for managing employees, salaries, and leaves
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EmployeeService employeeService = new EmployeeService();
    private static final LeaveService leaveService = new LeaveService();
    private static final PayrollService payrollService = new PayrollService();

    // Flag to indicate if we're running in test mode
    private static boolean isTestMode = false;
    
    public static void main(String[] args) {
        loadData();
        
        // Check if any command line arguments were provided
        if (args.length > 0 && args[0].equals("--test")) {
            // Set test mode flag
            isTestMode = true;
            // Run a test with sample data
            runSystemTest();
            // Exit after tests in test mode
            System.exit(0);
        } else {
            // Normal operation
            displayMenu();
        }
    }

    /**
     * Loads data from files during application startup
     */
    private static void loadData() {
        System.out.println("Loading data...");
        employeeService.loadEmployees();
        leaveService.loadLeaves();
        payrollService.loadSalaries();
        System.out.println("Data loaded successfully!");
    }
    
    /**
     * Run a system test with sample data to verify functionality
     */
    private static void runSystemTest() {
        System.out.println("\n===== RUNNING SYSTEM TEST =====");
        
        // Add a test manager
        Manager manager = new Manager(
            "M001", 
            "John Manager", 
            "john@company.com", 
            "555-1234", 
            "Operations", 
            LocalDate.now().minusYears(2), 
            50000.0, 
            5, 
            2);
        
        employeeService.addEmployee(manager);
        System.out.println("Added manager: " + manager);
        
        // Add a test developer
        Developer developer = new Developer(
            "D001", 
            "Jane Developer", 
            "jane@company.com", 
            "555-5678", 
            "Engineering", 
            LocalDate.now().minusYears(1), 
            45000.0, 
            "Java", 
            5);
        
        employeeService.addEmployee(developer);
        System.out.println("Added developer: " + developer);
        
        // Display all employees
        System.out.println("\nAll Employees:");
        employeeService.getAllEmployees().forEach(System.out::println);
        
        // Apply for leave
        LocalDate leaveStart = LocalDate.now().plusDays(10);
        LocalDate leaveEnd = leaveStart.plusDays(3);
        Leave leave = new Leave(developer.getId(), leaveStart, leaveEnd, "SICK", "Medical appointment");
        leaveService.applyLeave(leave);
        System.out.println("\nApplied leave: " + leave);
        
        // Approve the leave
        leaveService.approveLeave(developer.getId(), leaveStart);
        System.out.println("Approved leave for " + developer.getName());
        
        // Process salaries
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();
        System.out.println("\nProcessing salaries for " + currentMonth + "/" + currentYear);
        payrollService.processSalaries(employeeService.getAllEmployees(), leaveService.getAllLeaves(), currentMonth, currentYear);
        
        // View salary details
        System.out.println("\nSalary details for " + developer.getName() + ":");
        payrollService.viewSalaryDetails(developer.getId(), currentMonth, currentYear);
        
        // Save all data
        System.out.println("\nSaving data...");
        employeeService.saveEmployees();
        leaveService.saveLeaves();
        payrollService.saveSalaries();
        
        System.out.println("\n===== SYSTEM TEST COMPLETED =====");
        System.out.println("All tests passed successfully!");
        
        // Only display the menu if not in test mode
        if (!isTestMode) {
            displayMenu();
        }
    }

    /**
     * Displays the main menu and handles user input
     */
    private static void displayMenu() {
        int choice;
        do {
            System.out.println("\n===== EMPLOYEE PAYROLL MANAGEMENT SYSTEM =====");
            System.out.println("1. Add New Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Update Employee");
            System.out.println("5. Apply Leave");
            System.out.println("6. View Employee Leaves");
            System.out.println("7. Process Salary");
            System.out.println("8. View Salary Details");
            System.out.println("9. Save Data");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            
            try {
                choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        addNewEmployee();
                        break;
                    case 2:
                        viewAllEmployees();
                        break;
                    case 3:
                        searchEmployee();
                        break;
                    case 4:
                        updateEmployee();
                        break;
                    case 5:
                        applyLeave();
                        break;
                    case 6:
                        viewEmployeeLeaves();
                        break;
                    case 7:
                        processSalary();
                        break;
                    case 8:
                        viewSalaryDetails();
                        break;
                    case 9:
                        saveData();
                        break;
                    case 0:
                        saveData();
                        System.out.println("Thank you for using the Employee Payroll Management System. Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                choice = -1;
            }
        } while (choice != 0);
        
        scanner.close();
    }

    /**
     * Adds a new employee to the system
     */
    private static void addNewEmployee() {
        System.out.println("\n===== ADD NEW EMPLOYEE =====");
        System.out.println("Select employee type:");
        System.out.println("1. Manager");
        System.out.println("2. Developer");
        System.out.print("Enter choice: ");
        
        int type = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();
        
        System.out.print("Enter Basic Salary: ");
        double basicSalary = Double.parseDouble(scanner.nextLine());
        
        LocalDate joiningDate = LocalDate.now();
        
        Employee employee;
        
        if (type == 1) {
            System.out.print("Enter Team Size: ");
            int teamSize = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Enter Management Level (1-3): ");
            int managementLevel = Integer.parseInt(scanner.nextLine());
            
            employee = new Manager(id, name, email, phone, department, joiningDate, basicSalary, teamSize, managementLevel);
        } else {
            System.out.print("Enter Programming Language: ");
            String programmingLanguage = scanner.nextLine();
            
            System.out.print("Enter Experience Years: ");
            int experienceYears = Integer.parseInt(scanner.nextLine());
            
            employee = new Developer(id, name, email, phone, department, joiningDate, basicSalary, programmingLanguage, experienceYears);
        }
        
        employeeService.addEmployee(employee);
        System.out.println("Employee added successfully!");
    }

    /**
     * Displays all employees in the system
     */
    private static void viewAllEmployees() {
        System.out.println("\n===== ALL EMPLOYEES =====");
        employeeService.getAllEmployees().forEach(System.out::println);
    }

    /**
     * Searches for an employee by ID
     */
    private static void searchEmployee() {
        System.out.println("\n===== SEARCH EMPLOYEE =====");
        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine();
        
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            System.out.println(employee.getDetailedInfo());
        } else {
            System.out.println("Employee not found!");
        }
    }

    /**
     * Updates an existing employee's information
     */
    private static void updateEmployee() {
        System.out.println("\n===== UPDATE EMPLOYEE =====");
        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine();
        
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            System.out.println("Employee not found!");
            return;
        }
        
        System.out.println("Current Details: " + employee);
        
        System.out.println("What would you like to update?");
        System.out.println("1. Name");
        System.out.println("2. Email");
        System.out.println("3. Phone");
        System.out.println("4. Department");
        System.out.println("5. Basic Salary");
        System.out.print("Enter choice: ");
        
        int choice = Integer.parseInt(scanner.nextLine());
        
        switch (choice) {
            case 1:
                System.out.print("Enter new Name: ");
                employee.setName(scanner.nextLine());
                break;
            case 2:
                System.out.print("Enter new Email: ");
                employee.setEmail(scanner.nextLine());
                break;
            case 3:
                System.out.print("Enter new Phone: ");
                employee.setPhone(scanner.nextLine());
                break;
            case 4:
                System.out.print("Enter new Department: ");
                employee.setDepartment(scanner.nextLine());
                break;
            case 5:
                System.out.print("Enter new Basic Salary: ");
                employee.setBasicSalary(Double.parseDouble(scanner.nextLine()));
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }
        
        employeeService.updateEmployee(employee);
        System.out.println("Employee updated successfully!");
    }

    /**
     * Processes applying for leave
     */
    private static void applyLeave() {
        System.out.println("\n===== APPLY LEAVE =====");
        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine();
        
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            System.out.println("Employee not found!");
            return;
        }
        
        System.out.print("Enter Start Date (YYYY-MM-DD): ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());
        
        System.out.print("Enter End Date (YYYY-MM-DD): ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());
        
        System.out.print("Enter Leave Type (SICK, CASUAL, ANNUAL): ");
        String leaveType = scanner.nextLine();
        
        System.out.print("Enter Reason: ");
        String reason = scanner.nextLine();
        
        Leave leave = new Leave(employee.getId(), startDate, endDate, leaveType, reason);
        leaveService.applyLeave(leave);
        
        System.out.println("Leave applied successfully!");
    }

    /**
     * Views leaves for a specific employee
     */
    private static void viewEmployeeLeaves() {
        System.out.println("\n===== VIEW EMPLOYEE LEAVES =====");
        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine();
        
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            System.out.println("Employee not found!");
            return;
        }
        
        System.out.println("Leaves for " + employee.getName() + ":");
        leaveService.getLeavesByEmployeeId(id).forEach(System.out::println);
    }

    /**
     * Processes salary for all employees
     */
    private static void processSalary() {
        System.out.println("\n===== PROCESS SALARY =====");
        System.out.print("Enter Month (1-12): ");
        int month = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Enter Year: ");
        int year = Integer.parseInt(scanner.nextLine());
        
        payrollService.processSalaries(employeeService.getAllEmployees(), leaveService.getAllLeaves(), month, year);
        System.out.println("Salaries processed successfully!");
    }

    /**
     * Views salary details for a specific employee
     */
    private static void viewSalaryDetails() {
        System.out.println("\n===== VIEW SALARY DETAILS =====");
        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine();
        
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            System.out.println("Employee not found!");
            return;
        }
        
        System.out.print("Enter Month (1-12): ");
        int month = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Enter Year: ");
        int year = Integer.parseInt(scanner.nextLine());
        
        payrollService.viewSalaryDetails(id, month, year);
    }

    /**
     * Saves all data to files
     */
    private static void saveData() {
        System.out.println("\n===== SAVING DATA =====");
        employeeService.saveEmployees();
        leaveService.saveLeaves();
        payrollService.saveSalaries();
        System.out.println("Data saved successfully!");
    }
}
