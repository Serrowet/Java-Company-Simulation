import java.util.Random;

public class Machine {
    private String name;
    private MachineType machineType;
    private MachineSituation machineSituation = MachineSituation.AVAILABLE;
    private boolean underRepair = false;
    private int repairDaysRemaining = 0;
    private int maxWorkerCount;
    private int currentWorkerCount = 0;
    private MachineBreakDownManager machineBreakDownManager = new MachineBreakDownManager();

    public Machine(String name, MachineType machineType) {
        this.name = name;
        this.machineType = machineType;
        setMaxWorkerCount();
    }

    private void setMaxWorkerCount() {
        switch (machineType) {
            case LOW -> this.maxWorkerCount = 2;
            case MEDIUM -> this.maxWorkerCount = 3;
            case HIGH -> this.maxWorkerCount = 4;
        }
    }

    public MachineType getMachineType() { return machineType; }
    public void setMachineType(MachineType machineType) {
        this.machineType = machineType;
        setMaxWorkerCount();
    }
    public MachineSituation getMachineSituation() { return machineSituation; }
    public void setMachineSituation(MachineSituation machineSituation) { this.machineSituation = machineSituation; }
    public String getName() { return name; }
    public boolean isUnderRepair() { return underRepair; }
    public void setUnderRepair(boolean underRepair) { this.underRepair = underRepair; }
    public int getRepairDaysRemaining() { return repairDaysRemaining; }
    public void setRepairDaysRemaining(int repairDaysRemaining) { this.repairDaysRemaining = repairDaysRemaining; }
    public int getMaxWorkerCount() { return maxWorkerCount; }
    public int getCurrentWorkerCount() { return currentWorkerCount; }

    public boolean addWorker() {
        if (currentWorkerCount < maxWorkerCount) {
            currentWorkerCount++;
            return true;
        }
        return false;
    }

    public void removeWorker() {
        if (currentWorkerCount > 0) {
            currentWorkerCount--;
        }
    }

    public boolean hasAvailableSpace() {
        return currentWorkerCount < maxWorkerCount && !underRepair;
    }

    public double getSpeedFactor(){
        if (machineSituation == MachineSituation.UNDER_REPAIR || machineSituation == MachineSituation.BROKEN){
            return 0;
        } else {
            return switch (machineType) {
                case LOW -> 0.5 * (1 + (currentWorkerCount * 0.1));
                case MEDIUM -> 1.5 * (1 + (currentWorkerCount * 0.15));
                case HIGH -> 5.5 * (1 + (currentWorkerCount * 0.2));
            };
        }
    }

    public double calculateDailyElectricityExpense() {
        if (machineSituation == MachineSituation.ON_USE) {
            double baseCost = switch (machineType) {
                case LOW -> 150;
                case MEDIUM -> 300;
                case HIGH -> 700;
            };
            return baseCost * (1 + (currentWorkerCount * 0.1));
        }
        return 0;
    }
}