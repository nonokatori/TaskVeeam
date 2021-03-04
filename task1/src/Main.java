public class Main {

    public static void main(String[] args) {
        InfoDisk info = new InfoDisk();
        if (args.length != 1) {
            System.out.println("Введите путь к файлу");
            return;
        }

        info.createStringInfo(info.readFile(args[0]));

    }

}



