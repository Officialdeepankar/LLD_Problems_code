package OpenClose02.NotfollowingOpenClose;

import java.util.Scanner;

public class Shape {
    private String shapetype;

    public Shape(String shapetype) {
        this.shapetype = shapetype;
    }

    public void CalculateArea(String type)
    {

        if(type.equals("Circle"))
        {

            Scanner sc=new Scanner(System.in);
            System.out.println("provide radius");
            Integer rad =sc.nextInt();
            System.out.println(Math.PI*rad*rad);

        }else if (type.equals("Rectangle"))
        {

            Scanner sc=new Scanner(System.in);
            System.out.println("provide lenght and breadht");
            Integer lenght=sc.nextInt();
            Integer breadht=sc.nextInt();
            System.out.println(lenght*breadht);

        }



    }

}
