package com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Services;

import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Entities.EmployeeEntity;
import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Entities.SalaryAccount;
import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Repositories.SalaryAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
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

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public SalaryAccount incrementSalary(Long accountId) {
        // A "Parallel API Load Testing Command
        // seq 100 | xargs -P 10 -I {} curl --location --request PUT 'http://127.0.0.1:9090/employees/incrementAccBalance/1'
        // "This command makes 100 API requests using cURL, running 10 requests at the same time.
        //"It simulates multiple users updating an employee's account balance in parallel."
        // All the logging statements saved in logs/salary_account.log
        SalaryAccount salaryAccount=salaryAccountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Not found with salary Account "+accountId));
        BigDecimal prevBalance=salaryAccount.getBalance();
        BigDecimal newBalance=prevBalance.add(BigDecimal.valueOf(1L));

        salaryAccount.setBalance(newBalance);

        log.info("Incremented Salary Account: {} | Previous Balance: {} | New Balance: {}",
                accountId, prevBalance, newBalance);
        return  salaryAccountRepository.save(salaryAccount);

    }
}
