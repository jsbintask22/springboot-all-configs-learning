package cn.jsbintask.springbootallconfigs.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author jsbintask@gmail.com
 * @date 2019/2/15 17:09
 */
@Data
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
}
