package Controllers;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


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
                //expand();
                move();
            } catch (InterruptedException exception) { }
        }
    }


    // public void expand(){
    //     if(logo.getLayoutY() + logo.getFitHeight() <= downBorder){
    //         logo.setFitHeight(logo.getFitHeight() + 2);
    //     }
        
    //     if(logo.getLayoutY() >= upBorder){
    //         logo.setLayoutY(logo.getLayoutY() - 1);
    //     }

    //     if(logo.getLayoutX() + logo.getFitWidth() <= rightBorder){
    //         logo.setFitWidth(logo.getFitWidth() + 2);
    //     }
        
    //     if(logo.getLayoutX() >= leftBorder){
    //         logo.setLayoutX(logo.getLayoutY() - 1);
    //     }

    // }

    public void move(){
        if(logo.getLayoutX() <= leftBorder || logo.getLayoutX() + logo.getFitWidth() >= rightBorder){
            stepX= -stepX;
        }


        if(logo.getLayoutY() <= upBorder || logo.getLayoutY() + logo.getFitHeight() >= downBorder){
            stepY= -stepY;
        }

        logo.setLayoutX(logo.getLayoutX() + stepX);
        logo.setLayoutY(logo.getLayoutY() + stepY);

        // for(Wall verticalWall : billiard.verticalWalls){
        //     if(verticalWall.collision(this)){
        //         stepX = -stepX;
        //     }
        // }
        // for(Wall horizontalWall : billiard.horizontalWalls){
        //     if(horizontalWall.collision(this)){
        //         stepY = -stepY;
        //     }
        // }
        // for(Obstacle obstacle : billiard.obstacles){
        //     for(Wall verticalWall : obstacle.verticalWalls){
        //         if(verticalWall.collision(this)){
        //             stepX = -stepX;
        //         }
        //     }
        //     for(Wall horizontalWall : obstacle.horizontalWalls){
        //         if(horizontalWall.collision(this)){
        //             stepY = -stepY;
        //         }
        //     }
        // }
    }
}
