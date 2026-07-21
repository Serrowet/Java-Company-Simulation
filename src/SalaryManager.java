import java.util.List;

public class SalaryManager {
    private int totalSalaryExpense = 0;

    public int getTotalSalaryExpense() { return totalSalaryExpense; }

    public void checkSalaryPayment(List<Employee> employees, int totalDays) {
        if(totalDays > 0 && totalDays % 30 == 0) {
            int payment = calculateTotalSalary(employees);
            totalSalaryExpense += payment;

            System.out.println("💳 Day " + totalDays + ": Paid salaries = " + payment + "$");
            System.out.println("📊 Total Salary Expense so far = " + totalSalaryExpense + "$");
        }
    }

    private int calculateTotalSalary(List<Employee> employees) {
        int total = 0;
        for (Employee emp : employees) {
            total += emp.getSalary();
        }
        return total;
    }
}