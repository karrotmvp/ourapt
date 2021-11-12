const axios = require('axios');

const API_KEY = 'FKT4tn5arLHR0ontSGNarIayhldHf0t4fVi4cVG0IUI='; // API KEY 수정해주세요
// 보낼 유저 ID 대상 입력해주세요
const USER_IDS = [
  //   '1aa8be405dc84ffd9d4176a723c00b01',
  //   '810bbd8cd51e4e4f9b40b080ce04b2cc',
  //   'c2f7ed3482624e6f977264e2bf9ad745',
  //   'b71252f432e7420eac1c4abc54c5762f',
  //   '8b7b6b5aee12404cb4b23467eac38a3b',
  //   'd11e2011bd34437291fc4b5a57c40a0a',
  //   'fc629f5d01bb4b82b35a6ab2a596dcd1',
  //   'bf71924bbb6949a2bd95a5ce7aa5d21d',
  //   'e7d0a844865f43fc8524bf3d733672a1',
  //   'b2ba602d0acc4aac88810e86c4057f69',
  //   '392817eff5f0437aa30acd2ea6875321',
  //   '07f7929232c44490b8b351968e7547e7',
  //   '2d7658eb684e4c5db79c34217bb87f6a',
  //   '4b3bac7d7d234c539e5db602da5368a4',
  '6ad335c2cd1d441baee8652c75856f6e',
  //   'a5328fc756a14ec6928187996a62d821',
  //   'b095e77dbf4b41519058747fc51b3d76',
  //   'be397b9028bc4ce684d8ddf65d3ade6e',
  //   'f4e1683758994f24a6a9dd557c08502a',
];
const TITLE = '우리아파트 이웃들끼리만 이야기를 나눠보세요🤫'; // 메세지 제목을 입력하세요
const TEXT =
  '?1 님, 알림 신청하신 우리아파트가 오픈했어요. 오셔서 우리 아파트 이웃들과 이야기 나눠보세요!'; // 내용을 입력하세요, 순서대로 파라미터 입력이 가능합니다
const TEXT_PARAMS = []; // ?1, ?2, ?3 순서대로 TEXT에 들어갑니다.

async function getUsersByIds(userIds) {
  const users = await axios({
    method: 'get',
    url: 'https://oapi.alpha.kr.karrotmarket.com/api/v2/users/by_ids',
    headers: {
      'Content-Type': 'application/json',
      'X-Api-Key': API_KEY,
    },
    params: {
      ids: userIds.join(','),
    },
  });
  return users;
}
async function sendBizChat(userIds, message) {}

async function main() {
  try {
    const res = await getUsersByIds(USER_IDS);
    if (res.status !== 200) {
      console.log(`Fail to get users: ${res.status}`);
      console.log(res.data.errors);
      return;
    }
    const users = res.data.data.users;

    console.log(users);
  } catch (err) {
    console.error(err);
  }
}

main();
