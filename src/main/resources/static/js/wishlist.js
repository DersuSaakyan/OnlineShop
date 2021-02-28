$(".del").click(function () {
    let prId=this.dataset.id;
    $.ajax({
        type:"GET",
        url:"/deleteWishListPr",
        data:{
            prId:prId
        },success:function () {
            document.getElementById(prId).remove();
        }
    })
})