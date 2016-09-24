import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.awt.*;
import java.util.Scanner;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

class MOUSE
{
    private float mx;
    private float my;
    public void set(float x, float y){
        this.mx = x;
        this.my = y;
    }
    public float getX(){
        return this.mx;
    }
    public float getY(){
        return this.my;
    }

}

class _point {
    int x;
    int y;
    private boolean status;

    public _point(){

    }

    public _point(int x,int y){
        this.x = x;
        this.y = y;
        status = false;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

public class Main {

    // The window handle
    private long window;

    public void run()
    {
        try {
            init();
            loop();

            // Free the window callbacks and destroy the window
            glfwFreeCallbacks(window);
            glfwDestroyWindow(window);
        } finally
        {
            // Terminate GLFW and free the error callback
            glfwTerminate();
            glfwSetErrorCallback(null).free();
        }
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        int WIDTH = 1024;
        int HEIGHT = 800;

        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in our rendering loop
        });

        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
                window,
                (vidmode.width() - WIDTH) / 2,
                (vidmode.height() - HEIGHT) / 2
        );

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }
    public _point check(Point m1, Point m2, Point m3, Point m4) {
        _point p = new _point();
        float t = NULL;
        try{
            t = (m1.x - m2.x) / (m4.x - m2.x - m3.x + m1.x);
        }
        catch (Exception e){
            return p;
        }
        p.x = (int)(m1.x + (m3.x - m1.x) * t);
        p.y = (int)(m1.y + (m3.y - m1.y)*t);
        if (p.x != m1.x && p.x != m2.x && p.x != m3.x && p.x != m4.x)
            p.setStatus(true);
        return p;
    }
    private void loop() {
        GL.createCapabilities();
        float heidht = 1024.0f;
        float width = 800.0f;
        Point points[] = new Point[4]; for (int i = 0; i < 4; i++) {points[i] = new Point();}
        points[0].x = 100;
        points[0].y = 100;
        points[1].x = 130;
        points[1].y = 170;
        points[2].x = 200;
        points[2].y = 170;
        points[3].x = 250;
        points[3].y = 100;
        Point center = new Point(0,0);
        Trapeze trapeze = new Trapeze(points,center);
        float angle = 1;

        while ( !glfwWindowShouldClose(window) )
        {
            trapeze.rotate(angle);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            glBegin(GL_LINE_STRIP);
            glVertex2d(1.0f,0.0f);
            glVertex2d(-1.0f,0.0f);
            glEnd();
            glBegin(GL_LINE_STRIP);
            glVertex2d(0.0f,-1.0f);
            glVertex2d(0.0f,1.0f);
            glEnd();
//            glBegin(GL_LINE_STRIP);
//            glVertex2d(0.0f,0.0f);
//            glVertex2d(trapeze.getPoint(0).x/heidht, trapeze.getPoint(0).y/width);
//            glEnd();
            glBegin(GL_TRIANGLE_STRIP);
            glVertex2d(trapeze.getPoint(0).x/heidht, trapeze.getPoint(0).y/width);
            glVertex2d(trapeze.getPoint(1).x/heidht, trapeze.getPoint(1).y/width);
            glVertex2d(trapeze.getPoint(2).x/heidht, trapeze.getPoint(2).y/width);
            glVertex2d(trapeze.getPoint(3).x/heidht, trapeze.getPoint(3).y/width);
            glVertex2d(trapeze.getPoint(0).x/heidht, trapeze.getPoint(0).y/width);
            glVertex2d(trapeze.getPoint(1).x/heidht, trapeze.getPoint(1).y/width);
            glEnd();
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public static void main(String[] args)
    {
        new Main().run();
    }

}