package com.payroll.models;

import java.time.LocalDate;

/**
 * Manager class extending Employee
 * Contains specific attributes and behaviors for managers
 */
public class Manager extends Employee {
    private int teamSize;
    private int managementLevel; // 1: Junior, 2: Mid, 3: Senior

    /**
     * Constructor for Manager
     * 
     * @param id Employee ID
     * @param name Employee name
     * @param email Employee email
     * @param phone Employee phone
     * @param department Employee department
     * @param joiningDate Employee joining date
     * @param basicSalary Employee basic salary
     * @param teamSize Number of team members
     * @param managementLevel Management level (1-3)
     */
    public Manager(String id, String name, String email, String phone, String department,
                  LocalDate joiningDate, double basicSalary, int teamSize, int managementLevel) {
        super(id, name, email, phone, department, joiningDate, basicSalary);
        this.teamSize = teamSize;
        this.managementLevel = managementLevel;
    }

    /**
     * Calculate gross salary with manager-specific allowances
     * 
     * @return Gross salary amount
     */
    @Override
    public double calculateGrossSalary() {
        double managementAllowance = getBasicSalary() * (0.1 * managementLevel); // 10% per management level
        double teamAllowance = getBasicSalary() * (0.005 * teamSize); // 0.5% per team member
        
        return getBasicSalary() + managementAllowance + teamAllowance;
    }

    /**
     * Get employee type
     * 
     * @return "Manager"
     */
    @Override
    public String getEmployeeType() {
        return "Manager";
    }

    /**
     * Get detailed information about manager
     * 
     * @return Detailed manager information
     */
    @Override
    public String getDetailedInfo() {
        StringBuilder sb = new StringBuilder(super.getDetailedInfo());
        sb.append("Team Size: ").append(teamSize).append("\n");
        sb.append("Management Level: ").append(managementLevel).append("\n");
        return sb.toString();
    }

    /**
     * Convert manager to CSV format for file storage
     * 
     * @return CSV formatted string
     */
    @Override
    public String toCsv() {
        return String.format("%s,%s,%s,%s,%s,%s,%.2f,%s,%d,%d",
                getId(),
                getName(),
                getEmail(),
                getPhone(),
                getDepartment(),
                getJoiningDate(),
                getBasicSalary(),
                getEmployeeType(),
                teamSize,
                managementLevel);
    }

    /**
     * Create a Manager from CSV string
     * 
     * @param csv CSV formatted string
     * @return Manager object
     */
    public static Manager fromCsv(String csv) {
        String[] parts = csv.split(",");
        return new Manager(
                parts[0], // id
                parts[1], // name
                parts[2], // email
                parts[3], // phone
                parts[4], // department
                LocalDate.parse(parts[5]), // joiningDate
                Double.parseDouble(parts[6]), // basicSalary
                Integer.parseInt(parts[8]), // teamSize
                Integer.parseInt(parts[9]) // managementLevel
        );
    }

    /**
     * Getters and Setters
     */
    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public int getManagementLevel() {
        return managementLevel;
    }

    public void setManagementLevel(int managementLevel) {
        this.managementLevel = managementLevel;
    }
}
