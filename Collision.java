import java.awt.*;
import java.util.ArrayList;

public class Collision {
    ArrayList<Car> cars;
    ArrayList<Workshop<Volvo240>> workshops;

    public Collision(ArrayList<Car> cars, ArrayList<Workshop<Volvo240>> workshops){
        this.cars = cars;
        this.workshops = workshops;
    }

    public void CollisionLoadCar(){
        for (Car car : cars) {
            for (Workshop<Volvo240> ws : workshops){
                if (car instanceof Volvo240 ){
                    Point P = ws.getPosition();
                    if (car.getX() == P.x && car.getY() == P.y) {
                        ws.load( (Volvo240) car);
                    }
                }
            }
        }
    }
}