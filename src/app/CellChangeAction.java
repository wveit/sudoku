package app;

public interface CellChangeAction {
    public void run(int column, int row, int newValue);
}
