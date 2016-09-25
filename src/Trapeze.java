/**
 * Created by kawei on 24.09.2016.
 */
import java.awt.*;



public class Trapeze 
{
    public boolean DEBUG = false;
    private Point defaultPoints[];
    private float angle;
    private Point currentPoints[];
    private Point pointOfRotate;

    public Trapeze(Point defaultPoints[], Point center){
        this.defaultPoints = defaultPoints;
        currentPoints = defaultPoints;
        angle = 0;
        this.pointOfRotate = center;
    }

    private Point rotatePoint(Point point)
    {
        System.out.print("rotate x: "+ pointOfRotate.x + " y: " + pointOfRotate.y);
        Point rotated_point = new Point();
        rotated_point.x = (int)Math.ceil(pointOfRotate.x + (point.x - pointOfRotate.x) * Math.cos(angle) + (point.y - pointOfRotate.y) * Math.sin(angle));
        if(DEBUG)
        System.out.print("new x: " + rotated_point.x + "\n");
        rotated_point.y = (int) Math.ceil(pointOfRotate.y + (point.y - pointOfRotate.y) * Math.cos(angle) - (point.x - pointOfRotate.x) * Math.sin(angle));
        if(DEBUG)
        System.out.print("new y: " + rotated_point.y + "\n");
        return rotated_point;
    }

    public void rotate(float angle)
    {
        if(DEBUG)
            System.out.print("angle: " +  this.angle + "\n");
        this.angle += angle > 0 ? Math.PI / 5000 : - Math.PI / 5000;
        currentPoints[0] = rotatePoint(defaultPoints[0]);
        currentPoints[1] = rotatePoint(defaultPoints[1]);
        currentPoints[2] = rotatePoint(defaultPoints[2]);
        currentPoints[3] = rotatePoint(defaultPoints[3]);
    }
    public Point getPoint(int x)
    {
        return this.currentPoints[x];
    }
    public void setCenter(Point x){this.pointOfRotate = x;}
}
