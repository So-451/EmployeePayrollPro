package com.payroll.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Leave class for managing employee leaves
 */
public class Leave {
    private String employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String leaveType; // SICK, CASUAL, ANNUAL
    private String reason;
    private String status; // PENDING, APPROVED, REJECTED

    /**
     * Constructor for Leave
     * 
     * @param employeeId ID of the employee requesting leave
     * @param startDate Start date of leave
     * @param endDate End date of leave
     * @param leaveType Type of leave
     * @param reason Reason for leave
     */
    public Leave(String employeeId, LocalDate startDate, LocalDate endDate, String leaveType, String reason) {
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveType = leaveType;
        this.reason = reason;
        this.status = "PENDING"; // Default status
    }
    
    /**
     * Constructor for Leave with status
     * 
     * @param employeeId ID of the employee requesting leave
     * @param startDate Start date of leave
     * @param endDate End date of leave
     * @param leaveType Type of leave
     * @param reason Reason for leave
     * @param status Status of leave application
     */
    public Leave(String employeeId, LocalDate startDate, LocalDate endDate, String leaveType, String reason, String status) {
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveType = leaveType;
        this.reason = reason;
        this.status = status;
    }

    /**
     * Calculate the duration of the leave in days
     * 
     * @return Number of days
     */
    public long getDuration() {
        return ChronoUnit.DAYS.between(startDate, endDate) + 1; // +1 to include both start and end days
    }

    /**
     * Convert leave to CSV format for file storage
     * 
     * @return CSV formatted string
     */
    public String toCsv() {
        return String.format("%s,%s,%s,%s,%s,%s",
                employeeId,
                startDate,
                endDate,
                leaveType,
                reason.replace(",", ";"), // Replace commas to avoid CSV parsing issues
                status);
    }

    /**
     * Create a Leave from CSV string
     * 
     * @param csv CSV formatted string
     * @return Leave object
     */
    public static Leave fromCsv(String csv) {
        String[] parts = csv.split(",", 6); // Limit to 6 parts to handle possible commas in reason
        Leave leave = new Leave(
                parts[0], // employeeId
                LocalDate.parse(parts[1]), // startDate
                LocalDate.parse(parts[2]), // endDate
                parts[3], // leaveType
                parts[4].replace(";", ","), // Restore original commas in reason
                parts[5] // status
        );
        return leave;
    }

    /**
     * Check if leave falls within a specific month and year
     * 
     * @param month Month to check
     * @param year Year to check
     * @return true if leave is in the specified month and year
     */
    public boolean isInMonth(int month, int year) {
        return (startDate.getMonthValue() == month && startDate.getYear() == year) ||
               (endDate.getMonthValue() == month && endDate.getYear() == year) ||
               (startDate.isBefore(LocalDate.of(year, month, 1)) && 
                endDate.isAfter(LocalDate.of(year, month, 1).plusMonths(1).minusDays(1)));
    }

    /**
     * Calculate the number of leave days in a specific month and year
     * 
     * @param month Month to check
     * @param year Year to check
     * @return Number of leave days in the specified month
     */
    public long getDaysInMonth(int month, int year) {
        LocalDate monthStart = LocalDate.of(year, month, 1);
        LocalDate monthEnd = monthStart.plusMonths(1).minusDays(1);
        
        LocalDate overlapStart = startDate.isAfter(monthStart) ? startDate : monthStart;
        LocalDate overlapEnd = endDate.isBefore(monthEnd) ? endDate : monthEnd;
        
        if (overlapStart.isAfter(overlapEnd)) {
            return 0;
        }
        
        return ChronoUnit.DAYS.between(overlapStart, overlapEnd) + 1; // +1 to include both start and end days
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Leave [Employee ID: %s, From: %s, To: %s, Type: %s, Duration: %d days, Status: %s]",
                employeeId, startDate, endDate, leaveType, getDuration(), status);
    }
}
