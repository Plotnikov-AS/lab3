package ru.pis.lab3.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "course", schema = "lab3_schema")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
}
