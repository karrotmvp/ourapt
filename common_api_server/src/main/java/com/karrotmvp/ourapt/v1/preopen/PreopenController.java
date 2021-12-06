package com.karrotmvp.ourapt.v1.preopen;

import com.karrotmvp.ourapt.v1.auth.CurrentUser;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/preopen")
@AllArgsConstructor
public class PreopenController {
  private final EntityManager em;

  @PostMapping("")
  @Transactional
  public CommonResponseBody<Void> reserve(
    @CurrentUser KarrotProfile profile
  ) {
    em.persist(new Preopen(profile.getId(), new Date()));
    return CommonResponseBody.<Void>builder()
      .success().build();
  }
}
