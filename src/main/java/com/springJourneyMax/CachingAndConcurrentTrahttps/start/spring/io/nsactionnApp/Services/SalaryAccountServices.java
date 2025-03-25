package com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Services;

import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Entities.EmployeeEntity;
import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Entities.SalaryAccount;
import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Repositories.SalaryAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class SalaryAccountServices {

    private final SalaryAccountRepository salaryAccountRepository;

    public SalaryAccount createAccount(EmployeeEntity employee){

//        if(employee.getEmpNm().equals("Rohan")) throw new RuntimeException("Rohan is not Allowed ");
        SalaryAccount salaryAccount=SalaryAccount.builder()
                .employee(employee)
                .balance(BigDecimal.ZERO)
                .build();

        return salaryAccountRepository.save(salaryAccount);
    }

    public SalaryAccount incrementSalary(Long accountId) {
        SalaryAccount salaryAccount=salaryAccountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Not found with salary Account "+accountId));
        BigDecimal prevBalance=salaryAccount.getBalance();
        BigDecimal newBalance=prevBalance.add(BigDecimal.valueOf(1L));

        salaryAccount.setBalance(newBalance);

        return  salaryAccountRepository.save(salaryAccount);

    }
}
