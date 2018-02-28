package pl.edu.agh.to2.controller;

import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import pl.edu.agh.to2.command.Command;
import pl.edu.agh.to2.command.CommandFactory;
import pl.edu.agh.to2.command.CommandStack;
import pl.edu.agh.to2.exceptions.UnsupportedCommandException;
import pl.edu.agh.to2.model.Position;
import pl.edu.agh.to2.model.PowerpuffGirl;
import pl.edu.agh.to2.model.Serializer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Controller {
    private static final String SPACE = " ";
    private static final Color backgroundColorUp = Color.rgb(220, 220, 220);
    private static final Color backgroundColorDown = Color.rgb(175, 175, 175);
    private static final int numberOfCommandsToShow = 10;
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());
    private static final String BAJKA = "Bajka";
    private static final String BOJKA = "Bojka";
    private static final String BRAWURKA = "Brawurka";
    private static final int imageSizeX = 40;
    private static final int imageSizeY = 40;

    private final ImageView imageView = new ImageView();
    private final Parser parser = new Parser();

    private CommandStack commandStack = new CommandStack();
    private PowerpuffGirl powerpuffGirl;
    private GraphicsContext linesGc;
    private GraphicsContext imageGc;
    private GraphicsContext backgroundGc;
    private CommandFactory commandFactory;

    @FXML
    private Canvas linesCanvas;

    @FXML
    private Canvas backgroundCanvas;

    @FXML
    private Canvas imageCanvas;

    @FXML
    private ComboBox<String> commandComboBox;

    @FXML
    private ComboBox<String> choiceGirlComboBox;

    @FXML
    private ComboBox<String> savedDrawingsComboBox;

    @FXML
    void initialize() throws IOException {
        initializeListenerOnEnter();
        initializeLogger();
        initializeGirlChoiceComboBox();
        initializeCommandHistoryComboBox();
        initializeImportDrawingComboBox();

        linesGc = linesCanvas.getGraphicsContext2D();
        imageGc = imageCanvas.getGraphicsContext2D();
        backgroundGc = backgroundCanvas.getGraphicsContext2D();

        setBackground(backgroundColorDown);

        Image image = new Image("file:src/main/resources/images/bajka.png",
                imageSizeX, imageSizeY,
                false, false);
        imageView.setImage(image);

        powerpuffGirl = new PowerpuffGirl();
        addChangePositionListener();
        addChangeAngleListener();
        addChangeIsDownListener();
        addImportDrawingListener();

        commandFactory = new CommandFactory(powerpuffGirl);

        drawImage();
    }

    @FXML
    private void onCommandEntered() {
        String[] separatedWordsCommand = readUserCommand();

        try {
            if (separatedWordsCommand.length != 0 && parser.parse(separatedWordsCommand)) {
                executeUserCommand(commandFactory.getCommand(separatedWordsCommand));
            } else {
                new Alert(Alert.AlertType.ERROR, "Syntax error").showAndWait();
            }
        } catch (UnsupportedCommandException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).showAndWait();
        } catch (NumberFormatException exception) {
            new Alert(Alert.AlertType.ERROR, "You must pass number of repeats").showAndWait();
        }
        clearCommandComboBox();
    }

    @FXML
    private void onSaveClicked() {
        try {
            String timeStamp = Serializer.serializeDrawing(this.commandStack);
            addDrawingFileToComboBox(timeStamp);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Problem with saving a drawing").showAndWait();
        }
    }

    @FXML
    public void undoCommand() {
        powerpuffGirl.resetPosition();
        restoreCanvas();
        commandStack.undo();
    }

    private void initializeListenerOnEnter() {
        commandComboBox.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                onCommandEntered();
            }
        });
    }

    private void initializeImportDrawingComboBox() {
        File folder = new File(Serializer.drawingsFolderName);
        if (!folder.exists()) {
            folder.mkdir();
        }
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        if (listOfFiles.length != 0)
            for (File drawingFile : listOfFiles)
                if (drawingFile.isFile())
                    savedDrawingsComboBox.getItems().add(drawingFile.getName());
    }

    private void initializeLogger() throws IOException {
        FileHandler fh = new FileHandler("logFile.log");
        LOGGER.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
    }

    private void initializeGirlChoiceComboBox() {
        choiceGirlComboBox.getItems().addAll(BAJKA, BOJKA, BRAWURKA);
        choiceGirlComboBox.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            Image image = null;
            switch (newValue) {
                case BAJKA: {
                    image = new Image("file:src/main/resources/images/bajka.png",
                            imageSizeX, imageSizeY,
                            false, false);
                    break;
                }
                case BOJKA: {
                    image = new Image("file:src/main/resources/images/bojka.png",
                            imageSizeX, imageSizeY,
                            false, false);
                    break;
                }
                case BRAWURKA: {
                    image = new Image("file:src/main/resources/images/brawurka.png",
                            imageSizeX, imageSizeY,
                            false, false);
                    break;
                }
            }
            imageView.setImage(image);
        });
    }

    private void initializeCommandHistoryComboBox() {
        commandComboBox.setEditable(true);
    }

    private void addChangePositionListener() {
        powerpuffGirl.positionProperty().addListener((observable, oldValue, newValue) -> {
            logPositions(oldValue, newValue);

            wipeImage();
            drawLine(oldValue, newValue);
            drawImage();
        });
    }

    private void addChangeAngleListener() {
        powerpuffGirl.angleProperty().addListener((observable, oldValue, newValue) -> {
            imageView.setRotate(newValue.doubleValue());

            wipeImage();
            drawImage();
        });
    }

    private void addChangeIsDownListener() {
        powerpuffGirl.isDownProperty().addListener((observable, oldValue, newValue) -> {
            if (powerpuffGirl.isDown())
                setBackground(backgroundColorDown);
            else
                setBackground(backgroundColorUp);
        });
    }

    private void addImportDrawingListener() {
        savedDrawingsComboBox.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            String fileName = Serializer.drawingsFolderName + "/" + newValue;
            try {
                this.commandStack = Serializer.deserializeDrawing(fileName, this.powerpuffGirl);

                updateCanvasAndComboBoxAfterDeserializing();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Problem with deserializing a drawing").showAndWait();
            }
        });
    }

    private String[] readUserCommand() {
        if (commandComboBox.getValue() != null) {
            String userCommand = commandComboBox.getValue();
            LOGGER.log(Level.INFO, "User command: {0}", userCommand);
            return userCommand.split(SPACE);
        } else
            return new String[0];
    }

    private void executeUserCommand(Command command) {
        commandStack.execute(command);

        if (powerpuffGirl.isOutOfWindow(backgroundCanvas.getHeight(), backgroundCanvas.getWidth())) {
            new Alert(Alert.AlertType.ERROR, "Powerpuff girl is outside the window").showAndWait();
            undoCommand();
            return;
        }

        updateCommandHistoryComboBox();
    }

    private void addDrawingFileToComboBox(String timeStamp) {
        savedDrawingsComboBox.getItems().add(timeStamp);
    }

    private void updateCanvasAndComboBoxAfterDeserializing() {
        powerpuffGirl.resetPosition();
        restoreCanvas();
        for (Command cmd : commandStack.getCommands()) {
            cmd.execute();
        }

        updateCommandHistoryComboBox();
    }

    private void setBackground(Color backgroundColor) {
        backgroundGc.setFill(backgroundColor);
        backgroundGc.fillRect(0, 0, backgroundCanvas.getWidth(), backgroundCanvas.getHeight() - 50);
    }

    private void restoreCanvas() {
        linesGc.clearRect(0, 0, linesCanvas.getWidth(), linesCanvas.getHeight() - 50);
    }

    private void clearCommandComboBox() {
        commandComboBox.getSelectionModel().clearSelection();
    }

    private void updateCommandHistoryComboBox() {
        List<Command> commands = commandStack.getCommands();
        int lastIndex = commands.size() - 1;
        commandComboBox.getItems().clear();
        for (int i = 0; i < numberOfCommandsToShow; i++)
            if (i <= lastIndex)
                commandComboBox.getItems().add(commands.get(lastIndex - i).getNameWithArguments());
    }

    private void drawLine(Position old, Position newPosition) {
        if (powerpuffGirl.isDown()) {
            linesGc.strokeLine(old.getX(), old.getY(), newPosition.getX(), newPosition.getY());
        }
    }

    private void drawImage() {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        imageGc.drawImage(imageView.snapshot(params, null),
                powerpuffGirl.getX() - imageSizeX / 2,
                powerpuffGirl.getY() - imageSizeY / 2);
    }

    private void wipeImage() {
        imageGc.clearRect(0, 0, imageCanvas.getWidth(), imageCanvas.getHeight());
    }

    private void logPositions(Position oldValue, Position newValue) {
        LOGGER.log(Level.INFO, String.format("Old position ----> X: %s , Y: %s ", oldValue.getX(), oldValue.getY()));
        LOGGER.log(Level.INFO, String.format("New position ----> X: %s , Y: %s ", newValue.getX(), newValue.getY()));
    }
}
