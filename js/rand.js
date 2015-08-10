/* public class RandomGenerator {

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
}*/

function RandomGenerator(s){
	var seed = new Long(s, 0);
	var increment = new Long(12345, 0);//12345;
	var multiplier = new Long(1103515245, 0);//1103515245;
	
	return {
		generate : function(){
			var res = new Long(seed);
			res = res.shiftRight(16);
			
			seed = seed.multiply(multiplier);
			seed = seed.add(increment);
			seed = new Long(seed.getLowBits(), 0);
			
			return res.getLowBits() & 0x7FFF;
			/*var res = ((seed >> 16) & 0x7FFF);
			var tmp = (seed * multiplier + increment);
			seed = tmp & 0xFFFFFFFF;
			return res;*/
		}
	};
}