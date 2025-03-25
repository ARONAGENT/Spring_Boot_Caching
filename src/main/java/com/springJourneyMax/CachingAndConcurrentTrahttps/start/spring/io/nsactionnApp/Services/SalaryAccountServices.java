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
// --------- Propagation Levels (Commented) ---------

// @Transactional(propagation = Propagation.REQUIRED)  // Uses existing transaction if available; otherwise, creates a new one.
// @Transactional(propagation = Propagation.REQUIRES_NEW)  // Always creates a new transaction, suspending the existing one.
// @Transactional(propagation = Propagation.MANDATORY)  // Requires an existing transaction; throws an exception if none exists.
// @Transactional(propagation = Propagation.SUPPORTS)  // Uses a transaction if available; executes non-transactionally otherwise.
// @Transactional(propagation = Propagation.NOT_SUPPORTED)  // Executes non-transactionally, suspending any existing transaction.
// @Transactional(propagation = Propagation.NEVER)  // Ensures no active transaction; throws an exception if a transaction exists.
// @Transactional(propagation = Propagation.NESTED)  // Executes within a nested transaction if a parent transaction exists.

// --------- Isolation Levels (Commented) ---------

// @Transactional(isolation = Isolation.DEFAULT)  // Uses the default isolation level of the database.
// @Transactional(isolation = Isolation.READ_UNCOMMITTED)  // Allows reading uncommitted (dirty) data, improving performance but risking inconsistencies.
// @Transactional(isolation = Isolation.READ_COMMITTED)  // Ensures only committed data is read, preventing dirty reads.
// @Transactional(isolation = Isolation.REPEATABLE_READ)  // Ensures the same data is read consistently within a transaction, preventing non-repeatable reads.
// @Transactional(isolation = Isolation.SERIALIZABLE)  // Fully isolates transactions, preventing dirty reads, non-repeatable reads, and phantom reads.

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
