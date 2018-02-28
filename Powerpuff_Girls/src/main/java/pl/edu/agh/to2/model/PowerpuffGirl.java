package pl.edu.agh.to2.model;

import javafx.beans.property.*;

import java.io.Serializable;

public class PowerpuffGirl implements Serializable{
    private ObjectProperty<Position> positionProperty;
    private DoubleProperty angle;
    private BooleanProperty isDown;

    public PowerpuffGirl() {
        this.positionProperty = new SimpleObjectProperty<>(new Position(450, 450));
        this.angle = new SimpleDoubleProperty(0);
        this.isDown = new SimpleBooleanProperty(true);
    }

    public void moveForward(double distance) {
        double x = positionProperty.get().getX() + (Math.sin(Math.toRadians(angle.getValue())) * distance);
        double y = positionProperty.get().getY() + (-1 * Math.cos(Math.toRadians(angle.getValue())) * distance);
        positionProperty.setValue(new Position(x, y));
    }

    public void moveBackward(double distance) {
        double x = positionProperty.get().getX() + (-1 * Math.sin(Math.toRadians(angle.getValue())) * distance);
        double y = positionProperty.get().getY() + (Math.cos(Math.toRadians(angle.getValue())) * distance);
        positionProperty.setValue(new Position(x, y));
    }

    public void rotate(double dAngle) {
        this.angle.setValue(angle.getValue() + dAngle);
    }

    public boolean isOutOfWindow(double height, double width) {
        return getY() < 0 || getY() > height || getX() < 0 || getX() > width;
    }

    public void resetPosition() {
        positionProperty.setValue(new Position(450, 450));
        angle.setValue(0);
        isDown.setValue(true);
    }

    public double getAngle() {
        return angle.getValue();
    }

    public double getX() {
        return positionProperty.getValue().getX();
    }

    public double getY() {
        return positionProperty.getValue().getY();
    }

    public boolean isDown() {
        return isDown.getValue();
    }

    public ObjectProperty<Position> positionProperty() {
        return positionProperty;
    }

    public DoubleProperty angleProperty() {
        return angle;
    }

    public BooleanProperty isDownProperty() {
        return isDown;
    }
}
