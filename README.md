## About
This application is deployed to:

https://webappkeaeksamen-fbbpescmgmgwh3bn.westeurope-01.azurewebsites.net
## How to run locally
1. Clone this repository.
2. Open project in an IDE on branch *main*
3. Switch spring profile by replacing *prod* with *h2* on line 3 in *./src/main/resources/application.properties*
4. Change scope of h2 dependency by replacing *test* with *runtime* on line 52 in *./pom.xml*
5. Load maven changes.
6. Run *./src/main/java/beight/eksamenkea/EksamenKeaApplication.java*
7. Open http://localhost:8080/ in a webbrowser.
8. Username: *admin*
9. Password: *bondegaard*