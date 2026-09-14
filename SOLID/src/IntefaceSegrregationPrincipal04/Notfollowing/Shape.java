package IntefaceSegrregationPrincipal04.Notfollowing;

public abstract class Shape {

    abstract double calculateArea();


/*
Suppose now i want to add one new method inside area that is volume:
then Circle and Rectangle also needs to implement it which do not have volume they are 2D.

So for these we will use Inteface seggregation principle.


 */

    abstract double Volume();
    public String getName(){ return getClass().getSimpleName(); }
}
