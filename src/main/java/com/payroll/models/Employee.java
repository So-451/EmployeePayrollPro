package com.payroll.models;

import java.time.LocalDate;

/**
 * Abstract base class for all employee types
 * Implements common attributes and behaviors for employees
 */
public abstract class Employee {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String department;
    private LocalDate joiningDate;
    private double basicSalary;
    private int availableLeaveDays;

    /**
     * Constructor for Employee
     * 
     * @param id Employee ID
     * @param name Employee name
     * @param email Employee email
     * @param phone Employee phone
     * @param department Employee department
     * @param joiningDate Employee joining date
     * @param basicSalary Employee basic salary
     */
    public Employee(String id, String name, String email, String phone, String department, 
                   LocalDate joiningDate, double basicSalary) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.joiningDate = joiningDate;
        this.basicSalary = basicSalary;
        this.availableLeaveDays = 20; // Default leave days
    }

    /**
     * Calculate gross salary
     * Must be implemented by subclasses
     * 
     * @return Gross salary amount
     */
    public abstract double calculateGrossSalary();

    /**
     * Calculate tax based on gross salary
     * 
     * @param grossSalary The gross salary
     * @return Tax amount
     */
    public double calculateTax(double grossSalary) {
        if (grossSalary <= 20000) {
            return grossSalary * 0.05; // 5% tax
        } else if (grossSalary <= 50000) {
            return grossSalary * 0.1; // 10% tax
        } else {
            return grossSalary * 0.15; // 15% tax
        }
    }

    /**
     * Calculate net salary after tax deduction
     * 
     * @return Net salary amount
     */
    public double calculateNetSalary() {
        double gross = calculateGrossSalary();
        double tax = calculateTax(gross);
        return gross - tax;
    }

    /**
     * Get employee type
     * 
     * @return Employee type as string
     */
    public abstract String getEmployeeType();

    /**
     * Get detailed information about employee
     * 
     * @return Detailed employee information
     */
    public String getDetailedInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Employee ID: ").append(id).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Type: ").append(getEmployeeType()).append("\n");
        sb.append("Email: ").append(email).append("\n");
        sb.append("Phone: ").append(phone).append("\n");
        sb.append("Department: ").append(department).append("\n");
        sb.append("Joining Date: ").append(joiningDate).append("\n");
        sb.append("Basic Salary: $").append(String.format("%.2f", basicSalary)).append("\n");
        sb.append("Gross Salary: $").append(String.format("%.2f", calculateGrossSalary())).append("\n");
        sb.append("Tax: $").append(String.format("%.2f", calculateTax(calculateGrossSalary()))).append("\n");
        sb.append("Net Salary: $").append(String.format("%.2f", calculateNetSalary())).append("\n");
        sb.append("Available Leave Days: ").append(availableLeaveDays).append("\n");
        return sb.toString();
    }

    /**
     * Convert employee to CSV format for file storage
     * 
     * @return CSV formatted string
     */
    public abstract String toCsv();

    /**
     * Getters and Setters
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public int getAvailableLeaveDays() {
        return availableLeaveDays;
    }

    public void setAvailableLeaveDays(int availableLeaveDays) {
        this.availableLeaveDays = availableLeaveDays;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Type: %s | Department: %s | Basic Salary: $%.2f",
                id, name, getEmployeeType(), department, basicSalary);
    }
}
