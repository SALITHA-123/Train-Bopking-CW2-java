import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;




public class TrainStation extends Application {
    public static final int PASSENGER_CAPACITY = 42;                                    //Creating Main Array
    private static ArrayList<Passenger> waitingRoom = new ArrayList<>();
    private static ArrayList<Passenger> trainQueue = new ArrayList<>();                 //Creating Tempory Arrays
    Passenger passengerObj= new Passenger();
    PassengerQueue passengerQueueObj= new PassengerQueue();


    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String[] mainArray = new String[PASSENGER_CAPACITY];                                   //Creating Arrays
        Scanner input = new Scanner(System.in);
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println("!!!!!!!!>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<!!!!!!!!!!");
            System.out.println("!!!..Welcome To Denuwara Manike Intercity Train Seats Booking System..!!!\n .....Please Select an option According to your Requirement..... ");
            System.out.println("!!!!!!!!>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<!!!!!!!!!!");
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println("");
            System.out.println("Enter 1 to Load Data from Colombo to Badulla :");          //Select the Trip You Want Load Data
            System.out.println("Enter 2 to  Load Data from Badulla to Colombo :");

        BufferedReader fileReviewer = null;
        try {
            int i = 0;
            try {
                Scanner sc=new Scanner(System.in);
                int select1 = sc.nextInt();
                if (select1 == 1){
                    String strDirectory;
                    fileReviewer = new BufferedReader(new FileReader("E:\\Trip1.txt"));        //Load Data form trip1 text file
                    while ((strDirectory = fileReviewer.readLine()) != null) {

                        String name = strDirectory.substring(25,strDirectory.length());
                        if (!"null".equals(name)) {
                            mainArray[i] = name;                            //Loading Data in to main array
                        }
                        i++;
                    }
                }else if(select1 == 2){
                    String strDirectory;
                    fileReviewer = new BufferedReader(new FileReader("E:\\Trip2.txt"));     //Load Data form trip2 text file
                    while ((strDirectory = fileReviewer.readLine()) != null) {

                        String name = strDirectory.substring(25,strDirectory.length());
                        if (!"null".equals(name)) {
                            mainArray[i] = name;                            //Loading Data in to main array
                        }
                        i++;
                    }
                }else{
                    System.out.println("Invalid Input....!!!!!\n Please Reenter");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileReviewer != null)
                        fileReviewer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception e2) {
            System.out.println("Invalid Input.....!!!!!");
        }
        for (int i=0; i<PASSENGER_CAPACITY; i++) {
            if (mainArray[i] != null) {
                Passenger passenger = new Passenger();                    //Loading From Main Array
                passenger.setName(mainArray[i]);
                passenger.setSeat(i);
                waitingRoom.add(passenger);
            }
        }
            System.out.println("Loading Completed...!!!!");
            System.out.println("");


        menu:
        while (true) {                                                              //Create Main Menu to Display in Console
            System.out.println("Enter \"A\" to Add a Customer:");
            System.out.println("Enter \"V\" to View Train Queue:");
            System.out.println("Enter \"D\" to Delete Customer from Seats:");
            System.out.println("Enter \"S\" to Save Program data:");
            System.out.println("Enter \"L\" to Load Program Data from File:");
            System.out.println("Enter \"R\" to Run Simulation Details:");
            System.out.println("Enter \"Q\" to Quit:");

            System.out.println("Select an option According to your Requirement :");
            String option = input.next();
            switch (option) {                                              //Select an Options.........

                case "A":                                     // To Add New Customer to Queue
                case "a":
                    addPassenger();
                    break ;
                case "V":                                     // To View Train Queue
                case "v":
                    viewAllseats();
                    break ;
                case "D":                                     // To Delete Passenger from Queue
                case "d":
                    deletePassenger();
                    break ;

                case "S":                                    // To Save Data to File
                case "s":
                    storeQueuedata();

                    break ;
                case "L":                                     // To Load Data from File in to Queue
                case "l":
                    loadDatatoQueue();

                case "R":                                     // To Run Simulation data And View Report
                case "r":
                    runSimulation();
                    break ;

                case "Q":                                    //To Execute the Program
                case "q":
                    Quit();

                    break menu;
                default:
                    System.out.println("Invalid Input...????...Please Select Option Again...!!!!");
            }
        }

    }


    private static void addPassenger() {                                        //Create Method Add Passenger

        if (waitingRoom.isEmpty() && trainQueue.isEmpty()) {
            System.out.println("No Passengers to Add");
            return;
        }
        Stage stage = new Stage();
        if (trainQueue.isEmpty()) {
            int random = ThreadLocalRandom.current().nextInt(1,7);
            if (random > waitingRoom.size()) random=waitingRoom.size();             //Counting Random Number
                                                                                    //From Rolling Fair Dice
            for (int i=0; i<random; i++) {
                trainQueue.add(waitingRoom.remove(0));
            }
        }
        ArrayList<Button> buttons = new ArrayList<>();
        HBox hBox1= new HBox();
        for (int i=0; i<trainQueue.size(); i++) {
            Button passengerBtn= new Button(trainQueue.get(i).getName()+" at "+(trainQueue.get(i).getSeat()+1));
            passengerBtn.setMouseTransparent(true);                                //Add names For tempory Array
            buttons.add(passengerBtn);
            hBox1.getChildren().add(passengerBtn);
        }
        buttons.get(0).setStyle("-fx-background-color: yellow");
        HBox hBox2 = new HBox();
        Button add = new Button("Add Passenger");
        Button remove = new Button("Remove Passenger");
        hBox2.getChildren().addAll(add,remove);
        add.setOnAction(event -> {
            if (PassengerQueue.isFull()) {
                System.out.println("Queue is Full");
                stage.close();
            }else {
                buttons.remove(0).setStyle("-fx-background-color: green");
                Passenger passenger = trainQueue.remove(0);
                PassengerQueue.addToQueue(passenger, passenger.getSeat());
                if (buttons.isEmpty()) {                                          //Set on Actions For Add Button
                    stage.close();
                } else {
                    buttons.get(0).setStyle("-fx-background-color: yellow");
                }
            }
        });
        remove.setOnAction(event -> {
            buttons.remove(0).setStyle("-fx-background-color: red");
            waitingRoom.add(trainQueue.remove(0));
            if (buttons.isEmpty()) {                                               //Set on Actions For Remove Button
                stage.close();
            }else {
                buttons.get(0).setStyle("-fx-background-color: yellow");
            }
        });
        hBox1.setAlignment(Pos.TOP_CENTER);
        hBox1.setSpacing(15);
        hBox1.setPadding(new Insets(15, 12, 15, 12));
        hBox1.setStyle("-fx-background-color: #336699;");
        hBox2.setAlignment(Pos.BOTTOM_CENTER);
        hBox2.setSpacing(30);
        hBox2.setPadding(new Insets(15, 12, 15, 12));
        hBox2.setStyle("-fx-background-color: #336699;");                            //Designing Flow Pane
        VBox vBox = new VBox(hBox1,hBox2);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.setStyle("-fx-background-color: SKYBLUE;");
        Scene scene = new Scene(vBox,700,500);
        stage.setTitle(" Denuwara Menike Train Queue");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public static void viewAllseats() {                                             //Create Method for View all Queue and Seats
        FlowPane waiting = new FlowPane();
        FlowPane queue = new FlowPane();                                            //create 3 separate flow panes for three columns
        FlowPane train = new FlowPane();
        VBox waitingBox = new VBox(new Text("**Waiting Room**\n\n"),waiting);
        VBox queueBox = new VBox(new Text("**Queue**\n\n"),queue);
        VBox trainBox = new VBox(new Text("**Train Seats**\n\n"),train);
        for (Passenger passenger : waitingRoom){
            Button btn = new Button(passenger.getName()+" at "+(passenger.getSeat()+1));
            btn.setStyle(String.valueOf(new Ellipse(1,3)));
            btn.setStyle("-fx-background-color: white;");
            btn.setPrefSize(130,50);                           //Create Buttons for Waiting Room
            waiting.getChildren().add(btn);
        }
        for (Passenger passenger :trainQueue){
            Button btn = new Button(passenger.getName()+" at "+(passenger.getSeat()+1));
            btn.setPrefSize(130,30);                           //Create Buttons for Queue
            queue.getChildren().add(btn);
        }
        int i=0;
        for (Passenger passenger : PassengerQueue.getPassengerQueue()){
            i++;
            Button btn;
            if (passenger==null){
                btn = new Button(i+" is Empty");
                btn.setStyle("-fx-background-color: green");
            }else {
                btn = new Button(passenger.getName() + " at " + (passenger.getSeat() + 1));
                btn.setStyle("-fx-background-color: red");
            }
            btn.setPrefSize(100, 30);                     //Create Buttons for Train
            train.getChildren().add(btn);
        }

        waiting.setAlignment(Pos.CENTER);
        queue.setAlignment(Pos.CENTER);
        train.setAlignment(Pos.CENTER);
        waiting.setHgap(10);
        waiting.setVgap(10);
        train.setHgap(5);
        train.setVgap(5);                                                    //Designing Flow Pane
        waitingBox.setAlignment(Pos.CENTER);
        queueBox.setAlignment(Pos.CENTER);
        trainBox.setAlignment(Pos.CENTER);
        HBox hBox = new HBox(waitingBox,queueBox,trainBox);
        hBox.setPadding(new Insets(15, 12, 15, 12));
        hBox.setStyle("-fx-background-color:  SKYBLUE;");
        hBox.setAlignment(Pos.CENTER);
        Stage stage = new Stage();
        stage.setTitle("View Train Queue and Train");
        Scene scene = new Scene(hBox,1000,600);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public static void deletePassenger() {                                             //Create Method for delete Passenger
        while (true){
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the Seat Number to Delete or -1 Back to Main Menu");
            int seat = scanner.nextInt()-1;                                          //Getting Input for Start Process
            if (seat==-2) break;
            else if (seat<0 || seat>41){
                System.out.println("Invalid Input.!!!!");
                continue;
            }else {
                Passenger passenger = PassengerQueue.getPassengerQueue()[seat];
                if (passenger == null) {
                    System.out.println("No Records to Delete");
                    continue;
                }else {
                    System.out.println(passenger.getName()+" - "+passenger.getSeat());
                    Scanner sc = new Scanner(System.in);
                    System.out.print("Are You Want to Sure Delete this Booking? (Yes/No)");
                    String string = sc.next();                                        //Confirming Process...
                    if (string.equalsIgnoreCase("yes")) {
                        PassengerQueue.delete(seat);
                        System.out.println(" Booking Deleted");
                    }else if(string.equalsIgnoreCase("no")) {
                        System.out.println("Proceed Cancelled");
                    }else{
                        System.out.println("Invalid Response");
                    }
                }
            }
        }
    }

    public static void storeQueuedata() {                                         //Create Method for store Data
        if (PassengerQueue.isEmpty()) {
            System.out.println("No Records to Save");
            return;
        }
        try {                                                                       //Creating Path for Save Data
            FileOutputStream fileOut = new FileOutputStream("E:\\Queue.txt");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            for (Passenger passenger : PassengerQueue.getPassengerQueue()) {
                if (passenger != null) {
                    objectOut.writeObject(passenger);                             //write objects to a file named data.txt
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not Found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Saved Successfully");
    }

    public static void loadDatatoQueue() {                               // Create Method for Load Program
        try {
            FileInputStream fi = new FileInputStream(new File("E:\\Queue.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);
                                                                        //Creating Path for Load Data
            while (true) {
                try {
                    Passenger passenger = (Passenger) oi.readObject();
                    PassengerQueue.addToQueue(passenger,passenger.getSeat());
                }catch (EOFException e) {break;}
            }
        }catch (FileNotFoundException e) {
            System.out.println("File not Found");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Loaded Successfully");
    }

    private static void runSimulation() {                                 // Create Method for Run Simulation
        if (PassengerQueue.isEmpty()) {
            System.out.println("Queue is empty");
            return;
        }
        int maxLine=0;
        int totTime=0;
        int totPassenger=0;
        int maxStay = 0;
        int minStay = 100;
        ArrayList<Passenger> train = new ArrayList<>();
        ArrayList<Passenger> queue = new ArrayList<>();
        ArrayList<Passenger> tempLine = new ArrayList<>();
        for (Passenger passenger : PassengerQueue.getPassengerQueue()) {
            if (passenger!=null) queue.add(passenger);
        }
        int queueSize = queue.size();                                        //Creating Random Number
        while (queueSize != train.size()) {
            if (tempLine.isEmpty()) {
                int random = ThreadLocalRandom.current().nextInt(1,7);
                if (random>queue.size()) random=queue.size();
                if (random>maxLine) maxLine = random;
                for (int i=0; i<random; i++) {
                    Passenger passenger = queue.remove(0);
                    PassengerQueue.delete(passenger.getSeat());
                    tempLine.add(passenger);
                }
            }
            int time = ThreadLocalRandom.current().nextInt(3,19);
            for (Passenger passenger : tempLine){
                passenger.setSecondsInQueue(time);
            }
            int passengerTime = tempLine.get(0).getSecondsInQueue();           //Counting Time
            System.out.println(passengerTime);
            totTime = totTime+(passengerTime);
            if (passengerTime>maxStay) maxStay = passengerTime;
            if (passengerTime<minStay) minStay = passengerTime;
            train.add(tempLine.remove(0));
            totPassenger++;
        }
        String totalPassengers = ("Number of Passengers on Seats - "+(totPassenger));
        String average = "Average Waiting Time - " + (totTime / totPassenger);
        String maxLineLength = ("Maximum Length of Line - "+maxLine);
        String maxStayInLine = ("Maximum Staying Time - "+maxStay);
        String minStayInLine = ("Minimum Staying Time - "+minStay);

        try {
            Writer fileWriter = new FileWriter("E:\\output.txt", true);
            fileWriter.write("Simulation Report\n\n"+totalPassengers+"\n"+average+"\n"+maxLineLength
                    +"\n"+maxStayInLine+"\n"+minStayInLine+"\n\n\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Something Wrong \n Please Try Again");
        }

        Stage stage = new Stage();
        VBox vBox = new VBox(new Text("Report\n"),new Text(totalPassengers),new Text(average),new Text(maxLineLength)
                ,new Text(maxStayInLine),new Text(minStayInLine));
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-font-size: 25px;");
        Scene scene = new Scene(vBox,600,400);                //Creating Stage for Simulation Report
        stage.setTitle(" Simulation Report");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public static void Quit()  {                                      // To Quit From the Program
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println("!!!!!!!!>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<!!!!!!!!!!");
            System.out.println("!!!!!!!!!!......Your Proceed has been done.......!!!!!!!!!!");
            System.out.println("!!..Thank You for Using SriLankan Railway Booking System..!!");
            System.out.println("!!!!!!!!>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<!!!!!!!!!!");
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.exit(0);
        }
    }


