const data = [
  {
    id: 'bf5a0e50-c2ea-42a2-badd-e570ffca2789',
    created_at: '2021-12-08 11:24:27',
    updated_at: '2021-12-08 11:24:27',
    content_json:
      '{"apartment_name":"1","what_to_vote":"안쪽 반지름이 4.0cm이고 바깥쪽 반지름이 6.0cm인 부도체 공은 안쪽과 바깥쪽 표면 사이에 불균일한  전하 분포를 갖는다. 부피 전하밀도가 p(단위 μC/m³)는 단위부피당 전하를 뜻한다. 이 공에 대하여 p=b/r로 주어진다면 (여기서 r은 공의 중심으로부터의 거리이고, d=3.0μC/m³ 이다.), 공의 알짜 전하는 얼마인가?"}',
    user_id: 'f3794e0654624361b55b764a0cf055ca',
  },
  {
    id: '9fe598b9-9643-4016-84bd-563e8efc56e4',
    created_at: '2021-12-08 11:23:44',
    updated_at: '2021-12-08 11:23:44',
    content_json:
      '{"apartment_name":"1","what_to_vote":"페미니즘은 어째서 도태됐는가?"}',
    user_id: 'f3794e0654624361b55b764a0cf055ca',
  },
  {
    id: '57f47876-438d-483f-8120-f5ae5ce4dd4f',
    created_at: '2021-12-07 23:50:19',
    updated_at: '2021-12-07 23:50:19',
    content_json: '{"apartment_name":"13단지","what_to_vote":"커뮤니티"}',
    user_id: 'eda339ac7631405f8621fe08750d5ba4',
  },
  {
    id: '2f11dd8f-a733-4737-9df5-ccb61c01e6b8',
    created_at: '2021-12-07 21:58:00',
    updated_at: '2021-12-07 21:58:00',
    content_json: '{"apartment_name":"15단지 ","what_to_vote":"."}',
    user_id: 'bf48731fd4ec4d02b0d279752f3a03d9',
  },
  {
    id: '23dac252-acf7-46e8-b503-a135c547225e',
    created_at: '2021-12-07 20:45:05',
    updated_at: '2021-12-07 20:45:05',
    content_json: '{"apartment_name":"1단지","what_to_vote":"재미"}',
    user_id: '6abaf874cecc4aa4aec517e1edb23265',
  },
  {
    id: '2b42f3b0-ea3b-4fef-8223-a36238f66f93',
    created_at: '2021-12-07 18:33:26',
    updated_at: '2021-12-07 18:33:26',
    content_json: '{"apartment_name":"2","what_to_vote":"."}',
    user_id: '3f0628bf0b1045ed9ec484d94b862ada',
  },
  {
    id: 'ca1fee63-f13d-48f8-a9d8-1f1c780adea4',
    created_at: '2021-12-07 21:45:35',
    updated_at: '2021-12-07 21:45:35',
    content_json: '{"apartment_name":"2단지","what_to_vote":"밥집"}',
    user_id: '71f56a30160c446892ae8e39219d25da',
  },
  {
    id: '32edcfb7-67ea-4498-af79-b974c8467c2f',
    created_at: '2021-12-07 22:59:48',
    updated_at: '2021-12-07 22:59:48',
    content_json: '{"apartment_name":"6단지","what_to_vote":"청소"}',
    user_id: '9793e638078b484498e4448458239473',
  },
  {
    id: '9b86d677-65c0-4971-9b7d-c84f9af9c17e',
    created_at: '2021-12-07 17:15:17',
    updated_at: '2021-12-07 17:15:17',
    content_json: '{"apartment_name":"굿굿 ","what_to_vote":"테스트중"}',
    user_id: '028d2820ae0b458a86fc5e7b276b026d',
  },
  {
    id: 'ab2ec37d-14bd-49b3-acc1-e178c5d51599',
    created_at: '2021-12-07 19:37:17',
    updated_at: '2021-12-07 19:37:17',
    content_json: '{"apartment_name":"동양메이저아파트","what_to_vote":" "}',
    user_id: 'cfa595b79f274449a3549046957325d4',
  },
  {
    id: '4641e184-c0b4-47a3-95f2-9da3e124ad91',
    created_at: '2021-12-08 01:28:55',
    updated_at: '2021-12-08 01:28:55',
    content_json: '{"apartment_name":"상계16단지","what_to_vote":"운동"}',
    user_id: '963b64f50482423482c6c69962c23245',
  },
  {
    id: 'e35b88bb-65da-43f1-bcd4-005ba0a55a8b',
    created_at: '2021-12-07 18:44:05',
    updated_at: '2021-12-07 18:44:05',
    content_json:
      '{"apartment_name":"상계주공 13단지","what_to_vote":"길냥이 밥주지마세요"}',
    user_id: 'f72749aaad1a45ca90ce1ec581b81cf3',
  },
  {
    id: '2a0e0a01-4c31-4064-b5cb-cc7abf4e6d90',
    created_at: '2021-12-07 23:00:15',
    updated_at: '2021-12-07 23:00:15',
    content_json: '{"apartment_name":"상계주공 14단지","what_to_vote":"일상"}',
    user_id: 'd8c6c764fa204636bb91597ba1295e44',
  },
  {
    id: '5e0354c3-9238-4d56-aa8d-27019ccabda2',
    created_at: '2021-12-07 19:38:18',
    updated_at: '2021-12-07 19:38:18',
    content_json:
      '{"apartment_name":"상계주공 14단지","what_to_vote":"재건축 "}',
    user_id: '314d375c4a414b989e3993179d300deb',
  },
  {
    id: '5261d37c-c014-40f4-8cbe-ea6bdd89a473',
    created_at: '2021-12-08 00:17:47',
    updated_at: '2021-12-08 00:17:47',
    content_json:
      '{"apartment_name":"상계주공 15단지","what_to_vote":"주변편리시설"}',
    user_id: 'b74d77ce016f411097a32b02883e380f',
  },
  {
    id: '94acae55-423d-47f4-aafa-edd860bef5fe',
    created_at: '2021-12-07 22:21:48',
    updated_at: '2021-12-07 22:21:48',
    content_json:
      '{"apartment_name":"상계주공 6단지","what_to_vote":"물물교환 대이"}',
    user_id: '5194ca9c13414a0abc2c5bd4a0c7e843',
  },
  {
    id: 'a4d48c93-701c-4a6b-8dd9-2d84807ff6cb',
    created_at: '2021-12-08 12:34:05',
    updated_at: '2021-12-08 12:34:05',
    content_json: '{"apartment_name":"상계주공 6단지","what_to_vote":"재건축"}',
    user_id: 'c452971ed64b4069984dab99397cbac0',
  },
  {
    id: 'ac9e55da-aae3-4c7f-85f4-ef280a85a9f6',
    created_at: '2021-12-08 09:28:56',
    updated_at: '2021-12-08 09:28:56',
    content_json: '{"apartment_name":"상계주공15단지","what_to_vote":"네"}',
    user_id: '94103d6cf10f4f8eb54165f06a3374cd',
  },
  {
    id: '75a2d885-e640-42c8-bd7b-a89770cbdeca',
    created_at: '2021-12-07 20:06:06',
    updated_at: '2021-12-07 20:06:06',
    content_json: '{"apartment_name":"상계주공15단지","what_to_vote":"재느"}',
    user_id: '67096197e4d04e1fbf714fca5c5e1628',
  },
  {
    id: '64214c59-901c-470e-b5af-d07989d2f2ef',
    created_at: '2021-12-08 13:06:10',
    updated_at: '2021-12-08 13:06:10',
    content_json:
      '{"apartment_name":"상계주공16단지아파트 ","what_to_vote":"재건축"}',
    user_id: '23fb952a58684bd39f9668301caf812f',
  },
  {
    id: '589a93ae-11af-493e-a15c-eaaeabc5ef8c',
    created_at: '2021-12-08 11:04:21',
    updated_at: '2021-12-08 11:04:21',
    content_json:
      '{"apartment_name":"상계주공16단지아파트","what_to_vote":"재건축 언제 될까요?"}',
    user_id: '3ddfc47fd9954f3f805277df4e48d2a1',
  },
  {
    id: '0e904415-456c-4dac-a430-54b84585358b',
    created_at: '2021-12-07 23:19:12',
    updated_at: '2021-12-07 23:19:12',
    content_json:
      '{"apartment_name":"상계주공2단지","what_to_vote":"아파트 시설"}',
    user_id: '14cbc15fc3e54fd987bbfba51b52483a',
  },
  {
    id: 'd700f69d-9525-4f1e-b84e-436a604eafef',
    created_at: '2021-12-08 14:09:42',
    updated_at: '2021-12-08 14:09:42',
    content_json: '{"apartment_name":"상계주공2단지","what_to_vote":"중고"}',
    user_id: '4b0f3672574042ee8af028a5fb751faa',
  },
  {
    id: 'bb96cbdd-3e79-4910-9b09-6835d7bd70f2',
    created_at: '2021-12-07 20:18:40',
    updated_at: '2021-12-07 20:18:40',
    content_json:
      '{"apartment_name":"상계주공5단지","what_to_vote":"재건축.월세"}',
    user_id: '0415e5470e7c4f1f8d4849cb4fd59026',
  },
  {
    id: 'e07f5f67-022a-4e31-9893-95b69f85337f',
    created_at: '2021-12-08 12:53:25',
    updated_at: '2021-12-08 12:53:25',
    content_json:
      '{"apartment_name":"상계주공6단지","what_to_vote":"생각안해봄"}',
    user_id: '1c396e7568044e11a3dba0308e7f77ba',
  },
  {
    id: '1ce29dd2-31a2-4681-9f86-8feed5e4bb47',
    created_at: '2021-12-08 11:23:49',
    updated_at: '2021-12-08 11:23:49',
    content_json: '{"apartment_name":"아니요","what_to_vote":"글쎄요"}',
    user_id: '97fc501db9834f568953e1d0c911c170',
  },
];
// console.log(`// 총 데이터: ${data.length} 건`);
console.log(
  JSON.stringify(
    data
      .map((json) => {
        json.content_json =
          json.content_json.slice(0, 1) +
          `"userId": "${json.user_id}", ` +
          json.content_json.slice(1);
        return json;
      })
      .map((json) => json.content_json)
      .map((s) => JSON.parse(s)),
    null,
    2
  ).replace("'", '"')
);
