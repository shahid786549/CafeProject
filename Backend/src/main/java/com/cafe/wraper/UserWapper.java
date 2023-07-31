package com.cafe.wraper;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWapper {

    private Integer id;

    private String name;

    private String email;

    private  String contactNumber;

    private String status;

    public UserWapper(Integer id, String name, String email, String contactNumber, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.status = status;
    }


}
