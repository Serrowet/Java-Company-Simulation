import java.util.List;

public class ExpenseManager {
    private DailyExpenseCalculator dailyCalculator = new DailyExpenseCalculator();
    private MachineBreakDownManager breakdownManager = new MachineBreakDownManager();
    private SalaryManager salaryManager = new SalaryManager();

    // Total expenses
    private double totalExpensePaidSoFar = 0.0;
    private double totalRepairExpense = 0.0;
    private double dailyRepairExpense = 0.0;
    private double totalDailyExpense = 0.0;

    // Getter methods
    public double getTotalDailyExpense() { return totalDailyExpense; }
    public double getTotalExpensePaidSoFar() { return totalExpensePaidSoFar; }
    public double getTotalRepairExpense() { return totalRepairExpense; }
    public double getDailyRepairExpense() { return dailyRepairExpense; }
    public int getTotalSalaryExpense() { return salaryManager.getTotalSalaryExpense(); }

    // Setter methods
    public void setTotalExpensePaidSoFar(double value) { totalExpensePaidSoFar = value; }
    public void setTotalDailyExpense(double value) { totalDailyExpense = value; }

    // Process repair expenses
    public void addRepairExpense(double repairCost) {
        this.dailyRepairExpense += repairCost;
        this.totalRepairExpense += repairCost;
        System.out.println("💸 Added repair expense: $" + repairCost);
    }

    public void resetDailyRepairExpense() {
        this.dailyRepairExpense = 0.0;
    }

    // Main monthly expense calculation
    public double calculateAllMonthlyExpense(List<Employee> employees, List<Machine> machines,
                                             int totalDays, DisplayManager displayManager,
                                             int monthlyIncome, int totalIncome) {
        calculateAllDailyExpense(employees, machines, totalDays, displayManager);

        double netIncome = totalIncome - totalExpensePaidSoFar;

        if (totalDays > 0 && totalDays % 30 == 0) {
            processMonthlyFinancials(monthlyIncome, totalIncome);
        }

        return netIncome;
    }

    // Monthly financial processes
    private void processMonthlyFinancials(int monthlyIncome, int totalIncome) {
        double taxRate = 0.20;
        double incomeTax = monthlyIncome * taxRate;

        double monthlyExpense = totalDailyExpense + incomeTax + dailyRepairExpense;
        totalExpensePaidSoFar += monthlyExpense;

        double netIncome = totalIncome - totalExpensePaidSoFar;

        System.out.println("💸 Monthly income tax paid: $" + incomeTax);
        System.out.println("🔧 Monthly repair expenses: $" + dailyRepairExpense);
        System.out.println("📊 Net Income so far: $" + netIncome);

        resetMonthlyExpenses();
    }

    // Monthly reset
    private void resetMonthlyExpenses() {
        totalDailyExpense = 0;
        resetDailyRepairExpense();
    }

    // Daily expense calculation
    public void calculateAllDailyExpense(List<Employee> employees, List<Machine> machines,
                                         int totalDays, DisplayManager displayManager) {
        dailyCalculator.resetDailyExpenses();

        double repairCostFromStatusUpdate = breakdownManager.updateAllMachineStatus(machines);

        if (repairCostFromStatusUpdate > 0) {
            addRepairExpense(repairCostFromStatusUpdate);
        }

        processUtilityExpenses(employees, machines, totalDays, displayManager);
        processEmployeeExpenses(employees);

        double todayTotal = dailyCalculator.calculateTotalDailyExpense();
        totalDailyExpense += todayTotal;

        displayDailyExpenses(todayTotal, repairCostFromStatusUpdate);
    }

    // Utility expenses
    private void processUtilityExpenses(List<Employee> employees, List<Machine> machines,
                                        int totalDays, DisplayManager displayManager) {
        dailyCalculator.calculateElectricityExpensePerDay(employees, machines);
        dailyCalculator.calculateWaterExpensePerDay(employees);
        dailyCalculator.calculateGasExpensePerDay(employees, totalDays, displayManager);
        dailyCalculator.calculateMarketExpensePerDay();
    }

    // Employee expenses
    private void processEmployeeExpenses(List<Employee> employees) {
        dailyCalculator.calculateEmployeeMealExpensePerDay(employees);
        dailyCalculator.calculateEmployeesTransportExpensePerDay(employees);
    }

    // Display daily expenses
    private void displayDailyExpenses(double todayTotal, double totalRepairCost) {
        System.out.println("💸 Daily Expenses: $" + todayTotal);
        if (totalRepairCost > 0) {
            System.out.println("🔧 Total Daily Repair Costs: $" + totalRepairCost);
        }
        System.out.println("📊 Current Total Repair Expense: $" + totalRepairExpense);
    }

    // Salary payment
    public void checkSalaryPayment(List<Employee> employees, int totalDays) {
        salaryManager.checkSalaryPayment(employees, totalDays);
    }
}