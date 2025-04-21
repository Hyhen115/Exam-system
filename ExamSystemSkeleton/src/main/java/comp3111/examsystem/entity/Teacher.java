package comp3111.examsystem.entity;

/**
 * @author Wong Hon Yin
 * String username -> unique username
 * String name -> name of teacher
 * String gender -> Male/Female/Non-Binary
 * String age -> age of teacher
 * String position -> Professor/Lecturer/Teaching Assistant
 * String department -> string of department name
 * String password -> string of password
 */
public class Teacher extends Entity {
    private String username;
    private String name;
    private String gender;
    private String age;
    private String position;
    private String department;
    private String password;
    // Default constructor
    public Teacher() {
        super(); // Calls the default constructor of Entity
    }

    public Teacher(Long id, String username, String name, String gender, String age, String position, String department, String password) {
        super(id);
        this.username = username;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.position = position;
        this.department = department;
        this.password = password;
    }

    // getter and setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * will get string format of age
     * @return String age
     * @author Wong Hon Yin
     */
    public String getAge() {
        return age;
    }

    /**
     * The function to return the age (integer version)
     * @author Wong Hon Yin
     * @return int age
     */
    public Integer getIntegerAge() {
        try {
            return Integer.parseInt(age); // Convert String to Integer
        } catch (NumberFormatException e) {
            return null; // or handle it as needed
        }
    }

    /**
     * set age using string
     * @param age String age
     * @author Wong Hon Yin
     */
    public void setAgeUsingString(String age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
