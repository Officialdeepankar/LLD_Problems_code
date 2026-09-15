package DependencyInversion05.following;
// This Rule will not have a GUI to see , because the principal is very obvious and simple
//
public class Userservice {

    PersistanceLayer mongo =new MongoDb();
    PersistanceLayer oracle= new Oracle();

    /*
     Here we dont have to worry about changing the base code
     any new database class will extend the persistance layer class and implement the save method
     We will treat Persistance layer as rulebook/contract between Higher module "Userservice" and lower module like
     Mongodb class or oracle class.


     */
}
