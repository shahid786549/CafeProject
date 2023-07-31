package com.cafe.Entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "Bill.getAllBills",query="select b from Bill b order by b.id desc")
@NamedQuery(name = "Bill.getBillByUserName",query="select b from Bill b where b.createdBy=:username order by b.id desc")
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name="bill")
public class Bill implements Serializable {

    private static final long serailVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="uuid")
    private String uuid;

    @Column(name="name")
    private String name;

    @Column(name="contactNumber")
    private String contactNumber;

    @Column(name="email")
    private String email;

    @Column(name="paymentMethod")
    private String paymentMethod;

    @Column(name="total")
    private Integer total;

    @Column(name="productDetails",columnDefinition ="json")
    private String productDetails;

    @Column(name="createdBy")
    private String createdBy;
}
