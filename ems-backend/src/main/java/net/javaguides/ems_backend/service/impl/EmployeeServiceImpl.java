package net.javaguides.ems_backend.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.ems_backend.Entity.Employee;
import net.javaguides.ems_backend.Entity.ResourceNotFoundException;
import net.javaguides.ems_backend.Mapper.EmployeeMapper;
import net.javaguides.ems_backend.dto.EmployeeDto;
import net.javaguides.ems_backend.repository.EmployeeRepository;
import net.javaguides.ems_backend.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;

	@Override
	public EmployeeDto createEmployee(EmployeeDto employeeDto) {

		Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
		Employee savedEmployee = employeeRepository.save(employee);
		return EmployeeMapper.mapToEmployeeDto(savedEmployee);
	}

	@Override
	public EmployeeDto getEmployeeDto(Long employeedId) {
		Employee employee = employeeRepository.findById(employeedId)
				.orElseThrow(() ->
						new ResourceNotFoundException("Employee is not exist within given id : " + employeedId));
		return EmployeeMapper.mapToEmployeeDto(employee);
	}

	@Override
	public List<EmployeeDto> getAllEmployees() {
		List<Employee> employees = employeeRepository.findAll();
		return employees.stream().map(EmployeeMapper::mapToEmployeeDto)
				.collect(Collectors.toList());
	}
}
