package com.leehyeonmin34.weather_reminder.domain.user.domain;

import com.leehyeonmin34.weather_reminder.domain.user.model.*;
import com.leehyeonmin34.weather_reminder.domain.user.repository.RegionListConverter;
import com.leehyeonmin34.weather_reminder.global.common.model.DayTime;
import com.leehyeonmin34.weather_reminder.global.common.model.DayTimeDBConverter;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "user")
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private ColdNotiSetting coldNotiSetting = new ColdNotiSetting();

    @Embedded
    @AttributeOverride(name = "conditionCelcius", column = @Column(name = "hot_noti_condition_ceclius"))
    private HotNotiSetting hotNotiSetting = new HotNotiSetting();

    @Embedded
    @AttributeOverride(name = "conditionTime", column = @Column(name = "rain_noti_condition_time"))
    private RainNotiSetting rainNotiSetting = new RainNotiSetting();

    @Embedded
    @AttributeOverride(name = "conditionTime", column = @Column(name = "snow_noti_condition_time"))
    private SnowNotiSetting snowNotiSetting = new SnowNotiSetting();

    @Convert(converter = DayTimeDBConverter.class)
    @Column(name = "noti_time", updatable = true, nullable = true)
    private DayTime notiTime;

    @Convert(converter = RegionListConverter.class)
    @Column(name = "region_list", updatable = true, nullable = true)
    private List<Region> regionList = new ArrayList<>();

    @Column(name = "pause_until", updatable = true, nullable = true)
    private LocalDateTime pauseUntil;

    private User(Builder builder){
        this.id = builder.getId();
        this.createdAt = builder.getCreatedAt();
        this.updatedAt = builder.getUpdatedAt();
        this.coldNotiSetting = builder.getColdNotiSetting();
        this.hotNotiSetting = builder.getHotNotiSetting();
        this.rainNotiSetting = builder.getRainNotiSetting();
        this.snowNotiSetting = builder.getSnowNotiSetting();
        this.notiTime = builder.getNotiTime();
        this.regionList = builder.getRegionList();
        this.pauseUntil = builder.pauseUntil;
    }

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    public static class Builder{

        private Long id;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private ColdNotiSetting coldNotiSetting = new ColdNotiSetting();
        private HotNotiSetting hotNotiSetting = new HotNotiSetting();
        private RainNotiSetting rainNotiSetting = new RainNotiSetting();
        private SnowNotiSetting snowNotiSetting = new SnowNotiSetting();
        private DayTime notiTime;
        private List<Region> regionList = new ArrayList<>();
        private LocalDateTime pauseUntil;

        public Builder id(Long id){
            this.setId(id);
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt){
            this.setId(id);
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt){
            this.setUpdatedAt(updatedAt);
            return this;
        }

        public Builder coldNotiSetting(ColdNotiSetting coldNotiSetting){
            this.setColdNotiSetting(coldNotiSetting);
            return this;
        }

        public Builder hotNotiSetting(HotNotiSetting hotNotiSetting){
            this.setHotNotiSetting(hotNotiSetting);
            return this;
        }

        public Builder rainNotiSetting(RainNotiSetting rainNotiSetting){
            this.setRainNotiSetting(rainNotiSetting);
            return this;
        }

        public Builder snowNotiSetting(SnowNotiSetting snowNotiSetting){
            this.setSnowNotiSetting(snowNotiSetting);
            return this;
        }

        public Builder notiTime(DayTime notiTime){
            this.setNotiTime(notiTime);
            return this;
        }

        public Builder regionList(List<Region> regionList){
            this.setRegionList(regionList);
            return this;
        }

        public Builder pauseUntil(LocalDateTime pauseUntil){
            this.setPauseUntil(pauseUntil);
            return this;
        }

        public User build(){
            return new User(this);
        };
    }


    public void setNotiTime(DayTime notiTime){
        this.notiTime = notiTime;
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
