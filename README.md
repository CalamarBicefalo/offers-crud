#Offers

A simple REST API to manage offers 
implemented using Spring Boot.

##Assumptions

- We assume there is a single merchant as there is no need for 
more in the current spec

- We assume offers have a start - end 
date for validity because the briefing
talks about "length of time" which implies
a duration

- We are deliberately not including a 'clock'
dependency, although it improves testability
I personally prefer to think of alternative ways
and keep dependencies lower.