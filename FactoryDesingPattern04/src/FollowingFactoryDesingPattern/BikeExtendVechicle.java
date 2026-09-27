package FollowingFactoryDesingPattern;

public class BikeExtendVechicle extends Vehicle{
    @Override
    public void start() {
        System.out.println("Bike started");
    }

    @Override
    public void stop() {
        System.out.println("Bike stopped");
    }
}
