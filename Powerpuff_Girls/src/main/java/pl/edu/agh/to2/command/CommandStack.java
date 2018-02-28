package pl.edu.agh.to2.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommandStack implements Serializable {
    private final List<Command> commands = new ArrayList<>();

    public void execute(Command command) {
        commands.add(command);
        command.execute();
    }

    public void undo() {
        if (!commands.isEmpty()) {
            commands.remove(commands.size() - 1);

            for (Command command : commands) {
                command.execute();
            }
        }
    }

    public List<Command> getCommands() {
        return commands;
    }
}
