package com.payroll.utils;

import java.io.File;

/**
 * Constants used throughout the Payroll Management System
 */
public class PayrollConstants {
    // Get the current working directory
    private static final String BASE_DIR = System.getProperty("user.dir");
    private static final String DATA_DIR = BASE_DIR.endsWith("java") ? "../../.." : ".";
    
    // File paths
    public static final String EMPLOYEES_FILE = DATA_DIR + "/data/employees.csv";
    public static final String LEAVES_FILE = DATA_DIR + "/data/leaves.csv";
    public static final String SALARIES_FILE = DATA_DIR + "/data/salaries.csv";
    
    // Employee types
    public static final String EMPLOYEE_TYPE_MANAGER = "Manager";
    public static final String EMPLOYEE_TYPE_DEVELOPER = "Developer";
    
    // Leave types
    public static final String LEAVE_TYPE_SICK = "SICK";
    public static final String LEAVE_TYPE_CASUAL = "CASUAL";
    public static final String LEAVE_TYPE_ANNUAL = "ANNUAL";
    
    // Leave status
    public static final String LEAVE_STATUS_PENDING = "PENDING";
    public static final String LEAVE_STATUS_APPROVED = "APPROVED";
    public static final String LEAVE_STATUS_REJECTED = "REJECTED";
    
    // Tax rates
    public static final double TAX_RATE_LOW = 0.05;  // 5% for salary <= 20000
    public static final double TAX_RATE_MEDIUM = 0.10;  // 10% for salary <= 50000
    public static final double TAX_RATE_HIGH = 0.15;  // 15% for salary > 50000
    
    // Allowance rates
    public static final double TECH_ALLOWANCE = 0.15;  // 15% of basic salary for developers
    public static final double MANAGEMENT_ALLOWANCE = 0.10;  // 10% per level for managers
    public static final double TEAM_SIZE_ALLOWANCE = 0.005;  // 0.5% per team member for managers
    public static final double EXPERIENCE_BONUS = 0.02;  // 2% per year of experience for developers
    
    // Default values
    public static final int DEFAULT_LEAVE_DAYS = 20;  // Default available leave days per year
    
    // Static initializer to print the file paths for debugging
    static {
        System.out.println("Working directory: " + BASE_DIR);
        System.out.println("Data directory path: " + DATA_DIR);
        System.out.println("Employees file path: " + EMPLOYEES_FILE);
    }
}
