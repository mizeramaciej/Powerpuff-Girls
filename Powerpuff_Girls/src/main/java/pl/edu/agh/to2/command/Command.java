package pl.edu.agh.to2.command;

import pl.edu.agh.to2.model.PowerpuffGirl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public abstract class Command implements Serializable{
    protected transient PowerpuffGirl powerpuffGirl;
    protected final List<String> args;

    public Command(PowerpuffGirl powerpuffGirl, String... args) {
        this.powerpuffGirl = powerpuffGirl;
        this.args = Arrays.asList(args);
    }

    public abstract void execute();

    public abstract String getNameWithArguments();

    public void setPowerpuffGirl(PowerpuffGirl powerpuffGirl) {
        this.powerpuffGirl = powerpuffGirl;
    }
}
