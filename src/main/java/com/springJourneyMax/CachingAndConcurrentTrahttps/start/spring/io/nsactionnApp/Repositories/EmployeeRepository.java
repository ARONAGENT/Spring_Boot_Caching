package com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Repositories;

import com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Integer> {
    List<EmployeeEntity > findByEmail(String email);

}
