package com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Services;



import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Dtos.EmployeeDTO;
import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Entities.EmployeeEntity;
import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Exceptions.ResourceNotFoundException;
import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Repositories.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeServices {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeServices(ModelMapper modelMapper, EmployeeRepository employeeRepository) {
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;

    }


    @Cacheable(cacheNames = "employees",key = "#id")
    public EmployeeDTO getEmpById(int id) {
        log.info("Fetching employee with ID: {}", id);
        EmployeeEntity employeeEntity = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("No employee found with ID: {}", id);
                    return new ResourceNotFoundException("No such Element Found by id: " + id);
                });
        log.info("Successfully fetched employee with ID: {}", id);
        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }

    @CachePut(cacheNames = "employees", key = "#result.empId")
    public EmployeeDTO addEmp(EmployeeDTO employeeDTO) {
        log.info("Adding new employee: {}", employeeDTO);
        EmployeeEntity obj = modelMapper.map(employeeDTO, EmployeeEntity.class);
        EmployeeEntity employeeEntity = employeeRepository.save(obj);
        log.info("Successfully added employee with ID: {}", employeeEntity.getEmpId());
        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }

    public void isExistById(int empId){
        boolean exist =employeeRepository.existsById(empId);
        if(!exist) throw new NoSuchElementException();
    }

    @CachePut(cacheNames = "employees",key = "#result.empId")
    public EmployeeDTO updateEmp(int empId, EmployeeDTO employeeDTO) {
        log.info("Updating employee with ID: {}", empId);
        isExistById(empId);
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        employeeEntity.setEmpId(empId);
        employeeRepository.save(employeeEntity);
        return modelMapper.map(employeeEntity,EmployeeDTO.class);
    }

    @Cacheable(cacheNames = "employees")
    public List<EmployeeDTO> allEmployee() {
        log.info("Fetching all employees");
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        log.info("Successfully fetched {} employees", employeeEntities.size());
        return employeeEntities.stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    @CacheEvict(cacheNames = "employees",key = "#empId")
    public ResponseEntity<EmployeeDTO> deleteEmployee(int empId) {
        log.info("Attempting to delete employee with ID: {}", empId);
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(empId);
        if (employeeEntity.isEmpty()) {
            log.error("No employee found with ID: {} to delete", empId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        EmployeeDTO employeeDTO = modelMapper.map(employeeEntity.get(), EmployeeDTO.class);
        employeeRepository.deleteById(empId);
        log.info("Successfully deleted employee with ID: {}", empId);
        return ResponseEntity.ok(employeeDTO);
    }

}
