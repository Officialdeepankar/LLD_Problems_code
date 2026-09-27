package FollowingFactoryDesingPattern;

public class VechicleFactory {

    public VechicleFactory() {
    }

    public static Vehicle getVehicle(String Type)throws IllegalArgumentException
    {

        if(Type==null)
        {
            throw  new IllegalArgumentException("type cannot be null");
        }

        else if(Type.equals("Bike"))
        {
            return new BikeExtendVechicle();
        }else if(Type.equals("Bus")){
           return    new BusExtendsVehicle();
        }else if (Type.equals("Car"))
        {
          return  new CarExtendVechicle();
        }

     throw new IllegalArgumentException("The type provided do not match with anything");


    }
}
