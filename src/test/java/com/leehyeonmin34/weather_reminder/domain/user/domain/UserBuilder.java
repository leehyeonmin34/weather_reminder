package com.leehyeonmin34.weather_reminder.domain.user.domain;

import com.leehyeonmin34.weather_reminder.domain.user.model.Region;

public class UserBuilder {

    static public User buildByOneRegion(){
        User user = new User();
        user.getRegionList().add(Region.SEOUL);
        user.getColdNotiSetting().changeConditionCelcius((byte) 0);
        user.getHotNotiSetting().changeConditionCelcius((byte) 3);
        user.getRainNotiSetting().changeConditionTime((byte) 8);
        return user;
    }

    static public User buildByTwoRegion(){
        User user = buildByOneRegion();
        user.getRegionList().add(Region.BUSAN);
        return user;
    }

    static public User buildUserNoNoti(){
        User user = new User();
        return user;
    }

}
