package model;

public enum SHIP {
    BLUE("/view/resources/shipchooser/playerShip1_blue.png"),
    GREEN("/view/resources/shipchooser/playerShip1_green.png"),
    ORANGE("/view/resources/shipchooser/playerShip1_orange.png"),
    RED("/view/resources/shipchooser/playerShip1_red.png");

    private String urlShip;

    private SHIP(String urlShip)
    {
        this.urlShip = urlShip;
    }

    public String getUrl()
    {
        return this.urlShip;
    }
}
