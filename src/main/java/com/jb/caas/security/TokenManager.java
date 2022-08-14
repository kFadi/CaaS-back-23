package com.jb.caas.security;

/*
 * copyrights @ fadi
 */

import com.jb.caas.exceptions.CouponSecurityException;
import com.jb.caas.exceptions.SecMsg;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TokenManager {

    private final Map<UUID, Information> map;

    //////////////////////////////////////////////////

    public UUID add(ClientType type, int id) {

        removeInstances(type, id);

        Information information = Information.builder()
                .type(type)
                .id(id)
                .time(LocalDateTime.now())
                .build();

        UUID token = UUID.randomUUID();

        map.put(token, information);

        return token;
    }

    //////////////////////////////////////////////////

    public void removeInstances(ClientType type, int id) {
        map.entrySet().removeIf(ins -> (ins.getValue().getType() == type && ins.getValue().getId() == id));
    }

    //////////////////////////////////////////////////

    @Scheduled(fixedRate = 1000 * 60)
    public void removeExpiredInstancesOver30Minutes() {
        map.entrySet().removeIf(ins -> ins.getValue().getTime().isBefore(LocalDateTime.now().minusMinutes(30)));
    }

    //////////////////////////////////////////////////

    public int getIdByToken(UUID token) throws CouponSecurityException {

        Information information = map.get(token);
        if (information == null) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return information.getId();
    }

    //////////////////////////////////////////////////

    public ClientType getTypeByToken(UUID token) throws CouponSecurityException {

        Information information = map.get(token);
        if (information == null) {
            throw new CouponSecurityException(SecMsg.INVALID_TOKEN);
        }
        return information.getType();
    }

    //////////////////////////////////////////////////
}
