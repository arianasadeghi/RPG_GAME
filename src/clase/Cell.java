package clase;

public class Cell {
    private int Ox;
    private int Oy;
    private CellEntityType type;
    private boolean visited;

    public Cell(int Ox, int Oy, CellEntityType type) {
        this.Ox = Ox;
        this.Oy = Oy;
        this.type = type;
        this.visited = false;
    }

    public int getOx() {
        return Ox;
    }

    public int getOy() {
        return Oy;
    }

    public CellEntityType getType() {
        return type;
    }
    public void setType(CellEntityType Type) {
        this.type = Type;
    }
    public boolean isVisited() {
        return visited;
    }
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public String toString() {
        if(visited && type != CellEntityType.PLAYER){
            return "V";
        }
//        if (type == CellEntityType.ENEMY) {
//            return "E";
//        }
        if (type == CellEntityType.PLAYER) {
            return "P";
        }
        if (type == CellEntityType.PORTAL) {
            return "F";
        }
//        if (type == CellEntityType.SANCTUARY) {
//            return "S";
//        }
        return "N";
    }
}
