package com.payroll.services;

import com.payroll.utils.PayrollConstants;

import java.io.File;
import java.io.IOException;

/**
 * FileService provides utility methods for file operations
 */
public class FileService {
    
    /**
     * Constructor for FileService
     * Ensures that data directories exist
     */
    public FileService() {
        ensureDataDirectoryExists();
    }
    
    /**
     * Ensure that the data directory exists
     */
    private void ensureDataDirectoryExists() {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            boolean created = dataDir.mkdirs();
            if (created) {
                System.out.println("Created data directory.");
            } else {
                System.out.println("Failed to create data directory.");
            }
        }
        
        ensureFileExists(PayrollConstants.EMPLOYEES_FILE);
        ensureFileExists(PayrollConstants.LEAVES_FILE);
        ensureFileExists(PayrollConstants.SALARIES_FILE);
    }
    
    /**
     * Ensure that a file exists
     * 
     * @param filePath The file path
     */
    private void ensureFileExists(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("Created file: " + filePath);
                } else {
                    System.out.println("Failed to create file: " + filePath);
                }
            } catch (IOException e) {
                System.out.println("Error creating file " + filePath + ": " + e.getMessage());
            }
        }
    }
    
    /**
     * Check if a file exists
     * 
     * @param filePath The file path
     * @return true if the file exists
     */
    public boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
    
    /**
     * Delete a file
     * 
     * @param filePath The file path
     * @return true if the file was deleted
     */
    public boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
    
    /**
     * Backup a file
     * 
     * @param sourceFilePath The source file path
     * @return true if the file was backed up
     */
    public boolean backupFile(String sourceFilePath) {
        if (!fileExists(sourceFilePath)) {
            return false;
        }
        
        String backupFilePath = sourceFilePath + ".bak";
        deleteFile(backupFilePath); // Delete existing backup if any
        
        File sourceFile = new File(sourceFilePath);
        File backupFile = new File(backupFilePath);
        
        try {
            java.nio.file.Files.copy(sourceFile.toPath(), backupFile.toPath());
            return true;
        } catch (IOException e) {
            System.out.println("Error backing up file: " + e.getMessage());
            return false;
        }
    }
}
