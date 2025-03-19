package com.payroll.services;

import com.payroll.models.Developer;
import com.payroll.models.Employee;
import com.payroll.models.Manager;
import com.payroll.utils.PayrollConstants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EmployeeService provides methods for managing employees
 */
public class EmployeeService {
    private Map<String, Employee> employees;
    private FileService fileService;

    /**
     * Constructor for EmployeeService
     */
    public EmployeeService() {
        this.employees = new HashMap<>();
        this.fileService = new FileService();
    }

    /**
     * Add a new employee
     * 
     * @param employee The employee to add
     * @return true if successful, false if employee ID already exists
     */
    public boolean addEmployee(Employee employee) {
        if (employees.containsKey(employee.getId())) {
            System.out.println("Employee with ID " + employee.getId() + " already exists.");
            return false;
        }
        
        employees.put(employee.getId(), employee);
        return true;
    }

    /**
     * Update an existing employee
     * 
     * @param employee The employee with updated information
     * @return true if successful, false if employee ID does not exist
     */
    public boolean updateEmployee(Employee employee) {
        if (!employees.containsKey(employee.getId())) {
            System.out.println("Employee with ID " + employee.getId() + " not found.");
            return false;
        }
        
        employees.put(employee.getId(), employee);
        return true;
    }

    /**
     * Get an employee by ID
     * 
     * @param id The employee ID
     * @return The employee if found, null otherwise
     */
    public Employee getEmployeeById(String id) {
        return employees.get(id);
    }

    /**
     * Get all employees
     * 
     * @return List of all employees
     */
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    /**
     * Load employees from file
     */
    public void loadEmployees() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PayrollConstants.EMPLOYEES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 8) {
                    continue; // Skip invalid entries
                }
                
                String employeeType = parts[7];
                Employee employee;
                
                if (employeeType.equals("Manager")) {
                    employee = Manager.fromCsv(line);
                } else if (employeeType.equals("Developer")) {
                    employee = Developer.fromCsv(line);
                } else {
                    continue; // Skip unknown employee types
                }
                
                employees.put(employee.getId(), employee);
            }
            System.out.println("Loaded " + employees.size() + " employees.");
        } catch (IOException e) {
            System.out.println("No existing employee data found or error reading file. Starting with empty employee list.");
        }
    }

    /**
     * Save employees to file
     */
    public void saveEmployees() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PayrollConstants.EMPLOYEES_FILE))) {
            for (Employee employee : employees.values()) {
                writer.write(employee.toCsv());
                writer.newLine();
            }
            System.out.println("Saved " + employees.size() + " employees.");
        } catch (IOException e) {
            System.out.println("Error saving employees: " + e.getMessage());
        }
    }

    /**
     * Deduct leave days from an employee's available leave days
     * 
     * @param employeeId The employee ID
     * @param days Number of days to deduct
     * @return true if successful, false if employee not found or insufficient leave days
     */
    public boolean deductLeaveDays(String employeeId, int days) {
        Employee employee = getEmployeeById(employeeId);
        if (employee == null) {
            return false;
        }
        
        if (employee.getAvailableLeaveDays() < days) {
            return false;
        }
        
        employee.setAvailableLeaveDays(employee.getAvailableLeaveDays() - days);
        return true;
    }
}
