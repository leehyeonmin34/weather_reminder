package com.leehyeonmin34.weather_reminder.global;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@Table(name = "test_domain")
@AllArgsConstructor
public class TestDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    private String name;

    @Column
    @Convert(converter = TimeConverter.class)
    private LocalDateTime time;

    @Column
    private int column2;

    @Column
    private int column3;

    @Column
    private int column4;

    @Column
    private int column5;

    @Column
    private int column6;

    @Column
    private int column7;





}
