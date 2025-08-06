package net.javaguides.ems_backend.Mapper;

import net.javaguides.ems_backend.Entity.Employee;
import net.javaguides.ems_backend.dto.EmployeeDto;

public class EmployeeMapper {
	public static EmployeeDto mapToEmployeeDto (Employee employee){
		return new EmployeeDto(
				employee.getId(),
				employee.getFirstName(),
				employee.getLastName(),
				employee.getEmail()
		);
	}
	public static Employee mapToEmployee (EmployeeDto employeeDto){
		return new Employee(
				employeeDto.getId(),
				employeeDto.getFirstName(),
				employeeDto.getLastName(),
				employeeDto.getEmail()
		);
	}
}
