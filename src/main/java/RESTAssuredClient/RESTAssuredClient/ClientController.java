package RESTAssuredClient.RESTAssuredClient;

import java.util.Arrays;

public class ClientController {

    public static boolean checkEmployeeParameters(Employee employee){
        boolean response = false;
        if (employee.getEmpNo().equals("E01")){
            if(employee.getEmpName().toLowerCase().equals("ira")){
                if (employee.getPosition().toLowerCase().equals("qa")){
                    response = true;
                }
            }
        }
       return  response;
    }

   /* static final String URL_EMPLOYEES = "http://localhost:8080/employees";
    static final String URL_CREATE_EMPLOYEE = "http://localhost:8080/employee";
    static final String URL_UPDATE_EMPLOYEE = "http://localhost:8080/employee";
    static final String URL_EMPLOYEE_PREFIX = "http://localhost:8080/employee";

    public static void getAllEmployees(){
        // HttpHeaders
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        // Request to return JSON format
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("my_other_key", "my_other_value");

        // HttpEntity<String>: To get result as String.
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        // RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Send request with GET method, and Headers.
        ResponseEntity<String> response = restTemplate.exchange(URL_EMPLOYEES,
                HttpMethod.GET, entity, String.class);

        String result = response.getBody();

        System.out.println(result);

    }

    public static void putEmployee(){

        String empNo = "E01";

        Employee updateInfo = new Employee(empNo, "Tom", "Clerk");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<Employee> requestBody = new HttpEntity<>(updateInfo, headers);

        // Send request with PUT method.
        restTemplate.put(URL_UPDATE_EMPLOYEE, requestBody, new Object[] {});

        String resourceUrl = URL_EMPLOYEE_PREFIX + "/" + empNo;

        Employee e = restTemplate.getForObject(resourceUrl, Employee.class);

        if (e != null) {
            System.out.println("(Client side) Employee after update: ");
            System.out.println("Employee: " + e.getEmpNo() + " - " + e.getEmpName());
        }
    }


    public static void deleteEmployee(){
        RestTemplate restTemplate = new RestTemplate();

        // empNo="E01"
        String resourceUrl = "http://localhost:8080/employee/E01";

        // Send request with DELETE method.
        restTemplate.delete(resourceUrl);

        // Get
        Employee e = restTemplate.getForObject(resourceUrl, Employee.class);

        if (e != null) {
            System.out.println("(Client side) Employee after delete: ");
            System.out.println("Employee: " + e.getEmpNo() + " - " + e.getEmpName());
        } else {
            System.out.println("Employee not found!");
        }

    }
    public static void postEmployee(){
        Employee newEmployee = new Employee("E11", "New", "Bew");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<Employee> requestBody = new HttpEntity<>(newEmployee, headers);

        // Send request with POST method.
        ResponseEntity<Employee> result
                = restTemplate.postForEntity(URL_CREATE_EMPLOYEE, requestBody, Employee.class);

        System.out.println("Status code:" + result.getStatusCode());

        // Code = 200.
        if (result.getStatusCode() == HttpStatus.OK) {
            Employee e = result.getBody();
            System.out.println("(Client Side) Employee Created: "+ e.getEmpNo());
        }

    }*/
}
