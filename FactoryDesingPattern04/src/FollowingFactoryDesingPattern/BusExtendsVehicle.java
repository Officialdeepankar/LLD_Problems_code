package FollowingFactoryDesingPattern;

public class BusExtendsVehicle extends Vehicle{
    @Override
    public void start() {
        System.out.println("Bus started");
    }

    @Override
    public void stop() {
        System.out.println("Bus stopped");
    }
}
