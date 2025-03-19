package com.payroll.models;

import java.time.LocalDate;

/**
 * Salary class for managing employee salary records
 */
public class Salary {
    private String employeeId;
    private double basicSalary;
    private double grossSalary;
    private double taxAmount;
    private double netSalary;
    private int month;
    private int year;
    private int leaveDays;
    private LocalDate processDate;

    /**
     * Constructor for Salary
     * 
     * @param employeeId ID of the employee
     * @param basicSalary Basic salary amount
     * @param grossSalary Gross salary amount
     * @param taxAmount Tax amount
     * @param netSalary Net salary amount
     * @param month Month of the salary
     * @param year Year of the salary
     * @param leaveDays Number of leave days taken
     */
    public Salary(String employeeId, double basicSalary, double grossSalary, double taxAmount, 
                 double netSalary, int month, int year, int leaveDays) {
        this.employeeId = employeeId;
        this.basicSalary = basicSalary;
        this.grossSalary = grossSalary;
        this.taxAmount = taxAmount;
        this.netSalary = netSalary;
        this.month = month;
        this.year = year;
        this.leaveDays = leaveDays;
        this.processDate = LocalDate.now();
    }

    /**
     * Convert salary to CSV format for file storage
     * 
     * @return CSV formatted string
     */
    public String toCsv() {
        return String.format("%s,%.2f,%.2f,%.2f,%.2f,%d,%d,%d,%s",
                employeeId,
                basicSalary,
                grossSalary,
                taxAmount,
                netSalary,
                month,
                year,
                leaveDays,
                processDate);
    }

    /**
     * Create a Salary from CSV string
     * 
     * @param csv CSV formatted string
     * @return Salary object
     */
    public static Salary fromCsv(String csv) {
        String[] parts = csv.split(",");
        Salary salary = new Salary(
                parts[0], // employeeId
                Double.parseDouble(parts[1]), // basicSalary
                Double.parseDouble(parts[2]), // grossSalary
                Double.parseDouble(parts[3]), // taxAmount
                Double.parseDouble(parts[4]), // netSalary
                Integer.parseInt(parts[5]), // month
                Integer.parseInt(parts[6]), // year
                Integer.parseInt(parts[7]) // leaveDays
        );
        salary.setProcessDate(LocalDate.parse(parts[8])); // processDate
        return salary;
    }

    /**
     * Getters and Setters
     */
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(int leaveDays) {
        this.leaveDays = leaveDays;
    }

    public LocalDate getProcessDate() {
        return processDate;
    }

    public void setProcessDate(LocalDate processDate) {
        this.processDate = processDate;
    }

    @Override
    public String toString() {
        return String.format("Salary [Employee ID: %s, Month: %d/%d, Basic: $%.2f, Gross: $%.2f, Tax: $%.2f, Net: $%.2f, Leave Days: %d]",
                employeeId, month, year, basicSalary, grossSalary, taxAmount, netSalary, leaveDays);
    }
}
