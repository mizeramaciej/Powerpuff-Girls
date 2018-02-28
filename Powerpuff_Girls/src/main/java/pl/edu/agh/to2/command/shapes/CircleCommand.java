package pl.edu.agh.to2.command.shapes;

import pl.edu.agh.to2.command.Command;
import pl.edu.agh.to2.model.PowerpuffGirl;

import static java.lang.Double.parseDouble;

public class CircleCommand extends Command {
    public static final String NAME = "circle";

    public CircleCommand(PowerpuffGirl powerpuffGirl, String... args) {
        super(powerpuffGirl, args);
    }

    @Override
    public void execute() {
        double edgeLength = (2 * Math.PI * parseDouble(args.get(0))) / 360;
        powerpuffGirl.rotate(90);
        for (int i = 0; i < 360; i++) {
            drawEdge(edgeLength);
        }
        powerpuffGirl.rotate(270);
    }

    @Override
    public String getNameWithArguments() {
        return String.format("%s %s", NAME, args.get(0));
    }

    private void drawEdge(double edgeLength) {
        powerpuffGirl.moveForward(edgeLength);
        powerpuffGirl.rotate(1);
    }
}