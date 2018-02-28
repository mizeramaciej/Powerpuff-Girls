package pl.edu.agh.to2.command.shapes;

import pl.edu.agh.to2.command.Command;
import pl.edu.agh.to2.model.PowerpuffGirl;

import static java.lang.Double.parseDouble;

public class SquareCommand extends Command {
    public static final String NAME = "square";

    public SquareCommand(PowerpuffGirl powerpuffGirl, String... args) {
        super(powerpuffGirl, args);
    }

    @Override
    public void execute() {
        double a = parseDouble(args.get(0));
        drawEdge(a / 2);
        for (int i = 0; i < 3; i++) {
            drawEdge(a);
        }
        drawEdge(a / 2);

        powerpuffGirl.rotate(270);
    }

    @Override
    public String getNameWithArguments() {
        return String.format("%s %s", NAME, args.get(0));
    }

    private void drawEdge(double edgeLength) {
        powerpuffGirl.moveForward(edgeLength);
        powerpuffGirl.rotate(90);
    }
}
