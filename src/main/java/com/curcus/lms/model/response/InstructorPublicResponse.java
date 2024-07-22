package com.curcus.lms.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructorPublicResponse {
    private Long instructorId;
    private String name;
    private String firstName;
    private String lastName;

    @Override
    public String toString() {
        return "InstructorResponse{" +
                "instructorId=" + instructorId +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        InstructorPublicResponse that = (InstructorPublicResponse) o;
        return Objects.equals(instructorId, that.instructorId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);
    }


    @Override
    public int hashCode() {
        return Objects.hash(instructorId, name, firstName, lastName);
    }
}
