# SP_Kayu

BOUVARD Alexandre, BELLEC Louison  

Open food fact api implementation.

## Links

[Base repository](https://gitlab.isima.fr/javapro/caillou/-/tree/master/).

[Open Food Fact](https://fr.openfoodfacts.org/data).

The API id documented with [swagger](http://localhost:8080/swagger-ui/index.html).

## Routes 

### Products

- GET `/products/:id` :  Get a product info(barcode, name, nutritionScore, class and color) based on the id.


### Basket

- GET `/basket?mail=mail` : Get a basket(List of products, average nutriscore, average color, and average class) for the user given in mail. 
- DELETE `/basket/:id?mail=mail` Deletes a product form user's basket based on product id and mail. 
- POST `/basket/:id?mail=mail` Adds a product in user's basket based on product id and mail. 


## Features 

We have two main features on this API.

- The first one is just the calculation of a product nutrition score.
- The other one is a basket handling with the possibility to get, add and remove products for a user.

Users are authenticated via email.  
The basket is made of the list of products, an overall average nutrition score and the class and colors it translates to.  

Basket handling is very basic, just adding, deleting and getting the basket. However we make sure we can only add valid product in the basket and we return a complete overview of the basket on GET.

## Technical

### Database

We use an H2 Database so it's easier for development and testing, but it's in-memory, so it resets everytime we restart the API.
However, since we are using JPA, we could switch to any other SQL database if we want to.

We store the baskets in a table with 2 foreign key, the `mail` and the product `bar_code`.  
We don't want to have a direct relationship with Product table since the Product table is made to be a separated table specifically for optimisation (get or fetch mechanism explained below).

### Get or Fetch

We have a Get or Fetch mechanism when fetching product. When getting a product by ID, we first check our database, and returns the product if it exists.  
Otherwise we do the actual HTTP request to Open Food Fact, calculate nutrition score, class and color and store the product in the database.  
This mechanism allows to greatly speed up the fetching process after the first GET on a product.  

The H2 database is a nice fit for such usage, but it could be switched to another In-memory DB such as redis or mem-cached on a real application.

## Tests

We principally made end to end integration test because they are the most relevant in this use case. We test all our endpoint with valid and invalid IDs.  
We only made unitary tests on `ScoreCompute` class which containes the actual logic for calculating nutrition score.   

Integration tests uses all real components except for the api call which is mocked since we don't want to rely on external resources when testing.
