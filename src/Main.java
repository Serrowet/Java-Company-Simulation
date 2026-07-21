public class Main {
    public static void main(String[] args) {
        Company company = new Company();

        // Create machines
        Machine machine1 = new Machine("Low 1", MachineType.LOW);
        Machine machine2 = new Machine("Medium 1", MachineType.MEDIUM);
        Machine machine3 = new Machine("High 1", MachineType.HIGH);
        Machine machine4 = new Machine("Low 2", MachineType.LOW);
        Machine machine5 = new Machine("Medium 2", MachineType.MEDIUM);

        // Create employees
        Employee employee1 = new Employee("Serdar", EmployeeExperience.JUNIOR, 1000, EmployeeSituation.AVAILABLE);
        Employee employee2 = new Employee("Sabit", EmployeeExperience.MID, 5000, EmployeeSituation.AVAILABLE);
        Employee employee3 = new Employee("Gungor", EmployeeExperience.SENIOR, 10000, EmployeeSituation.AVAILABLE);
        Employee employee4 = new Employee("Tim", EmployeeExperience.SENIOR, 100000, EmployeeSituation.AVAILABLE);
        Employee employee5 = new Employee("John", EmployeeExperience.JUNIOR, 1200, EmployeeSituation.AVAILABLE);
        Employee employee6 = new Employee("Emma", EmployeeExperience.MID, 6000, EmployeeSituation.AVAILABLE);
        Employee employee7 = new Employee("David", EmployeeExperience.SENIOR, 15000, EmployeeSituation.AVAILABLE);
        Employee employee8 = new Employee("Sarah", EmployeeExperience.MID, 7000, EmployeeSituation.AVAILABLE);
        Employee employee9 = new Employee("Michael", EmployeeExperience.SENIOR, 120000, EmployeeSituation.AVAILABLE);

        // Create jobs
        Job job1 = new Job();
        job1.setPayment(5000);
        Job job2 = new Job();
        job2.setPayment(3000);
        Job job3 = new Job();
        job3.setPayment(7000);
        Job job4 = new Job();
        job4.setPayment(4400);
        Job job5 = new Job();
        job5.setPayment(6000);
        Job job6 = new Job();
        job6.setPayment(7000);
        Job job7 = new Job();
        job7.setPayment(8000);

        // Add everything to company
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addEmployee(employee3);
        company.addEmployee(employee4);
        company.addEmployee(employee5);
        company.addEmployee(employee6);
        company.addEmployee(employee7);
        company.addEmployee(employee8);
        company.addEmployee(employee9);

        company.addMachine(machine1);
        company.addMachine(machine2);
        company.addMachine(machine3);
        company.addMachine(machine4);
        company.addMachine(machine5);

        company.addJob(job1);
        company.addJob(job2);
        company.addJob(job3);
        company.addJob(job4);
        company.addJob(job5);
        company.addJob(job6);
        company.addJob(job7);

        // Run simulation
        company.run();
    }
}