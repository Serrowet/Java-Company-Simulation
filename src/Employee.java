public class Employee {
    private String name;
    private EmployeeExperience experience;
    private int salary;
    private EmployeeSituation situation;

    public Employee(String name, EmployeeExperience experience, int salary, EmployeeSituation situation) {
        this.name = name;
        this.experience = experience;
        this.salary = salary;
        this.situation = situation;
    }

    public String getName() { return name; }
    public EmployeeExperience getExperience() { return experience; }
    public int getSalary() { return salary; }
    public EmployeeSituation getSituation() { return situation; }
    public void setStatus(EmployeeSituation situation) { this.situation = situation; }
}