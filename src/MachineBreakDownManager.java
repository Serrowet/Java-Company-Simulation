import java.util.List;
import java.util.Random;

public class MachineBreakDownManager {
    private Random random = new Random();

    private static final double LOW_BREAKDOWN_CHANCE = 0.02;
    private static final double MEDIUM_BREAKDOWN_CHANCE = 0.05;
    private static final double HIGH_BREAKDOWN_CHANCE = 0.08;

    private static final int LOW_REPAIR_TIME = 2;
    private static final int MEDIUM_REPAIR_TIME = 3;
    private static final int HIGH_REPAIR_TIME = 4;

    private static final int LOW_REPAIR_COST = 500;
    private static final int MEDIUM_REPAIR_COST = 700;
    private static final int HIGH_REPAIR_COST = 900;

    public double getBreakdownChance(MachineType machineType) {
        return switch (machineType) {
            case LOW -> LOW_BREAKDOWN_CHANCE;
            case MEDIUM -> MEDIUM_BREAKDOWN_CHANCE;
            case HIGH -> HIGH_BREAKDOWN_CHANCE;
        };
    }

    public int getRepairTime(MachineType machineType) {
        return switch (machineType) {
            case LOW -> LOW_REPAIR_TIME;
            case MEDIUM -> MEDIUM_REPAIR_TIME;
            case HIGH -> HIGH_REPAIR_TIME;
        };
    }

    public double getRepairCost(MachineType machineType) {
        return switch (machineType) {
            case LOW -> LOW_REPAIR_COST;
            case MEDIUM -> MEDIUM_REPAIR_COST;
            case HIGH -> HIGH_REPAIR_COST;
        };
    }

    // Main machine management
    public double updateAllMachineStatus(List<Machine> machines) {
        double totalRepairCost = 0;

        for (Machine machine : machines) {
            double repairCost = updateMachineStatus(machine);
            totalRepairCost += repairCost;
        }

        return totalRepairCost;
    }

    public double updateMachineStatus(Machine machine) {
        if (machine.isUnderRepair()) {
            return handleRepairProcess(machine);
        } else {
            return handleBreakdownCheck(machine);
        }
    }

    // Repair process
    private double handleRepairProcess(Machine machine) {
        if (machine.getMachineSituation() == MachineSituation.BROKEN) {
            transitionToUnderRepair(machine);
        }

        return updateRepairStatus(machine);
    }

    private void transitionToUnderRepair(Machine machine) {
        machine.setMachineSituation(MachineSituation.UNDER_REPAIR);
        System.out.println("🛠️ " + machine.getName() + " is now UNDER REPAIR (" + machine.getRepairDaysRemaining() + " days remaining)");
    }

    private double updateRepairStatus(Machine machine) {
        if (machine.isUnderRepair()) {
            machine.setRepairDaysRemaining(machine.getRepairDaysRemaining() - 1);

            if (machine.getRepairDaysRemaining() <= 0) {
                return completeRepair(machine);
            }
        }

        return 0;
    }

    // Breakdown check
    private double handleBreakdownCheck(Machine machine) {
        if (checkForBreakDown(machine)) {
            breakMachine(machine);
        }
        return 0;
    }

    public boolean checkForBreakDown(Machine machine) {
        if (machine.getMachineSituation() == MachineSituation.ON_USE && !machine.isUnderRepair()) {
            double breakdownChance = getBreakdownChance(machine.getMachineType());
            return random.nextDouble() < breakdownChance;
        }
        return false;
    }

    public void breakMachine(Machine machine) {
        int repairTime = getRepairTime(machine.getMachineType());
        machine.setMachineSituation(MachineSituation.BROKEN);
        machine.setUnderRepair(true);
        machine.setRepairDaysRemaining(repairTime);

        System.out.println("🚨 " + machine.getName() + " (" + machine.getMachineType() + ") BROKEN! Repair time: " + repairTime + " days");
    }

    public double completeRepair(Machine machine) {
        double repairCost = getRepairCost(machine.getMachineType());

        machine.setMachineSituation(MachineSituation.AVAILABLE);
        machine.setUnderRepair(false);
        machine.setRepairDaysRemaining(0);

        System.out.println("🔧 " + machine.getName() + " (" + machine.getMachineType() + ") REPAIRED! Cost: $" + repairCost);

        return repairCost;
    }

    // Calculate repair costs
    public double calculateDailyRepairCosts(List<Machine> machines) {
        double totalRepairCost = 0;

        for (Machine machine : machines) {
            if (machine.getRepairDaysRemaining() <= 0 && machine.isUnderRepair()) {
                double repairCost = getRepairCost(machine.getMachineType());
                totalRepairCost += repairCost;
                System.out.println("💸 Repair cost for " + machine.getName() + " (" + machine.getMachineType() + "): $" + repairCost);
            }
        }

        return totalRepairCost;
    }
}