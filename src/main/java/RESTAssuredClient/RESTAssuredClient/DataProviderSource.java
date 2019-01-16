package RESTAssuredClient.RESTAssuredClient;

import org.testng.annotations.DataProvider;

import java.util.ArrayList;

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

    @DataProvider(name = "createUser")
    public static Object[][] createUserObject() {

        return new Object[][] {
                {new User(19,"testusername","testfirstName","testlastname","testemail", "testpassword", "testphone", 7)},
                {new User(20,"testusername20","testfirstName20","testlastname20","testemail20", "testpassword20", "testphone20", 7)},
        };
    }

    @DataProvider(name = "createListOfUsers")
    public static Object[] createUsersList() {

        ArrayList<User> userArrayList= new ArrayList<>();
        userArrayList.add(new User(5,"testusername5","testfirstName5","testlastname5","testemai5l", "testpassword5", "testphone5", 5));
        userArrayList.add(new User(4,"4test","4test","4test","4test", "4test", "444444", 4));

        Object[] objArray = userArrayList.toArray();

        return objArray;
    }

}
