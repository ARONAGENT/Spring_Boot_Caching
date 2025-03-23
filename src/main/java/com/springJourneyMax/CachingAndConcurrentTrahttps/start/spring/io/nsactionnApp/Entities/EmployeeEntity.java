package com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name="employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer empId;

    private String empNm;

    private String email;

    private float salary;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeEntity that = (EmployeeEntity) o;
        return Float.compare(salary, that.salary) == 0 && Objects.equals(empId, that.empId) && Objects.equals(empNm, that.empNm) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empId, empNm, email, salary);
    }
}
