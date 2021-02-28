
$(".like").click(function () {
    let id=this.dataset.id;
    $.ajax({
        type:"GET",
        url:"/addWishList",
        data:{
            id:id
        },success: (isLike)=> {
            if(isLike==="notFound"){
                alert("Please sign in");
            }
            else if(isLike==="Wish"){
                alert("It is Already in cart");
            }
            else {
                if (this.children[0].className === "fa fa-heart") {
                    this.children[0].className = "fa fa-heart-o";
                } else {
                    this.children[0].className = "fa fa-heart";
                }
            }
        }
    })
});




