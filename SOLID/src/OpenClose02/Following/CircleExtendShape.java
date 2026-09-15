package OpenClose02.Following;

public class CircleExtendShape extends ShapeDefination {

    private Integer radius;

    public CircleExtendShape(Integer radius) {
        this.radius = radius;
    }

    @Override
    void CalculateArea() {
        System.out.println(Math.PI*radius*radius);
    }
}
