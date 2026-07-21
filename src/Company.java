import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Company {
    private String companyName;
    private int numberOfEmployees;
    private double netIncome = 0;

    // Lists
    private List<Employee> employees = new ArrayList<>();
    private List<Machine> machines = new ArrayList<>();
    private ExpenseManager expenseManager = new ExpenseManager();

    // Classes
    private JobManagement jobManagement = new JobManagement(employees, machines);
    private DisplayManager displayManager = new DisplayManager();
    private SimulationManager simulationManager;
    private Random random = new Random();

    // Time variables
    private long totalMilliseconds = 0;
    private final int msPerDay = 1000;
    private int totalDays = 0;

    // Constructor
    public Company() {
        this.simulationManager = new SimulationManager(this);
    }

    // Getter methods
    public List<Employee> getEmployees() { return employees; }
    public List<Machine> getMachines() { return machines; }
    public ExpenseManager getExpenseManager() { return expenseManager; }
    public double getNetIncome() { return netIncome; }
    public void setNetIncome(double netIncome) { this.netIncome = netIncome; }
    public int getTotalDays() { return totalDays; }
    public long getTotalMilliseconds() { return totalMilliseconds; }
    public int getMsPerDay() { return msPerDay; }

    // Addition methods
    public void addEmployee(Employee emp) {
        employees.add(emp);
        numberOfEmployees = employees.size();
    }

    public void addMachine(Machine m) {
        machines.add(m);
    }

    public void addJob(Job j) {
        jobManagement.addJob(j);
    }

    public void addRandomJob() {
        Job newJob = Job.generateRandomJob();
        if(newJob != null) {
            addJob(newJob);
        }
    }

    // JobManagement Methods
    public void assignToJobs() {
        jobManagement.assignToJobs();
    }

    public void moveCompletedJob(Job j) {
        jobManagement.moveCompletedJob(j);
    }

    // Getters
    public List<Job> getAllJobs() { return jobManagement.getAllJobs(); }
    public List<Job> getCompletedJobs() { return jobManagement.getCompletedJobs(); }
    public List<Job> getActiveJobs() { return jobManagement.getActiveJobs(); }
    public List<Job> getWaitingJobs() { return jobManagement.getWaitingJobs(); }
    public int getTotalIncome() { return jobManagement.getTotalIncome(); }
    public int getMonthlyIncome() { return jobManagement.getMonthlyIncome(); }

    private boolean maxDayControl(int milliseconds, int maxDays) {
        totalMilliseconds += milliseconds;
        int newTotalDays = (int)(totalMilliseconds / msPerDay);

        if (newTotalDays > totalDays) {
            totalDays = newTotalDays;
            System.out.println("\n======= DAY " + totalDays + " =======");
        }

        if (totalDays >= maxDays) {
            return false;
        }
        return true;
    }

    public void simulateJobs(int milliseconds, int maxDays) {
        System.out.println("🚀 Starting simulation for " + maxDays + " days...");

        while (true) {
            displayManager.displayDayWithSeason(totalDays);

            if (!maxDayControl(milliseconds, maxDays)) {
                System.out.println("⏹️ Simulation stopped: reached day " + maxDays);
                break;
            }

            simulationManager.runDailyOperations(milliseconds);
            simulationManager.processActiveJobs(milliseconds);

            // Simple wait simulation
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        simulationManager.displayFinalResults();
    }

    public void run() {
        simulateJobs(1000, 100);
    }
}