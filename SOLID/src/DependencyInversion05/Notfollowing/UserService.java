package DependencyInversion05.Notfollowing;
// This Rule will not have a GUI to see , because the principal is very obvious and simple
public class UserService {

    //suppose here client wants to connect with both mongodb and postgres .

    static void main(String[] args) {

        Mongodb mg=new Mongodb();
        Postgres pgo = new Postgres();


        // when i want to make connection with postgres i need to call
        pgo.giveConnection();
/*
        when in want to make connection wit Mongodb  , i need to call
        now suppose i want to add other db like db2, cassandra, oracle etc
        then i have to change again and agian in the User service.
         which is breaking open close principal
     */

    }
}
