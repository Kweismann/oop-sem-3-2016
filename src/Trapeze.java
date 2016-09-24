/**
 * Created by kawei on 24.09.2016.
 */
import java.awt.*;

public class Trapeze 
{
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

    private Point rotatePoint(Point point){
        Point rotated_point = new Point();
        rotated_point.x = (int)Math.round(pointOfRotate.x + (point.x - pointOfRotate.x) * Math.cos(angle) + (point.y - pointOfRotate.y) * Math.sin(angle));
        rotated_point.y = (int) Math.round(pointOfRotate.y + (point.y - pointOfRotate.y) * Math.cos(angle) - (point.x - pointOfRotate.x) * Math.sin(angle));
        return rotated_point;
    }

    public void rotate(float angle){
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

}
