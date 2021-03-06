package pattern.structural.composite;

public interface Employee {

    void addEmployee(Employee employee);

    void removeEmployee(Employee employee);

    String getName();

    double getSalary();

}
