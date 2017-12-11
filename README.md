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

- There is mutability in some areas
such as creation of offers which is not 
ideal, i'd personally take advantage
of kotlin constructs, but for the sake
of the exercise it feels like a fair
compromise.

- Once cancelled offers cannot be
re-enabled as there is no specific
requirement for that. We could implement
state nicely exposing the appropriate
hypermedia links