package pl.edu.agh.to2.command.complex;

import pl.edu.agh.to2.command.Command;
import pl.edu.agh.to2.command.CommandFactory;
import pl.edu.agh.to2.exceptions.UnsupportedCommandException;
import pl.edu.agh.to2.model.PowerpuffGirl;

import java.util.ArrayList;
import java.util.List;

//EXAMPLES
//powtorz 2 [ np 30 powtorz 2 [ np 70 lw 10 powtorz 2 [ square 40 lw 35 ] np 25 ] pw 90 ]
//powtorz 3 [ powtorz 4 [ np 90 pw 90 ] np 100 ]
//powtorz 3 [ powtorz 4 [ rectangle 30 40 pw 90 ] np 90 ]
//powtorz 3 [ powtorz 4 [ rectangle 30 40 powtorz 2 [ circle 20 ws 15 ] pw 90 ] np 90 ]

public class RepeatCommand extends Command {
    public static final String NAME = "powtorz";
    private static final String CLOSING_BRACKET = "]";
    private final Integer numberOfRepeats;
    private List<Command> commands;

    public RepeatCommand(PowerpuffGirl powerpuffGirl, String... arguments) throws UnsupportedCommandException {
        super(powerpuffGirl, arguments);
        numberOfRepeats = Integer.parseInt(arguments[0]);
        commands = buildCommands(args);
    }

    private List<Command> buildCommands(List<String> args) throws UnsupportedCommandException {
        List<Command> commands = new ArrayList<>();
        for (int i = 2; i < args.size() - 1; i++) {
            CommandAndRemainingSize result = getFirstCommand(args.subList(i, args.size()));
            commands.add(result.getCmd());
            i += result.getCommandSize();
        }
        return commands;
    }

    private CommandAndRemainingSize getFirstCommand(List<String> args) throws UnsupportedCommandException {
        CommandFactory commandFactory = new CommandFactory(powerpuffGirl);
        List<String> currentCommand = new ArrayList<>();

        currentCommand.add(args.get(0));

        if (currentCommand.get(0).equals(NAME)) {
            int count = 0;
            int index;
            for (index = 3; index < args.size(); index++) {
                if (args.get(index).equals(NAME)) {
                    count++;
                }
                if (args.get(index).equals(CLOSING_BRACKET)) {
                    if (count > 0) {
                        count--;
                    } else {
                        break;
                    }
                }
            }

            Command repeatCommand = commandFactory.getCommand(args.subList(0, index).toArray(new String[0]));
            return new CommandAndRemainingSize(repeatCommand, index);
        } else {
            int index = 1;
            while (index < args.size() && !isWord(args.get(index))) {
                currentCommand.add(args.get(index));
                index++;
            }
            Command cmd = commandFactory.getCommand(currentCommand.toArray(new String[0]));
            return new CommandAndRemainingSize(cmd, index - 1);
        }
    }

    @Override
    public void execute() {
        for (int i = 0; i < numberOfRepeats; i++) {
            for (Command cmd : commands) {
                cmd.execute();
            }
        }
    }

    @Override
    public String getNameWithArguments() {
        StringBuilder result = new StringBuilder(NAME + " ");
        for (String str : args) {
            result.append(str).append(" ");
        }
        return result.toString();
    }

    @Override
    public void setPowerpuffGirl(PowerpuffGirl powerpuffGirl) {
        super.setPowerpuffGirl(powerpuffGirl);
        for (Command command : commands) {
            command.setPowerpuffGirl(powerpuffGirl);
        }
    }

    private boolean isWord(String arg) {
        for (int i = 0; i < arg.length(); i++) {
            if (!Character.isLetter(arg.charAt(i)))
                return false;
        }
        return true;
    }

    private class CommandAndRemainingSize {
        private Command cmd;
        private Integer commandSize;

        CommandAndRemainingSize(Command cmd, Integer commandSize) {
            this.cmd = cmd;
            this.commandSize = commandSize;
        }

        Command getCmd() {
            return cmd;
        }

        Integer getCommandSize() {
            return commandSize;
        }
    }

}
