package pl.edu.agh.to2.model;

import pl.edu.agh.to2.command.Command;
import pl.edu.agh.to2.command.CommandStack;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Serializer {

    public static final String drawingsFolderName = System.getProperty("user.dir") + "/savedDrawings";

    public static String serializeDrawing(CommandStack commandStack) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss").format(new Date());
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(drawingsFolderName + "/" + timeStamp));
        out.writeObject(commandStack);
        out.close();

        return timeStamp;
    }

    public static CommandStack deserializeDrawing(String fileName, PowerpuffGirl powerpuffGirl) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        CommandStack commandStack = (CommandStack) ois.readObject();
        for (Command command : commandStack.getCommands())
            command.setPowerpuffGirl(powerpuffGirl);

        return commandStack;
    }

}
