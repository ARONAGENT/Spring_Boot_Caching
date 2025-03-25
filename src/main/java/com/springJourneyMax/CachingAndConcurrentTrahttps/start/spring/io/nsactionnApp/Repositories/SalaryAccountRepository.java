package com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Repositories;

import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Entities.SalaryAccount;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalaryAccountRepository extends JpaRepository<SalaryAccount,Long> {

    @Override
    // Blocks other transactions from reading or writing until the lock is released.
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<SalaryAccount> findById(Long id);

    // @Lock(LockModeType.NONE)  // No locking, allows concurrent access.
    // @Lock(LockModeType.READ)  // Acquires a shared lock, allows multiple reads but no writes.
    // @Lock(LockModeType.OPTIMISTIC)  // Uses versioning to check for conflicts before committing.
    // @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)  // Similar to OPTIMISTIC but forces a version increment.
    // @Lock(LockModeType.PESSIMISTIC_READ)  // Blocks other transactions from writing but allows reading.
    // @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)  // PESSIMISTIC_WRITE + forces version increment on lock acquisition.
}
