//You might notice a lot of imports here.
//You are probably wondering why I didn't just import org.lwjgl.opengl.GL11.*
//Well, I did it as a hint to you.
//OpenGL has a lot of commands, and it can be kind of intimidating.
//This is a list of all the commands I used when I implemented my project.
//Therefore, if a command appears in this list, you probably need it.
//If it doesn't appear in this list, you probably don't.
//Of course, your milage may vary. Don't feel restricted by this list of imports.

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import java.security.Key;
import java.util.Iterator;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

/**
 * @author Brennan Smith
 */
public class StudentLWJGLController implements CS355LWJGLController {

    //This is a model of a house.
    //It has a single method that returns an iterator full of Line3Ds.
    //A "Line3D" is a wrapper class around two Point2Ds.
    //It should all be fairly intuitive if you look at those classes.
    //If not, I apologize.
    private WireFrame model = new HouseModel();
    private boolean perspective = true;
    private Point3D position = new Point3D(0.0, 0.0, 0.0);
    private float angle = 0.0f;

    //This method is called to "resize" the viewport to match the screen.
    //When you first start, have it be in perspective mode.
    @Override
    public void resizeGL() {
        glViewport(0, 0, LWJGLSandbox.DISPLAY_WIDTH, LWJGLSandbox.DISPLAY_HEIGHT);
        setPerspectiveMode();
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        //move to front of house
        resetPosition();
        perspective = true;
    }

    //no implementation needed for lab4
    @Override
    public void update() {

    }

    private void setOrthoMode() {
//        glOrtho(-25, 25, -25, 25, 2, 100);
        perspective = false;
    }

    private void setPerspectiveMode() {
//        gluPerspective(50, LWJGLSandbox.DISPLAY_WIDTH / LWJGLSandbox.DISPLAY_HEIGHT, 1, 200);
        perspective = true;
    }

//    private void updateMatrices() {
//        glMatrixMode(GL_MODELVIEW);
//        glLoadIdentity();
//        glRotatef(angle, 0.0f, 1.0f, 0.0f);
//        glTranslatef((float) position.x, (float) position.y, (float) position.z);
//
//        glMatrixMode(GL_PROJECTION);
//        glLoadIdentity();
//    }

    //This is called every frame, and should be responsible for keyboard updates.
    //An example keyboard event is captured below.
    //The "Keyboard" static class should contain everything you need to finish
    // this up.
    @Override
    public void updateKeyboard() {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            position.x -= Math.sin(Math.toRadians(angle));
            //y should always be zero because were can't look up or down
            position.z += Math.cos(Math.toRadians(angle));
//            updateMatrices();
        } else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            position.x -= Math.sin(Math.toRadians(angle - 90)); //get perpendicular
            //y doesn't change
            position.z += Math.cos(Math.toRadians(angle - 90)); //get perpendicular
//            updateMatrices();
        } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            position.x += Math.sin(Math.toRadians(angle));
            //y should always be zero because were can't look up or down
            position.z -= Math.cos(Math.toRadians(angle));
//            updateMatrices();
        } else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            position.x += Math.sin(Math.toRadians(angle - 90)); //get perpendicular
            //y doesn't change
            position.z -= Math.cos(Math.toRadians(angle - 90)); //get perpendicular
//            updateMatrices();
        } else if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            angle -= 1.0;
            if (angle <= 0.0) {
                angle = 360;
            }
//            updateMatrices();
        } else if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            angle += 1.0;
            if (angle >= 360) {
                angle = 0.0f;
            }
//            updateMatrices();
        } else if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
            position.y -= 1.0;
//            updateMatrices();
        } else if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
            position.y += 1.0;
//            updateMatrices();
        } else if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
            setOrthoMode();
        } else if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
            setPerspectiveMode();
        } else if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
            resetPosition();
        }
    }

    private void resetPosition() {
        position.x = 0;
        position.y = -2.0;
        position.z = -25;
        angle = 0;
    }

    private void setUpMatrix(){
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        if (perspective) {
            gluPerspective(50, LWJGLSandbox.DISPLAY_WIDTH / LWJGLSandbox.DISPLAY_HEIGHT, 1, 350.0f);
        } else {
            glOrtho(-25, 25, -25, 25, 1, 250);
        }

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glRotatef(angle, 0, 1, 0);
        glTranslatef((float)position.x, (float)position.y, (float)position.z);
        //Do your drawing here.
    }
    //This method is the one that actually draws to the screen.
    @Override
    public void render() {
        //This clears the screen.
        glClear(GL_COLOR_BUFFER_BIT);
        setUpMatrix();
        drawHouse();
    }

    private void drawHouse() {
        glBegin(GL_LINES);
        Iterator<Line3D> lines = model.getLines();
        while (lines.hasNext()) {
            Line3D line = lines.next();
            glVertex3d(line.start.x, line.start.y, line.start.z);
            glVertex3d(line.end.x, line.end.y, line.end.z);
        }
        glEnd();
    }

}
