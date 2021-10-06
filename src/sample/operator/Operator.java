package sample.operator;

public class Operator {
    private String firstName;
    private String lastName;
    private String classOper;
    private String position;

    public static int id;

    public Operator() {
    }

    public Operator(String firstName, String lastName, String classOper, String position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.classOper = classOper;
        this.position = position;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getClassOper() {
        return classOper;
    }

    public void setClassOper(String classOper) {
        this.classOper = classOper;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
