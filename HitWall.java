import java.util.ArrayList;

public class HitWall {
    ArrayList <Car> cars;
    int panelWidth;
    int panelHeight;

    public HitWall(ArrayList<Car> cars, int panelWidth, int panelHeight ) {
        this.cars = cars;
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
    }

    public void CarHitsWall(){
        for (Car car : cars) {

            //Limit car so that it does not go outside frame
            boolean hitWall = false;

            if (car.getX() < 0) {
                car.setX(0) ;
                hitWall = true;
            }
            else if (car.getX() > panelWidth-100) {
                car.setX(panelWidth - 100);
                hitWall = true;
            }

            if (car.getY()< 0) {
                car.setY(0);
                hitWall = true;
            }
            else if (car.getY()> panelHeight - 60) {
                car.setY(panelHeight - 60 );
                hitWall = true;
            }

            if (hitWall) {
                car.stopEngine();
                car.turnRight();
                car.turnRight();
                car.startEngine();

            }

        }
    }
}
