package RESTAssuredClient.RESTAssuredClient;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

public class Application {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        String log4jConfPath = "C:\\Users\\Iryna_Vavruk\\JAVAPROJECTS\\RESTAssuredClient\\RestAssuredApiTesting\\src\\test\\resources\\log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);

        System.out.println("Hello RESTAssuredClient");
    }

}