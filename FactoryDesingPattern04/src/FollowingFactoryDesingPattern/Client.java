package FollowingFactoryDesingPattern;


public class Client {

    public static void main(String[] args) {

        Vehicle bike=   VechicleFactory.getVehicle("Bike");
        bike.start();
        bike.stop();


    }
}
