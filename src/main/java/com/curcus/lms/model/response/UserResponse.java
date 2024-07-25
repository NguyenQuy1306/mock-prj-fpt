package com.curcus.lms.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse implements Serializable {
        private int userId;
        private String name;
        private String email;
        private String avtUrl;
        private String firstName;
        private String lastName;
        private String phoneNumber;

        @Override
        public String toString() {
            return "StudentResponse{" +
                    "studentId=" + userId +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", phoneNumber='" + phoneNumber + '\'' +
                    ", avtUrl='" + avtUrl + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            UserResponse that = (UserResponse) o;
            return Objects.equals(userId, that.userId) && Objects.equals(name, that.name)
                    && Objects.equals(email, that.email) && Objects.equals(firstName, that.firstName)
                    && Objects.equals(lastName, that.lastName) && Objects.equals(phoneNumber, that.phoneNumber)
                    && Objects.equals(avtUrl, that.avtUrl);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, name, email, firstName, lastName, phoneNumber, avtUrl);
        }
}
