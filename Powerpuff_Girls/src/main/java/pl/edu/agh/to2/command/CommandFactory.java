package pl.edu.agh.to2.command;

import pl.edu.agh.to2.exceptions.UnsupportedCommandException;
import pl.edu.agh.to2.command.complex.RepeatCommand;
import pl.edu.agh.to2.command.shapes.CircleCommand;
import pl.edu.agh.to2.command.shapes.RectangleCommand;
import pl.edu.agh.to2.command.shapes.SquareCommand;
import pl.edu.agh.to2.command.shapes.TriangleCommand;
import pl.edu.agh.to2.command.simple.*;
import pl.edu.agh.to2.model.PowerpuffGirl;

import java.util.Arrays;

public class CommandFactory {
    private PowerpuffGirl powerpuffGirl;

    public CommandFactory(PowerpuffGirl powerpuffGirl) {
        this.powerpuffGirl = powerpuffGirl;
    }

    public Command getCommand(String[] command) throws UnsupportedCommandException {
        String[] args = Arrays.copyOfRange(command, 1, command.length);
        switch (command[0]) {
            case CircleCommand.NAME:
                return new CircleCommand(powerpuffGirl, args);
            case RectangleCommand.NAME:
                return new RectangleCommand(powerpuffGirl, args);
            case SquareCommand.NAME:
                return new SquareCommand(powerpuffGirl, args);
            case TriangleCommand.NAME:
                return new TriangleCommand(powerpuffGirl, args);
            case DownCommand.NAME:
                return new DownCommand(powerpuffGirl);
            case UpCommand.NAME:
                return new UpCommand(powerpuffGirl);
            case MoveBackCommand.NAME:
                return new MoveBackCommand(powerpuffGirl, args);
            case MoveForwardCommand.NAME:
                return new MoveForwardCommand(powerpuffGirl, args);
            case TurnLeftCommand.NAME:
                return new TurnLeftCommand(powerpuffGirl, args);
            case TurnRightCommand.NAME:
                return new TurnRightCommand(powerpuffGirl, args);
            case RepeatCommand.NAME:
                return new RepeatCommand(powerpuffGirl, args);
            default:
                throw new UnsupportedCommandException("Command does not exist");
        }
    }
}
