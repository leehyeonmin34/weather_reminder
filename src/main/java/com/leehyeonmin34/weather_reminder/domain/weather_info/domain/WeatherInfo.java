package com.leehyeonmin34.weather_reminder.domain.weather_info.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class WeatherInfo {

    @Id
    @GeneratedValue
    Long id;

}
