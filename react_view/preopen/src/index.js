const mini = new Mini();

// let BASE_URL = "http://ourapt-api-alpha.ap-northeast-2.elasticbeanstalk.com";
let BASE_URL =
  "http://ourapt-api-production.ap-northeast-2.elasticbeanstalk.com/";
let backBtn = document.getElementById("back-btn");
let registerBtn = document.getElementById("register-btn");

// 내비게이션 바
backBtn.addEventListener("click", function (event) {
  mini.close();
});

// 체크버튼 선택지:
const wantSupply = false;
const wantDemand = false;
const justFun = false;
const answer = [wantSupply, wantDemand, justFun];

// 맨 처음 페이지 렌더링 할 때: 이미 신청한 사람인지 확인해요
let urlSearchParams = new URLSearchParams(window.location.search);

checkIsAgreedOuraptPreopen();
getVotingCount().then((count) => {
  patchCountOfVoting(count);
});

// 이미 신청한 사람이라면?
// 0. 당근 서버에서 확인하는 절차를 거쳐요.
// 1. 만약 신청한 사람이라면, 제이콥에게 이 사람의 코드를 보내줄 거예요.
// 2. 제이콥이 이 사람이 이전에 골랐던 선택지를 보내줄 거예요. 그걸 확인해서 체크버튼을 보여줘야 해요.
// 3. 이미 신청했으므로, 선택지를 고르거나 신청 버튼을 누르는 등의 인터랙션을 막아줘요.
async function checkIsAgreedOuraptPreopen() {
  if (urlSearchParams.has("code")) {
    const code = urlSearchParams.get("code");
    const accessToken = await getAccessToken(code);
    await getMyVote(accessToken);
    afterIsAgreedOuraptPreopen();
  }
}

async function getAccessToken(code) {
  const response = await fetch(`${BASE_URL}/api/v1/oauth/karrot`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      authorizationCode: code,
    }),
  });
  if (response.ok) {
    const resBody = await response.json();
    if (!resBody || !resBody.data) {
      return;
    }
    return "Bearer " + resBody.data.access_token;
  }
}

async function getMyVote(token) {
  const response = await fetch(`${BASE_URL}/api/v1/preopen/me/voting`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: token,
    },
  });
  if (response.ok) {
    const resBody = await response.json();
    if (!resBody || !resBody.data) {
      return;
    }
  }
}

function patchMyVote() {
  if (resBody.data.wantSupplyChecked) {
    answerCheck(document.getElementById("wantSupply"), 0);
  }
  if (resBody.data.wantDemandChecked) {
    answerCheck(document.getElementById("wantDemand"), 1);
  }
  if (resBody.data.justFunChecked) {
    answerCheck(document.getElementById("justFun"), 2);
  }
}

function afterIsAgreedOuraptPreopen() {
  document.getElementById("register-btn").innerText = "알림 받기 완료";
  registerBtnDisable();
  checkDisable();
}

// 선택지 체크 비활성화
function checkDisable() {
  document.getElementsByTagName("ul")[0].style.pointerEvents = "none";
}

// 사전오픈 신청 버튼 활성화: 늘 활성화 되어있어요.
// function registerBtnAble() {
//   registerBtn.classList.replace("register-btn-disabled", "register-btn-abled");
//   registerBtn.disabled = false;
// }

// 사전오픈 신청 버튼 비활성화: 1) 이미 신청한 경우 비활성화됩니다.
function registerBtnDisable() {
  registerBtn.classList.replace("register-btn-abled", "register-btn-disabled");
  registerBtn.disabled = true;
}

// 사전오픈 신청 버튼 활성화/비활성화 여부 체크: 선택지 클릭 발생 시 매번 체크해줍니다.
// function registerBtnActive() {
//   if (answer[0] || answer[1] || answer[2]) {
//     registerBtnAble();
//   } else {
//     registerBtnDisable();
//   }
// }

// 선택지를 클릭할 때: 해당 선택지에 따라 저장한 answer값 변경하고, 선택지 컬러를 변경하며, 사전오픈 신청 버튼 활성화 여부를 체크해줍니다.
function answerCheck(item, i) {
  if (answer[i]) {
    answer[i] = false;
    item.childNodes[1].src = "./check-unselect.svg";
    // registerBtnActive();
    console.log(answer);
  } else {
    answer[i] = true;
    item.childNodes[1].src = "./check-selected.svg";
    // registerBtnActive();
    console.log(answer);
  }
}

// 투표 제출 및 알람 동의
async function submitVoting(token) {
  const response = await fetch(`${BASE_URL}/api/v1/preopen/voting/submit`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: token,
    },
    body: JSON.stringify({
      wantSupplyChecked: wantSupply,
      wantDemandChecked: wantDemand,
      justFunChecked: justFun,
    }),
  });
}

// 현재 투표 인원 받아오기
async function getVotingCount() {
  const response = await fetch(`${BASE_URL}/api/v1/preopen/voting/count`, {
    method: "GET",
  });
  if (response.ok) {
    const resBody = await response.json();
    if (!resBody || !resBody.data || !resBody.data.countOfVoting) {
      return;
    }
    return resBody.data.countOfVoting;
  }
}

function patchCountOfVoting(count) {
  document.getElementById("registered").innerText = count;
}

// 모달창 열기: 신청버튼을 눌렀을 때, 전체 서버 통신이 안전하게 완료되고 난 다음 띄워줍니다.
function openRegisteredModal() {
  document.getElementById("modal").style.display = "block";
}

// 모달창 닫기
function closeRegisteredModal() {
  document.getElementById("modal").style.display = "none";
}

// 선택지
document
  .getElementById("wantSupply")
  .addEventListener("click", function (event) {
    console.log("emergency@");
    answerCheck(this, 0);
  });
document
  .getElementById("wantDemand")
  .addEventListener("click", function (event) {
    answerCheck(this, 1);
  });
document.getElementById("justFun").addEventListener("click", function (event) {
  answerCheck(this, 2);
});

// 오픈시 알림받기 -> with API
document
  .getElementById("register-btn")
  .addEventListener("click", function (event) {
    mini.startPreset({
      preset:
        // 알파 앱
        // "https://mini-assets.kr.karrotmarket.com/presets/mvp-apartment-login/alpha.html",
        // 프로덕션 앱
        "https://mini-assets.kr.karrotmarket.com/presets/mvp-apartment-login/production.html",
      params: {
        // 알파 앱
        // appId: "6e6ba05f78534202aa4afe21daf1c825",
        // 프로덕션 앱
        appId: "4eaa7851558f401eb6c2361337e8ef4f",
      },
      onSuccess: async function (result) {
        if (result && result.code) {
          const accessToken = await getAccessToken(result.code);
          if (accessToken) {
            await submitVoting(accessToken);
          }
          openRegisteredModal();
        }
      },
      onError: function (error) {
        alert("다시 한 번 시도해 주세요");
      },
    });
  });

// 모달 제어
document
  .getElementById("modal-close")
  .addEventListener("click", function (event) {
    closeRegisteredModal();
    afterIsAgreedOuraptPreopen();
  });
