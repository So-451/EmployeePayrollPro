#!/bin/bash

# Employee Payroll Management System Run Script

# Set colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}===== Employee Payroll Management System =====${NC}"
echo "Starting setup process..."

# Create bin directory if it doesn't exist
if [ ! -d "bin" ]; then
  echo "Creating bin directory..."
  mkdir -p bin
fi

# Compile the Java files
echo "Compiling Java files..."
javac -d bin src/main/java/com/payroll/*.java src/main/java/com/payroll/*/*.java

# Check if compilation was successful
if [ $? -eq 0 ]; then
  echo -e "${GREEN}Compilation successful!${NC}"
  
  # Check if the user wants to run tests
  if [ "$1" == "--test" ]; then
    echo -e "${YELLOW}Running system tests...${NC}"
    java -cp bin com.payroll.Main --test
  else
    echo -e "${YELLOW}Starting the application...${NC}"
    java -cp bin com.payroll.Main
  fi
else
  echo "Compilation failed. Please check the errors above."
  exit 1
fi