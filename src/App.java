import java.io.IOException;

public class App {
    public static void main(String[] args)
            throws IOException {
        ProcessBuilder pb = new ProcessBuilder("./build.sh");
        Process process = pb.start();
//        process.onExit().
//        int exitCode = process.exitValue();
//
//        if(exitCode == 0) {
            System.out.println("Completed");
//            return;
//        }

//        System.out.println("Error while executing following command: ./build.sh, exit code: " + exitCode );

    }
}
