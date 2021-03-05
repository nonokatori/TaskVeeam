public class Main {

    public static void main(String[] args) {
        InfoDisk info = new InfoDisk();
        if (args.length != 1) {
            System.out.println("Enter the path to the file.");
            return;
        }

        info.createStringInfo(args[0]);

    }

}



