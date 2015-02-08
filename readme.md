_DBInterface_ is a simple wrapper that sits on top of the standard Android SQLite database, making it easier to link Java objects to records in the database.

It also includes a query builder inspired by [Arel](https://github.com/rails/arel), to reduce the amount of raw SQL that hasa to be written, but maintaining the ability to use SQL when it is necessary.

# E.G.

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

    
    
## To do

- Better query building
- Move `getAll()` to `Record`