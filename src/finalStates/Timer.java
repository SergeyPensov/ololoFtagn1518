package finalStates;

public class Timer {

    long t;

    public Timer() {
        t = System.currentTimeMillis();
    }

    public String getTime(boolean reset) {
        long dt = System.currentTimeMillis() - t;
        String result;
        if( dt > 1000 ) {
            if( dt > 60*1000) {
                long m = dt / 60000;
                dt = dt % 60000;
                result = String.format("%dm:%1.0fs", m, (float)dt*1e-3f);
            }
            else {
                result = String.format("%1.2fs", (float)dt*1e-3f);
            }
        }
        else {
            result = Long.toString(dt) + "ms";
        }

        if(reset) t = System.currentTimeMillis();

        return result;
    }

    long getStartTime() {
        return t;
    }

    long getTimeMs() {
        return System.currentTimeMillis() - t;
    }

}
