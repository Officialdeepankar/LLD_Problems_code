package OpenClose02.Following;

import OpenClose02.NotfollowingOpenClose.Shape;

public class Client {
  public  static void main(String args[]) {
        CircleExtendShape c1=new CircleExtendShape(34);
        ShapeDefination r1=new RectangleExtendsShape(4,5);
        c1.CalculateArea();
        r1.CalculateArea();
    }
}
