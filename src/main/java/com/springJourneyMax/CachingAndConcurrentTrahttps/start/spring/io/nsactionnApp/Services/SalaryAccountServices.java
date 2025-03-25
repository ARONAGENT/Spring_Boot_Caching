package com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Services;

import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Entities.EmployeeEntity;
import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Entities.SalaryAccount;
import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Repositories.SalaryAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SalaryAccountServices {

    private final SalaryAccountRepository salaryAccountRepository;

    public SalaryAccount createAccount(EmployeeEntity employee){

        if(employee.getEmpNm().equals("Rohan")) throw new RuntimeException("Rohan is not Allowed ");
        SalaryAccount salaryAccount=SalaryAccount.builder()
                .employee(employee)
                .balance(BigDecimal.ZERO)
                .build();

        return salaryAccountRepository.save(salaryAccount);
    }
}
