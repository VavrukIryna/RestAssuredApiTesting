package RESTAssuredClient.RESTAssuredClient;

import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.util.Objects;

public class Employee {

    private String empNo;
    private String empName;
    private String position;
    private DateTime timestamp;

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }



    public Employee() {

    }

    public Employee(String empNo, String empName, String position, DateTime timestamp) {
        this.empNo = empNo;
        this.empName = empName;
        this.position = position;
        this.timestamp = timestamp;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empNo='" + empNo + '\'' +
                ", empName='" + empName + '\'' +
                ", position='" + position + '\'' +
                '}';
    }

}