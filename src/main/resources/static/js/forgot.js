let passConf=document.querySelector("#InputPassword2");
let codeInp=document.querySelector("#InputCode");
console.log(codeInp);




if (passConf.placeholder==="Password is not correct"){
    passConf.classList.add('errorPlaceholder');
}
if(codeInp.placeholder==="Activate code is not correct"){
    codeInp.classList.add('errorPlaceholder');
}
