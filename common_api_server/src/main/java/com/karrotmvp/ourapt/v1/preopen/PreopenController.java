package com.karrotmvp.ourapt.v1.preopen;


import com.karrotmvp.ourapt.v1.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/v1/preopen")
public class PreopenController {

    @Autowired
    private PreopenRepository preopenRepository;

    @Autowired
    private UserRepository userRepository;
}