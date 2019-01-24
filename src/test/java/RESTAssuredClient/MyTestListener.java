package RESTAssuredClient;

import io.qameta.allure.Attachment;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class MyTestListener extends MyTests implements ITestListener {
    private static String getTestMethodName (ITestResult iTestResult){
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }
@Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog(String message){
        return message;
}

}
