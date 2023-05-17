package Controllers;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;


public class MovementThread extends Thread{
    private double upBorder, downBorder;
    private double rightBorder, leftBorder;
    private int stepX, stepY;
    private ImageView logo;
    private Button startingButton;

    public MovementThread(Button startingButton, ImageView logo) {
        super("consommateur");
        this.upBorder = startingButton.getLayoutY();
        this.downBorder = startingButton.getLayoutY()+ startingButton.getPrefHeight();
        this.leftBorder = startingButton.getLayoutX();
        this.rightBorder = startingButton.getLayoutX() + startingButton.getPrefWidth();
        this.stepX = 1;
        this.stepY = 1; 
        this.logo = logo;
        this.startingButton = startingButton;
    }


    public void run(){
        while(true){
            try{
                Thread.sleep(20);
                move();
            } catch (InterruptedException exception) { }
        }
    }

    public void move(){
        if(logo.getLayoutX() <= leftBorder || logo.getLayoutX() + logo.getFitWidth() >= rightBorder){
            stepX= -stepX;
        }
        if(logo.getLayoutY() <= upBorder || logo.getLayoutY() + logo.getFitHeight() >= downBorder){
            stepY= -stepY;
        }
        logo.setLayoutX(logo.getLayoutX() + stepX);
        logo.setLayoutY(logo.getLayoutY() + stepY);
    }
}
