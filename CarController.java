import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.util.ArrayList;

/*
* This class represents the Controller part in the MVC pattern.
* It's responsibilities is to listen to the View and responds in a appropriate manner by
* modifying the model state and the updating the view.
 */

public class CarController {
    // member fields:

    // The delay (ms) corresponds to 20 updates a sec (hz)
    private final int delay = 50;
    // The timer is started with a listener (see below) that executes the statements
    // each step between delays.
    private Timer timer = new Timer(delay, new TimerListener());

    // The frame that represents this instance View of the MVC pattern
    CarView frame;
    // A list of cars, modify if needed
    ArrayList<Car> cars = new ArrayList<>();
    private Saab95 saab95;
    private Scania scania;
    // workshop volvo cars
    private Workshop<Volvo240> volvoWorkshop = new Workshop<>(10);

    //methods:

    public static void main(String[] args) {
        // Instance of this class
        CarController cc = new CarController();

        // start position for each car
        cc.cars.add(new Volvo240(0,0));
        cc.saab95 = new Saab95(0,100);
        cc.scania = new Scania(0,200);

        cc.cars.add(cc.saab95);
        cc.cars.add(cc.scania);

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

            // with index, we update each car's pos separately, so they move independently
            for (int i = 0; i < cars.size(); i++) {
                Car car = cars.get(i);
                car.move();

                int x = (int) Math.round(car.getX());
                int y = (int) Math.round(car.getY());
                int carWidth    = 100;
                int carHeight   = 60;
                int panelWidth  = frame.drawPanel.getWidth();
                int panelHeight = frame.drawPanel.getHeight();
                boolean collided = false;

                int workshopX = 300;
                int workshopY = 300;
                int workshopWidth = 101;
                int workshopHeight = 96;
                Rectangle workshopP = new Rectangle(workshopX,workshopY,workshopWidth,workshopHeight);
                Rectangle carP  = new Rectangle(x,y,carWidth,carHeight);

                // when volvo car drive into workshop
                if ( carP.intersects(workshopP)){
                    if(car instanceof Volvo240 volvo240){
                        car.stopEngine();
                        volvoWorkshop.load(volvo240);
                        cars.remove(i);
                        i--;
                        continue;
                    } else {
                        // non-volvo car just revert direction
                        car.stopEngine();
                        car.turnRight();
                        car.turnRight();
                        car.startEngine();
                    }
                }
                // left walls
                if (x < 0){
                    x = 0;
                    collided=true;
                }// right walls
                if (x > panelWidth - carWidth ){
                    x = panelWidth - carWidth ;
                    collided=true;
                }// top walls
                if (y < 0){
                    y = 0;
                    collided=true;
                }// bottom walls
                if (y > panelHeight - carHeight ){
                    y = panelHeight - carHeight ;
                    collided=true;
                }
                if (collided){
                    car.stopEngine();
                    car.turnRight();
                    car.turnRight();
                    car.startEngine();
                }
                // update the coordinate in Car
                car.setX(x);
                car.setY(y);
                frame.drawPanel.moveit( i,x, y);
                // repaint() calls the paintComponent method of the panel
                frame.drawPanel.repaint();
                //System.out.println("move");
            }
        }
    }

    // Calls the gas method for each car once
    void gas(int amount) {
        double gas = ((double) amount) / 100;
        for (Car car : cars
                ) {
            car.gas(gas);
            System.out.println("x:" + car.getX());
            System.out.println("y:" + car.getY());
            System.out.println("gas:" + gas);
        }
    }
    void brake(int amount){
        double brake = ((double) amount) / 100;
        for (Car car : cars) {
            car.brake(brake);
            System.out.println("x" +car.getX());
            System.out.println("y" +car.getY());
            System.out.println("brake" + brake);
        }
    }
    void startEngine(){
        for (Car car : cars) {
            car.startEngine();
        }
    }
    void stopEngine(){
        for (Car car : cars) {
            car.stopEngine();
        }
    }
    // specifically for Saab car
    void turboOn(){
        saab95.setTurboOn();
    }
    void turboOff(){
        saab95.setTurboOff();
    }
    // specifically for Scania truck
    void liftBed(double degree){
        scania.raiseFlak(degree);
        System.out.println("raise" +degree);
    }
    void lowerBed(double degree){
        scania.lowerFlak(degree);
        System.out.println("lower" + degree);
    }
    double getScaniaBedAngle(){
        return scania.getFlak();
    }
}
