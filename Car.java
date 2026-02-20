import Interface.Movable;

import java.awt.*;

@SuppressWarnings("ALL")
public abstract class Car implements Movable {

    // Private variables
    private int nrDoors; // Number of doors on the car
    private double enginePower; // Engine power of the car
    private double currentSpeed; // The current speed of the car
    private Color color; // Color of the car
    private String modelName; // The car model name
    private boolean EngineOn = false;

    //All possible directions
    private enum Direction{
        UP,
        DOWN,
        RIGHT,
        LEFT
    }
    //variables for moveable
    private Direction direction = Direction.RIGHT;
    private double x,y;

    // panel's width and height uses in method collision

    //Constructor
    public Car(int nrDoors, double enginePower, Color color,String modelName,double x,double y)
    {   this.nrDoors = nrDoors;
        this.enginePower = enginePower;
        this.color = color;
        this.modelName = modelName;
        this.x = x;
        this.y = y;
        stopEngine();
    }

    //Public getters
    public int getNrDoors(){
        return nrDoors;
    }
    public double getEnginePower(){
        return enginePower;
    }
    public double getCurrentSpeed(){
        return currentSpeed;
    }
    public Color getColor(){
        return color;
    }
    public double getX() {return x; }
    public double getY(){return y;}
    public String getmodelName() {return modelName;}

    public Direction getDirection(){return direction;}

    //public setter
    public void setColor(Color clr){
        color = clr;
    }
    public void setX(double x) {this.x = x;}
    public void setY(double y){this.y = y;}

    //Engine (public)
    public void startEngine(){
        EngineOn=true;
        currentSpeed = 0.1;
    }
    public void stopEngine(){
        EngineOn=false;
        currentSpeed = 0;
    }

    //Speed (private)
    private void incrementSpeed(double amount){
        currentSpeed = getCurrentSpeed() + speedFactor() * amount;
    }
    private void decrementSpeed(double amount){
        currentSpeed = getCurrentSpeed() - speedFactor() * amount;
    }

    //Volvo and Saab have different way to calculate their speedFactor
    protected abstract double speedFactor();

    //Gas and brake (Public) needs to be validated
    public void gas(double amount){
        if(!EngineOn){return;}
        if (amount >= 0 && amount <= 1)
        {   incrementSpeed(amount);
            if (currentSpeed > enginePower)
            {   currentSpeed=enginePower;}
        }
        else { throw new IllegalArgumentException();}
    }

    public void brake(double amount){
        if (amount >= 0 && amount <= 1)
        { decrementSpeed(amount);
            if (currentSpeed < 0)
                currentSpeed = 0;}
        else { throw new IllegalArgumentException();}
    }
    // in java swing y-axis is flipped zero-start-from-top-to-bottom
    /**private void collision(){
        if (x<=0) direction = Direction.RIGHT;      // left walls
        if (y<=0) direction = Direction.DOWN;       // top walls
        if (x>= width) direction = Direction.LEFT;  // right walls
        if (y>= height) direction = Direction.UP;   // bottom walls
    }*/

    //implement the methods for Interface.Movable
    @Override
    public void move() {
        switch (direction){
            case UP -> y+=currentSpeed;
            case DOWN -> y-=currentSpeed;
            case RIGHT -> x+=currentSpeed;
            case LEFT -> x-=currentSpeed;
        }
        //collision();
    }
    @Override
    public void turnLeft() {
        switch (direction) {
            case UP -> direction = Direction.LEFT;
            case LEFT -> direction = Direction.DOWN;
            case DOWN -> direction = Direction.RIGHT;
            case RIGHT -> direction = Direction.UP;
        }
    }
    @Override
    public void turnRight() {
        switch(direction) {
            case UP -> direction=Direction.RIGHT;
            case RIGHT -> direction=Direction.DOWN;
            case DOWN -> direction = Direction.LEFT;
            case LEFT -> direction = Direction.UP;
        }
    }

}
