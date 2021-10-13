// screen.lockOrientation("portrait");

const mini = new Mini();
let BASE_URL = "http://ourapt-api-alpha.ap-northeast-2.elasticbeanstalk.com";
// let BASE_URL = "http://localhost:8080";
// let BASE_URL = "http://b833-121-166-172-250.ngrok.io";

let URLform = document.getElementById("BURLform");
URLform.addEventListener("submit", function (event) {
  event.preventDefault();
  BASE_URL = document.getElementById("BURL").value;
  console.log(`제이콥, 여기예요! ${BASE_URL}`);
  alert(BASE_URL);
});

let backBtn = document.getElementById("back-btn");
let registerBtn = document.getElementById("register-btn");
let countOfVoting = 0;

// 체크버튼 선택지: 구조 개선해놓기
// 1) 지금은 리스트로 받는데 바로 객체로 받을 수 있도록 하기 - 서버 통신 쉬워지도록 : done!
// 2) 변경될때마다 체크버튼 컬러 바뀌는 함수도 같이 동작할 수 있도록 하기 - 상태관리 생각하기
const wantSupply = false;
const wantDemand = false;
const justFun = false;
const answer = [wantSupply, wantDemand, justFun];

// 맨 처음 페이지 렌더링 할 때: 이미 신청한 사람인지 확인해요 (현재)
// 이미 신청한 사람의 경우 당근에서 뭔가를 쏴준다고 했는데... 그게 뭔지 확인해보고 다시 적용해볼 것 -> with API
// url로 쿼리 파라미터를 보내주시는데: 동의한 사용자 id를 보내주신다!
let urlSearchParams = new URLSearchParams(window.location.search);
console.log("URL 서치 파람즈");
console.log(urlSearchParams.get("code"));

checkIsAgreedOuraptPreopen();
currentVotingCount();
// console.log("쿼리 파라미터 찍힐까요?");
// console.log(window.location.search);

// 이미 신청한 사람이라면?
// 0. 당근 서버에서 확인하는 절차를 거쳐요. 이미 신청한 사람이라는 것을 확인해줄 거예요. (지금의 로컬스토리지 플래그 사용할 필요 X)
// 1. 만약 신청한 사람이라면, 제이콥에게 이 사람의 정보를 보내줄 거예요.
// 2. 제이콥이 이 사람이 이전에 골랐던 선택지를 보내줄 거예요. 그걸 확인해서 체크버튼을 보여줘야 해요. => 체크버튼 반영하는 함수를 분리해서 따보는 것도 방법?
// 3. 이미 신청했으므로, 선택지를 고르거나 신청 버튼을 누르는 등의 인터랙션을 막아줘요.
async function checkIsAgreedOuraptPreopen() {
  // if (window.localStorage.getItem("isAgreedOuraptPreopen")) {
  if (urlSearchParams.has("code")) {
    console.log("받아왔어요");
    document.getElementById("register-btn").innerText = "알림 받기 완료";
    // registerBtnDisable();
    // checkDisable();
    // 기존 데이터 값을 받아와서 아래로 내려보내주고, answerCheck와 registerBtnActive를 다시 불러줄까?
    const code = urlSearchParams.get("code");
    const accessToken = await getAccessToken(code);
    await getMyVote(accessToken);
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
    console.log("액세스토큰 받아올게요");
    if (!resBody || !resBody.data) {
      return "액세스토큰을 받아오지 못해요!";
    }
    console.log(`액세스 토큰을 받아왔는데요, ${resBody.data.access_token}`);
    return "Bearer " + resBody.data.access_token;
  }
  // console.log(await response.json());
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
    console.log("내 투표내역 받아왔어요!");
    if (!resBody || !resBody.data) {
      return "내 투표내역을 조회할 수 없어요!";
    }
    console.log(resBody.data);
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
}

// 선택지 체크 비활성화
function checkDisable() {
  document.getElementsByTagName("ul")[0].style.pointerEvents = "none";
}

// 사전오픈 신청 버튼 활성화: 1) 선택지를 선택한 경우에만 활성화됩니다.
function registerBtnAble() {
  console.log("외않되?");
  registerBtn.classList.replace("register-btn-disabled", "register-btn-abled");
  registerBtn.disabled = false;
}

// 사전오픈 신청 버튼 비활성화: 1) 아무런 선택지를 고르지 않은 경우, 2) 이미 신청한 경우 비활성화됩니다.
function registerBtnDisable() {
  registerBtn.classList.replace("register-btn-abled", "register-btn-disabled");
  registerBtn.disabled = true;
}

// 사전오픈 신청 버튼 활성화/비활성화 여부 체크: 선택지 클릭 발생 시 매번 체크해줍니다.
function registerBtnActive() {
  console.log("버튼 컬러");
  if (answer[0] || answer[1] || answer[2]) {
    registerBtnAble();
  } else {
    registerBtnDisable();
  }
}

// 선택지를 클릭할 때: 해당 선택지에 따라 저장한 answer값 변경하고, 선택지 컬러를 변경하며, 사전오픈 신청 버튼 활성화 여부를 체크해줍니다.
// 선택지 컬러 변경하는 부분을 함수로 분리하는게 낫지 않을까? answer가 변경되는 경우에 작동하도록 해보고 싶다.
// 이유 -> 기신청자의 경우 데이터 받아서 체크 띄우고 싶어서... 그게 깔끔할 것 같아서...
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
    body: JSON.stringify(
      !wantSupply && !wantDemand && !justFun
        ? {
            wantSupplyChecked: wantSupply,
            wantDemandChecked: wantDemand,
            justFunChecked: true,
          }
        : {
            wantSupplyChecked: wantSupply,
            wantDemandChecked: wantDemand,
            justFunChecked: justFun,
          }
    ),
  });
  if (response.ok) {
    console.log("투표 제출했습니다!");
  }
}

// 현재 투표 인원 받아오기
async function currentVotingCount() {
  const response = await fetch(`${BASE_URL}/api/v1/preopen/voting/count`, {
    method: "GET",
  });
  if (response.ok) {
    const resBody = await response.json();
    if (!resBody || !resBody.data) {
      return "error 났어요!";
    }
    countOfVoting = resBody.data.countOfVoting;
    console.log(`${countOfVoting}명 참여했대요.`);
  }
}

// 모달창 열기: 신청버튼을 눌렀을 때, 전체 서버 통신이 안전하게 완료되고 난 다음 띄워줍니다.
function openRegisteredModal() {
  document.getElementById("modal").style.display = "block";
}

// 모달창 닫기
function closeRegisteredModal() {
  document.getElementById("modal").style.display = "none";
}

// 내비게이션 바
backBtn.addEventListener("click", function (event) {
  mini.close();
});

// 선택지
document
  .getElementById("wantSupply")
  .addEventListener("click", function (event) {
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

// 현재 몇 명 신청했나 보여주기 -> with API
document.getElementById("registered").innerText = countOfVoting;

async function toJacob(token) {
  const response = await fetch(
    // `http://33dd-121-166-172-250.ngrok.io/api/v1/app/token?token=${token}`,
    // `http://77c2-27-35-31-50.ngrok.io/api/v1/app/token?token=${token}`,
    `http://b3ca-121-166-172-250.ngrok.io/v1/app/token?token=${token}`,
    {
      method: "GET",
    }
  );
  if (response.ok) {
  }
}

// 오픈시 알림받기 -> with API
document
  .getElementById("register-btn")
  .addEventListener("click", function (event) {
    console.log("제출할게요");
    window.localStorage.setItem("isAgreedOuraptPreopen", true);
    mini.startPreset({
      preset:
        "https://mini-assets.kr.karrotmarket.com/presets/common-login/alpha.html",
      params: {
        appId: "6e6ba05f78534202aa4afe21daf1c825",
      },
      onSuccess: async function (result) {
        if (result && result.code) {
          console.log("미니 프리셋은 온석세슽!");
          console.log(result.code);
          // toJacob(result.code);
          // alert(result.code);
          const accessToken = await getAccessToken(result.code);
          if (accessToken) {
            await submitVoting(accessToken);
          }
          // submitVoting(getAccessToken(result.code));
          openRegisteredModal();
        }
      },
      onError: function (error) {
        console.log(error);
      },
    });
  });

// 모달 제어
document
  .getElementById("modal-close")
  .addEventListener("click", function (event) {
    console.log("ee?");
    closeRegisteredModal();
    // checkIsAgreedOuraptPreopen();
    // checkDisable();
  });
