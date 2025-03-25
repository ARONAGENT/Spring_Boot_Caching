package com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Controllers;



import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Dtos.EmployeeDTO;
import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Entities.SalaryAccount;
import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Services.EmployeeServices;
import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Services.SalaryAccountServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/employees")
public class EmployeeController {

    private final EmployeeServices empServ;
    private final SalaryAccountServices salaryAccountServices;
    public EmployeeController(EmployeeServices empServ, SalaryAccountServices salaryAccountServices) {
        this.empServ = empServ;
        this.salaryAccountServices = salaryAccountServices;
    }

    @GetMapping(path="/info")
    public String firstEmp(){
        return "Rest Api Working Successfully";
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeByID(@PathVariable int id){
        return ResponseEntity.ok(empServ.getEmpById(id));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<EmployeeDTO>> allEmployee(){
        return ResponseEntity.ok(empServ.allEmployee());
    }
    @PostMapping(path="/add")
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody  EmployeeDTO employeeDTO){
        EmployeeDTO employeeDTO1=empServ.addEmp(employeeDTO);
        return new ResponseEntity<>(employeeDTO1, HttpStatus.CREATED);
    }

    @PutMapping(path = "/update/{empId}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable int empId, @RequestBody EmployeeDTO employeeDTO){
        return ResponseEntity.ok(empServ.updateEmp(empId,employeeDTO));
    }

    @DeleteMapping(path = "/delete/{empId}")
    public ResponseEntity<EmployeeDTO> deleteByEmployeeID(@PathVariable int empId) {
        return empServ.deleteEmployee(empId);
    }

    @PutMapping(path = "/incrementAccBalance/{account_id}")
    public ResponseEntity<SalaryAccount> incrementSalary(@PathVariable Long account_id){
        SalaryAccount account=salaryAccountServices.incrementSalary(account_id);
        return ResponseEntity.ok(account);
    }

}
