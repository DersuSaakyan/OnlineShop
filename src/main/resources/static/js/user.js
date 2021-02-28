

$(".cats").click(function () {
    let catId = this.dataset.id;
    $.ajax({
        url: "/getProductByCat",
        data: {
            catId: catId
        },
        type: "GET",
        success: function (str) {
            $("#cats").html("");
            let arr = str.split("\n");
            for (let i = 0; i < arr.length - 1; i++) {

                let sub_arr = arr[i].split("&copy;");
                let title = sub_arr[0];
                let price = sub_arr[1];
                let id = sub_arr[2];
                let color=sub_arr[3];

                $("#cats").append(` <div  class="col-lg-3 col-md-6 special-grid c" id="${id}">
                    <div class="products-single fix">
                        <div class="box-img-hover" style="height: 230px">
                         
                            <img src="/getProductImg/${id}" class="img-fluid" alt="Image">
                            <div class="mask-icon">
                                <ul>
                                    <li><a href="/view/${id}" data-toggle="tooltip" data-placement="right" title="View"><i
                                            class="fas fa-eye"></i></a></li>
                                    <li><a href="#" data-toggle="tooltip" data-placement="right" title="Compare"><i
                                            class="fas fa-sync-alt"></i></a></li>
                                    <li><a href="#" data-toggle="tooltip" data-placement="right"
                                           title="Add to Wishlist"><i class="far fa-heart"></i></a></li>
                                </ul>
                                 <button class="cart del" onclick="del(this)" id="${id}">Delete</button>
                            </div>
                        </div>
                        <div class="why-text" style="background: ${color}">
                            <h4>${title}</h4>
                            <h5>${price} $</h5>
                        </div>
                    </div>
            </div>`)
            }

        }
    })
})

$("#all").click(function () {
        $.ajax({
            url: "/allProducts",
            type: "POST",
            success: function (p) {
                $("#cats").html("");
                let arr = p.split("\n");
                for (let i = 0; i < arr.length - 1; i++) {
                    let sub_arr = arr[i].split("&copy;");
                    let title = sub_arr[0];
                    let price = sub_arr[1];
                    let id = sub_arr[2];
                    let color=sub_arr[3];

                    $("#cats").append(`<div  class="col-lg-3 col-md-6 special-grid c" id="${id}">
                    <div class="products-single fix">
                        <div class="box-img-hover" style="height: 230px">
                         
                            <img src="/getProductImg/${id}" class="img-fluid" alt="Image">
                            <div class="mask-icon">
                                <ul>
                                    <li><a href="/view/${id}" data-toggle="tooltip" data-placement="right" title="View"><i
                                            class="fas fa-eye"></i></a></li>
                                    <li><a href="#" data-toggle="tooltip" data-placement="right" title="Compare"><i
                                            class="fas fa-sync-alt"></i></a></li>
                                    <li><a href="#" data-toggle="tooltip" data-placement="right"
                                           title="Add to Wishlist"><i class="far fa-heart"></i></a></li>
                                </ul>
                              <button class="cart del" onclick="del(this)"  id="${id}">Delete</button>
                            </div>
                        </div>
                        <div class="why-text" style="background: ${color}">
                            <h4>${title}</h4>
                            <h5>${price} $</h5>
                        </div>
                    </div>
            </div>`)
                }
            }
        })
    }
)

$(".del").click(function () {
    let prId = this.dataset.id;
    console.log(prId);
    $.ajax({
        type: "POST",
        url: "/deletePr",
        data: {
            prId: prId
        }, success: function (id) {
            document.getElementById(id).remove();
        }
    })
})
function del(obj) {
    let prId =obj.id;
    $.ajax({
        type: "POST",
        url: "/deletePr",
        data: {
            prId: prId
        }, success: function (id) {
            document.getElementById(id).remove();
        }
    })
}

