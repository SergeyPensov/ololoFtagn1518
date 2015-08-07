/**
 * Created by kirill.sidorchuk on 8/7/2015.
 */
public class RandomGenerator {

    private long seed;
    private final static long increment = 12345;
    private final static long multiplier = 1103515245;

    public RandomGenerator(long seed) {
        this.seed = seed;
    }

    public int generate() {
        int result = (int) ((seed >> 16) & 0x7FFF);
        seed = (seed * multiplier + increment) & 0xFFFFFFFFl;
        return result;
    }

    public static void main(String[] args) {
        RandomGenerator LOG = new RandomGenerator(17);
        for( int it=0; it<10; ++it) {
            System.out.println(LOG.generate());
        }
    }
}
