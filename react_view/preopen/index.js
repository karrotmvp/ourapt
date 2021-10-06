// 내비게이션 바

const mini = new Mini();

const back = document.getElementById("back-arrow");
back.addEventListener("click", backEvent);

function backEvent() {
  console.log("뒤로 돌아갈게요!");
  mini.close();
}
