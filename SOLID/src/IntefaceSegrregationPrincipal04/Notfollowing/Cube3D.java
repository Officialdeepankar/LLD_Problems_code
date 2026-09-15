package IntefaceSegrregationPrincipal04.Notfollowing;

public class Cube3D extends Shape{

    Integer side=0;

    public Cube3D(Integer side) {
        this.side = side;
    }

    @Override
    double calculateArea() {
        double a = 6*side*side;
        System.out.println("Area of cube is : "+a);
        return a;
    }

    @Override
    double Volume() {
        double v = side*side*side;
        System.out.println("Volume of cube is : "+v);
        return v;
    }
    @Override public String toString(){ return "Cube3D(side=" + side + ")"; }


}
