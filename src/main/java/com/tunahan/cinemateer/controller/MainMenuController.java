package com.tunahan.cinemateer.controller;


import com.tunahan.cinemateer.dao.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import com.tunahan.cinemateer.model.Movie;
import com.tunahan.cinemateer.model.MyListener;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    private VBox chosenFilmCard;

    @FXML
    private Label movieNameLabel;

    @FXML
    private ImageView filmImg;

    @FXML
    private Label titleLabel;

    @FXML
    private Label directorLabel;

    @FXML
    private Label companyLabel;

    @FXML
    private Label yearLabel;

    @FXML
    private Label runningTimeLabel;

    @FXML
    private Label movieCastLabel;


    @FXML
    private GridPane grid;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private ScrollPane scrollPane;

    private List<Movie> mov = new ArrayList<>();
    private MyListener myListener;

    private WatchlistDAO wDAO;
    private MovieDAO mDAO;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (mov.size() > 0) {
            myListener = new MyListener() {
                @Override
                public void onClickListener(Movie m) {
                    setChoosenMovie(m);
                }
            };
        }

        int column = 0;
        int row = 0;
        try(Connection con = new ConnectionManager("src/main/java/com/tunahan/cinemateer/mw.properties").getCon()) {
            wDAO = new WatchlistDAO(con);
            mDAO = new MovieDAO(con);

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/tunahan/cinemateer/view/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                //itemController.setData(mov.get(i), myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row);

                setDimensions();
                GridPane.setMargin(anchorPane, new Insets(10));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setDimensions() {
        grid.setMinWidth(Region.USE_COMPUTED_SIZE);
        grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
        grid.setMaxWidth(Region.USE_COMPUTED_SIZE);

        grid.setMinHeight(Region.USE_COMPUTED_SIZE);
        grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
        grid.setMaxHeight(Region.USE_COMPUTED_SIZE);

    }
    private void setChoosenMovie(Movie m) {
        movieNameLabel.setText(m.getTitle());
        Image image = new Image(getClass().getResourceAsStream(m.getTitleImage()));
        filmImg.setImage(image);
    }

}

