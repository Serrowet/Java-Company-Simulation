import java.util.List;

public class DisplayManager {

    public void displayResourcesStatus(List<Employee> employees, List<Machine> machines) {
        System.out.println("\n=== RESOURCES STATUS ===");
        System.out.println("--- Employees ---");
        for (Employee e : employees) {
            String icon = getEmployeeStatusIcon(e.getSituation());
            System.out.printf("%-15s [%s] %s%n", e.getName(), icon, e.getSituation());
        }
        System.out.println("Total Employees: " + employees.size());

        System.out.println("\n--- Machines ---");
        for (Machine m : machines) {
            String icon = getMachineStatusIcon(m.getMachineSituation());
            String status = m.getMachineSituation().toString();
            if (m.isUnderRepair()) {
                status += " (" + m.getRepairDaysRemaining() + " days remaining)";
            }
            status += " [" + m.getCurrentWorkerCount() + "/" + m.getMaxWorkerCount() + " workers]";
            System.out.printf("%-15s [%s] %s%n", m.getName(), icon, status);
        }
        System.out.println("Total Machines: " + machines.size());
        System.out.println("========================\n");
    }

    public void displayJobStatistics(List<Job> allJobs, List<Job> activeJobs,
                                     List<Job> completedJobs, List<Job> waitingJobs,
                                     int totalIncome, double netIncome, int totalDays,
                                     ExpenseManager expenseManager) {
        int totalJobs = allJobs.size();
        int active = activeJobs.size();
        int completed = completedJobs.size();
        int waiting = waitingJobs.size();

        String completedIcon = "✅";
        String activeIcon = "🛠️";
        String waitingIcon = "⏳";

        System.out.println("\n=== JOB STATISTICS ===");
        System.out.println(completedIcon + " Completed Jobs: " + completed);
        System.out.println(activeIcon + " Active Jobs: " + active);
        System.out.println(waitingIcon + " Waiting Jobs: " + waiting);
        System.out.println("Total Jobs: " + totalJobs);
        System.out.println("💰 Total Income: " + totalIncome + "$");
        System.out.println("📊 Net Income: " + netIncome + "$");
        System.out.println("🔧 Total Repair Expenses: " + expenseManager.getTotalRepairExpense() + "$");
        System.out.println("💳 Total Salary Expenses: " + expenseManager.getTotalSalaryExpense() + "$");
        System.out.println("📅 Total Days: " + totalDays);
        System.out.println("========================");
    }

    public void displayJobProgress(Job j) {
        int progress = j.getProgress();
        int barLength = 20;
        int filled = (progress * barLength) / 100;
        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < filled; i++) bar.append("#");
        for (int i = filled; i < barLength; i++) bar.append("-");

        System.out.println(j.getJobSize() + " Job Progress: [" + bar + "] " + progress + "% ");
        if (!j.getAssignedEmployees().isEmpty())
            System.out.println("👨‍💼 Assigned Employee: " + j.getAssignedEmployees().get(0).getName() + " (" + j.getAssignedEmployees().get(0).getExperience() + ")");
        if (!j.getAssignedMachines().isEmpty()) {
            System.out.println("🏭 Machines (" + j.getAssignedMachines().size() + "):");
            double totalSpeed = 0;
            for (Machine machine : j.getAssignedMachines()) {
                double speed = machine.getSpeedFactor();
                totalSpeed += speed;
                String status = machine.isUnderRepair() ? " [BROKEN]" : "";
                System.out.println("   - " + machine.getName() + " (" + machine.getMachineType() + ") [Speed: " + speed + "x]" + status);
            }
            System.out.println("   Total Speed: " + totalSpeed + "x");
        }
        System.out.println("---------------------------------------------------------");
    }

    public void displayDayWithSeason(int totalDays) {
        int startYear = 2025;
        int dayOfYear = totalDays % 365 + 1;
        int year = startYear + (totalDays / 365);
        int month = (dayOfYear - 1) / 30 + 1;
        int dayOfMonth = (dayOfYear - 1) % 30 + 1;
        Season currentSeason = getSeasonByMonth(month);

        System.out.println("📅 " + dayOfMonth + "/" + month + "/" + year + " | " + currentSeason.getName() + " " + currentSeason.getIcon());
    }

    public void displayFinalStatistics(List<Job> completedJobs, List<Job> activeJobs,
                                       List<Job> waitingJobs, int totalIncome,
                                       double netIncome, int totalDays,
                                       ExpenseManager expenseManager) {
        System.out.println("\n📈 ====== FINAL STATISTICS ======");
        System.out.println("✅ Completed Jobs: " + completedJobs.size());
        System.out.println("🛠️ Active Jobs: " + activeJobs.size());
        System.out.println("⏳ Waiting Jobs: " + waitingJobs.size());
        System.out.println("💰 Total Income: $" + totalIncome);
        System.out.println("📊 Net Income: $" + netIncome);
        System.out.println("🔧 Total Repair Expenses: $" + expenseManager.getTotalRepairExpense());
        System.out.println("💳 Total Salary Expenses: $" + expenseManager.getTotalSalaryExpense());
        System.out.println("📅 Total Days: " + totalDays);
        System.out.println("================================\n");
    }

    public void displayExpenseStatistics(ExpenseManager expenseManager) {
        System.out.println("\n=== EXPENSE STATISTICS ===");
        System.out.println("🔧 Total Repair Expenses: " + expenseManager.getTotalRepairExpense() + "$");
        System.out.println("💳 Total Salary Expenses: " + expenseManager.getTotalSalaryExpense() + "$");
        System.out.println("💡 Total Utility Expenses: " + expenseManager.getTotalDailyExpense() + "$");
        System.out.println("💸 Total Expenses Paid: " + expenseManager.getTotalExpensePaidSoFar() + "$");
        System.out.println("===========================");
    }

    public String getEmployeeStatusIcon(EmployeeSituation situation) {
        switch (situation) {
            case AVAILABLE: return "🟢";
            case BUSY: return "🔴";
            case ON_LEAVE: return "🟡";
            default: return "⚪";
        }
    }

    public String getMachineStatusIcon(MachineSituation situation) {
        switch (situation) {
            case AVAILABLE: return "🟢";
            case ON_USE: return "🔴";
            case BROKEN: return "🚨";
            case UNDER_REPAIR: return "🔧";
            default: return "⚪";
        }
    }

    public Season getSeasonByMonth(int month) {
        return switch (month) {
            case 1, 2, 12 -> Season.WINTER;
            case 3, 4, 5 -> Season.SPRING;
            case 6, 7, 8 -> Season.SUMMER;
            case 9, 10, 11 -> Season.AUTUMN;
            default -> null;
        };
    }
    public void displayActiveAndWaitingJobs(List<Job> activeJobs, List<Job> waitingJobs) {
        System.out.println("\n=== ACTIVE & WAITING JOBS ===");

        // Active Jobs
        System.out.println("🛠️ ACTIVE JOBS (" + activeJobs.size() + "):");
        if (activeJobs.isEmpty()) {
            System.out.println("   No active jobs");
        } else {
            for (Job job : activeJobs) {
                String employeeInfo = job.getAssignedEmployees().isEmpty() ?
                        "No employee" : job.getAssignedEmployees().get(0).getName() +
                        " (" + job.getAssignedEmployees().get(0).getExperience() + ")";
                String machineInfo = job.getAssignedMachines().isEmpty() ?
                        "No machine" : job.getAssignedMachines().get(0).getName();

                System.out.printf("   • %-12s | 👨‍💼 %-15s | 🏭 %-10s | 📊 %3d%%%n",
                        job.getJobSize(), employeeInfo, machineInfo, job.getProgress());
            }
        }

        // Waiting Jobs
        System.out.println("⏳ WAITING JOBS (" + waitingJobs.size() + "):");
        if (waitingJobs.isEmpty()) {
            System.out.println("   No waiting jobs");
        } else {
            for (Job job : waitingJobs) {
                System.out.printf("   • %-12s | 💰 $%-10d%n",
                        job.getJobSize(), job.getPayment());
            }
        }
        System.out.println("===============================");
    }
}