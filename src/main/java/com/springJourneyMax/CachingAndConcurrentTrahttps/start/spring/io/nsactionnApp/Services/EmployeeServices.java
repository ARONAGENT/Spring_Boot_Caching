package com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Services;



import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Dtos.EmployeeDTO;
import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Entities.EmployeeEntity;
import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Exceptions.ResourceNotFoundException;
import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Repositories.EmployeeRepository;
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
public class EmployeeServices {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeServices(ModelMapper modelMapper, EmployeeRepository employeeRepository) {
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;

    }



    public EmployeeDTO getEmpById(int id){
        EmployeeEntity employeeEntity= employeeRepository.findById(id)
                .orElseThrow(()-> new
                        ResourceNotFoundException("No such Element Found by id : "+id));
        return modelMapper.map(employeeEntity,EmployeeDTO.class);
    }


    public EmployeeDTO addEmp(EmployeeDTO employeeDTO) {
        EmployeeEntity obj=modelMapper.map(employeeDTO,EmployeeEntity.class);
        EmployeeEntity employeeEntity=employeeRepository.save(obj);
        return modelMapper.map(employeeEntity,EmployeeDTO.class);
    }

    public void isExistById(int empId){
        boolean exist =employeeRepository.existsById(empId);
        if(!exist) throw new NoSuchElementException();
    }

    public EmployeeDTO updateEmp(int empId, EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity=modelMapper.map(employeeDTO,EmployeeEntity.class);
        isExistById(empId);
        employeeEntity.setEmpId(empId);
        employeeRepository.save(employeeEntity);
        return modelMapper.map(employeeEntity,EmployeeDTO.class);
    }

    public List<EmployeeDTO> allEmployee() {
        List<EmployeeEntity> employeeEntity=employeeRepository.findAll();
        return employeeEntity
                .stream()
                .map(employeeEntity1 -> modelMapper.map(employeeEntity1,EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public ResponseEntity<EmployeeDTO> deleteEmployee(int empId) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(empId);
        if (employeeEntity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        EmployeeDTO employeeDTO = modelMapper.map(employeeEntity.get(), EmployeeDTO.class);
        employeeRepository.deleteById(empId);
        return ResponseEntity.ok(employeeDTO);
    }

}
