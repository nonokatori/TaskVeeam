import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {


    private String name;
    private String type;
    private HashMap<String,String> param = new HashMap<>();

    {
        param.put("ActiveState","");
        param.put("User","");
        param.put("Group","");
        param.put("ExecMainStartTimestamp","");
    }

    public String getInfo (String [] inputParam) {
        name = inputParam[1];
        type = (inputParam[0].contains("timer")) ? "timer" : "service";

        if (executeCommand(String.format("systemctl status %s.%s",name,type))==null) {
            return name+"."+type+ " no exists. Check if the name you entered is correct.";
        }
        Pattern pattern;
        String str;
        Matcher matcher;
        for (Map.Entry<String,String> entry: param.entrySet()) {
            pattern = Pattern.compile(param.get(entry.getKey())+"=(.*)");

            str = executeCommand(String.format("systemctl show %s.%s -p %s", name,type,entry.getKey()));
            matcher = pattern.matcher(str);
            if(matcher.find()) {
                param.put(entry.getKey(),matcher.group(1));
            }
        }
        return type.equals("timer") ? getTimerInfo() : getServicenfo();

    }

    public String executeCommand(String command) {
        String s = null;
        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            s = reader.readLine();
            System.out.println(s);
            p.waitFor();
            p.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return s;
    }


    public String getTimerInfo () {
        return new StringBuilder().append(name).append(".")
                .append(type).append(" ")
                .append(param.get("Active")).append(", last started ")
                .append(param.get("ExecMainStartTimestamp")).toString();
    }

    public String getServicenfo() {
        return new StringBuilder().append(name).append(".")
                .append(type).append(" ")
                .append(param.get("ActiveState")).append(", ")
                .append(param.get("User").equals("")? "none" : param.get("User")).append(" Users, ")
                .append(param.get("Group").equals("")? "none" : param.get("Group")).append(" Group, ")
                .append("last started ").append(param.get("ExecMainStartTimestamp")).toString();
    }

}
