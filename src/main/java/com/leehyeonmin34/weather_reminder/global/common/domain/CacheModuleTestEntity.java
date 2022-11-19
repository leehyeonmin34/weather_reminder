package com.leehyeonmin34.weather_reminder.global.common.domain;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class CacheModuleTestEntity implements Serializable {
    private String key;
    private String value;

    public CacheModuleTestEntity(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheModuleTestEntity that = (CacheModuleTestEntity) o;
        return Objects.equals(key, that.key) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
