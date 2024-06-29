package com.curcus.lms.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_id")
    private Long user_id;
    private String email;
    private String password;

    private String first_name;
    private String last_name;
    private String headline;
    private String description;
    private String website_link;
    private String facebook_link;
    private String twitter_link;
    private String linkedin_link;
    private String youtube_link;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Student student;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Instructor instructor;

    // public User() {

    // }
    // public User(Long user_id, String email, String password, String role, String
    // first_name,
    // String last_name, String headline, String description, String website_link,
    // String facebook_link,
    // String twitter_link, String linkedin_link, String youtube_link) {
    // this.user_id = user_id;
    // this.email = email;
    // this.password = password;
    // this.role = role;
    // this.first_name = first_name;
    // this.last_name = last_name;
    // this.headline = headline;
    // this.description = description;
    // this.website_link = website_link;
    // this.facebook_link = facebook_link;
    // this.twitter_link = twitter_link;
    // this.linkedin_link = linkedin_link;
    // this.youtube_link = youtube_link;
    // }

    // public Long getUser_id() {
    // return user_id;
    // }

    // public void setUser_id(Long user_id) {
    // this.user_id = user_id;
    // }

    // public String getEmail() {
    // return email;
    // }

    // public void setEmail(String email) {
    // this.email = email;
    // }

    // public String getPassword() {
    // return password;
    // }

    // public void setPassword(String password) {
    // this.password = password;
    // }

    // public String getRole() {
    // return role;
    // }

    // public void setRole(String role) {
    // this.role = role;
    // }

    // public String getFirst_name() {
    // return first_name;
    // }

    // public void setFirst_name(String first_name) {
    // this.first_name = first_name;
    // }

    // public String getLast_name() {
    // return last_name;
    // }

    // public void setLast_name(String last_name) {
    // this.last_name = last_name;
    // }

    // public String getHeadline() {
    // return headline;
    // }

    // public void setHeadline(String headline) {
    // this.headline = headline;
    // }

    // public String getDescription() {
    // return description;
    // }

    // public void setDescription(String description) {
    // this.description = description;
    // }

    // public String getWebsite_link() {
    // return website_link;
    // }

    // public void setWebsite_link(String website_link) {
    // this.website_link = website_link;
    // }

    // public String getFacebook_link() {
    // return facebook_link;
    // }

    // public void setFacebook_link(String facebook_link) {
    // this.facebook_link = facebook_link;
    // }
    // public String getTwitter_link() {
    // return twitter_link;
    // }

    // public void setTwitter_link(String twitter_link) {
    // this.twitter_link = twitter_link;
    // }
    // public String getLinkedin_link() {
    // return linkedin_link;
    // }

    // public void setLinkedin_link(String linkedin_link) {
    // this.linkedin_link = linkedin_link;
    // }

    // public String getYoutube_link() {
    // return youtube_link;
    // }

    // public void setYoutube_link(String youtube_link) {
    // this.youtube_link = youtube_link;
    // }
}
