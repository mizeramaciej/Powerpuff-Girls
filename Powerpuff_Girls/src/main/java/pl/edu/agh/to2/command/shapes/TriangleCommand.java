package pl.edu.agh.to2.command.shapes;

import pl.edu.agh.to2.command.Command;
import pl.edu.agh.to2.model.PowerpuffGirl;

import static java.lang.Double.parseDouble;

public class TriangleCommand extends Command {
    public static final String NAME = "triangle";

    public TriangleCommand(PowerpuffGirl powerpuffGirl, String... args) {
        super(powerpuffGirl, args);
    }

    @Override
    public void execute() {
        powerpuffGirl.rotate(150);
        for (int i = 0; i < 3; i++) {
            drawEdge(parseDouble(args.get(0)));
        }

        powerpuffGirl.rotate(210);
    }

    @Override
    public String getNameWithArguments() {
        return String.format("%s %s", NAME, args.get(0));
    }

    private void drawEdge(double edgeLength) {
        powerpuffGirl.moveForward(edgeLength);
        powerpuffGirl.rotate(120);
    }
}
