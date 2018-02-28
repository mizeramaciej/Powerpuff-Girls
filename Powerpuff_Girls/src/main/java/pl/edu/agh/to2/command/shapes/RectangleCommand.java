package pl.edu.agh.to2.command.shapes;

import pl.edu.agh.to2.command.Command;
import pl.edu.agh.to2.model.PowerpuffGirl;

import static java.lang.Double.parseDouble;

public class RectangleCommand extends Command {
    public static final String NAME = "rectangle";

    public RectangleCommand(PowerpuffGirl powerpuffGirl, String... args) {
        super(powerpuffGirl, args);
    }

    @Override
    public void execute() {
        double a = parseDouble(args.get(0));
        double b = parseDouble(args.get(1));
        drawEdge(a / 2);
        drawEdge(b);
        drawEdge(a);
        drawEdge(b);
        drawEdge(a / 2);

        powerpuffGirl.rotate(270);
    }

    @Override
    public String getNameWithArguments() {
        return String.format("%s %s %s", NAME, args.get(0), args.get(1));
    }

    private void drawEdge(double edgeLength) {
        powerpuffGirl.moveForward(edgeLength);
        powerpuffGirl.rotate(90);
    }
}
