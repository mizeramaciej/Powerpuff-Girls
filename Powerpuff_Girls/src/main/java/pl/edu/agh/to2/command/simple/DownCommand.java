package pl.edu.agh.to2.command.simple;

import pl.edu.agh.to2.command.Command;
import pl.edu.agh.to2.model.PowerpuffGirl;

public class DownCommand extends Command {
    public static final String NAME = "down";

    public DownCommand(PowerpuffGirl powerpuffGirl, String... args) {
        super(powerpuffGirl, args);
    }

    @Override
    public void execute() {
        powerpuffGirl.isDownProperty().setValue(true);
        powerpuffGirl.moveForward(0);
    }

    @Override
    public String getNameWithArguments() {
        return String.format("%s", NAME);
    }
}
