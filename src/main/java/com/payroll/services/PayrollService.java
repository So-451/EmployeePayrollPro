package com.payroll.services;

import com.payroll.models.Employee;
import com.payroll.models.Leave;
import com.payroll.models.Salary;
import com.payroll.utils.PayrollConstants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;

/**
 * PayrollService provides methods for managing salary processing
 */
public class PayrollService {
    private List<Salary> salaries;
    private Map<String, Queue<Salary>> pendingSalaries; // Queue for each employee
    private FileService fileService;

    /**
     * Constructor for PayrollService
     */
    public PayrollService() {
        this.salaries = new ArrayList<>();
        this.pendingSalaries = new HashMap<>();
        this.fileService = new FileService();
    }

    /**
     * Process salaries for all employees
     * 
     * @param employees List of employees
     * @param leaves List of leaves
     * @param month Month to process
     * @param year Year to process
     */
    public void processSalaries(List<Employee> employees, List<Leave> leaves, int month, int year) {
        for (Employee employee : employees) {
            processSalary(employee, leaves, month, year);
        }
    }

    /**
     * Process salary for a single employee
     * 
     * @param employee The employee
     * @param leaves List of leaves
     * @param month Month to process
     * @param year Year to process
     */
    private void processSalary(Employee employee, List<Leave> leaves, int month, int year) {
        // Calculate leave days for the month
        int leaveDays = calculateLeaveDays(employee.getId(), leaves, month, year);
        
        // Calculate working days in the month
        int totalDaysInMonth = YearMonth.of(year, month).lengthOfMonth();
        
        // Calculate deduction for leave days (if any)
        double leaveDeduction = 0;
        if (leaveDays > 0) {
            leaveDeduction = (employee.getBasicSalary() / totalDaysInMonth) * leaveDays;
        }
        
        // Calculate salary components
        double basicSalary = employee.getBasicSalary();
        double grossSalary = employee.calculateGrossSalary() - leaveDeduction;
        double taxAmount = employee.calculateTax(grossSalary);
        double netSalary = grossSalary - taxAmount;
        
        // Create salary record
        Salary salary = new Salary(
                employee.getId(),
                basicSalary,
                grossSalary,
                taxAmount,
                netSalary,
                month,
                year,
                leaveDays
        );
        
        // Add to salaries list
        salaries.add(salary);
        
        // Add to pending salaries queue
        if (!pendingSalaries.containsKey(employee.getId())) {
            pendingSalaries.put(employee.getId(), new LinkedList<>());
        }
        pendingSalaries.get(employee.getId()).add(salary);
    }

    /**
     * Calculate leave days for an employee in a specific month
     * 
     * @param employeeId The employee ID
     * @param leaves List of leaves
     * @param month Month to calculate
     * @param year Year to calculate
     * @return Number of leave days
     */
    private int calculateLeaveDays(String employeeId, List<Leave> leaves, int month, int year) {
        int leaveDays = 0;
        
        for (Leave leave : leaves) {
            if (leave.getEmployeeId().equals(employeeId) && 
                leave.getStatus().equals("APPROVED") && 
                leave.isInMonth(month, year)) {
                
                leaveDays += leave.getDaysInMonth(month, year);
            }
        }
        
        return leaveDays;
    }

    /**
     * View salary details for a specific employee and month
     * 
     * @param employeeId The employee ID
     * @param month Month to view
     * @param year Year to view
     */
    public void viewSalaryDetails(String employeeId, int month, int year) {
        boolean found = false;
        
        for (Salary salary : salaries) {
            if (salary.getEmployeeId().equals(employeeId) && 
                salary.getMonth() == month && 
                salary.getYear() == year) {
                
                System.out.println("\n===== SALARY DETAILS =====");
                System.out.println("Employee ID: " + salary.getEmployeeId());
                System.out.println("Month/Year: " + salary.getMonth() + "/" + salary.getYear());
                System.out.println("Basic Salary: $" + String.format("%.2f", salary.getBasicSalary()));
                System.out.println("Gross Salary: $" + String.format("%.2f", salary.getGrossSalary()));
                System.out.println("Tax Amount: $" + String.format("%.2f", salary.getTaxAmount()));
                System.out.println("Net Salary: $" + String.format("%.2f", salary.getNetSalary()));
                System.out.println("Leave Days: " + salary.getLeaveDays());
                System.out.println("Process Date: " + salary.getProcessDate());
                
                found = true;
                break;
            }
        }
        
        if (!found) {
            System.out.println("No salary record found for the specified month and year.");
        }
    }

    /**
     * Load salaries from file
     */
    public void loadSalaries() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PayrollConstants.SALARIES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Salary salary = Salary.fromCsv(line);
                salaries.add(salary);
                
                // Add to pending salaries queue
                if (!pendingSalaries.containsKey(salary.getEmployeeId())) {
                    pendingSalaries.put(salary.getEmployeeId(), new LinkedList<>());
                }
                pendingSalaries.get(salary.getEmployeeId()).add(salary);
            }
            System.out.println("Loaded " + salaries.size() + " salary records.");
        } catch (IOException e) {
            System.out.println("No existing salary data found or error reading file. Starting with empty salary records.");
        }
    }

    /**
     * Save salaries to file
     */
    public void saveSalaries() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PayrollConstants.SALARIES_FILE))) {
            for (Salary salary : salaries) {
                writer.write(salary.toCsv());
                writer.newLine();
            }
            System.out.println("Saved " + salaries.size() + " salary records.");
        } catch (IOException e) {
            System.out.println("Error saving salaries: " + e.getMessage());
        }
    }

    /**
     * Get all salaries
     * 
     * @return List of all salaries
     */
    public List<Salary> getAllSalaries() {
        return salaries;
    }

    /**
     * Get salaries for a specific employee
     * 
     * @param employeeId The employee ID
     * @return List of salaries for the employee
     */
    public List<Salary> getSalariesByEmployeeId(String employeeId) {
        List<Salary> employeeSalaries = new ArrayList<>();
        
        for (Salary salary : salaries) {
            if (salary.getEmployeeId().equals(employeeId)) {
                employeeSalaries.add(salary);
            }
        }
        
        return employeeSalaries;
    }

    /**
     * Get pending salaries for a specific employee
     * 
     * @param employeeId The employee ID
     * @return Queue of pending salaries
     */
    public Queue<Salary> getPendingSalaries(String employeeId) {
        return pendingSalaries.getOrDefault(employeeId, new LinkedList<>());
    }

    /**
     * Process the oldest pending salary for an employee
     * 
     * @param employeeId The employee ID
     * @return The processed salary, or null if no pending salaries
     */
    public Salary processNextSalary(String employeeId) {
        Queue<Salary> queue = pendingSalaries.get(employeeId);
        if (queue == null || queue.isEmpty()) {
            return null;
        }
        
        return queue.poll();
    }
}
