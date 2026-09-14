package IntefaceSegrregationPrincipal04.following;

public class Rectangle extends Shape2D{

    private Integer l,b;

    public Rectangle(Integer l, Integer b) {
        this.l = l;
        this.b = b;
    }

    @Override
    double area() {
        double a = (double) l*b;
        System.out.println("Area of rectangle is : "+a);
        return a;
    }
    public Integer getL(){ return l; }
    public Integer getB(){ return b; }
    @Override public String toString(){ return "Rectangle(l=" + l + ",b=" + b + ")"; }
}
