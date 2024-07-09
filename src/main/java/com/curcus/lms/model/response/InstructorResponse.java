package com.curcus.lms.model.response;

import java.io.Serializable;
import java.util.Objects;

public class InstructorResponse implements Serializable{
    private Long instructorId;
    private String name;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    public Long getinstructorId() {
        return instructorId;
    }
    public void setinstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    @Override
    public String toString() {
        return "InstructorResponse{" +
                "studentId=" + instructorId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        InstructorResponse that = (InstructorResponse) o;
        return Objects.equals(instructorId, that.instructorId) && Objects.equals(name, that.name)
                && Objects.equals(email, that.email) && Objects.equals(firstName, that.firstName)
                && Objects.equals(lastName, that.lastName) && Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instructorId, name, email, firstName, lastName, phoneNumber);
    }


}
