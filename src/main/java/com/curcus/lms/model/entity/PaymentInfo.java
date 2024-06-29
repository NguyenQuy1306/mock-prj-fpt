package com.curcus.lms.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "payment_infos")
public class PaymentInfo {
    @Id
    @GeneratedValue
    private Long paymentId;



    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "userId")
    private User studentId;

    @OneToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "cartId")
    private Cart cartId;

    @Column(nullable = false)
    private String paymentMethod;
    @Column(nullable = false)
    private Long totalPrice;
}
