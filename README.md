# Spring-Boot-Mongo-Embedded

#### Postman Collection - [Imprt from here](https://www.getpostman.com/collections/6789ea26c72976a612eb)

- - - -

## MongoDB  

### FEATURES
- NoSql - Document DB
- JSON like syntax
- easier to scale. much faster
- MongoDB is written in C++. 
- Cross-platform, document oriented database that provides, high performance, high availability, and easy scalability

___Database___ - 
Database is a physical container for collections. Each database gets its own set of files on the file system. A single MongoDB server typically has multiple databases.

___Collection___ - 
Collection is a group of MongoDB documents. It is the equivalent of an RDBMS table. A collection exists within a single database. Collections do not enforce a schema. Documents within a collection can have different fields. Typically, all documents in a collection are of similar or related purpose.

____Primary Key (Default key _id provided by MongoDB itself)___ - 
_id is a 12 bytes hexadecimal number which assures the uniqueness of every document. You can provide _id while inserting the document. If you don’t provide then MongoDB provides a unique id for every document. These 12 bytes first 4 bytes for the current timestamp, next 3 bytes for machine id, next 2 bytes for process id of MongoDB server and remaining 3 bytes are simple incremental VALUE.

- test is the default database.

- You do not need to create the database before you switch. MongoDB creates the database when you first store data in that database (such as create the first collection in the database).

- If a collection does not exist, MongoDB creates the collection when you first store data for that collection.

### COMMANDS

code(show dbs)

use mycustomers

db

db.createUser(
   {
     user: "gagan_test",
     pwd: "test",   // Or  "<cleartext password>"
     roles:
       [
         "readWrite","dbAdmin"
       ]
   }
);

db.help();  db.stats();

db.dropDatabase()

db.createCollection("mycol", { capped : true, size : 6142800, max : 10000 } );

db.createCollection('customers');  

db.mycollection.drop() // returns true if dropped successfully  

show collections;

db.collection.save() and insert() works similar except that if _id already exists insert() will throw error abd save() will replace that record

//insertOne(), insert(), insertMany() are different functions that can be used
db.customers.insert({first_name:"John",last_name:"Doe"});

The operation returns a document that contains the acknowledgement indicator and an array that contains the _id of each successfully inserted documents.
db.inventory.insertMany([
   { item: "journal", qty: 25, status: "A", size: { h: 14, w: 21, uom: "cm" }, tags: [ "blank", "red" ] },
   { item: "notebook", qty: 50, status: "A", size: { h: 8.5, w: 11, uom: "in" }, tags: [ "red", "blank" ] },
   { item: "paper", qty: 10, status: "D", size: { h: 8.5, w: 11, uom: "in" }, tags: [ "red", "blank", "plain" ] },
   { item: "planner", qty: 0, status: "D", size: { h: 22.85, w: 30, uom: "cm" }, tags: [ "blank", "red" ] },
   { item: "postcard", qty: 45, status: "A", size: { h: 10, w: 15.25, uom: "cm" }, tags: [ "blue" ] }
]);

db.customers.find().pretty();

db.inventory.findOneAndUpdate({gender:"DDD"},{$set:{first_name:"updated"}})   // updateOne(), updateMany()
   
db.customers.update({first_name:"John"},{$set:{gender:"changed"}});

db.customers.update({first_name:"John"},{first_name:"John","last_name":"Doe",gender:"male"});
  
db.customers.update({first_name:"John"},{$inc:{age:5}});

db.customers.update({first_name:"John"},{$unset:{age:1}});

db.customers.update({first_name:"Marry"},{first_name:"mary",last_name:"Samson"},{upsert:true});

db.customers.update({first_name:"Joan"},{$rename:{"gender":"sex"}});

//By default, MongoDB will update only a single document. To update multiple documents, you need to set a parameter 'multi' to true.
db.mycol.update({'title':'MongoDB Overview'},{$set:{'title':'New MongoDB Tutorial'}},{multi:true})
 
//Replaces if same object id document is found otherwise inserts new
db.mycol.save(
   {
      "_id" : ObjectId("507f191e810c19729de860ea"), 
		"title":"Tutorials Point New Topic",
      "by":"Tutorials Point"
   }
)

db.customers.remove({first_name:"mary"});

db.customers.remove({first_name:"mary"},{justOne:true});  

db.inventory.remove({first_name:"DDD"},{justOne:1})  
  
db.customers.remove({})    //will delete whole documents from the collection. This is equivalent of SQL's truncate command.

db.customers.find({first_name:"Joan"});

db.customers.find({$or:[{first_name:"Joan"},{last_name:"Doe"}]});

db.inventory.find({$and: [{item:"postcard",qty:45}]}).pretty()   // $or

db.inventory.find({$nor: [{item:"postcard",qty:45}]}).pretty()

db.inventory.find( { qty: 0, status: "D" } );   //and condition

db.inventory.find( { size: { h: 14, w: 21, uom: "cm" } } )  //Equality matches on the embedded document require an exact match, including the field order.

db.inventory.find( { tags: [ "red", "blank" ] } )      //tags field matches the specified array exactly, including the order

db.inventory.find( { }, { item: 1, status: 1 } );    //return the _id, item, and the status fields from all documents in the inventory collection

You do not have to specify the _id field to return the field. It returns by default. To exclude the field, set it to 0 in the projection document. For example, copy and paste the following to return only the item, and the status fields in the matching documents:
db.inventory.find( {}, { _id: 0, item: 1, status: 1 } );

db.customers.find({age:{$lt:200}}).pretty(); //gt,lte,gte

db.customers.find({"address.city":"Boston"});

db.customers.find({age:{$lt:100}}).sort({age:1}).pretty();  //asc

db.customers.find({age:{$lt:100}}).sort({age:-1}).pretty();  //desc  

db.customers.find().count();

db.customers.find({age:{$lt:100}}).sort({age:-1}).limit(1).pretty();

db.mycol.find({},{"title":1,_id:0}).limit(1).skip(1)

db.customers.find().forEach(function(doc){print("Customer Name: " + doc.first_name)});

db.mycol.createIndex({qty:1)
  
db.mycol.getIndexes()
  
db.inventory.dropIndex({qty:1})
  
db.inventory.aggregate([{$group:{_id: "$by_user", num:{$sum:1}}}])
  
db.inventory.aggregate([{$group:{_id: "$by_user", num:{$avg:"$qty"}}}])   //$max, $min
   
db.inventory.aggregate([{$group:{_id: "$by_user", num:{$push:"$qty"}}}])  //Inserts the value to an array in the resulting document.
   
db.inventory.aggregate([{$group:{_id: "$by_user", num:{$addToSet:"$qty"}}}])   //Inserts the value to an array in the resulting document but does not create duplicates.
   
db.inventory.aggregate([{$group:{_id: "$by_user", num:{$first:"$qty"}}}])    //Gets the first document from the source documents according to the grouping. Typically this makes only sense together with some previously applied “$sort”-stage.
   
db.inventory.aggregate([{$group:{_id: "$by_user", num:{$last:"$qty"}}}])    //Gets the last document from the source documents according to the grouping. Typically this makes only sense together with some previously applied “$sort”-stage.

mongod --port 27017 --dbpath "D:\set up\mongodb\data" --replSet rs0
It will start a mongod instance with the name rs0, on port 27017.
Now start the command prompt and connect to this mongod instance.
In Mongo client, issue the command rs.initiate() to initiate a new replica set.
To check the replica set configuration, issue the command rs.conf(). To check the status of replica set issue the command rs.status().

Sharding is the process of storing data records across multiple machines and it is MongoDB's approach to meeting the demands of data growth. As the size of the data increases, a single machine may not be sufficient to store the data nor provide an acceptable read and write throughput. Sharding solves the problem with horizontal scaling. With sharding, you add more machines to support data growth and the demands of read and write operations

mongodump --host tutorialspoint.com --port 27017        //This commmand will backup all databases of specified mongod instance.

mongodump --dbpath /data/db/ --out /data/backup/       //This command will backup only specified database at specified path.

mongodump --collection mycol --db test                   //This command will backup only specified collection of specified database.

mongorestore 											//command restores all of the data from the backup directory.

mongostat    // checks the status of all running mongod instances and return counters of database operations. These counters include inserts, queries, updates, deletes, and cursors. Command also shows when you’re hitting page faults, and showcase your lock percentage. This means that you're running low on memory, hitting write capacity or have some performance issue.

mongotop            //tracks and reports the read and write activity of MongoDB instance on a collection basis. By default, mongotop returns information in each second
 
mongotop 30        //return values every 30 seconds.
