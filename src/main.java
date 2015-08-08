import finalStates.Problem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill.sidorchuk on 8/7/2015.
 */
public class main {

    private static String readFile(final String path) throws IOException {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return new String(data, "UTF-8");
    }

    public static void main(String[] args) {

        List<String> inputFileNames = new ArrayList<String>();
        List<String> phrases = new ArrayList<String>();
        Integer timeLimit = null;
        Integer memoryLimit = null;

        for( int i=0; i<args.length; i+=2) {
            final String opt = args[i];
            if( opt.equals("-f")) {
                inputFileNames.add(args[i+1]);
            }
            else if( opt.equals("-t")) {
                timeLimit = Integer.parseInt(args[i+1]);
            }
            else if( opt.equals("-m")) {
                memoryLimit = Integer.parseInt(args[i+1]);
            }
            else if( opt.equals("-p")) {
                phrases.add(args[i+1]);
            }
        }

        for (String inputFileName : inputFileNames) {
            try {
                final String json = readFile(inputFileName);
                Problem problem = Problem.read(json);
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
