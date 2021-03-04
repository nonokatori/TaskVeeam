import java.io.*;

public class Task3 {

    public static void main(String[] args) {

        String pathToFile;
        String directoryOfFiles;
        try {
            pathToFile = args[0];
            directoryOfFiles = args[1];
            readFile(pathToFile, directoryOfFiles);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Enter arguments: [program_name] [path_ro_file] [path_to_directory_with_files]");
        }

    }

    public static void readFile(String pathTofile, String directory) {
        ChecksumVerifier checksumVerifier;
        String s;
        String[] strings;
        StringBuilder sb = new StringBuilder();


        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(pathTofile), "UTF8"))) {
            while ((s=reader.readLine())!=null)
            {
                strings = s.split(" ");
                checksumVerifier = new ChecksumVerifier(strings[0], strings[1], strings[2]);
                sb.append(checksumVerifier.checkChecksum(directory)).append("\n");
            }

        } catch (FileNotFoundException e) {
            System.out.println("The file does not exist or the specified path is invalid.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(sb.toString());
    }

}


