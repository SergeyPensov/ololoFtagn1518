package finalStates;

/**
 * @Author Sergey Pensov
 */
public class FindFinalStates {
    private HexMap map;
    private Pivot pilot;
    private SpriteWorker worker;

    public FindFinalStates(Unit unit, HexMap map) {
        this.map = map;
        this.pilot = unit.pivot;
        this.worker = new SpriteWorker(unit);
    }
}
