package IntefaceSegrregationPrincipal04.Notfollowing;

public class Circle extends Shape{

    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double calculateArea() {
        double area= Math.PI*radius*radius;
        System.out.println("Area of circle is : "+ area);
        return area;
    }

    @Override
    double Volume() {
        throw new IllegalArgumentException("Circle cannot have volume as it is 2D");
    }
    public double getRadius(){ return radius; }
    @Override public String toString(){ return "Circle(r=" + radius + ")"; }
}
