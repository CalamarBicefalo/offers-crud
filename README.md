#Offers

A simple REST API to manage offers 
implemented using Spring Boot.

##Assumptions

- We assume there is a single merchant as there is no need for 
more in the current spec

- The 'persistence' of the app is a simple
in memory map. It is concurrency aware
as a REST API is supposed to be hit
by many threads.

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

- In order to demonstrate when an offer
is active or not, a redeem / cancel links
will be included in the payload.
The intent is to use the 3rd level of the
Richardson Maturity model, and manage state
in the backend to then expose it via links.
Instead of exposing flags / enums and 
managing state in the client.

- The redeem endpoint is not implemented
and it'll return 404. It was not required
and was added as a link for demonstration purposes.