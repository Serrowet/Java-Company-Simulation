import java.util.List;
import java.util.Random;

public class DailyExpenseCalculator {
    private Random random = new Random();

    // Daily expense variables
    private double dailyElectricityExpense;
    private double dailyWaterExpense;
    private double dailyGasExpense;
    private double dailyMarketExpense;
    private double dailyEmployeeMealExpense;
    private double dailyEmployeeTransportExpense;
    private double machinesDailyElectricityExpense;
    private double dailyRepairExpense;

    // Getter methods
    public double getDailyElectricityExpense() { return dailyElectricityExpense; }
    public double getDailyWaterExpense() { return dailyWaterExpense; }
    public double getDailyGasExpense() { return dailyGasExpense; }
    public double getDailyMarketExpense() { return dailyMarketExpense; }
    public double getDailyEmployeeMealExpense() { return dailyEmployeeMealExpense; }
    public double getDailyEmployeeTransportExpense() { return dailyEmployeeTransportExpense; }
    public double getMachinesDailyElectricityExpense() { return machinesDailyElectricityExpense; }
    public double getDailyRepairExpense() { return dailyRepairExpense; }

    public void resetDailyExpenses() {
        dailyElectricityExpense = 0;
        dailyWaterExpense = 0;
        dailyGasExpense = 0;
        dailyMarketExpense = 0;
        dailyEmployeeMealExpense = 0;
        dailyEmployeeTransportExpense = 0;
        machinesDailyElectricityExpense = 0;
        dailyRepairExpense = 0;
    }

    public int getRepairCost(MachineType machineType) {
        return switch (machineType){
            case LOW -> 500;
            case MEDIUM -> 700;
            case HIGH -> 900;
        };
    }

    public void calculateMachinesElectricityExpensePerDay(List<Machine> machines) {
        machinesDailyElectricityExpense = 0;
        for (Machine m : machines) {
            machinesDailyElectricityExpense += m.calculateDailyElectricityExpense();
        }
    }

    public void calculateElectricityExpensePerDay(List<Employee> employees, List<Machine> machines) {
        calculateMachinesElectricityExpensePerDay(machines);
        double employeeDailyElectricityExpense = 0;
        double companyDailyElectricityExpense = random.nextDouble(50) + 50;

        for (Employee emp : employees) {
            if (emp.getSituation() == EmployeeSituation.AVAILABLE || emp.getSituation() == EmployeeSituation.BUSY) {
                employeeDailyElectricityExpense += random.nextDouble(5) + 5;
            }
        }
        dailyElectricityExpense = employeeDailyElectricityExpense + companyDailyElectricityExpense + machinesDailyElectricityExpense;
    }

    public void calculateWaterExpensePerDay(List<Employee> employees) {
        double companyDailyWaterExpense = random.nextDouble(60) + 40;
        double employeeDailyWaterExpense = 0;

        for (Employee emp : employees) {
            if (emp.getSituation() == EmployeeSituation.AVAILABLE || emp.getSituation() == EmployeeSituation.BUSY) {
                employeeDailyWaterExpense += random.nextDouble(3) + 2;
            }
        }
        dailyWaterExpense = employeeDailyWaterExpense + companyDailyWaterExpense;
    }

    public void calculateGasExpensePerDay(List<Employee> employees, int totalDays, DisplayManager displayManager) {
        double companyDailyGasExpense = 0;
        double employeeDailyGasExpense = 0;

        int dayOfYear = totalDays % 365 + 1;
        int month = (dayOfYear - 1) / 30 + 1;
        Season currentSeason = displayManager.getSeasonByMonth(month);

        switch (currentSeason) {
            case WINTER -> companyDailyGasExpense = random.nextDouble(500) + 500;
            case SPRING -> companyDailyGasExpense = random.nextDouble(200) + 100;
            case SUMMER -> companyDailyGasExpense = random.nextDouble(50) + 50;
            case AUTUMN -> companyDailyGasExpense = random.nextDouble(200) + 300;
        }

        for (Employee emp : employees) {
            if (emp.getSituation() == EmployeeSituation.AVAILABLE || emp.getSituation() == EmployeeSituation.BUSY) {
                employeeDailyGasExpense += random.nextDouble(3) + 2;
            }
        }
        dailyGasExpense = employeeDailyGasExpense + companyDailyGasExpense;
    }

    public void calculateMarketExpensePerDay() {
        dailyMarketExpense = random.nextDouble(50) + 50;
    }

    public void calculateEmployeeMealExpensePerDay(List<Employee> employees) {
        int employeesDailyMealExpense = 0;
        for(Employee emp : employees) {
            EmployeeExperience empExperience = emp.getExperience();
            switch (empExperience){
                case JUNIOR -> employeesDailyMealExpense += 20;
                case MID -> employeesDailyMealExpense += 50;
                case SENIOR -> employeesDailyMealExpense += 100;
            }
        }
        dailyEmployeeMealExpense = employeesDailyMealExpense;
    }

    public void calculateEmployeesTransportExpensePerDay(List<Employee> employees) {
        int employeesDailyTransportExpense = 0;
        for(Employee emp : employees) {
            EmployeeExperience empExperience = emp.getExperience();
            switch (empExperience){
                case JUNIOR -> employeesDailyTransportExpense += 5;
                case MID -> employeesDailyTransportExpense += 15;
                case SENIOR -> employeesDailyTransportExpense += 30;
            }
        }
        dailyEmployeeTransportExpense = employeesDailyTransportExpense;
    }

    public double calculateDailyRepairExpense(List<Machine> machines) {
        dailyRepairExpense = 0;
        for (Machine m : machines) {
            if (m.getRepairDaysRemaining() <= 0 && m.isUnderRepair()) {
                double repairCost = getRepairCost(m.getMachineType());
                dailyRepairExpense += repairCost;
                System.out.println("💸 Repair cost for " + m.getName() + " (" + m.getMachineType() + "): $" + repairCost);
            }
        }
        return dailyRepairExpense;
    }

    public double calculateTotalDailyExpense() {
        return dailyElectricityExpense + dailyWaterExpense + dailyGasExpense +
                dailyMarketExpense + dailyEmployeeMealExpense + dailyEmployeeTransportExpense + dailyRepairExpense;
    }
}