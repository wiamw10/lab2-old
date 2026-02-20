import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;

// This panel represents the animated part of the view with the car images.

public class DrawPanel extends JPanel{
    //list of cars from CarController
    ArrayList<Car> cars;
    ArrayList<Workshop<Volvo240>> workshops;
    //Map that matches cars to respective car image
    Map<Class<? extends Movable>, BufferedImage> images = new HashMap<>();

    //Map for workshop pictures
    Map<Class<? extends Workshop<?>>, BufferedImage> WorkshopImages = new HashMap<>();
    //Map for workshop Points
    Map<Class<? extends Workshop<?>>, Point> workshopPositions = new HashMap<>();

    // Initializes the panel and reads the images
    public DrawPanel(int x, int y, ArrayList<Car> cars, ArrayList<Workshop<Volvo240>> workshops) {
        this.cars = cars;
        this.workshops = workshops;
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.lightGray);

        // Print an error message in case file is not found with a try/catch block
        try {
            //Add car images
            images.put(Volvo240.class, ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Volvo240.jpg")));
            images.put(Saab95.class,   ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Saab95.jpg")));
            images.put(Scania.class,   ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Scania.jpg")));

            //Add workshop images
            WorkshopImages.put(VolvoWorkshop.class, ImageIO.read(DrawPanel.class.getResourceAsStream("pics/VolvoBrand.jpg")));

            //Add workshop points
            workshopPositions.put(VolvoWorkshop.class, new Point(300,0));
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    // This method is called each time the panel updates/refreshes/repaints itself
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Car car : cars){
            BufferedImage image = images.get(car.getClass());
            int x = (int) car.getX();
            int y = (int) car.getY();
            g.drawImage(image, x, y, null);
        }

        for (Workshop<?> ws : workshops){
            BufferedImage img = WorkshopImages.get(ws.getClass());
            Point pos = workshopPositions.get(ws.getClass());
            if(img !=null && pos !=null){
            g.drawImage(img, pos.x, pos.y, null);
        }}
    }}
