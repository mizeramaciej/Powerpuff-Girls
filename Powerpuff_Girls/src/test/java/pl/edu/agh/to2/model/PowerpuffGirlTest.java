package pl.edu.agh.to2.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PowerpuffGirlTest {
    private PowerpuffGirl powerpuffGirl = new PowerpuffGirl();

    @Test
    public void initialPosition() {
        assertEquals(450, powerpuffGirl.getX(), 1e-6);
        assertEquals(450, powerpuffGirl.getY(), 1e-6);
        assertEquals(0, powerpuffGirl.getAngle(), 1e-6);
        assertTrue(powerpuffGirl.isDown());
    }

    @Test
    public void moveForward() {
        powerpuffGirl.moveForward(100);

        assertEquals(450, powerpuffGirl.getX(), 1e-6);
        assertEquals(350, powerpuffGirl.getY(), 1e-6);
    }

    @Test
    public void moveBackward() {
        powerpuffGirl.moveBackward(100);

        assertEquals(450, powerpuffGirl.getX(), 1e-6);
        assertEquals(550, powerpuffGirl.getY(), 1e-6);
    }

    @Test
    public void rotate() {
        powerpuffGirl.rotate(45);

        assertEquals(45, powerpuffGirl.getAngle(), 1e-6);
    }

    @Test
    public void rotateAndMove() {
        powerpuffGirl.rotate(45);
        powerpuffGirl.moveBackward(50);

        assertEquals(450 + (-1) * Math.sin(Math.toRadians(45)) * 50, powerpuffGirl.getX(), 1e-6);
        assertEquals(450 + Math.cos(Math.toRadians(45)) * 50, powerpuffGirl.getY(), 1e-6);
    }

    @Test
    public void resetPosition() {
        powerpuffGirl.moveBackward(10);
        assertEquals(460, powerpuffGirl.getY(), 1e-6);
        powerpuffGirl.resetPosition();
        assertEquals(450, powerpuffGirl.getX(), 1e-6);
        assertEquals(450, powerpuffGirl.getY(), 1e-6);
        assertEquals(0, powerpuffGirl.getAngle(), 1e-6);
    }
}