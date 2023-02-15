package com.leehyeonmin34.weather_reminder.domain.user.service;

import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.user.model.NotiSetting;
import com.leehyeonmin34.weather_reminder.domain.user.model.NotiType;
import com.leehyeonmin34.weather_reminder.domain.user.repository.UserRepository;
import com.leehyeonmin34.weather_reminder.global.error.exception.UnhandledEnumTypeAtSwitchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
@RequiredArgsConstructor
public class NotiSettingUtils {

    private final UserRepository userRepository;

    public void turnOff(final Long userId, final String notiTypeCode){
        findNotiSetting(userId, notiTypeCode).turnOff();
    }

    public boolean isOn(final Long userId, final String notiTypeCode){
        return findNotiSetting(userId, notiTypeCode).isOn();
    }

    public NotiSetting findNotiSetting(final Long userId, final String notiTypeCode){
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        return findNotiSetting(user, notiTypeCode);
    }

    public NotiSetting findNotiSetting(final User user, final String notiTypeCode){
        return findNotiSetting(user, NotiType.of(notiTypeCode));
    }

    public NotiSetting findNotiSetting(final User user, final NotiType notiType){
        switch(notiType){
            case RAIN:
                return user.getRainNotiSetting();
            case SNOW:
                return user.getSnowNotiSetting();
            case HOT:
                return user.getHotNotiSetting();
            case COLD:
                return user.getColdNotiSetting();
            default:
                throw new UnhandledEnumTypeAtSwitchException(NotiType.class, notiType.getCode());
        }
    }

}
