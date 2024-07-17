package com.curcus.lms.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cart_items")
public class CartItems {
    @EmbeddedId
    private CartItemsId id;

    @ManyToOne
    @MapsId("cartId")
    @JoinColumn(name = "cart_id", referencedColumnName = "cartId")
    private Cart cart;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id", referencedColumnName = "courseId")
    private Course course;
}
