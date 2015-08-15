package finalStates;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by kirill.sidorchuk on 8/7/2015.
 */
public class Unit {
    public Point pivot;
    public Point[] members;
    public int maxAngle;

    @Override
    public String toString() {
        return "Unit{" +
                "pivot=" + pivot +
                ", size=" + (members != null ? members.length : 0) +
                ", maxAngle=" + maxAngle +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Unit unit = (Unit) o;

        if (!pivot.equals(unit.pivot)) return false;

        for( Point p : members) {
            int hash = p.hashCode();
            boolean found=false;
            for( Point p2 : unit.members) {
                if( p2.hashCode() == hash) {
                    found=true;
                    break;
                }
            }
            if( !found) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = pivot.hashCode();
        result = 31 * result + Arrays.hashCode(members);
        return result;
    }
}
