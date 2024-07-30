package com.curcus.lms.model.response;

import lombok.Getter;
import lombok.Setter;
import java.util.Objects;
import java.io.Serializable;

@Setter
@Getter
public class StudentResponse implements Serializable {

    private int studentId;
    private String name;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    private String avtUrl;
    private String publicAvtId;
    private String userAddress;
    private String userCity;
    private String userCountry;
    private Long userPostalCode;

    @Override
    public String toString() {
        return "StudentResponse{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", avtUrl='" + avtUrl + '\'' +
                ", publicAvtId='" + publicAvtId + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", userCity='" + userCity + '\'' +
                ", userCountry='" + userCountry + '\'' +
                ", userPostalCode=" + userPostalCode +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        StudentResponse that = (StudentResponse) o;
        return Objects.equals(studentId, that.studentId)
                && Objects.equals(name, that.name)
                && Objects.equals(email, that.email)
                && Objects.equals(firstName, that.firstName)
                && Objects.equals(lastName, that.lastName)
                && Objects.equals(phoneNumber, that.phoneNumber)
                && Objects.equals(avtUrl, that.avtUrl)
                && Objects.equals(publicAvtId, that.publicAvtId)
                && Objects.equals(userAddress, that.userAddress)
                && Objects.equals(userCity, that.userCity)
                && Objects.equals(userCountry, that.userCountry)
                && Objects.equals(userPostalCode, that.userPostalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                studentId,
                name,
                email,
                firstName,
                lastName,
                phoneNumber,
                avtUrl,
                publicAvtId,
                userAddress,
                userCity,
                userCountry,
                userPostalCode);
    }
}
