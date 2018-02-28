package pl.edu.agh.to2.command.simple;

import pl.edu.agh.to2.command.Command;
import pl.edu.agh.to2.model.PowerpuffGirl;

import static java.lang.Double.parseDouble;

public class TurnRightCommand extends Command {
    public static final String NAME = "pw";

    public TurnRightCommand(PowerpuffGirl powerpuffGirl, String... args) {
        super(powerpuffGirl, args);
    }

    @Override
    public void execute() {
        powerpuffGirl.rotate(parseDouble(args.get(0)));
    }

    @Override
    public String getNameWithArguments() {
        return String.format("%s %s", NAME, args.get(0));

    }
}
