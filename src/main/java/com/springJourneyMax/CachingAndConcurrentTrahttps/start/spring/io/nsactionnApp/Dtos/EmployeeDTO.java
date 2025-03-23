package com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Dtos;

import lombok.*;

import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDTO {


    private Integer empId;

    private String empNm;

   private String email;

    private float salary;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDTO that = (EmployeeDTO) o;
        return Float.compare(salary, that.salary) == 0 && Objects.equals(empId, that.empId) && Objects.equals(empNm, that.empNm) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empId, empNm, email, salary);
    }
}
