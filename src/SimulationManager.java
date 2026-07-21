import java.util.ArrayList;
import java.util.List;

public class SimulationManager {
    private Company company;
    private DisplayManager displayManager;
    private MachineBreakDownManager breakdownManager = new MachineBreakDownManager();
    private JobProcessor jobProcessor = new JobProcessor();

    public SimulationManager(Company company) {
        this.company = company;
        this.displayManager = new DisplayManager();
    }

    public void runDailyOperations(int milliseconds) {
        processMachines();
        processFinances();
        processJobs();
        displayStatus();
    }

    private void processMachines() {
        breakdownManager.updateAllMachineStatus(company.getMachines());
    }

    private void processFinances() {
        company.getExpenseManager().checkSalaryPayment(company.getEmployees(), company.getTotalDays());
        company.setNetIncome(company.getExpenseManager().calculateAllMonthlyExpense(
                company.getEmployees(),
                company.getMachines(),
                company.getTotalDays(),
                displayManager,
                company.getMonthlyIncome(),
                company.getTotalIncome()
        ));
    }

    private void processJobs() {
        company.addRandomJob();
        company.assignToJobs();
    }

    private void displayStatus() {
        displayManager.displayResourcesStatus(company.getEmployees(), company.getMachines());
        displayManager.displayActiveAndWaitingJobs(company.getActiveJobs(), company.getWaitingJobs());
        displayManager.displayJobStatistics(
                company.getAllJobs(),
                company.getActiveJobs(),
                company.getCompletedJobs(),
                company.getWaitingJobs(),
                company.getTotalIncome(),
                company.getNetIncome(),
                company.getTotalDays(),
                company.getExpenseManager()
        );
    }

    public void processActiveJobs(int milliseconds) {
        jobProcessor.processActiveJobs(company, milliseconds, displayManager);
    }

    public void displayFinalResults() {
        System.out.println("🎉 All jobs completed!");
        displayManager.displayFinalStatistics(
                company.getCompletedJobs(),
                company.getActiveJobs(),
                company.getWaitingJobs(),
                company.getTotalIncome(),
                company.getNetIncome(),
                company.getTotalDays(),
                company.getExpenseManager()
        );

        displayManager.displayExpenseStatistics(company.getExpenseManager());
    }
}