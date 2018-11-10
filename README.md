# SpringBoot-Hibernate-Transactions
Spring Hibernate Transactions example

## Ways of Managing Transactions
A transaction can be managed in the following ways:

### 1. Programmatically manage by writing custom code 
This is the legacy way of managing transaction.

EntityManagerFactory factory = Persistence.createEntityManagerFactory("PERSISTENCE_UNIT_NAME");                                        
EntityManager entityManager = entityManagerFactory.createEntityManager();                   
Transaction transaction = entityManager.getTransaction()                  
try                                       
{  
   transaction.begin();                   
   someBusinessCode();                    
   transaction.commit();  
}                  
catch(Exception ex)                   
{                     
   transaction.rollback();  
   throw ex;                  
}

### Pros:
The scope of the transaction is very clear in the code.

### Cons:
It's repetitive and error-prone.
Any error can have a very high impact.
A lot of boilerplate needs to be written and if you want to call another method from this method then again you need to manage it in the code.

### 2. Use Spring to manage the transaction
Spring supports two types of transaction management:

**1. Programmatic transaction management:** This means that you have to manage the transaction with the help of programming. That gives you extreme flexibility, but it is difficult to maintain.                                                                               
**2. Declarative transaction management:** This means you separate transaction management from the business code. You only use annotations or XML-based configuration to manage the transactions.

Choosing between **Programmatic** and **Declarative Transaction Management:**

* Programmatic transaction management is good only if you have a small number of transactional operations. (This is not a good best proctice)
* Transaction name can be explicitly set only using Programmatic transaction management.
* Programmatic transaction management should be used when you want explicit control over managing transactions.
* On the other hand, if your application has numerous transactional operations, declarative transaction management is worth while.
* Declarative Transaction management keeps transaction management out of businesslogic, and is not difficult to configure.
 
**2.1 Programmatic transaction management**

The Spring Framework provides two means of programmatic transaction management.

**a. Using the TransactionTemplate (Recommended by Spring Team)**

**b. Using a PlatformTransactionManager implementation directly**
 
**2.2. Declarative Transaction**
**Step 1:** Define a transaction manager in your spring application

@Autowired                                                                                                                              
	@Bean(name = "transactionManager")                                                                                                   
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {                                             
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);                                   
            return transacationManager;                                                                                                 
	}
 
**Step 2:** Turn on support for transaction annotations by adding below entry to your spring application 
**@EnableTransactionManagement**
public class AppConfig
{
 ...
}

**Step 3:** Add the @Transactional annotation to the Class (or method in a class) or Interface (or method in an interface). 

* The @Transactional annotation may be placed before an interface definition, a method on an interface, a class definition, or a public method on a class.
* If you want some methods in the class (annotated with  @Transactional) to have different attributes settings like isolation or propagation level then put annotation at method level which will override class level attribute settings.

@Transactional   attributes.

@Transactional (isolation=Isolation.READ_COMMITTED)

The default is Isolation.DEFAULT
Most of the times, you will use default unless and until you have specific requirements.
Informs the transaction (tx) manager that the following isolation level should be used for the current tx. Should be set at the point from where the tx starts because we cannot change the isolation level after starting a tx.
**DEFAULT:** Use the default isolation level of the underlying database.

**READ_COMMITTED:** A constant indicating that dirty reads are prevented; non-repeatable reads and phantom reads can occur.

**READ_UNCOMMITTED:** This isolation level states that a transaction may read data that is still uncommitted by other transactions.

**REPEATABLE_READ:** A constant indicating that dirty reads and non-repeatable reads are prevented; phantom reads can occur.

**SERIALIZABLE:** A constant indicating that dirty reads, non-repeatable reads, and phantom reads are prevented.

What do these Jargons dirty reads, phantom reads, or repeatable reads mean?

**Dirty Reads:** Transaction "A" writes a record. Meanwhile, Transaction "B" reads that same record before Transaction A commits. Later, Transaction A decides to rollback and now we have changes in Transaction B that are inconsistent. This is a dirty read. Transaction B was running in READ_UNCOMMITTED isolation level so it was able to read Transaction A changes before a commit occurred.

**Non-Repeatable Reads:** Transaction "A" reads some record. Then Transaction "B" writes that same  record and commits. Later Transaction A reads that same record again and may get different values because Transaction B made changes to that record and committed. This is a non-repeatable read.

**Phantom Reads:** Transaction "A" reads a range of records. Meanwhile, Transaction "B" inserts a new record in the same range that Transaction A initially fetched and commits. Later Transaction A reads the same range again and will also get the record that Transaction B just inserted. This is a phantom read: a transaction **fetched a range** of records multiple times from the database and obtained different result sets (containing phantom records).

**@Transactional(propagation=Propagation.REQUIRED)**

If not specified, the **default propagational behavior is REQUIRED.**

Other options are  REQUIRES_NEW , MANDATORY  , SUPPORTS  , NOT_SUPPORTED  , NEVER  , and  NESTED .

**REQUIRED**

Indicates that the target method cannot run without an active tx. If a tx has already been started before the invocation of this method, then it will continue in the same tx or a new tx would begin soon as this method is called.    
**REQUIRES_NEW**

Indicates that a new tx has to start every time the target method is called. If already a tx is going on, it will be suspended before starting a new one.                                                                                                                  
**MANDATORY**

Indicates that the target method requires an active tx to be running. If a tx is not going on, it will fail by throwing an exception.   
**SUPPORTS**

Indicates that the target method can execute irrespective of a tx. If a tx is running, it will participate in the same tx. If executed without a tx it will still execute if no errors.
Methods which fetch data are the best candidates for this option.                                                                       
**NOT_SUPPORTED**

Indicates that the target method doesn’t require the transaction context to be propagated.
Mostly those methods which run in a transaction but perform in-memory operations are the best candidates for this option.               
**NEVER**

Indicates that the target method will raise an exception if executed in a transactional process.
This option is mostly not used in projects.
 
