===================================
*  Ticket BookingService Assignment  *
===================================

This ticket service allows user to create multiple holds and reservations for one customer.
Also this service will hold any available seat within levels user provided and return holdId.
If available seats are not enough in one level, then hole available seat in next level.
There is no limit on number of holding nor on number of seat to hold. This service will returns
seat holdId when it can hold at least one seat. So if user request 8 seats, but find 4 seat then
it holds the 4 seats and return seat holdId. However seat hold id can be empty if service cannot
find any available seat in the levels user provided.



===================================
1. Build Project By Maven
===================================

Run below command inside of this project folder, ```ticket-booking-service-0.0.1.jar``` will be created under ./target
inside of project directory

```$ mvn clean install```

===================================
2. Run As Server Mode
===================================

This service is designed for web service as running server mode
For running this service with default configuration, just run below command in the folder which contains ticket-service-0.0.1.jar
Default port will be 9797 and hold expiration time is 120 seconds.


```$ java -cp ticket-booking-service-0.0.1.jar org.springframework.boot.loader.JarLauncher```


Once you start service by above command, it will generate database file ticket-service.mv.db (H2 database file)
Service will be running as server mode by embed tomcat inside of ticket-service-0.0.1.jar
If you want to stop the service after starting, please do Ctrl+C on the console

Also if you want to change the default configuration (port number or expiration time), then please copy application.properties
under /src/main/resources of this project to wherever you want and edit properties.
Below is example property change. change port to 9393 and seat hold expiration time to 3minute

```
[ application.properties ]
#
#   Ticket Service Property
#
server.port=${port:9393}
service.log.level=${log.level:DEBUG}
seat.hold.expire.second=${expire.time:180}
```
===================================
3. Send Request to Service
===================================

After starting service as server mode, service will expect receiving request over HTTP.
If curl command is available, then you can send requests like below. If curl is not available, then REST client plugin for Chrome or FireFox
Browser can be used. All the requests will be handled asynchronously

#### 1) request to find available seats ####
   endpoint: `/ticket-service/v1/available-seats/venue?level={venueLevel}`

```$ curl -X GET http://localhost:9797/ticket-booking-service/v1/available-seats/venue?level=3```

```json
{"venueLevel":3,"numberOfAvailableSeats":1500}
```


`level` is queryParam and optional, so you can omit the param and it will give total available seats through whole levels

```$ curl -X GET http://localhost:9797/ticket-booking-service/v1/available-seats/venue```
```json
{"venueLevel":null,"numberOfAvailableSeats":6250}
```


#### 2) request to find and hold seats ####
   endpoint: `/ticket-service/v1/hold/num-seats/{numberOfSeats}/email/{customerEmail}/venue?minLevel={minLevel}&maxLevel={maxLevel}`
   minLevel and maxLevel are queryParam and optional. So you can omit any or both of queryParams.
   If no minLevel is given, it will search from 1 (Orchestra). also If no maxLevel,
   then it will search up to 4 (Balcony 2). If response take some time, it will return later asynchronously

```$ curl -X POST http://localhost:9797/ticket-booking-service/v1/hold/num-seats/900/email/sandeep@test.com/venue?minLevel=1&maxLevel=3```
```json
$ {"holdId":50,"customerEmail":"sandeep@test.com","details":[{"venueLevel":1,"numOfSeats":900}]}
```

```$ curl -X POST http://localhost:9797/ticket-booking-service/v1/hold/num-seats/20/email/sandeep@test.com/venue?minLevel=3```
```json
{"holdId":51,"customerEmail":"sandeep@test.com","details":[{"venueLevel":3,"numOfSeats":20}]}
```

* if fail to hold any seat, it will return null for holdId

```$ curl  http://localhost:9797/ticket-booking-service/v1/hold/num-seats/9000/email/sandeep@test.com/venue?minLevel=2```
```json
{"holdId":null,"customerEmail":"sandeep@test.com","details":[]}
```

#### 3) request to reserve seat by holdId 
   endpoint: `/ticket-service/v1/hold/{holdId}/email/{customerEmail}/reserve`
   If reservation finished successfully, it will return confirmationCode


```$ curl -X POST http://localhost:9797/ticket-booking-service/v1/hold/51/email/sandeep@test.com/reserve```
```json
{"holdId":51,"customerEmail":"sandeep@simpson.com","confirmationCode":"787bff5f-ed20-33bc-949d-e49fa52ac38c"}
```

However seat hold is expired or customerEmail is not matched for the seatHold, it will return error message

(seat hold expired or no hold found)
```$ curl -X POST http://localhost:9797/ticket-booking-service/v1/hold/100/email/sandeep@test.com/reserve```
```json
{"timestamp":1456366927001,"status":404,"error":"Not Found","exception":"com.walmart.ticketbooking.error.SeatHoldNotFoundException","message":"no such hold","path":"/ticket-booking-service/v1/hold/52/email/sandeep@test.com/reserve"}
```

(customer validation fail wrong email for hold id)
```$ curl -X POST http://localhost:9797/ticket-booking-service/v1/hold/51/email/sandeepinvalid@test.com/reserve```

```json
{"timestamp":1456366917001,"status":400,"error":"Bad Request","exception":"com.walmart.ticketbooking.error.CustomerValidationException","message":"email is not matching","path":"/ticket-booking-service/v1/hold/51/email/sandeepinvalid@test.com/reserve"}
```


#### 4) reset database with removing all the hold and customer data 
 for testing convenience, admin endpoint is available to clean up all the seat holds and customer info.

```$ curl -X DELETE http://localhost:9797/admin/seat-holds```

All SeatHolds with Customer Info have been deleted!


