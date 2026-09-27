package FollowingFactoryDesingPattern;

public class CarExtendVechicle extends Vehicle{
    @Override
    public void start() {
        System.out.println("Car Started");
    }

    @Override
    public void stop() {
        System.out.println("Car stopped ");
    }


}
