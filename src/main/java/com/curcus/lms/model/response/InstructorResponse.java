package com.curcus.lms.model.response;

import lombok.Getter;
import lombok.Setter;
import java.util.Objects;
import java.io.Serializable;

@Setter
@Getter
public class InstructorResponse implements Serializable {

    private Long instructorId;
    private String name;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    @Override
    public String toString() {
        return "InstructorResponse{" +
                "instructorId=" + instructorId +
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
