import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/*
* This class represents the Controller part in the MVC pattern.
* Its responsibilities are to listen to the View and responds in appropriate manner by
* modifying the model state and the updating the view.
 */

public class CarController {
    // The delay (ms) corresponds to 20 updates a sec (hz)
    private final int delay = 50;
    // The timer is started with a listener (see below) that executes the statements
    // each step between delays.
    private Timer timer = new Timer(delay, new TimerListener());

    // The frame that represents this instance View of the MVC pattern
    CarView frame;
    // A list of cars, modify if needed
    ArrayList<Car> cars = new ArrayList<>();

    //A list of workshops
    ArrayList<Workshop<Volvo240>> workshops = new ArrayList<>();

    //methods:
    public static void main(String[] args) {
        // Instance of this class
        CarController cc = new CarController();

        //Create car objects and add them to list
        Volvo240 volvo = new Volvo240();
        volvo.setY(0);
        cc.cars.add(volvo);

        Saab95 saab = new Saab95();
        saab.setY(100);
        cc.cars.add(saab);

        Scania scania = new Scania();
        scania.setY(200);
        cc.cars.add(scania);

        //Create workshops and add to list
        Workshop<Volvo240> volvoWs = new VolvoWorkshop(10, new Point(0, 300));
        cc.workshops.add(volvoWs);

        // Start a new view and send a reference of self
        cc.frame = new CarView("CarSim 1.0", cc);

        // Start the timer
        cc.timer.start();
    }

    /* Each step the TimerListener moves all the cars in the list and tells the
    * view to update its images. Change this method to your needs.
    * */
    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int panelWidth = frame.drawPanel.getWidth();
            int panelHeight = frame.drawPanel.getHeight();
            HitWall collisionLogic = new HitWall(cars, panelWidth,panelHeight);
            Collision collide = new Collision(cars, workshops);

          for (Car car : cars) {
              collisionLogic.CarHitsWall();
              collide.CollisionLoadCar();

              //move the cars
              car.move();
              // repaint() calls the paintComponent method of the panel
                frame.drawPanel.repaint();

               }
            }
        }
    void startAllEngines() {
        for (Car car : cars) {
            car.startEngine();
        }
    }

    void stopAllEngines(){
        for (Car car : cars) {
            car.stopEngine();
        }
    }

    // Calls the break method for each car once
    void brake(int amount) {
        for (Car car : cars) {
            double brake = ((double) amount) / 100;
            car.brake(brake);
        }
    }

    // Calls the gas method for each car once
    void gas(int amount) {
        double gas = ((double) amount) / 100;
       for (Car car : cars
                ) {
            car.gas(gas);
        }
    }

    void stopTurbo(){
        for (Car car : cars){
            if (car instanceof Saab95){
                ((Saab95) car).setTurboOff();
            }
        }
    }
    void startTurbo(){
        for (Car car : cars){
            if (car instanceof Saab95) {
                ((Saab95) car).setTurboOn();
            }
        }
    }
    void liftTheBed(double degrees){
        for (Car car: cars){
            if (car instanceof Scania){
                ((Scania) car).raiseFlatBed(degrees);
            }
        }
    }
    void lowerTheBed(double degree){
        for(Car car: cars){
            if (car instanceof Scania){
                ((Scania) car).lowerFlatBed(degree);
            }
        }
    }
}
