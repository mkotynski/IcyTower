package model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ShipPicker extends VBox {

    private ImageView circleImage;
    private ImageView shipImage;

    private String circleNotChoosen = "/view/resources/shipchooser/numeralX.png";
    private String circleChoosen = "/view/resources/shipchooser/numeral0.png";

    //final static Image circleNot = new Image("view/resources/shipchooser/numeralX.png");
    private SHIP ship;

    private boolean isCircleChoosen;

    public ShipPicker(SHIP ship)
    {
        Image img = new Image(getClass().getResource("/model/resources/numeralX.png").toExternalForm(),30,30,false,false);
        circleImage = new ImageView(img);
        System.out.println(ship.getUrl());
        shipImage = new ImageView(new Image(getClass().getResource(ship.getUrl()).toExternalForm(),50,50, false,false));
        this.ship = ship;
        isCircleChoosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(5);
        this.getChildren().add(circleImage);
        this.getChildren().add(shipImage);
    }

    public SHIP getShip()
    {
        return ship;
    }

    public boolean getIsCircleChoose()
    {
        return isCircleChoosen;
    }

    public void setIsCircleChoosen(boolean isCircleChoosen)
    {
        this.isCircleChoosen = isCircleChoosen;
        String imageToSet = this.isCircleChoosen ? circleChoosen : circleNotChoosen;
        circleImage.setImage(new Image(getClass().getResource(imageToSet).toExternalForm()));
    }
}
