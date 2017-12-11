#Offers

A simple REST API to manage offers 
implemented using Spring Boot.

##Running the app
If you are using a *NIX machine (and I hope you are),
there is a run script, so simply type `./run.sh` in the root
folder

If you are in a windows machine there are two options:
1. Get a *NIX machine and go back to the previous step
2. Use the gradle wrapper and run `gradlew bootRun`

##Design considerations
- The app has been TDDed and created in small incremental steps,
you can check the git history to get a taste of the process.
- The REST API should be threadsafe, as it uses a handcrafted
in memory persistence storage we took advantage of Concurrent Java\
data types.
- The REST API tries to aim to reach the 3rd level of Richardson
Maturity model, using HATEOAS to serve hypermedia links.
- State is managed using links
- We only included links relevant for our little exercise.
- Hit http://localhost:8080 to start discovering the API :)
- Cancel uses PATCH as it is the HttpMethod that makes most sense
- The rest of the links are fairly standard
- We skipped 'self' links for the sake of simplicity.
- There is a lovely e2e Journey that demonstrates the happy path

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