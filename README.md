# Employee Payroll Management System

A Java-based payroll management system for handling employee salaries, leaves, and tax deductions.

## Features

- **Employee Management**: Add, view, update, and search employees
- **Different Employee Types**: Support for Managers and Developers with specialized attributes
- **Leave Management**: Apply for leaves, track leave status and history
- **Payroll Processing**: Calculate salaries with proper tax deductions and allowances
- **Data Persistence**: Save and load data from CSV files

## Technical Implementation

- **OOP Principles**:
  - **Inheritance**: Employee base class extended by Manager and Developer classes
  - **Encapsulation**: Private attributes with getter and setter methods
  - **Abstraction**: Abstract methods like calculateGrossSalary()
  
- **Data Structures**:
  - **HashMap**: For storing employee data with fast lookups by ID
  - **Queue**: For managing salary processing in order
  - **Stack**: For tracking recent leave applications with undo functionality
  - **ArrayList**: For storing lists of employees, leaves, and salaries

- **File Handling**:
  - BufferedReader and BufferedWriter for reading and writing data
  - CSV file format for simplicity and ease of use

## Directory Structure

