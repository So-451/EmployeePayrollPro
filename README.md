# Employee Payroll Management System

A Java-based payroll management system for handling employee salaries, leaves, and tax deductions.

## Setup Guide

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Basic understanding of command line operations

### Installation Steps

1. **Clone or download the repository**
   ```
   git clone [repository-url]
   ```
   or download and extract the ZIP file.

2. **Navigate to the project directory**
   ```
   cd employee-payroll-system
   ```

3. **Option 1: Using the run script (recommended)**
   
   Make the script executable (if needed):
   ```
   chmod +x run.sh
   ```
   
   Run the application:
   ```
   ./run.sh
   ```
   
   For quick testing, use the test flag:
   ```
   ./run.sh --test
   ```

4. **Option 2: Manual compilation and execution**
   
   Compile the Java files:
   ```
   javac -d bin src/main/java/com/payroll/*.java src/main/java/com/payroll/*/*.java
   ```
   
   Run the application:
   ```
   java -cp bin com.payroll.Main
   ```
   
   For quick testing:
   ```
   java -cp bin com.payroll.Main --test
   ```

### Data Files
- The system uses CSV files stored in the `data` directory:
  - `employees.csv`: Stores employee information
  - `leaves.csv`: Stores leave records
  - `salaries.csv`: Stores processed salary records

### Troubleshooting
- If you encounter "Class not found" errors, ensure your classpath is correct
- If data doesn't load, check that the CSV files exist in the correct location
- For permission issues with file operations, ensure you have write permissions

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

```
.
├── data                        # CSV data files
│   ├── employees.csv           # Employee records
│   ├── leaves.csv              # Leave application records
│   └── salaries.csv            # Processed salary records
├── src                         # Source code
│   └── main
│       └── java
│           └── com
│               └── payroll
│                   ├── models  # Class models
│                   │   ├── Developer.java
│                   │   ├── Employee.java
│                   │   ├── Leave.java
│                   │   ├── Manager.java
│                   │   └── Salary.java
│                   ├── services # Business logic services
│                   │   ├── EmployeeService.java
│                   │   ├── FileService.java
│                   │   ├── LeaveService.java
│                   │   └── PayrollService.java
│                   ├── utils    # Utility classes
│                   │   └── PayrollConstants.java
│                   └── Main.java # Main application entry point
├── bin                         # Compiled class files (generated)
├── run.sh                      # Run script for easy execution
└── README.md                   # Documentation
```

## Usage Guide

### Main Menu Options

1. **Add New Employee**: Create a new Manager or Developer
2. **View All Employees**: List all employees in the system
3. **Search Employee**: Find an employee by their ID
4. **Update Employee**: Modify existing employee details
5. **Apply Leave**: Submit a leave request
6. **View Employee Leaves**: Check leave history and status
7. **Process Salary**: Calculate monthly salary with deductions
8. **View Salary Details**: View processed salary information
9. **Save Data**: Write all changes to CSV files
0. **Exit**: Quit the application

### Sample Workflows

#### Adding a New Developer
1. Select option 1 from the main menu
2. Choose Developer as the employee type
3. Enter required details: ID, name, email, etc.
4. Specify programming language and years of experience

#### Applying for Leave
1. Select option 5 from the main menu
2. Enter the employee ID
3. Specify leave start and end dates
4. Select leave type (SICK, CASUAL, VACATION)

#### Processing Monthly Salary
1. Select option 7 from the main menu
2. Enter the month and year to process
3. The system will calculate salaries for all employees
4. View the processed salary details with option 8

