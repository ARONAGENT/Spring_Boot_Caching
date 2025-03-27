# Caching and Concurrent Transaction Management

## 1. Introduction to Caching

### What is Caching?
Caching allows you to efficiently reuse previously retrieved or computed data. In computing, a cache is a high-speed data storage layer that stores a subset of data, typically transient in nature. This ensures that future requests for that data are served faster than by accessing the data’s primary storage location.

## 2. Spring Boot Default Caching

Spring Boot provides built-in caching mechanisms using annotations:

### Annotations Used in Caching:

- **@EnableCaching**: Enables caching in a Spring Boot application. It sets up a `CacheManager` and creates an in-memory cache using a concurrent `HashMap`.
- **@Cacheable**: Marks a method’s response to be cached. Spring Boot manages the request and response of the method to the cache specified in the annotation.
- **@CachePut**: Updates the cache after invoking the method. It ensures the cache is updated with new data.
- **@CacheEvict**: Removes the data from the cache, ensuring that outdated data is cleared.

### Internal Working of Caching in Spring Boot:
1. When a method annotated with `@Cacheable` is called, Spring AOP intercepts the call and checks the cache before executing the method.
2. The `CacheManager` retrieves the associated cache.
3. **Cache Hit or Miss**:
   - **Hit**: Returns the cached value directly.
   - **Miss**: Executes the method and stores the result in the cache.
4. If the cache was missed, the result is stored using the `put` method of `Cache`.
5. Finally, the result is returned to the caller.

## 3. Redis Cache in Spring Boot

### Dependencies for Redis:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
You can integrate a cloud-based Redis cache for better performance.

**Video Execution Of Redis Cache->**
<br>


https://github.com/user-attachments/assets/a0196d99-a5e9-4b44-8ab9-02872318aebe



## 4. Database Transactions and ACID Properties

A database transaction is a logical unit that includes several steps. If one step fails, the entire transaction fails.

### ACID Properties:
- **Atomicity**: Ensures all operations succeed or none do (e.g., bank transfers should either debit and credit both accounts or fail entirely).
- **Consistency**: Guarantees the database transitions from one valid state to another.
- **Isolation**: Ensures concurrent transactions do not interfere with each other.
- **Durability**: Ensures that once a transaction is committed, it remains so even in case of system failure.

### Advantages of ACID Properties:
- Data Integrity
- Reliability
- Concurrency Control
- Fault Tolerance
- Structured Transaction Management

### Disadvantages of ACID Properties:
- Performance Overhead
- Complexity
- Scalability Challenges
- Deadlock Possibilities
- Limited Concurrency

ACID properties are often ignored in caching and analytics systems.

## 5. Concurrent Transaction Management

### Common Transaction Problems:
- **Dirty Read**: Reads uncommitted data from another transaction.
- **Non-Repeatable Read**: Same row read twice but with different values.
- **Phantom Read**: Same query retrieves different rows when executed twice.

### Isolation Levels in Spring Boot:
- **Read Uncommitted**
- **Read Committed**
- **Repeatable Read**
- **Serializable**

### Serialization in Databases:
- **PostgreSQL**: Uses Serializable Snapshot Isolation (SSI).
- **Oracle, MySQL, SQL Server**: Use row and range locks.

## 6. Transaction Annotations in Spring Boot

### `@Transactional` Annotation:
Spring manages transactions using AOP proxies. When a method annotated with `@Transactional` is executed:
- **Transaction Starts**: Before the method execution.
- **Commit/Rollback**: If successful, it commits the transaction; otherwise, it rolls back.

### Transaction Propagation:
- **REQUIRED**: Uses an existing transaction if available, otherwise creates a new one.
- **REQUIRES_NEW**: Always creates a new transaction.
- **MANDATORY**: Throws an exception if no transaction exists.
- **NOT_SUPPORTED**: Runs the method without a transaction.
- **SUPPORTS**: Uses an existing transaction if available, otherwise runs without one.
- **NEVER**: Throws an exception if a transaction exists.
- **NESTED**: Creates a sub-transaction within the parent transaction.

## 7. Transaction Locks in Spring Data JPA

Transaction locks ensure data consistency in multi-threaded environments.

### Why Use Transaction Locks?
- Isolation levels define visibility, while locks enforce isolation.
- Prevents conflicts and enforces concurrency control.

### Types of Locks:

Execution Image ->
<br>
**1.Without Using Locking ->**
<br>
![0](https://github.com/user-attachments/assets/7d488ff9-f422-4e2d-83fa-07925c48d696)

#### **Optimistic Locking**:
- Uses a `version` column in the database.
- The application reads the version before modification.
- The version is incremented upon update.
- The transaction aborts if the version check fails.

  Execution Image ->
  <br>
**2.With Optimistic Locking -**
   <br>
  ![1](https://github.com/user-attachments/assets/1aa2fc93-ccef-4ed1-8e4d-e5fe59757c21)


#### **Pessimistic Locking**:
Use the `@Lock` annotation in Spring Data JPA.

- **PESSIMISTIC_READ**: Ensures the data is read but not modified by other transactions (shared lock).
- **PESSIMISTIC_WRITE**: Prevents other transactions from reading or writing (exclusive lock).

  Execution Image ->
  <br>
  **2.With Pessimistic Locking**
  <br>
  ![2 PessimisticLocking](https://github.com/user-attachments/assets/32da7b84-a23e-49b8-a828-8b761807cdda)


## 8. Real-World Transaction Strategies

| Strategy               | Use Case Examples                      |
|------------------------|---------------------------------------|
| **Read Committed**     | E-commerce transactions (e.g., Amazon) |
| **Read Uncommitted**   | Data analytics, non-critical reporting |
| **Serializable**       | Financial transactions (e.g., banks)  |
| **Optimistic Locking** | Collaborative editing (e.g., Google Docs) |
| **Pessimistic Locking**| Booking systems (e.g., airline seats) |
| **No Transactions**    | Social media, large-scale systems (e.g., Twitter) |

---
## Conclusion
This documentation provides a comprehensive guide to caching and transaction management in Spring Boot, covering concepts, implementation, and real-world scenarios. By using the correct caching mechanisms and transaction management strategies, you can improve application performance, consistency, and concurrency handling.

Feel free to contribute to this repository to enhance transaction management techniques in Spring Boot applications!

