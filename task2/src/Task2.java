import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task2 {

    public static void main(String[] args) {

        Service service = new Service();
        if (args.length != 2) {
            System.out.println("Неверные входные данные.\n" +
                    "Шаблон входных данных: java [name_program] --[system_type] [system_name]");
            return;
        }

        System.out.println(service.getInfo(args));

    }
}
