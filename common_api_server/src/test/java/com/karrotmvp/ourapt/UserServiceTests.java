package com.karrotmvp.ourapt;

import com.karrotmvp.ourapt.v1.user.UserService;
import com.karrotmvp.ourapt.v1.user.dto.KarrotOApiUserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

//    @Test
//    void getKarrotUserProfilesByIdsTest() {
//        List<KarrotOApiUserDto> users = userService.getKarrotUserProfilesByIds(Set.of(new String[]{"6b5a561616df4893a977875e9e4d5c5a"}));
//    }
}
