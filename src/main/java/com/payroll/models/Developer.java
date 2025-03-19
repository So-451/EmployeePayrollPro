package com.payroll.models;

import java.time.LocalDate;

/**
 * Developer class extending Employee
 * Contains specific attributes and behaviors for developers
 */
public class Developer extends Employee {
    private String programmingLanguage;
    private int experienceYears;

    /**
     * Constructor for Developer
     * 
     * @param id Employee ID
     * @param name Employee name
     * @param email Employee email
     * @param phone Employee phone
     * @param department Employee department
     * @param joiningDate Employee joining date
     * @param basicSalary Employee basic salary
     * @param programmingLanguage Primary programming language
     * @param experienceYears Years of experience
     */
    public Developer(String id, String name, String email, String phone, String department,
                    LocalDate joiningDate, double basicSalary, String programmingLanguage, int experienceYears) {
        super(id, name, email, phone, department, joiningDate, basicSalary);
        this.programmingLanguage = programmingLanguage;
        this.experienceYears = experienceYears;
    }

    /**
     * Calculate gross salary with developer-specific allowances
     * 
     * @return Gross salary amount
     */
    @Override
    public double calculateGrossSalary() {
        double technicalAllowance = getBasicSalary() * 0.15; // 15% technical allowance
        double experienceBonus = getBasicSalary() * (0.02 * experienceYears); // 2% per year of experience
        
        return getBasicSalary() + technicalAllowance + experienceBonus;
    }

    /**
     * Get employee type
     * 
     * @return "Developer"
     */
    @Override
    public String getEmployeeType() {
        return "Developer";
    }

    /**
     * Get detailed information about developer
     * 
     * @return Detailed developer information
     */
    @Override
    public String getDetailedInfo() {
        StringBuilder sb = new StringBuilder(super.getDetailedInfo());
        sb.append("Programming Language: ").append(programmingLanguage).append("\n");
        sb.append("Experience Years: ").append(experienceYears).append("\n");
        return sb.toString();
    }

    /**
     * Convert developer to CSV format for file storage
     * 
     * @return CSV formatted string
     */
    @Override
    public String toCsv() {
        return String.format("%s,%s,%s,%s,%s,%s,%.2f,%s,%s,%d",
                getId(),
                getName(),
                getEmail(),
                getPhone(),
                getDepartment(),
                getJoiningDate(),
                getBasicSalary(),
                getEmployeeType(),
                programmingLanguage,
                experienceYears);
    }

    /**
     * Create a Developer from CSV string
     * 
     * @param csv CSV formatted string
     * @return Developer object
     */
    public static Developer fromCsv(String csv) {
        String[] parts = csv.split(",");
        return new Developer(
                parts[0], // id
                parts[1], // name
                parts[2], // email
                parts[3], // phone
                parts[4], // department
                LocalDate.parse(parts[5]), // joiningDate
                Double.parseDouble(parts[6]), // basicSalary
                parts[8], // programmingLanguage
                Integer.parseInt(parts[9]) // experienceYears
        );
    }

    /**
     * Getters and Setters
     */
    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }
}
