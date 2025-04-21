package comp3111.examsystem.entity;

import java.util.List;

/**
 * @author Wong Hon Yin
 * Sting username -> username MUST be unique
 * String name -> Student name
 * String age -> age of student
 * String gender -> Professor/Lecturer/Teaching Assistant
 * String department -> String of department name
 * String password -> String of password
 */
public class Student extends Entity{
    private String username;
    private String name;
    private String age;
    private String gender;
    private String department;
    private String password;

    public Student() {
        super();
    }

    public Student(Long id, String username, String name, String age, String gender, String department, String password) {
        super(id);
        this.username = username;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.department = department;
        this.password = password;
    }

    // Getters and Setters
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

    /**
     * get age (String version)
     * @return String age
     * @author Wong Hon Yin
     */
    public String getAge() {
        return age;
    }

    /**
     * get age (integer version)
     * @return Integer Age
     * @author Wong Hon Yin
     */
    public Integer getAgeAsInteger() {
        try{
            return Integer.parseInt(age);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * set age (String version)
     * @param age String age
     * @author Wong Hon Yin
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * set age (integer version)
     * @param age integer age
     * @author Wong Hon Yin
     */
    public void setAgeUsingInteger(Integer age) {
        if(age != null) {
            this.age = age.toString();
        } else {
            this.age = null;
        }

    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

