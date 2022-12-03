package com.leehyeonmin34.weather_reminder.domain.dust_info.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class DustInfo {

    @Id
    @GeneratedValue
    Long id;

}
