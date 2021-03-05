import java.io.*;
import java.util.Arrays;

public class InfoDisk {

    private String type;
    private String name;
    private String size;
    private String sizeFreeSpace;
    private String typeFileSystem;
    private String mount;

    private StringBuilder sb = new StringBuilder();


    public void createStringInfo (String pathFile) {

        try(FileReader fr = new FileReader(pathFile); BufferedReader reader = new BufferedReader(fr)) {
            name = reader.readLine();
        } catch (FileNotFoundException e) {
            System.out.println("Invalid file path.");
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }

        String resCmd = executeCommand(String.format("lsblk -d %s", name));
        if (resCmd==null) {
            System.out.println("This path on file is not suitable for the given command, please try again.");
            return;
        }
        String [] arrInfo = parseInfo(resCmd);
        size = arrInfo[3];
        type = arrInfo[5];

        if (!"disk".equals(type)) {
            arrInfo = parseInfo(executeCommand(String.format("df -T --block-size=M %s", name)));
            typeFileSystem = arrInfo[1];
            sizeFreeSpace = arrInfo[4];
            mount = arrInfo[6];
            System.out.println(getInfoAboutDiskDevice());
            return;
        }

        System.out.println(getInfoAboutDisk());
    }

    public String executeCommand(String command) {
        String s = null;
        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            reader.readLine();
            s = reader.readLine();
            p.waitFor();
            p.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return s;

    }

    public String[] parseInfo (String str) {
        String [] allField = str.replaceAll("( )+", " ").split("\\s");
        return allField;
    }


    public String getInfoAboutDiskDevice() {
        return sb.append(name).append(" ")
                .append(type).append(" ")
                .append(size).append(" ")
                .append(sizeFreeSpace).append(" ")
                .append(typeFileSystem).append(" ")
                .append(mount).toString();

    }

    public String getInfoAboutDisk() {
        return sb.append(name).append(" ")
                .append(type).append(" ")
                .append(size).append(" ").toString();

    }
}
