import com.google.gson.Gson;
import finalStates.*;
import vis.BoardVis;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
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

    private static void saveFile(final String path, final String text) throws IOException {
        try( FileOutputStream fos = new FileOutputStream(new File(path))) {
            byte[] data = text.getBytes(Charset.defaultCharset());
            fos.write(data);
            fos.close();
        }
    }

    public static void main(String[] args) {

        List<String> inputFileNames = new ArrayList<String>();
        List<String> phrases = new ArrayList<String>();
        Integer timeLimit = null;
        Integer memoryLimit = null;
        Integer cpuCoresCount = null;

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
            else if( opt.equals("-c")) {
                cpuCoresCount = Integer.parseInt(args[i+1]);
            }
        }

        for (String inputFileName : inputFileNames) {
            try {
                final String json = readFile(inputFileName);
                Problem problem = Problem.read(json);
                Board board = problem.getBoard();

                // drawing initial board state
                BoardVis vis = new BoardVis();
                BufferedImage image = vis.draw(board, null, null);
                ImageIO.write(image, "png", new File(inputFileName + "_board.png"));

                // drawing units
                int unitCounter = 0;
                for (Unit unit : problem.units) {
                    Board unitBoard = new Board(unit);
                    image = vis.draw(unitBoard, null, null);
                    ImageIO.write(image, "png", new File(String.format("%s_unit_%02d.png", inputFileName, unitCounter)));
                    ++unitCounter;
                }

                Solver solver = new Solver(problem, inputFileName);
                final SolverResult[] results = solver.solveAll(new int[]{problem.sourceSeeds[0]});

                Gson gson = new Gson();
                results[0].solution = "iiiiiiiimmiiiiiimimmiiiimimimmimimimimmimimimeemimeeeemimim\n" +
                        "imimiiiiiimmeemimimimimiimimimmeemimimimmeeeemimimimmiiiiii\n" +
                        "pmiimimimeeemmimimmemimimimiiiiiimeeemimimimimeeemimimimmii\n" +
                        "iimemimimmiiiipimeeemimimmiiiippmeeeeemimimimiiiimmimimeemi\n" +
                        "mimeeeemimimiiiipmeeemmimmiimimmmimimeemimimimmeeemimiiiiip\n" +
                        "miiiimmeeemimimiiiipmmiipmmimmiippimemimeeeemimmiipppmeeeee\n" +
                        "mimimmiimipmeeeemimimiimmeeeeemimmeemimmeeeemimiiippmiippmi\n" +
                        "iimmiimimmmmmeeeemimmiippimmimimeemimimimmeemimimimmeemimim\n" +
                        "imiimimimeeemmimimmmiiiiipimeemimimimmiiiimimmiiiiiiiimiimi\n" +
                        "mimimeeemmimimimmiiiiiimimmemimimimimmimimimeemimiiiiiiiimi\n" +
                        "iiimimimiimimimmimmimimimimmeeeemimimimimmmimimimimeemimimi\n" +
                        "mimmmemimimmiiiiiiimiimimimmiiiiiimeeeeemimimimimmimimimmmm\n" +
                        "emimimmeeeemimimimmiimimimmiiiiiipmeeeeemimimimimmiiiiimmem\n" +
                        "imimimimmmmimimmeeeemimimimimeeemimimimmiimimimeeemmimimmii\n" +
                        "iiiiimimiiiiiimimmiiiiiiiimmimimimimiiiimimimeemimimimimmee\n" +
                        "emimimimimiiiiiiimiiiimimmemimimimmeemimimimeeemmimimmiiiii\n" +
                        "immiiiipmmiiimmmimimeemimimeeemmimmiiiippmiiiimiiippimiimim\n" +
                        "eemimimeeeemimimiiiipmeemimimiimiimimmimeeemimimmippipmmiim\n" +
                        "emimmipimeeeemimmeemimiippimeeeeemimimmmimmmeeeemimimiiipim\n" +
                        "miipmemimmeeeemimimiipipimmipppimeeemimmpppmmpmeeeeemimmemm ";
                final String resultJSON = gson.toJson(results);
                saveFile( inputFileName + "_result.json", resultJSON);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
