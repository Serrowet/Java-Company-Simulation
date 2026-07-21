import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JobManagement {
    private List<Job> allJobs;
    private List<Job> completedJobs;
    private List<Job> activeJobs;
    private List<Job> waitingJobs;
    private List<Employee> employees;
    private List<Machine> machines;
    private Random random;

    private int totalIncome;
    private int monthlyIncome;

    public JobManagement(List<Employee> employees, List<Machine> machines) {
        this.allJobs = new ArrayList<>();
        this.completedJobs = new ArrayList<>();
        this.activeJobs = new ArrayList<>();
        this.waitingJobs = new ArrayList<>();
        this.employees = employees;
        this.machines = machines;
        this.random = new Random();
    }

    public void addJob(Job j) {
        allJobs.add(j);
        waitingJobs.add(j);
        System.out.println("➕ Added job to waiting list: " + j.getJobSize() + " | Payment: $" + j.getPayment());
    }

    public void assignToJobs() {
        List<Job> toMove = new ArrayList<>();
        int assignedJobs = 0;

        System.out.println("🔄 Starting job assignment...");
        System.out.println("📊 Available employees: " + countAvailableEmployees());
        System.out.println("📊 Available machines: " + countAvailableMachines());
        System.out.println("📊 Waiting jobs: " + waitingJobs.size());

        // STRATEJİ 1: Önce mevcut waiting jobs'lara atama yap
        for (Job j : waitingJobs) {
            if (!j.isCompleted()) {
                Employee emp = findAvailableEmployee();
                Machine mac = findAvailableMachine();

                if (emp != null && mac != null && mac.addWorker()) {
                    j.addAssignedMachine(mac);
                    j.addAssignedEmployee(emp);
                    emp.setStatus(EmployeeSituation.BUSY);

                    if (mac.getCurrentWorkerCount() == 1) {
                        mac.setMachineSituation(MachineSituation.ON_USE);
                    }

                    System.out.println("🔧 Assigned " + j.getJobSize() + " job to " + emp.getName() +
                            " with " + mac.getName() + " machine (" +
                            mac.getCurrentWorkerCount() + "/" + mac.getMaxWorkerCount() + " workers)");
                    toMove.add(j);
                    assignedJobs++;
                }
            }
        }

        System.out.println("📊 After waiting jobs - Available employees: " + countAvailableEmployees());


        assignAvailableWorkersToMachines(toMove);
        assignedJobs += toMove.size() - assignedJobs;

        System.out.println("📊 After direct assignments - Available employees: " + countAvailableEmployees());

        for (Job j : toMove) {
            waitingJobs.remove(j);
            activeJobs.add(j);
        }

        if (assignedJobs > 0) {
            System.out.println("🚀 Assigned " + assignedJobs + " jobs today");
        }
    }

    private void assignAvailableWorkersToMachines(List<Job> toMove) {
        int additionalAssignments = 0;

        System.out.println("🔍 Checking for available workers to assign...");

        // Tüm available işçileri kontrol et
        for (Employee emp : employees) {
            if (emp.getSituation() == EmployeeSituation.AVAILABLE) {
                System.out.println("👤 Found available employee: " + emp.getName());

                Machine machine = findAvailableMachine();
                if (machine != null) {
                    System.out.println("🏭 Found available machine: " + machine.getName() +
                            " (" + machine.getCurrentWorkerCount() + "/" + machine.getMaxWorkerCount() + ")");

                    if (machine.addWorker()) {
                        Job newJob = createNewJobForWorker();
                        if (newJob != null) {
                            newJob.addAssignedMachine(machine);
                            newJob.addAssignedEmployee(emp);
                            emp.setStatus(EmployeeSituation.BUSY);

                            if (machine.getCurrentWorkerCount() == 1) {
                                machine.setMachineSituation(MachineSituation.ON_USE);
                            }

                            System.out.println("👥✅ DIRECT ASSIGNMENT: " + newJob.getJobSize() +
                                    " job to " + emp.getName() + " with " + machine.getName() +
                                    " (" + machine.getCurrentWorkerCount() + "/" + machine.getMaxWorkerCount() + " workers)");

                            allJobs.add(newJob);
                            toMove.add(newJob);
                            additionalAssignments++;
                        }
                    }
                } else {
                    System.out.println("❌ No available machine for " + emp.getName());
                }
            }
        }

        if (additionalAssignments > 0) {
            System.out.println("📊 Directly assigned " + additionalAssignments + " available workers to machines");
        } else {
            System.out.println("ℹ️ No direct assignments made");
        }
    }

    private Job createNewJobForWorker() {
        Job newJob = new Job();
        int payment;

        switch (newJob.getJobSize()) {
            case SMALL -> payment = random.nextInt(4000) + 1000;
            case MEDIUM -> payment = random.nextInt(15000) + 5000;
            case LARGE -> payment = random.nextInt(30000) + 20000;
            case EXTRA_LARGE -> payment = random.nextInt(50000) + 50000;
            default -> payment = 0;
        }
        newJob.setPayment(payment);

        System.out.println("🆕 Created new job for available worker: " + newJob.getJobSize() + " | Payment: $" + payment);
        return newJob;
    }

    private Employee findAvailableEmployee() {
        for (Employee emp : employees) {
            if(emp.getSituation() == EmployeeSituation.AVAILABLE) return emp;
        }
        return null;
    }

    private Machine findAvailableMachine() {
        List<Machine> availableMachines = new ArrayList<>();
        for (Machine mac : machines) {
            if(mac.hasAvailableSpace()) {
                availableMachines.add(mac);
            }
        }

        if (availableMachines.isEmpty()) return null;
        return availableMachines.get(random.nextInt(availableMachines.size()));
    }

    // DEBUG METODLARI
    private int countAvailableEmployees() {
        int count = 0;
        for (Employee emp : employees) {
            if (emp.getSituation() == EmployeeSituation.AVAILABLE) count++;
        }
        return count;
    }

    private int countAvailableMachines() {
        int count = 0;
        for (Machine mac : machines) {
            if (mac.hasAvailableSpace()) count++;
        }
        return count;
    }

    private void releaseJob(Job j) {
        for (Employee emp : j.getAssignedEmployees()) {
            emp.setStatus(EmployeeSituation.AVAILABLE);
        }
        for (Machine mac : j.getAssignedMachines()) {
            mac.removeWorker();
            if (mac.getCurrentWorkerCount() == 0) {
                mac.setMachineSituation(MachineSituation.AVAILABLE);
            }
        }
        j.clearAssignedResources();
        System.out.println("🔄 Released resources from job: " + j.getJobSize());
    }

    public void moveCompletedJob(Job j) {
        if(activeJobs.remove(j)) {
            completedJobs.add(j);
            releaseJob(j);
            totalIncome += j.getPayment();
            monthlyIncome += j.getPayment();
            System.out.println("✅ Moved completed job to history: " + j.getJobSize() + " | Earned: $" + j.getPayment());
            System.out.println("💰 Total Income updated: $" + totalIncome);
        }
    }

    public List<Job> getAllJobs() { return allJobs; }
    public List<Job> getCompletedJobs() { return completedJobs; }
    public List<Job> getActiveJobs() { return activeJobs; }
    public List<Job> getWaitingJobs() { return waitingJobs; }
    public int getTotalIncome() { return totalIncome; }
    public int getMonthlyIncome() { return monthlyIncome; }

    public void resetMonthlyIncome() {
        monthlyIncome = 0;
    }
}