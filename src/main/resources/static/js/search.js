let searchBody=document.querySelector("#searchBody");
searchBody.classList.remove('in');
function search () {
    searchBody.innerHTML="";
    let val=$("#inp").val().trim();
    if(val!=""){
        searchBody.classList.remove('hide');
        searchBody.classList.add('in');
        $.ajax({
            url:"/searchProduct",
            data:{
                str:val
            },success : function (product) {
                let str = product.split("*")
                for (var i = 0; i < str.length; i++) {

                    var s = str[i];
                    let arr = s.split("&");
                    if (arr[0] != undefined && arr[1] != undefined) {
                        let pr = document.createElement("a");
                        let img = document.createElement("img");
                        var br = document.createElement("br");
                        pr.style.padding = 5;
                        pr.style.color = "white";
                        img.style.paddingBottom = 5;
                        img.src = "/getProductImg/" + arr[1];
                        img.width = 80;
                        pr.href = "/view/"+arr[1];
                        pr.innerHTML = arr[0];
                        let p=pr.innerText;
                        if(pr.innerText.toLowerCase().includes(val.toLowerCase())) {
                            pr.innerHTML = insertMark(p, p.toLowerCase().indexOf(val.toLowerCase()), val.length);
                            searchBody.append(img, pr, br);
                        }
                    }
                }
            }
        })

    }else{
        searchBody.classList.remove('in');
        searchBody.classList.add('hide');
        searchBody.innerHTML="";
    }
}
function insertMark(str,pos,len) {
    return str.slice(0,pos)+'<mark>'+str.slice(pos,pos+len)+'</mark>'+str.slice(pos+len)
}