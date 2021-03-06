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
        try (FileOutputStream fos = new FileOutputStream(new File(path))) {
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
        boolean saveImages = false;

        for (int i = 0; i < args.length; i += 2) {
            final String opt = args[i];
            if (opt.equals("-f")) {
                inputFileNames.add(args[i + 1]);
            } else if (opt.equals("-t")) {
                timeLimit = Integer.parseInt(args[i + 1]);
            } else if (opt.equals("-m")) {
                memoryLimit = Integer.parseInt(args[i + 1]);
            } else if (opt.equals("-p")) {
                phrases.add(args[i + 1]);
            } else if (opt.equals("-c")) {
                cpuCoresCount = Integer.parseInt(args[i + 1]);
            } else if( opt.equals("-saveimages")) {
                saveImages = true;
            }
        }

        for (String inputFileName : inputFileNames) {
            Timer timer = new Timer();
            try {
                final String json = readFile(inputFileName);
                Problem problem = Problem.read(json);
                Board board = problem.getBoard();

//                if( saveImages ) {
                    // drawing initial board state
                    BoardVis vis = new BoardVis();
                    BufferedImage image = vis.draw(board, null, null, null);
                    ImageIO.write(image, "png", new File(inputFileName + "_board.png"));
//                }

                // drawing units
                for (int unitCounter=0; unitCounter<problem.units.length; ++unitCounter) {
                    image = vis.draw(problem.unitBoards[unitCounter],
                            problem.units[unitCounter], new UnitState(Point.zero,0, 6), null);
                    ImageIO.write(image, "png", new File(String.format("%s_unit_%02d.png", inputFileName, unitCounter)));
                }

                Solver solver = new Solver(problem, inputFileName, saveImages);
                final SolverResult[] results = solver.solveAll();

                Gson gson = new Gson();
                final String resultJSON = gson.toJson(results);
                final String resultJsonFile = inputFileName + "_result.json";
                saveFile(resultJsonFile, resultJSON);

                final String submit = formatSubmitScript(resultJsonFile);
                saveFile(inputFileName + "_result_submit.bat", submit);

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Solving for " + new File(inputFileName).getName() + " took " + timer.getTime(false));
        }

    }

    private static String formatSubmitScript( String jsonResultFile) {
        final String API_TOKEN = ":plrVW5TJZhcAW/2HmsWBJOnGd2vwab+5Ssq9Y1HVggk=";
        final String TEAM_ID = "195";
//        final String OUTPUT = escape(resultJSON);

        return String.format("c:/soft/curl -k --user %s -X POST -H \"Content-Type: application/json\" -d @%s https://davar.icfpcontest.org/teams/%s/solutions\n@echo off\n@pause\n",
                API_TOKEN, jsonResultFile, TEAM_ID);
    }

    private static String escape(final String resultJSON) {
        StringBuilder sb = new StringBuilder(resultJSON.length());
        for (int i = 0; i < resultJSON.length(); ++i) {
            char c = resultJSON.charAt(i);
            if (c == '\"') {
                sb.append("\\\"");
            } else {
                sb.append(c);
            }
        }

        return sb.toString().replaceAll("\n","");
    }
}
