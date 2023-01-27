##Task 8:

###View Documentation : https://documenter.getpostman.com/view/1277233/2s8ZDVb4D5
#### Credentails:
    - username: user password: user123, roles: user
    - username : admin password: admin123, roles: user, admin
    
##Task 7:
#### any method annotated with ```@Instrumented``` gets its execution time logged at info level
Example usage: 
```java
@Instrumented
public ResponseEntity<?> authAndGetJWT(@RequestBody CredentialDTO credentialDTO) {
...
}
```
Log output
```
2023-01-27 23:08:04.764  INFO 9268 --- [nio-8080-exec-3] s.m.l.f.instrumentation.Instrumentor     : authAndGetJWT() took 137 ms to complete
```