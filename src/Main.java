import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.awt.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

class MOUSE

{
    private Point position;

    public void setPosition(Point p){this.position = p;}

    public Point getPosition(){
        return this.position;
    }

}

public class Main {

    // The window handle
    private long window;

    public void run(int WIDTH, int HEIGHT)
    {
        try
        {
            init(WIDTH, HEIGHT);
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




    private void init(int WIDTH, int HEIGHT)
    {
        GLFWErrorCallback.createPrint(System.err).set();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        Point points[] = new Point[4]; for (int i = 0; i < 4; i++) {points[i] = new Point();}
        points[0].x = 100; points[0].y = 100; points[1].x = 200;points[1].y = 100;points[2].x = 200;points[2].y = 200;points[3].x = 100;points[3].y = 200;

        MOUSE mouse = new MOUSE();
        Point center = new Point(0,0);
        Trapeze trapeze = new Trapeze(points,center);
        // Setup a mouse position callback.
        glfwSetCursorPosCallback(window, new GLFWCursorPosCallback(){
            @Override
            public void invoke(long window, double xpos, double ypos) {
                System.out.print("SCAN x: " + xpos + " y: " + ypos + "\n");
                mouse.setPosition(new Point((int)xpos, (int)ypos));
         }
        });

        // Setup a mouse key callback.
        glfwSetMouseButtonCallback(window, (new GLFWMouseButtonCallback() {

            @Override
            public void invoke(long window, int button, int action, int mods) {
                if(button == 0) {
                    if(action == GLFW_PRESS) {
                        System.out.print("x: " + mouse.getPosition().x + " y: " + mouse.getPosition().y + "\n");
                        trapeze.setCenter(mouse.getPosition());
                    }
                }
            }
        }));

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) ->
        {
                    if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS)
                        glfwSetWindowShouldClose(window, true);
                    if (key == GLFW_KEY_Q)
                        myrotate(trapeze, 1);
                    if (key == GLFW_KEY_E)
                        myrotate(trapeze, -1);
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
        loop(WIDTH, HEIGHT, trapeze);
    }


    public void myrotate(Trapeze trapeze, int x)
    {
        trapeze.rotate(x);
    }

    private void loop(int WIDTH, int HEIGHT, Trapeze trapeze) {
        GL.createCapabilities();

        while ( !glfwWindowShouldClose(window) )
        {

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            glBegin(GL_LINE_STRIP);
            glVertex2d(1.0f,0.0f);
            glVertex2d(-1.0f,0.0f);
            glEnd();
            glBegin(GL_LINE_STRIP);
            glVertex2d(0.0f,-1.0f);
            glVertex2d(0.0f,1.0f);
            glEnd();
            glBegin(GL_TRIANGLE_STRIP);
            glVertex2d((float)trapeze.getPoint(0).x/HEIGHT * 2, (float)trapeze.getPoint(0).y/WIDTH * 2);
            glVertex2d((float)trapeze.getPoint(1).x/HEIGHT * 2, (float)trapeze.getPoint(1).y/WIDTH * 2);
            glVertex2d((float)trapeze.getPoint(2).x/HEIGHT * 2, (float)trapeze.getPoint(2).y/WIDTH * 2);
            glVertex2d((float)trapeze.getPoint(3).x/HEIGHT * 2, (float)trapeze.getPoint(3).y/WIDTH * 2);
            glVertex2d((float)trapeze.getPoint(0).x/HEIGHT * 2, (float)trapeze.getPoint(0).y/WIDTH * 2);
            glVertex2d((float)trapeze.getPoint(1).x/HEIGHT * 2, (float)trapeze.getPoint(1).y/WIDTH * 2);
            glEnd();
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public static void main(String[] args)
    {
        int WIDTH = 1000;
        int HEIGHT = 1000;
        new Main().run(WIDTH, HEIGHT);
    }

}