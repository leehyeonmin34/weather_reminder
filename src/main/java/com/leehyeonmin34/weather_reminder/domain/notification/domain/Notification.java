package com.leehyeonmin34.weather_reminder.domain.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@AllArgsConstructor
@Getter
@Entity
public class Notification {

    @Id
    @GeneratedValue
    private Long id;

    private String msg;

    private Long userId;

    static public class Builder{

        private String msg;

        private Long userId;

        public Builder(String msg, Long userId){
            this.msg = msg;
            this.userId = userId;
        }

        public Notification build(){
            return new Notification(this);
        }

    }

    private Notification(Builder builder){
        this.msg = builder.msg;
        this.userId = builder.userId;
    }

}
