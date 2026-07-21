import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Job {
    private Random rand = new Random();
    private JobSize jobSize;
    private List<Employee> assignedEmployees;
    private List<Machine> assignedMachines;
    private int progress;
    private boolean completed = false;
    private double speedFactor;
    private double sizeFactor;
    private int elapsedDays = 0;
    private long totalMilliseconds = 0;
    private int payment;

    public Job() {
        this.assignedEmployees = new ArrayList<>();
        this.assignedMachines = new ArrayList<>();
        randomJobSize();
        setSizeFactor();
    }

    public static Job generateRandomJob() {
        Random rand = new Random();
        if (rand.nextInt(100) < 70) {
            Job newJob = new Job();
            int payment;
            switch (newJob.getJobSize()) {
                case SMALL -> payment = rand.nextInt(4000) + 1000;
                case MEDIUM -> payment = rand.nextInt(15000) + 5000;
                case LARGE -> payment = rand.nextInt(30000) + 20000;
                case EXTRA_LARGE -> payment = rand.nextInt(50000) + 50000;
                default -> payment = 0;
            }
            newJob.setPayment(payment);
            System.out.println("New Job arrived: " + newJob.getJobSize() + " | Payment: " + newJob.getPayment() + "$");
            return newJob;
        } else {
            System.out.println("No new job today.");
            return null;
        }
    }

    // Getters and Setters
    public JobSize getJobSize() { return jobSize; }
    public List<Machine> getAssignedMachines() { return assignedMachines; }
    public List<Employee> getAssignedEmployees() { return assignedEmployees; }
    public int getProgress() { return progress; }
    public boolean isCompleted() { return completed; }
    public int getElapsedDays() { return elapsedDays; }
    public int getPayment() { return payment; }
    public void setPayment(int payment) { this.payment = payment; }

    public void addAssignedMachine(Machine machine) { assignedMachines.add(machine); }
    public void addAssignedEmployee(Employee employee) {
        assignedEmployees.add(employee);
        setSpeedFactorSize(employee.getExperience());
    }

    public void clearAssignedResources() {
        assignedEmployees.clear();
        assignedMachines.clear();
    }

    public void removeAssignedMachine(Machine machine) { assignedMachines.remove(machine); }
    public void removeAssignedEmployee(Employee employee) { assignedEmployees.remove(employee); }

    // Random job size determination
    public void randomJobSize() {
        int a = rand.nextInt(100);
        if (a < 25) jobSize = JobSize.SMALL;
        else if (a < 50) jobSize = JobSize.MEDIUM;
        else if (a < 90) jobSize = JobSize.LARGE;
        else jobSize = JobSize.EXTRA_LARGE;
    }

    // Speed factor
    public void setSpeedFactorSize(EmployeeExperience experience) {
        if (completed) return;
        switch (experience) {
            case JUNIOR -> speedFactor = 1;
            case MID -> speedFactor = 2;
            case SENIOR -> speedFactor = 3;
            default -> speedFactor = 5;
        }
    }


    // Size factor
    public void setSizeFactor() {
        switch (jobSize) {
            case SMALL -> sizeFactor = 2;
            case MEDIUM -> sizeFactor = 3.5;
            case LARGE -> sizeFactor = 5;
            case EXTRA_LARGE -> sizeFactor = 7;
            default -> sizeFactor = 1;
        }
    }

    // Job progress and day counter
    public void work(EmployeeExperience experience, int milliseconds, int msPerDay) {
        if (completed || assignedEmployees.isEmpty()) return;

        setSpeedFactorSize(experience);

        double totalMachineSpeed = 0;
        for (Machine machine : assignedMachines) {
            if (!machine.isUnderRepair()) {
                totalMachineSpeed += machine.getSpeedFactor();
            }
        }

        if (totalMachineSpeed == 0) return;

        // Simple progress calculation
        double dailyProgress = (speedFactor * totalMachineSpeed * 10.0) / sizeFactor;
        double timeRatio = (double) milliseconds / msPerDay;
        progress += (int) (dailyProgress * timeRatio);

        // Show progress whenever it changes
        System.out.println("📈 " + jobSize + " job progress: " + progress + "%");

        if (progress >= 100) {
            progress = 100;
            completed = true;
            System.out.println("🎉 " + jobSize + " job COMPLETED! Payment: $" + payment);
        }
    }
    // Update day counter from global time
    public void updateElapsedDaysFromGlobal(long globalTotalMs, int msPerDay) {
        this.elapsedDays = (int) (globalTotalMs / msPerDay);
        this.totalMilliseconds = globalTotalMs;
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobSize=" + jobSize +
                ", progress=" + progress + "%" +
                ", elapsedDays=" + elapsedDays +
                ", assignedEmployees=" + assignedEmployees.stream().map(Employee::getName).toList() +
                ", assignedMachines=" + assignedMachines.stream().map(Machine::getName).toList() +
                '}';
    }
}