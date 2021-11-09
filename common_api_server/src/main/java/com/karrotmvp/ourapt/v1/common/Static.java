package com.karrotmvp.ourapt.v1.common;

import com.karrotmvp.ourapt.v1.apartment.entity.Region;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Static {
    public static Map<String, Region> regionDict = new HashMap<>();
    public static Date serverStartTime;

    static {
        //depth3
        Static.regionDict.put("test", new Region("test", "테스트리전이름"));
        Static.regionDict.put("1f0758ccde06", new Region("1f0758ccde06", "송도1동"));
        Static.regionDict.put("a87002cc41f1", new Region("a87002cc41f1", "송도2동"));
        Static.regionDict.put("0b96cc858bf6", new Region("0b96cc858bf6","송도3동"));
        Static.regionDict.put("6a7eefda7865", new Region("6a7eefda7865","잠실2동"));
        Static.regionDict.put("ee1c4acc500b", new Region("ee1c4acc500b","잠실3동"));
        // depth4
        Static.regionDict.put("b7ca1e49757c", new Region("b7ca1e49757c","송도1동 더샵하버뷰"));
        Static.regionDict.put("2eba3a86bcce", new Region("2eba3a86bcce","송도1동 롯데캐슬"));
        Static.regionDict.put("0601eb4940ba", new Region("0601eb4940ba","송도1동 e편한세상"));
        Static.regionDict.put("f955430329f9", new Region("f955430329f9","송도1동 한진해모로"));
        Static.regionDict.put("a90515eb2ebf", new Region("a90515eb2ebf","송도1동 마스터뷰"));
        Static.regionDict.put("ce58d8005285", new Region("ce58d8005285","송도1동 성지아파트"));
        Static.regionDict.put("6770c9f9e7b3", new Region("6770c9f9e7b3","송도1동 현대아이파크"));
        Static.regionDict.put("d3b0f3c81c7b", new Region("d3b0f3c81c7b","송도1동 금호어울림"));
        Static.regionDict.put("0af1ffae235f", new Region("0af1ffae235f","송도2동 더샵엑스포"));
        Static.regionDict.put("a14a8bca0f2e", new Region("a14a8bca0f2e","송도1동 월드마크 1,2 단지"));
        Static.regionDict.put("3167ae4b772a", new Region("3167ae4b772a","송도1동 호반베르디움 2차"));
        Static.regionDict.put("5b3b9b16f3bf", new Region("5b3b9b16f3bf","송도1동 자이하버뷰"));
        Static.regionDict.put("ae6188ad0d7d", new Region("ae6188ad0d7d","송도1동 코오롱 더프라우"));
        Static.regionDict.put("7176c1b017ab", new Region("7176c1b017ab","송도1동 그린워크"));
        Static.regionDict.put("2071edd4fa6c", new Region("2071edd4fa6c","송도1동 코오롱 더프라우 3단지"));
        Static.regionDict.put("ef08ad4f4af3", new Region("ef08ad4f4af3","잠실3동 잠실주공5단지"));
        Static.regionDict.put("f9ea71209dee", new Region("f9ea71209dee","송도1동 퍼스트월드"));
        Static.regionDict.put("36dc06db13e2", new Region("36dc06db13e2","송도3동 그린스퀘어"));
        Static.regionDict.put("bf0ad88cdb07", new Region("bf0ad88cdb07","송도3동 롯데캐슬 캠퍼스타운"));
        Static.regionDict.put("cc5cda36231b", new Region("cc5cda36231b","송도1동 푸르지오 하버뷰"));
        Static.regionDict.put("95f8d341827f", new Region("95f8d341827f","송도1동 해모로월드뷰"));
        Static.regionDict.put("21048a8304cf", new Region("21048a8304cf","송도1동 더샵센트럴파크"));
        Static.regionDict.put("c08ea5b8649d", new Region("c08ea5b8649d","송도1동 아메리칸타운"));
        Static.regionDict.put("6d157a4858e4", new Region("6d157a4858e4","송도1동 아트윈 푸르지오"));
        Static.regionDict.put("10895b29a526", new Region("10895b29a526","송도1동 힐스테이트"));
        Static.regionDict.put("fe82298e3c63", new Region("fe82298e3c63","송도3동 글로벌캠퍼스푸르지오"));
        Static.regionDict.put("e020eb41d01b", new Region("e020eb41d01b","잠실2동 잠실엘스"));
        Static.regionDict.put("2acd52800525", new Region("2acd52800525","송도1동 웰카운티"));
        Static.regionDict.put("37ac0da953d5", new Region("37ac0da953d5","송도3동 센트럴시티"));
        Static.regionDict.put("909a193d3ff2", new Region("909a193d3ff2","잠실2동 잠실리센츠"));
        Static.regionDict.put("df115ab931cb", new Region("df115ab931cb","잠실3동 잠실 트리지움"));
        Static.regionDict.put("996bc98b6583", new Region("996bc98b6583","송도1동 풍림아이원"));
        Static.regionDict.put("77a5e929f0cd", new Region("77a5e929f0cd","송도1동 월드마크 7, 8 단지"));
        Static.regionDict.put("5b1a7c427f53", new Region("5b1a7c427f53","송도1동 퍼스트파크"));
        Static.regionDict.put("292dd084eabf", new Region("292dd084eabf","송도1동 센트럴파크푸르지오"));
        Static.regionDict.put("02518c408a28", new Region("02518c408a28","송도1동 그린애비뉴"));
    }

    public static KarrotProfile makeAdminKarrotProfile(String userId) {
        return new KarrotProfile(userId, "우리아파트(관리자)",
          "<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 32 32'><defs><style>.cls-1{fill:#e95454;}.cls-2{fill:#fff;}</style></defs><g id='레이어_2' data-name='레이어 2'><g id='레이어_1-2' data-name='레이어 1'><circle class='cls-1' cx='16' cy='16' r='16'/><path class='cls-2' d='M6,17.07v1.51H5.19V17.07H3.06v-.72H8.14v.72ZM4.65,15.91a1.16,1.16,0,0,1-1.22-1.23,1.16,1.16,0,0,1,1.22-1.23h1.9a1.23,1.23,0,1,1,0,2.46Zm.09-1.75a.5.5,0,0,0-.5.52.49.49,0,0,0,.5.52H6.47a.52.52,0,0,0,0-1Z'/><path class='cls-2' d='M8.54,17.76V15.28h2v-1H8.53v-.71h2.86V16h-2v1.06h2.42v.71Zm3.67.82V13.42H13v5.16Z'/><path class='cls-2' d='M16.57,16.53a1.36,1.36,0,0,1-1.49,1.37,1.35,1.35,0,0,1-1.49-1.37V14.87a1.35,1.35,0,0,1,1.49-1.37,1.36,1.36,0,0,1,1.49,1.37ZM15.76,15c0-.46-.23-.75-.68-.75s-.69.29-.69.75v1.48c0,.46.23.75.69.75s.68-.29.68-.75Zm1.36,3.62V13.42h.81v1.77h.7V16h-.7v2.62Z'/><path class='cls-2' d='M18.59,17.7V17h.53V14.36h-.47v-.72H22v.72h-.52V17H22v.72Zm2-3.34h-.7V17h.7Zm1.69,4.22V13.42h.81V15.3h.7v.77h-.7v2.51Z'/><path class='cls-2' d='M23.86,18.23v-.71h5.08v.71Zm.47-1.37v-3.3h4.14v.71H25.14v.58h3.27v.7H25.14v.59h3.42v.72Z'/></g></g></svg>");
    }
}
