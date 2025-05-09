package delivery.service;

import java.util.List;

import org.springframework.stereotype.Service;

import delivery.Employee;
import delivery.repository.EmployeeRepository;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void removeEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public void assignOrderToEmployee(Long employeeId) {
        Employee employee = findEmployeeById(employeeId);
        if (employee.getNumberOfAvailableOrders() <= 0) {
            throw new RuntimeException("Employee has no available orders");
        }
        employee.setNumberOfAvailableOrders(employee.getNumberOfAvailableOrders() - 1);
        employeeRepository.save(employee);
    }

    /* 
    public Long getNextEmployeeId() {
        Employee lastEmployee = employeeRepository.findTopByOrderByIdDesc();

        if (lastEmployee != null) {
            return lastEmployee.getId() + 1;
        } else {
            return 1L;
        }
    }
        */
}
