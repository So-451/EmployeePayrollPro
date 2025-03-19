package com.payroll.services;

import com.payroll.models.Leave;
import com.payroll.utils.PayrollConstants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * LeaveService provides methods for managing employee leaves
 */
public class LeaveService {
    private List<Leave> leaves;
    private Stack<Leave> recentLeaves; // Stack to track recent leave applications
    private FileService fileService;

    /**
     * Constructor for LeaveService
     */
    public LeaveService() {
        this.leaves = new ArrayList<>();
        this.recentLeaves = new Stack<>();
        this.fileService = new FileService();
    }

    /**
     * Apply for a new leave
     * 
     * @param leave The leave to apply
     * @return true if successful
     */
    public boolean applyLeave(Leave leave) {
        leaves.add(leave);
        recentLeaves.push(leave);
        return true;
    }

    /**
     * Approve a leave application
     * 
     * @param employeeId The employee ID
     * @param startDate Start date of the leave
     * @return true if successful, false if leave not found
     */
    public boolean approveLeave(String employeeId, LocalDate startDate) {
        for (Leave leave : leaves) {
            if (leave.getEmployeeId().equals(employeeId) && 
                leave.getStartDate().equals(startDate) &&
                leave.getStatus().equals("PENDING")) {
                
                leave.setStatus("APPROVED");
                return true;
            }
        }
        return false;
    }

    /**
     * Reject a leave application
     * 
     * @param employeeId The employee ID
     * @param startDate Start date of the leave
     * @return true if successful, false if leave not found
     */
    public boolean rejectLeave(String employeeId, LocalDate startDate) {
        for (Leave leave : leaves) {
            if (leave.getEmployeeId().equals(employeeId) && 
                leave.getStartDate().equals(startDate) &&
                leave.getStatus().equals("PENDING")) {
                
                leave.setStatus("REJECTED");
                return true;
            }
        }
        return false;
    }

    /**
     * Get all leaves
     * 
     * @return List of all leaves
     */
    public List<Leave> getAllLeaves() {
        return leaves;
    }

    /**
     * Get leaves for a specific employee
     * 
     * @param employeeId The employee ID
     * @return List of leaves for the employee
     */
    public List<Leave> getLeavesByEmployeeId(String employeeId) {
        List<Leave> employeeLeaves = new ArrayList<>();
        
        for (Leave leave : leaves) {
            if (leave.getEmployeeId().equals(employeeId)) {
                employeeLeaves.add(leave);
            }
        }
        
        return employeeLeaves;
    }

    /**
     * Get the most recent leave application
     * 
     * @return The most recent leave, or null if no leaves
     */
    public Leave getMostRecentLeave() {
        if (recentLeaves.isEmpty()) {
            return null;
        }
        
        return recentLeaves.peek();
    }

    /**
     * Undo the most recent leave application
     * 
     * @return The removed leave, or null if no leaves
     */
    public Leave undoRecentLeave() {
        if (recentLeaves.isEmpty()) {
            return null;
        }
        
        Leave leave = recentLeaves.pop();
        leaves.remove(leave);
        return leave;
    }

    /**
     * Load leaves from file
     */
    public void loadLeaves() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PayrollConstants.LEAVES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Leave leave = Leave.fromCsv(line);
                leaves.add(leave);
                recentLeaves.push(leave);
            }
            System.out.println("Loaded " + leaves.size() + " leave records.");
        } catch (IOException e) {
            System.out.println("No existing leave data found or error reading file. Starting with empty leave records.");
        }
    }

    /**
     * Save leaves to file
     */
    public void saveLeaves() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PayrollConstants.LEAVES_FILE))) {
            for (Leave leave : leaves) {
                writer.write(leave.toCsv());
                writer.newLine();
            }
            System.out.println("Saved " + leaves.size() + " leave records.");
        } catch (IOException e) {
            System.out.println("Error saving leaves: " + e.getMessage());
        }
    }
}
