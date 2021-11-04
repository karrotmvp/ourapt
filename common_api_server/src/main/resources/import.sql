-- Admin user
INSERT INTO ourapt.user (karrot_id, created_at, updated_at, banned_at, deleted_at, is_admin, push_agreed_at, checked_in) VALUES ('ADMIN', '2021-11-01 14:01:59', '2021-11-01 14:02:03', null, null, true, null, null);

-- Apartments
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('054709ba-f5b9-448e-b697-b2ddf2f84bad', '2021-10-19 15:35:49', '2021-10-19 15:35:49', 'https://phinational.org/wp-content/uploads/2017/07/fb-test-image-470x246.jpg', '더샵', null, '송도 더샵 하버뷰', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', 'a87002cc41f1', '송도2동', 'b7ca1e49757c', '더샵하버뷰');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('0d9d2adc-90e0-482e-aa0f-6f2bfb211eea', '2021-10-20 20:17:58', '2021-10-20 20:17:58', null, '롯데캐슬', '2021-10-19 15:35:49', '송도 롯데캐슬(인천송원초 앞)', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', '0b96cc858bf6', '송도3동', '2eba3a86bcce', '롯데캐슬');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('0ff46eee-953e-4ea5-b5f5-24be8acdbf8e', '2021-10-19 15:35:49', '2021-10-19 15:35:49', null, 'e편한세상', '2021-10-19 15:35:49', '송도 e편한세상', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', '7335f7ea0197', '송도5동', '0601eb4940ba', 'e편한세상');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('1626be21-95da-44b0-b5f0-b7bf8822ec02', '2021-10-20 20:24:10', '2021-10-20 20:24:10', null, '해모로', '2021-10-19 15:35:49', '송도 한진해모로', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', '1f0758ccde06', '송도1동', 'f955430329f9', '한진해모로');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('16efc0e0-d994-4891-b7d3-2ba2c040dca9', '2021-10-20 20:14:28', '2021-10-20 20:14:28', null, '더샵', '2021-10-19 15:35:49', '송도 더샵 마스터뷰', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', '5178f5b05fa5', '송도4동', 'a90515eb2ebf', '마스터뷰');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('284db946-8e7e-4ba5-8c84-4c08456946b9', '2021-10-19 15:35:49', '2021-10-19 15:35:49', null, '성지 리벨루스', '2021-10-19 15:35:49', '송도 성지 리벨루스', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', '1f0758ccde06', '송도1동', 'ce58d8005285', '성지아파트');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('3002e99e-756d-4c1d-9850-4bcb67abbe72', '2021-10-20 20:24:10', '2021-10-20 20:24:10', null, '아이파크', '2021-10-19 15:35:49', '송도 현대아이파크', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', '1f0758ccde06', '송도1동', '6770c9f9e7b3', '현대아이파크');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('322ba36a-a2d3-425f-982f-2058fb8d418e', '2021-10-19 15:35:49', '2021-10-19 15:35:49', null, '금호어울림', '2021-10-19 15:35:49', '송도 금호어울림', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', '1f0758ccde06', '송도1동', 'd3b0f3c81c7b', '금호어울림');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('34eb09d0-23c6-43bc-8ed3-a4df53eb8fa8', '2021-10-20 19:56:14', '2021-10-20 19:56:14', 'https://phinational.org/wp-content/uploads/2017/07/fb-test-image-470x246.jpg', '더샵', null, '송도 더샵 엑스포', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', 'a87002cc41f1', '송도2동', '0af1ffae235f', '더샵엑스포');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('36cc77c3-6151-4b3f-bb77-81fd03ad7ca6', '2021-10-20 19:59:42', '2021-10-20 19:59:42', null, '푸르지오', '2021-10-19 15:35:49', '송도 푸르지오 월드마크 1,2 단지', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', 'a87002cc41f1', '송도2동', 'a14a8bca0f2e', '월드마크 1,2 단지');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('44c4985a-404b-4b76-a8cc-f6e168932a4f', '2021-10-19 15:35:49', '2021-10-19 15:35:49', null, '베르디움', '2021-10-19 15:35:49', '송도 호반베르디움 2차', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', '0b96cc858bf6', '송도3동', '3167ae4b772a', '호반베르디움 2차');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('4abcf6ef-5630-427c-bd13-89f633aa0a61', '2021-10-20 19:48:19', '2021-10-20 19:48:19', 'https://phinational.org/wp-content/uploads/2017/07/fb-test-image-470x246.jpg', '자이', null, '송도 자이 하버뷰', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', 'a87002cc41f1', '송도2동', '5b3b9b16f3bf', '자이하버뷰');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('5e2375f6-f908-4824-9adb-d23d0b813442', '2021-10-20 19:57:35', '2021-10-20 19:57:35', null, '코오롱 더프라우', '2021-10-19 15:35:49', '송도 코오롱 더프라우 1, 2단지', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', 'a87002cc41f1', '송도2동', 'ae6188ad0d7d', '코오롱 더프라우');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('61ab9fdf-0b0a-4c3a-b1c2-0f49a5cb85f4', '2021-10-20 19:53:59', '2021-10-20 19:53:59', 'https://phinational.org/wp-content/uploads/2017/07/fb-test-image-470x246.jpg', '더샵', null, '송도 더샵 그린워크', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', 'a87002cc41f1', '송도2동', '7176c1b017ab', '그린워크');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('70d9253f-f9da-48b7-b821-863b1c1ced13', '2021-10-20 19:58:29', '2021-10-20 19:58:29', null, '코오롱 더프라우', '2021-10-19 15:35:49', '송도 코오롱 더프라우 3단지', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', 'a87002cc41f1', '송도2동', '2071edd4fa6c', '코오롱 더프라우 3단지');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('795efbb9-9e09-4813-a835-ddb2192ad6fa', '2021-10-19 15:35:49', '2021-10-19 15:35:49', null, '더샵', '2021-10-19 15:35:49', '송도 더샵 퍼스트월드', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', '1f0758ccde06', '송도1동', 'f9ea71209dee', '퍼스트월드');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('7d8e3295-abd6-40e8-9ad4-4862ee3547df', '2021-10-19 15:35:49', '2021-10-19 15:35:49', null, '더샵', '2021-10-19 15:35:49', '송도 더샵 그린스퀘어', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', '0b96cc858bf6', '송도3동', '36dc06db13e2', '그린스퀘어');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('86dd8a2e-2592-41f6-b742-7e06aae8169b', '2021-10-19 15:35:49', '2021-10-19 15:35:49', null, '롯데캐슬', '2021-10-19 15:35:49', '송도 롯데캐슬 (캠퍼스타운)', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', '0b96cc858bf6', '송도3동', 'bf0ad88cdb07', '롯데캐슬 캠퍼스타운');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('874ed712-f3aa-42b0-8587-2877a36c1cd7', '2021-10-20 20:24:10', '2021-10-20 20:24:10', 'https://phinational.org/wp-content/uploads/2017/07/fb-test-image-470x246.jpg', '푸르지오', null, '송도 푸르지오 하버뷰', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', 'a87002cc41f1', '송도2동', 'cc5cda36231b', '푸르지오 하버뷰');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('8cfcf77e-17c2-40eb-810f-24ba548ef77e', '2021-10-20 20:24:10', '2021-10-20 20:24:10', null, '해모로', '2021-10-19 15:35:49', '송도 해모로월드뷰', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', '0b96cc858bf6', '송도3동', '95f8d341827f', '해모로월드뷰');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('96e37a05-d1b8-46b4-a9ed-3f78008fd9cc', '2021-10-19 15:35:49', '2021-10-19 15:35:49', 'https://phinational.org/wp-content/uploads/2017/07/fb-test-image-470x246.jpg', '더샵', null, '송도 더샵 센트럴파크', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', 'a87002cc41f1', '송도2동', '21048a8304cf', '더샵센트럴파크');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('a303d21e-3206-40b6-8d46-63786ed94634', '2021-10-20 20:24:59', '2021-10-20 20:24:59', null, '푸르지오', '2021-10-19 15:35:49', '송도 에듀포레 푸르지오', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', '0b96cc858bf6', '송도3동', '91e27adb9038', '에듀포레 푸르지오');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('a63cf656-368b-42b4-8eb8-7965f12df4b6', '2021-10-20 20:24:59', '2021-10-20 20:24:59', null, '베르디움', '2021-10-19 15:35:49', '송도 더퍼스트 베르디움', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', '0b96cc858bf6', '송도3동', '43fee38d860f', '호반베르디움 1차');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('a87a3872-4bc0-4d4c-b0c6-298cc602e291', '2021-10-20 20:14:28', '2021-10-20 20:14:28', null, '아이파크', '2021-10-19 15:35:49', '송도 아메리칸타운 아이파크', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', '0b96cc858bf6', '송도3동', 'c08ea5b8649d', '아메리칸타운');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('ab347c3d-c84b-4bbc-85a7-6006b68534c7', '2021-10-20 20:24:10', '2021-10-20 20:24:10', null, '푸르지오', '2021-10-19 15:35:49', '송도 아트윈 푸르지오', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', '5178f5b05fa5', '송도4동', '6d157a4858e4', '아트윈 푸르지오');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('acbde2dc-107c-4152-ae12-db358f03c19f', '2021-10-20 20:24:59', '2021-10-20 20:24:59', null, '캐슬앤해모로', '2021-10-19 15:35:49', '송도 캐슬앤해모로', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', '0b96cc858bf6', '송도3동', 'abd65674e415', '캐슬앤해모로');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('c7d9b768-b2eb-4dbe-9343-112b3e4069ae', '2021-10-20 19:59:45', '2021-10-20 19:59:45', null, '힐스테이트', '2021-10-19 15:35:49', '송도 힐스테이트 3 ~ 6단지', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', 'a87002cc41f1', '송도2동', '10895b29a526', '힐스테이트');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('cb55ac06-7040-4d71-aad5-c02a80108f99', '2021-10-19 15:35:49', '2021-10-19 15:35:49', null, '푸르지오', '2021-10-19 15:35:49', '송도 글로벌캠퍼스푸르지오', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', '0b96cc858bf6', '송도3동', 'fe82298e3c63', '글로벌캠퍼스푸르지오');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('d3dc580c-c917-4ac1-bde8-d9824c1c4819', '2021-10-19 15:35:49', '2021-10-19 15:35:49', null, '웰카운티', '2021-10-19 15:35:49', '송도 웰카운티', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', '1f0758ccde06', '송도1동', '2acd52800525', '웰카운티');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('d4f7bf39-1355-466d-832b-dddadb4dab2c', '2021-10-19 15:35:49', '2021-10-19 15:35:49', null, '더샵', '2021-10-19 15:35:49', '송도 더샵 센트럴시티', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', '0b96cc858bf6', '송도3동', '37ac0da953d5', '센트럴시티');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('e091fba9-bc77-4bf0-9a46-964c1b93d356', '2021-10-19 15:35:49', '2021-10-19 15:35:49', null, '풍림아이원', '2021-10-19 15:35:49', '송도 풍림아이원', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', '1f0758ccde06', '송도1동', '996bc98b6583', '풍림아이원');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('e1a44344-6671-4aa0-b8ac-983c598789c3', '2021-10-20 20:14:28', '2021-10-20 20:14:28', null, '푸르지오', '2021-10-19 15:35:49', '송도 푸르지오 월드마크 7, 8단지', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', 'a87002cc41f1', '송도2동', '77a5e929f0cd', '월드마크 7, 8 단지');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('e5ad089c-ad3c-44a7-8654-96bfd7928c97', '2021-10-19 15:35:49', '2021-10-19 15:35:49', null, '더샵', '2021-10-19 15:35:49', '송도 더샵 퍼스트파크', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', '5178f5b05fa5', '송도4동', '5b1a7c427f53', '퍼스트파크');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('eeb2a7e7-de30-46b7-886b-63fe826f3a64', '2021-10-20 20:17:58', '2021-10-20 20:17:58', 'https://phinational.org/wp-content/uploads/2017/07/fb-test-image-470x246.jpg', '푸르지오', null, '송도 센트럴파크 푸르지오', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', 'a87002cc41f1', '송도2동', '292dd084eabf', '센트럴파크푸르지오');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('f3929a48-352f-4949-a1ae-a15fce461eed', '2021-10-20 20:24:59', '2021-10-20 20:24:59', 'https://phinational.org/wp-content/uploads/2017/07/fb-test-image-470x246.jpg', '더샵', null, '송도 더샵 그린애비뉴', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', 'a87002cc41f1', '송도2동', '02518c408a28', '그린애비뉴');
INSERT INTO ourapt.apartment (id, created_at, updated_at, banner_image, brand_name, inactive_at, name, region_id_depth1, region_name_depth1, region_id_depth2, region_name_depth2, region_id_depth3, region_name_depth3, region_id_depth4, region_name_depth4) VALUES ('TEST', '2021-10-19 15:35:49', '2021-10-19 15:35:49', 'https://phinational.org/wp-content/uploads/2017/07/fb-test-image-470x246.jpg', 'TEST', null, 'TEST', 'a58f163cc2e0', '인천광역시', '6a0efc659cea', '연수구', 'a87002cc41f1', '송도2동', 'b7ca1e49757c', 'TEST');