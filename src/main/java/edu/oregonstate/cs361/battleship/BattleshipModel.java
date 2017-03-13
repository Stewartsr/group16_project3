package edu.oregonstate.cs361.battleship;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by michaelhilton on 1/4/17.
 */
public class BattleshipModel {

    private Ship aircraftCarrier = new Ship("AircraftCarrier",5, new Coordinate(0,0),new Coordinate(0,0));
    private StealthShip battleship = new StealthShip("Battleship",4, new Coordinate(0,0),new Coordinate(0,0));
    //private Ship cruiser = new Ship("Cruiser",3, new Coordinate(0,0),new Coordinate(0,0));
    //private Ship destroyer = new Ship("Destroyer",2, new Coordinate(0,0),new Coordinate(0,0));
    private StealthShip submarine = new StealthShip("Submarine",3, new Coordinate(0,0),new Coordinate(0,0));
    private Civilian clipper = new Civilian("Clipper", 3, new Coordinate(0, 0), new Coordinate(0, 0));
    private Civilian dinghy = new Civilian("Dinghy", 1, new Coordinate(0,0), new Coordinate(0, 0));

    private Ship computer_aircraftCarrier = new Ship("Computer_AircraftCarrier",5, new Coordinate(2,2),new Coordinate(2,6));
    private StealthShip computer_battleship = new StealthShip("Computer_Battleship",4, new Coordinate(2,8),new Coordinate(5,8));
    //private Ship computer_cruiser = new Ship("Computer_Cruiser",3, new Coordinate(4,1),new Coordinate(4,4));
    //private Ship computer_destroyer = new Ship("Computer_Destroyer",2, new Coordinate(7,3),new Coordinate(7,5));
    private StealthShip computer_submarine = new StealthShip("Computer_Submarine",3, new Coordinate(9,6),new Coordinate(9,8));
    private Civilian computer_clipper = new Civilian("Computer_Clipper", 3, new Coordinate(5, 1), new Coordinate(5, 3));
    private Civilian computer_dinghy = new Civilian("Computer_Dinghy", 1, new Coordinate(1,1), new Coordinate(1, 1));

    public ArrayList<Coordinate> playerHits;
    public ArrayList<Coordinate> playerMisses;
    public ArrayList<Coordinate> computerHits;
    public ArrayList<Coordinate> computerMisses;

    public String results;
    public String error_message;
    //playerHitpoints to count how many hits player has taken, when it equals to 14, computer wins
    //computerHitpoints to count how many hits AI has taken, when it equals to 14, player wins
    public int playerHitpoints = 0;
    public int computerHitpoints = 0;
    public String AI_win = "You lose...T_T";
    public String Player_win = "You WIN!!! ^_^";


    public BattleshipModel() {
        playerHits = new ArrayList<>();
        playerMisses= new ArrayList<>();
        computerHits = new ArrayList<>();
        computerMisses= new ArrayList<>();
    }

    public static BattleshipModel ofStatus(String statusStr) {
        System.out.println("STRING");
        return null;
    }

    public Ship getShip(String shipName) {
        if (shipName.equalsIgnoreCase("aircraftcarrier")) {
            return aircraftCarrier;
        } if(shipName.equalsIgnoreCase("battleship")) {
            return battleship;
        } if(shipName.equalsIgnoreCase("clipper")) {
            return clipper;
        } if(shipName.equalsIgnoreCase("dinghy")) {
            return dinghy;
        }if(shipName.equalsIgnoreCase("submarine")) {
            return submarine;
        } else {
            return null;
        }
    }

    public BattleshipModel placeShip(String shipName, String row, String col, String orientation, BattleshipModel currModel) {
        int rowint = Integer.parseInt(row);
        int colInt = Integer.parseInt(col);
        if(orientation.equals("horizontal")){
            if (shipName.equalsIgnoreCase("aircraftcarrier")) {
                currModel.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint,colInt+4));
            } if(shipName.equalsIgnoreCase("battleship")) {
                currModel.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint,colInt+3));
            } if(shipName.equalsIgnoreCase("clipper")) {
                currModel.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint,colInt+2));
            } if(shipName.equalsIgnoreCase("dinghy")) {
                currModel.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint,colInt));
            }if(shipName.equalsIgnoreCase("submarine")) {
                currModel.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint,colInt+2));
            }
        }else{
            //vertical
                if (shipName.equalsIgnoreCase("aircraftcarrier")) {
                    currModel.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint+4,colInt));
                } if(shipName.equalsIgnoreCase("battleship")) {
                    currModel.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint+3,colInt));
                } if(shipName.equalsIgnoreCase("clipper")) {
                    currModel.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint+2,colInt));
                } if(shipName.equalsIgnoreCase("dinghy")) {
                    currModel.getShip(shipName).setLocation(new Coordinate(rowint,colInt),new Coordinate(rowint,colInt));
                }if(shipName.equalsIgnoreCase("submarine")) {
                    currModel.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint+2,colInt));
                }
        }
        return currModel;
    }

    public void shootAtComputer(int row, int col) {
        Coordinate coor = new Coordinate(row,col);
        if(computer_aircraftCarrier.covers(coor)){
            computerHits.add(coor);
            computerHitpoints += 1;
        }else if (computer_battleship.covers(coor)){
            computerHits.add(coor);
            computerHitpoints += 1;
        }else if (computer_clipper.covers(coor)){
            computerHits.add(coor);
            computerHitpoints += 1;
        }else if (computer_dinghy.covers(coor)){
            computerHits.add(coor);
            computerHitpoints += 1;
        }else if (computer_submarine.covers(coor)){
            computerHits.add(coor);
            computerHitpoints += 1;
        } else {
            computerMisses.add(coor);
        }
    }

    public void shootAtPlayer() {
        double randomRow = Math.random() * 10 + 1;
        double randomCol = Math.random() * 10 + 1;
        int max = 10;
        int min = 1;
        Random random = new Random();
        int randRow = random.nextInt(max - min + 1) + min;
        int randCol = random.nextInt(max - min + 1) + min;

        Coordinate coor = new Coordinate(randRow,randCol);
        if(playerMisses.contains(coor)){
            System.out.println("Dupe");
        }


        if(aircraftCarrier.covers(coor)){
            playerHits.add(coor);
            playerHitpoints += 1;
        }else if (battleship.covers(coor)){
            playerHits.add(coor);
            playerHitpoints += 1;
        }else if (clipper.covers(coor)){
            playerHits.add(coor);
            playerHitpoints += 1;
        }else if (dinghy.covers(coor)){
            playerHits.add(coor);
            playerHitpoints += 1;
        }else if (submarine.covers(coor)){
            playerHits.add(coor);
            playerHitpoints += 1;
        } else {
            playerMisses.add(coor);
        }
    }

    public boolean checkfirepoint(int row, int col){
        int hitsize = computerHits.size();
        int missize = computerMisses.size();

        int i = 0;
        while( i < hitsize) {
            Coordinate z = computerHits.get(i);
            int xhit = z.Across;
            int yhit = z.Down;
            if (row == xhit && col == yhit){
                return true;
            }else{
                i += 1;
            }
        }

        int j = 0;
        while( j < missize){
            Coordinate m = computerMisses.get(j);
            int xmiss = m.Across;
            int ymiss = m.Down;
            if(row == xmiss && col == ymiss){
                return true;
            }else{
                j += 1;
            }
        }
        return false;
    }

    public boolean scanPlayer(int row, int col ) {
        Coordinate coor = new Coordinate(row,col);
        if((computer_aircraftCarrier.covers(coor)) && (computer_aircraftCarrier.getStealth()==false)){
            return true;
        }else if ((computer_battleship.covers(coor))&& (computer_battleship.getStealth()==false)){
            return true;
        }else if ((computer_clipper.covers(coor)) && (computer_clipper.getStealth()==false)){
            return true;
        }else if (computer_dinghy.covers(coor) && (computer_dinghy.getStealth()==false)){
            return true;
        }else if (computer_submarine.covers(coor)  && (computer_submarine.getStealth()==false)){
            return true;
        } else {
            return false;
        }
    }
}