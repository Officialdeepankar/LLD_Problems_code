package OpenClose02.Following;

public class RectangleExtendsShape extends ShapeDefination {

    private Integer l;
    private Integer b;

    public RectangleExtendsShape(Integer l, Integer b) {
        this.l = l;
        this.b = b;
    }

    @Override
    void CalculateArea() {
        System.out.println(l*b);
    }
}
