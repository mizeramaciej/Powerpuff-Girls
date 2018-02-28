package pl.edu.agh.to2.command.simple;

import pl.edu.agh.to2.command.Command;
import pl.edu.agh.to2.model.PowerpuffGirl;

import static java.lang.Double.parseDouble;

public class MoveBackCommand extends Command {
    public static final String NAME = "ws";

    public MoveBackCommand(PowerpuffGirl powerpuffGirl, String... args) {
        super(powerpuffGirl, args);
    }

    @Override
    public void execute() {
        powerpuffGirl.moveBackward(parseDouble(args.get(0)));
    }

    @Override
    public String getNameWithArguments() {
        return String.format("%s %s", NAME, args.get(0));
    }
}
