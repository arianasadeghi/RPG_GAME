package clase;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Grid extends ArrayList<ArrayList<Cell>> {
    public int length;
    public int width;
    private Character character;
    private Cell currentCell;

    public Grid(int length, int width) {
        this.length = length;
        this.width = width;
    }
    public Cell getCurrentCell() {
        return currentCell;
    }

    public Cell getNextCell(int x, int y) throws ImpossibleMove {
        if(x < 0 || x >= length || y < 0 || y >= width){
            throw new ImpossibleMove("Impossible move");
        }
        return this.get(x).get(y);
    }

    public static Grid createGrid(int length, int width) {
        if(length > 10 || width > 10) {
            throw new IllegalArgumentException("Grid length must be between 10 and 10");
        }
        Grid grid = new Grid(length, width);
        Random rand = new Random();

        for(int i = 0; i < length; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for(int j = 0; j < width; j++) {
                row.add(new Cell(i, j, CellEntityType.VOID));
            }
            grid.add(row);
        }
        Random random = new Random();
        int count1 = random.nextInt(5) + 2;
        int count2 = random.nextInt(7) + 4;
        grid.placeSpecialCell(CellEntityType.SANCTUARY, count1, rand);
        grid.placeSpecialCell(CellEntityType.ENEMY, count2, rand);
        grid.placePortal(CellEntityType.PORTAL);
        grid.placePlayer(CellEntityType.PLAYER);
        return grid;
    }

//    public static Grid createGrid(int length, int width){
//        Grid grid = new Grid(length, width);
//        for(int i = 0; i < length; i++) {
//            ArrayList<Cell> row = new ArrayList<>();
//            for(int j = 0; j < width; j++) {
//                row.add(new Cell(i, j, CellEntityType.VOID));
//            }
//            grid.add(row);
//        }
//        grid.placeSpecialCell(CellEntityType.SANCTUARY, 4, 3);
//        grid.placeSpecialCell(CellEntityType.SANCTUARY, 1, 3);
//        grid.placeSpecialCell(CellEntityType.SANCTUARY, 2, 0);
//        grid.placeSpecialCell(CellEntityType.SANCTUARY, 0, 3);
//        grid.placeSpecialCell(CellEntityType.ENEMY, 3, 4);
//        grid.placePlayer(CellEntityType.PLAYER);
//        grid.placePortal(CellEntityType.PORTAL);
//        return grid;
//    }

    private void placePortal(CellEntityType type) {
        Cell lastCell = this.get(length - 1).get(width - 1);
        lastCell.setType(CellEntityType.PORTAL);
    }
    public void placePlayer(CellEntityType type) {
        Cell playerCell = this.get(0).get(0);
        playerCell.setType(CellEntityType.PLAYER);
        currentCell = playerCell;
    }

    public void placeSpecialCell(CellEntityType type, int count, Random random){
        int placed = 0;
        while(placed < count){
            int Ox = random.nextInt(length);
            int Oy = random.nextInt(width);
            Cell cell = this.get(Ox).get(Oy);

            if(cell.getType() == CellEntityType.VOID){
                cell.setType(type);
                placed++;
            }
        }
    }
//    public void placeSpecialCell(CellEntityType type, int Ox, int Oy) {
//        Cell specialCell = this.get(Ox).get(Oy);
//        specialCell.setType(type);
//    }

    private void moveTo(int x, int y){
        Cell nextCell = this.get(x).get(y);

        nextCell.setVisited(true);
        currentCell.setVisited(true);
        nextCell.setType(CellEntityType.PLAYER);
        currentCell.setType(CellEntityType.VOID);
        currentCell = nextCell;
    }

    public void goNorth() throws ImpossibleMove{
        int currentX = currentCell.getOx();
        int currentY = currentCell.getOy();

        if(currentX == 0){
            throw new ImpossibleMove("Nu poti merge spre nord.");
        }
        moveTo(currentX - 1, currentY);
    }
    public void goSouth() throws ImpossibleMove{
        int currentX = currentCell.getOx();
        int currentY = currentCell.getOy();
        if(currentX == length - 1){
            throw new ImpossibleMove("Nu poti merge spre sud.");
        }
        moveTo(currentX + 1, currentY);
    }
    public void goEast() throws ImpossibleMove{
        int currentX = currentCell.getOx();
        int currentY = currentCell.getOy();
        if(currentY == width - 1){
            throw new ImpossibleMove("Nu poti merge spre est.");
        }
        moveTo(currentX, currentY + 1);
    }
    public void goWest() throws ImpossibleMove{
        int currentX = currentCell.getOx();
        int currentY = currentCell.getOy();
        if(currentY == 0){
            throw new ImpossibleMove("Nu poti merge spre vest.");
        }
        moveTo(currentX, currentY - 1);
    }

    public JPanel getGridPanel(){
        JPanel panel = new JPanel(new GridLayout(length, width));

        for(int i = 0; i < length; i++){
            for(int j = 0; j < width; j++){
                Cell cell = this.get(i).get(j);
                JButton button = new JButton();
                if(cell.getType() == CellEntityType.PORTAL){
                    button.setBackground(new Color(25,40,40));
                } else if(cell.getType() == CellEntityType.PLAYER) {
                    button.setBackground(Color.WHITE);
                } else if(cell.isVisited()) {
                    button.setBackground(Color.BLACK);
//                }else if(cell.getType() == CellEntityType.SANCTUARY) {
//                    button.setBackground(Color.PINK);
//                }else if(cell.getType() == CellEntityType.ENEMY){
//                    button.setBackground(Color.GREEN);
                } else {
                    button.setBackground(Color.GRAY);
                }
                panel.add(button);
            }
        }
        return panel;
    }
}
