import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

// This panel represents the animated part of the view with the car images.

public class DrawPanel extends JPanel{
    // generalize data
    private class CarData{
        BufferedImage image;
        Point position;

        CarData(BufferedImage image, Point position){
            this.image = image;
            this.position = position;
        }
        /** // cars' image size , might be useful later
        int getWidth(){
            return image.getWidth();
        }
        int getHeight(){
            return image.getHeight();
        }*/
    }
    private List<CarData> carData = new ArrayList<>();
    private BufferedImage volvoWorkshopImage;
    private Point volvoWorkshopPoint = new Point(300,300);

    // TODO: Make this general for all cars
    void moveit(int index,int x,int y){
        if(index<0 || index >= carData.size())  return;
        carData.get(index).position.setLocation(x,y);
    }
    // Initializes the panel and reads the images
    public DrawPanel(int x, int y) {
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.darkGray);
        // Print an error message in case file is not found with a try/catch block
        try {
            // You can remove the "pics" part if running outside of IntelliJ and
            // everything is in the same main folder.
            // volvoImage = ImageIO.read(new File("Volvo240.jpg"));

            // Rememember to rightclick src New -> Package -> name: pics -> MOVE *.jpg to pics.
            // if you are starting in IntelliJ.

            // cars
            carData.add(new CarData(ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Volvo240.jpg")),
                    new Point(0,0)));
            carData.add(new CarData(ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Saab95.jpg")),
                    new Point(0,100)));
            carData.add(new CarData(ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Scania.jpg")),
                    new Point(0,200)));

            // workshop
            volvoWorkshopImage = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/VolvoBrand.jpg"));
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
    // This method is called each time the panel updates/refreshes/repaints itself
    // TODO: Change to suit your needs.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(CarData car : carData){
            g.drawImage(car.image, car.position.x, car.position.y,null);
        }
        g.drawImage(volvoWorkshopImage, volvoWorkshopPoint.x, volvoWorkshopPoint.y, null);
    }
}
