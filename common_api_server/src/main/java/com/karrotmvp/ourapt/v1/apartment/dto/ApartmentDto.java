package com.karrotmvp.ourapt.v1.apartment.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ApartmentDto {
    private String id;
    private String keyName;
    private String channelName;
    private String nameDepth1;
    private String regionHashDepth1;
    private String nameDepth2;
    private String regionHashDepth2;
    private String nameDepth3;
    private String regionHashDepth3;
    private String nameDepth4;
    private String regionHashDepth4;
    private int channelDepthLevel;
}
