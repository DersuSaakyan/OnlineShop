let quantity=document.querySelectorAll(".quantity");
let total=document.querySelectorAll(".totals");

$.ajax({
    type:"GET",
    url:"getOrdersData"
})