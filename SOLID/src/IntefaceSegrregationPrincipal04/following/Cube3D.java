package IntefaceSegrregationPrincipal04.following;

public class Cube3D extends Shape3D{

    private Integer side;

    public Cube3D(Integer side) { this.side = side; }
    public Cube3D(){ this.side = 0; }

    @Override
    double area() {
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
    public Integer getSide(){ return side; }
    @Override public String toString(){ return "Cube3D(side=" + side + ")"; }
}
