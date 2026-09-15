package IntefaceSegrregationPrincipal04.following;

public class Circle extends Shape2D{
    private Integer  radius;

    public Circle(Integer radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        double area= Math.PI*radius*radius;
        System.out.println("Area of circle is : "+ area);
        return area;
    }
    public Integer getRadius(){ return radius; }
    @Override public String toString(){ return "Circle(r=" + radius + ")"; }
}
