package com.leehyeonmin34.weather_reminder.domain.user.domain;

import com.leehyeonmin34.weather_reminder.domain.user.model.*;
import com.leehyeonmin34.weather_reminder.domain.user.repository.RegionListConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Embedded
    @AttributeOverride(name = "conditionCelcius", column = @Column(name = "cold_noti_condition_ceclius"))
    private ColdNotiSetting coldNotiSetting;

    @Embedded
    @AttributeOverride(name = "conditionCelcius", column = @Column(name = "hot_noti_condition_ceclius"))
    private HotNotiSetting hotNotiSetting;

    @Embedded
    @AttributeOverride(name = "conditionTime", column = @Column(name = "rain_noti_condition_time"))
    private RainNotiSetting rainNotiSetting;

    @Embedded
    @AttributeOverride(name = "conditionTime", column = @Column(name = "snow_noti_condition_time"))
    private SnowNotiSetting snowNotiSetting;

//    @Embedded
//    private DustNotiSetting dustNotiSetting;

    @Column(name = "remind_at", updatable = true, nullable = true)
    private byte remindAt;

    @Convert(converter = RegionListConverter.class)
    private List<Region> regionList;

    private LocalDateTime pauseUntil;

    public void setRemindAt(byte remindAt){
        this.remindAt = remindAt;
    }

    public void addRegion(String regionCode){
        this.regionList.add(Region.of(regionCode));
    }

    public void removeRegion(String regionCode){
        this.regionList.remove(Region.of(regionCode));
    }

    public void pauseRemindingForNDays(Long n){ this.pauseUntil = LocalDateTime.now().plusDays(n);}

    public void restartReminding(){ this.pauseUntil = null;}

    public boolean wantRemind(){
        return pauseUntil == null || pauseUntil.isBefore(LocalDateTime.now());
    }


}
