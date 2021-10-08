const mini = new Mini();
var backBtn = document.getElementById("back-btn");
var registerBtn = document.getElementById("register-btn");

// 임시코드
// if (window.localStorage.getItem("isAgreedOuraptPreopen")) {
//   window.localStorage.removeItem("isAgreedOuraptPreopen");
// }

checkDisable();

function checkDisable() {
  if (window.localStorage.getItem("isAgreedOuraptPreopen")) {
    console.log("받아왔어요");
    document.getElementById("register-btn").innerText = "알림받기 완료";
    registerBtn.disabled = true;
    document.getElementsByTagName("ul")[0].style.pointerEvents = "none";
    // 기존 데이터 값을 받아와서 아래로 내려보내주고, answerCheck와 registerBtnActive를 다시 불러줄까?
  }
}

// 내비게이션 바
backBtn.addEventListener("click", function (event) {
  mini.close();
});

// 선택지 셀렉션

const answer = [false, false, false];

function answerCheck(item, i) {
  if (answer[i]) {
    answer[i] = false;
    item.childNodes[1].src = "./check-unselect.png";
    registerBtnActive();
    console.log(answer);
  } else {
    answer[i] = true;
    item.childNodes[1].src = "./check-selected.png";
    registerBtnActive();
    console.log(answer);
  }
}

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

// 현재 몇 명 신청했나 보여주기

document.getElementById("registered").innerText = "0";

// 오픈시 알림받기

function registerBtnActive() {
  console.log("버튼 컬러");
  if (answer[0] || answer[1] || answer[2]) {
    console.log("버튼 컬러 빨갛게 바뀌어야 하는데");
    console.log(registerBtn);
    registerBtn.classList.replace(
      "register-btn-disabled",
      "register-btn-abled"
    );
    registerBtn.disabled = false;
  } else {
    document
      .getElementById("register-btn")
      .classList.replace("register-btn-abled", "register-btn-disabled");
    registerBtn.disabled = true;
  }
}

document
  .getElementById("register-btn")
  .addEventListener("click", function (event) {
    console.log("제출할게요");
    openRegisteredModal();
    window.localStorage.setItem("isAgreedOuraptPreopen", true);
    mini.startPreset({
      preset:
        "https://mini-assets.kr.karrotmarket.com/presets/common-login/alpha.html",
      params: {
        appId: "6e6ba05f78534202aa4afe21daf1c825",
      },
      onSuccess: function (result) {
        if (result && result.code) {
          console.log(result.code);
          // alert(result.code);
        }
      },
    });
  });

// 모달 제어

document
  .getElementById("modal-close")
  .addEventListener("click", function (event) {
    console.log("ee?");
    closeRegisteredModal();
    checkDisable();
  });

function openRegisteredModal() {
  document.getElementById("modal").style.display = "block";
}

function closeRegisteredModal() {
  document.getElementById("modal").style.display = "none";
}
