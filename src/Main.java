import clase.Grid;
import clase.ImpossibleMove;

import java.util.Random;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Random rand = new Random();
        int length = rand.nextInt(7) + 4;
        int width = rand.nextInt(7) + 4;
        Grid grid = Grid.createGrid(5, 5);
        Scanner sc = new Scanner(System.in);
        print(grid);
        while(true) {
            String input = sc.nextLine().toUpperCase();
            try {
                switch (input) {
                    case "W":
                        grid.goNorth();
                        print(grid);
                        break;
                        case "S":
                            grid.goSouth();
                            print(grid);
                            break;
                            case "A":
                                grid.goWest();
                                print(grid);
                                break;
                                case "D":
                                    grid.goEast();
                                    print(grid);
                                    break;
                    default:
                        System.out.println("Tasta invalida");
                        continue;
                }
            } catch (ImpossibleMove e) {
                System.out.println(e.getMessage());
            }
        }
        //        try {
//            grid.goNorth();
//        } catch (Grid.ImpossibleMoveException e) {
//            System.out.println(e.getMessage());
//        }
//        print(grid);
//        System.out.println();
//
//        try {
//            grid.goSouth();
//        } catch (Grid.ImpossibleMoveException e) {
//            System.out.println(e.getMessage());
//        }
//        print(grid);
//        System.out.println();
//        try {
//            grid.goEast();
//        } catch (Grid.ImpossibleMoveException e) {
//            System.out.println(e.getMessage());
//        }
//        print(grid);
//        System.out.println();
//        try {
//            grid.goWest();
//        } catch (Grid.ImpossibleMoveException e) {
//            System.out.println(e.getMessage());
//        }
//        print(grid);
//        try {
//            grid.goWest();
//        } catch (Grid.ImpossibleMoveException e) {
//            System.out.println(e.getMessage());
//        }
//        print(grid);
    }
    private static void print(Grid grid) {
        for(int i = 0; i < grid.size(); i++){
            for(int j = 0; j < grid.get(i).size(); j++){
                System.out.print(grid.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }
}