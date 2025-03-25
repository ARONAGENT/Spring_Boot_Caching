package com.springJourneyMax.CachingAndConcurrentTrahttps.start.spring.io.nsactionnApp.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "salary_account")
public class SalaryAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

   // Optimistic locking
//     1. A new column called “version” is added to the database table.
//     2. Before a user modifies a database row, the application reads the version
//        number of the row.
//     3. When the user updates the row, the application increases the version number
//         by 1 and writes it back to the database.
//     4. A database validation check is put in place; the next version number should
//       exceed the current version number by 1. The transaction aborts if the
//       validation fails and the user tries again from step 2.
    @Version
    private Long version;

    @OneToOne
    @JsonIgnore
    private EmployeeEntity employee;
}
