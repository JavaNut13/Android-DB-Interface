_DBInterface_ is a simple wrapper that sits on top of the standard Android SQLite database, making it easier to link Java objects to records in the database.

It also includes a query builder inspired by [Arel](https://github.com/rails/arel), to reduce the amount of raw SQL that hasa to be written, but maintaining the ability to use SQL when it is necessary.

## How to:

Clone the repo, chuck the _dbinterface_ folder into your project (`Record`, `DBInterface` and `Query` are required). Create a class for each table in your database, look at `Person` or `Location` for examples.

# E.G.

### Creating records

Two example records - `Person` and `Location` - are given to show how to extend the `Record` class.

Create a Location:

    // Create the object
    Location place = new Location();
    // Set it's attributes
    place.setName("My place");
    place.setLocation("123.456, -12.5");
    // DBInterface manages the connection to the database
    DBInterface dbi = new DBInterface(getContext()).open();
    // This will create a record in the DB
    place.save(dbi);
    // Don't forget to close the database!
    dbi.close();

Get a single location by ID:

    DBInterface dbi = new DBInterface(getContext()).open();
    Cursor data = new Query(dbi).in(Location.class).find(LOCATION_ID);
    Location ml = new Location(data);
    // Do stuff to ml and then save it if required
    ml.save();
    dbi.close();

Get a list of people:

    DBInterface dbi = new DBInterface(getContext()).open();
    Query search = new Query(dbi).in(Person.class).where("name like ?", SEARCH_PARAMS);
    ArrayList<Person> people = Person.getAll(search.all());
    dbi.close()
    // use people for something handy
    
### Performing queries

The methods in the `Query` class can be chained together in any order. Each one sets the corresponding section of a normal android SQL query:

    database.query(table, select, where, whereargs, groupBy, null, orderBy);
    
> NOTE: `in()` or `from()` should be called on _every_ query, otherwise it won't know where to do the query.

If the same method is called twice, it is likely not going to chain the clauses together (for example `where(things).where(otherthings)` is not fully tested) will **NOT** work as you would expect. This is something that should be improved in the future.
    
## To do

- Better query building (Perhaps with an object that builds the `where` clause?)
- Move `getAll()` to `Record`