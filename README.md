# 🏭 Company Management Simulator

A comprehensive Java-based console simulation designed to model the daily operations, resource management, and financial cycles of a business enterprise.

The simulation represents a company's operational workflow by managing employees, machines, jobs, expenses, salaries, and seasonal changes through an object-oriented design.

---

## ✨ Key Features

### ⚙️ Dynamic Machine System
- Machines are categorized into different tiers:
  - LOW
  - MEDIUM
  - HIGH
- Each machine type has its own:
  - Maximum worker capacity
  - Production speed multiplier
  - Electricity consumption cost
- Machines calculate daily electricity expenses based on active usage and assigned workers.

### 🔧 Realistic Breakdowns & Maintenance
- Machines can randomly fail during daily operations.
- Breakdown probability depends on machine type:
  - LOW machines: 2% failure chance
  - MEDIUM machines: higher failure probability
  - HIGH machines: up to 8% failure chance
- Broken machines enter a repair process with:
  - Repair duration
  - Repair costs
  - Different maintenance requirements depending on machine tier

### 💰 Automated Salary Processing
- The `SalaryManager` automatically tracks simulation time.
- Employee salaries are processed every 30 days.
- The system manages financial cycles without manual input.

### 🌦️ Seasonal Cycles
The simulation tracks time progression through four seasons:

- ❄️ Winter
- 🌸 Spring
- ☀️ Summer
- 🍂 Autumn

Season changes affect the simulation timeline and daily reports.

### 🔄 Centralized Simulation Engine
The `SimulationManager` acts as the core engine of the application.

It manages:
- Daily machine updates
- Job generation
- Financial calculations
- Employee operations
- Status reporting
- Simulation progression

---

# 🏗️ Architecture & Design

The project follows a modular object-oriented architecture with separated responsibilities.

## 📌 Core Components

### 🏢 Company Management
The `Company` class represents the main business entity and manages:
- Employees
- Machines
- Jobs
- Company resources

### 👷 Employee System
Employees have different experience levels and contribute to company operations.

Supported experience levels:
- JUNIOR
- MID
- SENIOR

### 🏭 Machine Management
Machines are controlled through dedicated classes and support multiple states:

Machine situations:
- AVAILABLE
- ON_USE
- BROKEN
- UNDER_REPAIR

### 📋 Job Management
Jobs are generated and processed with different sizes and payouts.

Supported job sizes:
- SMALL
- MEDIUM
- LARGE
- EXTRA_LARGE

### 🛠️ Manager Classes
Complex operations are separated into specialized manager classes:

- `JobProcessor`  
  Handles job execution and production processes.

- `SalaryManager`  
  Handles employee salary payments.

- `MachineBreakDownManager`  
  Controls random machine failures and repair processes.

- `DisplayManager`  
  Handles console output and simulation reports.

---

# 📁 Project Structure

```
src/
│
├── Main.java
├── Company.java
├── Employee.java
├── EmployeeExperience.java
├── EmployeeSituation.java
├── Job.java
├── JobManagement.java
├── JobProcessor.java
├── JobSize.java
├── Machine.java
├── MachineBreakDownManager.java
├── MachineSituation.java
├── MachineType.java
├── SalaryManager.java
├── Season.java
├── SimulationManager.java
└── DisplayManager.java
```

---

# 🚀 Getting Started

## Prerequisites

- Java Development Kit (JDK) 14 or newer

---

## Installation

Clone the repository:

```bash
git clone https://github.com/Serrowet/Java-Company-Simulation.git
```

Navigate to the source directory:

```bash
cd src
```

Compile the project:

```bash
javac *.java
```

Run the application:

```bash
java Main
```

---

# 🎮 Simulation Flow

When the application starts:

1. A company is created.
2. Employees with different experience levels are initialized.
3. Machines are added to the company.
4. Initial jobs are generated.
5. The simulation starts running day by day.
6. Each day includes:
   - Machine operations
   - Job processing
   - Expenses
   - Salary checks
   - Random events
   - Status reports

---

# 🧠 Technologies Used

- Java
- Object-Oriented Programming (OOP)
- Enums
- Class-based architecture
- Console-based application design

---

# 📌 Future Improvements

Possible improvements:
- Graphical User Interface (GUI)
- Database integration
- Save/load simulation states
- Advanced employee skill systems
- More complex economic mechanics

---



# 👨‍💻 Author

**Serdar Sabit Güngör**  
Software Engineering Student  

GitHub: https://github.com/Serrowet
