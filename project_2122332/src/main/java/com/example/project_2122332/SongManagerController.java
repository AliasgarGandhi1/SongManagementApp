package com.example.project_2122332;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SongManagerController implements Initializable {

    @FXML private Label lblCurrentFile;
    @FXML private AnchorPane scenePane;
    @FXML private Button btnMove, btnDelete;
    @FXML private TextField txtTitle, txtArtist, txtDuration, txtSource, txtDestination, txtSongId;
    @FXML private TableView<Song> tblSong;
    @FXML private TableColumn<Song, Integer> ID;
    @FXML private TableColumn<Song, String> Title;
    @FXML private TableColumn<Song, String> Artist;
    @FXML private TableColumn<Song, Integer> Duration;
    Stage stage;
    File selectedFile;
//    File newFile;
    int lengthOfFile;

    @FXML
    public void onExitButtonClick(ActionEvent evt)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("You are about to Exit!!");
        alert.setContentText("Do you want to save before Exit?");

        if(alert.showAndWait().get() == ButtonType.OK)
        {
            stage = (Stage) scenePane.getScene().getWindow();
            stage.close();
        }
    }

    public void onLoadButtonClick(ActionEvent evt)
    {
        File dataDirectory = new File("./Data");
        alignTableData();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        fileChooser.setInitialDirectory(dataDirectory);

        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            lblCurrentFile.setText("Current File Path: "+selectedFile);
            lengthOfFile = SongLoader.lengthOfFile(selectedFile.toString());
            ObservableList<Song> listOfSongs = FXCollections.observableArrayList(SongLoader.loadCSV(selectedFile.toString()));
            tblSong.setItems(listOfSongs);
            tblSong.refresh();
            btnDelete.setDisable(false);
            btnMove.setDisable(false);
        }
    }

    private void alignTableData()
    {
        ID.setStyle( "-fx-alignment: CENTER;");
        Title.setStyle( "-fx-alignment: CENTER;");
        Artist.setStyle( "-fx-alignment: CENTER;");
        Duration.setStyle( "-fx-alignment: CENTER;");
    }

    public void onMoveButtonClick(ActionEvent evt)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        int sourceSongId=0, destinationSongId=0;
        boolean isValidID=false, isValidValue = true;

        SongManagerController smc = new SongManagerController();
        isValidValue = smc.checkValidValue(txtSource.getText());
        isValidValue = smc.checkValidValue(txtDestination.getText());

        if (isValidValue)
        {
            sourceSongId = Integer.parseInt(txtSource.getText());
            destinationSongId = Integer.parseInt(txtDestination.getText());
            if((sourceSongId<1 || sourceSongId>lengthOfFile) || (destinationSongId<1 || destinationSongId>lengthOfFile))
            {
                alert.setTitle("Invalid Index");
                alert.setContentText("Source Song ID and Destination Song ID Must be between 1 and "+lengthOfFile);
                if(alert.showAndWait().get() == ButtonType.OK) {
                    txtSource.requestFocus();
                    txtSource.setText("");
                    txtDestination.setText("");
                    isValidID = false;
                }
            }
            else
                isValidID=true;
        }
        if (isValidValue && isValidID)
        {
            if(SongLoader.rearrangeCSVData(sourceSongId, destinationSongId, selectedFile.toString())) {
                alert.setTitle("Successful");
                alert.setContentText("Record Replaced");
                alert.showAndWait();
            }
        }
    }

    public void onDeleteButtonClick(ActionEvent evt)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        int songId = 0;
        boolean isValidID=false;
        SongManagerController smc = new SongManagerController();

        if(smc.checkValidValue(txtSongId.getText()))
        {
            songId = Integer.parseInt(txtSongId.getText());
            if(songId<1 || songId>lengthOfFile)
            {
                alert.setTitle("Invalid Index");
                alert.setContentText("Song ID Must be between 1 and "+lengthOfFile);
                if(alert.showAndWait().get() == ButtonType.OK) {
                    txtSongId.requestFocus();
                    txtSongId.setText("");
                    isValidID = false;
                }
            }
            else
                isValidID=true;
        }

        if(isValidID)
        {
            if(SongLoader.deleteCSVData(songId, selectedFile.toString()))
            {
                alert.setTitle("Successful");
                alert.setContentText("Record Deleted");
                alert.showAndWait();
            }

        }
    }

    public void onSaveButtonClick(ActionEvent evt)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Data");
        alert.setContentText("Please Enter Data in Textbox.");
        if (txtTitle.getText() == "" || txtArtist.getText() == "" || txtDuration.getText() == "") {
            if(alert.showAndWait().get() == ButtonType.OK){
                txtTitle.requestFocus();
            }
        }
        else {
            File dataDirectory = new File("./Data");
            alignTableData();
            FileChooser saveFileChooser = new FileChooser();
            saveFileChooser.setTitle("Save File");
            saveFileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                    new FileChooser.ExtensionFilter("All Files", "*.*"));
            saveFileChooser.setInitialDirectory(dataDirectory);

            selectedFile = saveFileChooser.showSaveDialog(null);
            if (selectedFile != null) {
                lblCurrentFile.setText("Current File Path: "+selectedFile);
                lengthOfFile = SongLoader.lengthOfFile(selectedFile.toString()); //Return the length of file.
                btnMove.setDisable(false);

                Song song = new Song(txtTitle.getText(), txtArtist.getText(), Integer.parseInt(txtDuration.getText()));

                if(!SongLoader.saveCSVData(song, selectedFile.toString(), 0))
                {
                    SongLoader.createCSV(selectedFile.toString());
                    SongLoader.saveCSVData(song, selectedFile.toString(), 1);
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Song> list= FXCollections.observableArrayList();
        tblSong.setItems(list);

        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        Artist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        Duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
    }

    private boolean checkValidValue(String value)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        try {

            alert.setTitle("Invalid Data");

            if(value == "")
            {
                alert.setContentText("Please Enter Data in Textbox.");
                if(alert.showAndWait().get() == ButtonType.OK){
                    return false;
                }
            }

            alert.setContentText("Please Enter integer data (Number).");
            for (int i=0; i<value.length(); i++) {
                if (value.charAt(i) < 48 || value.charAt(i) > 57) {
                    if (alert.showAndWait().get() == ButtonType.OK) {
                        txtSongId.setText("");
                        return false;
                    }
                    break;
                }
            }

        }
        catch (Exception e)
        {
            System.out.println("Something Wrong Happen.");
        }
        return true;
    }
}