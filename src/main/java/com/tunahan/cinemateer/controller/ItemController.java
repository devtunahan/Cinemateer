package com.tunahan.cinemateer.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import com.tunahan.cinemateer.model.Movie;
import com.tunahan.cinemateer.model.MyListener;

public class ItemController {

    @FXML
    private Label filmLabel;

    @FXML
    private ImageView filmImg;


    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(movie);
    }

    private Movie movie;
    private MyListener myListener;

    public void setData(Movie m,MyListener myListener) {
        this.movie = m;
        this.myListener = myListener;
        filmLabel.setText(m.getTitle());
        Image image = new Image(getClass().getResourceAsStream(m.getTitleImage()));
        filmImg.setImage(image);
    }
}
