package IntefaceSegrregationPrincipal04.Notfollowing;

public class Rectangle extends Shape{
   private Integer l,b;

    public Rectangle(Integer l, Integer b) {
        this.l = l;
        this.b = b;
    }

    @Override
    double calculateArea() {
        double a = l*b;
        System.out.println("Area of rectangle is : "+a);
        return a;
    }

    @Override
    double Volume() {
        //System.out.println("Rectangle cannot have a volume : ");
        throw new IllegalArgumentException("Rectangle cannot have volume as it is 2D");
    }
    public Integer getL(){ return l; }
    public Integer getB(){ return b; }
    @Override public String toString(){ return "Rectangle(l=" + l + ",b=" + b + ")"; }
}
