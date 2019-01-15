package RESTAssuredClient.RESTAssuredClient;

import org.testng.annotations.DataProvider;

public class DataProviderSource {
    @DataProvider(name = "getAllEmployeeNumber")
    public static Object[][] createTestData() {

        return new String[][] {
                {"E01"},
                {"E02"},
                {"E03"}
        };
    }

    @DataProvider(name = "getAllEmployeeName")
    public static Object[][] createTestDataName() {

        return new String[][] {
                {"E01", "Ira"},
                {"E02", "Olya"},
                {"E03", "Katya"}
        };
    }
}
