// 내비게이션 바

const mini = new Mini();

var backBtn = document.getElementById("back-btn")
// console.log(backBtn)

backBtn.addEventListener("click", function(event) {
  // console.log("제발!")
  mini.close();
})
