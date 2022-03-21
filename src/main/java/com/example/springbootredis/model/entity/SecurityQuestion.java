package com.example.springbootredis.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "security_question_info")
@Data
public class SecurityQuestion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_type")
    private Integer questionType;

    @Column(name = "question")
    private String question;

    @Column(name = "locale")
    private Integer locale;

    @Column(name = "group_id")
    private String groupId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "is_active")
    private Boolean isActive;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "is_delete")
    private Boolean isDelete;
}
