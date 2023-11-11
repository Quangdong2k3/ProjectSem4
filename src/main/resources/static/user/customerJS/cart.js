$(document).ready(function () {
  getAllCart();
});
// function editCart(w_id,book_id,w_price){
//     var sl = $(".w_quantity").val();
//     alert(sl)
//     let itemToAdd = {
//         id:w_id,
//         b_id: book_id,
//         w_price: w_price,
//         quantity: sl,
//         // Thêm các thuộc tính khác tại đây
//     };
//     $.ajax({
//         url: 'http://localhost:8080/BookStore/mywishlist/edit', // Thay thế bằng URL của API hoặc phương thức máy chủ của bạn.
//         type: 'PUT',
//         contentType: "application/json",
//         data: JSON.stringify(itemToAdd),
//         success: function(data) {
//             getAllWishlist();

//         },
//         error: function(error) {
//             console.log(error.responseText);
//         }
//     });
// }

function deleteCart(cart_id) {
  var rs = confirm("Are you sure you want to delete");
  if (rs) {
    $.ajax({
      url: "http://localhost:8080/BookStore/api/Cart/remove/" + cart_id, // Thay thế bằng URL của API hoặc phương thức máy chủ của bạn.
      type: "DELETE",
      success: function () {
        getAllViewCart();
        getAllCart();
      },
      error: function () {
        console.log("Lỗi khi tải dữ liệu qua AJAX");
      },
    });
  }
}
function addCart(book_id, w_price, w_quantity) {
  let itemToAdd = {
    book_id: book_id,
    price: w_price,
    quantity: w_quantity,
    // Thêm các thuộc tính khác tại đây
  };
  $.ajax({
    url: "http://localhost:8080/BookStore/api/Cart/add", // Thay thế bằng URL của API hoặc phương thức máy chủ của bạn.
    type: "POST",
    contentType: "application/json",
    data: JSON.stringify(itemToAdd),
    success: function (data) {
      alert("Thêm vào giỏ hàng thành công");
      
        getAllCart();
    },
    error: function (error) {
      console.log(error.responseText);
    },
  });
}

function getAllCart() {
  $.ajax({
    url: "http://localhost:8080/BookStore/api/Cart/getAll", // Thay thế bằng URL của API hoặc phương thức máy chủ của bạn.
    type: "GET",
    contentType: "application/json",
    dataType: "json",
    success: function (data) {
      // Xử lý dữ liệu đã lấy được từ máy chủ ở đây.
      // Ví dụ: data sẽ chứa danh sách bạn muốn hiển thị.
      // Hãy cập nhật HTML trong tbody của bạn với dữ liệu này.
      // $('.add-to-cart-product .cart-product').each(function(){
      //     $(this).remove();
      // });
      $(".add-to-cart-product").empty();
      var dem = 0;
      var total = 0;
      // Duyệt qua danh sách dữ liệu và cập nhật tbody
      data.forEach(function (data) {
        total += data.book.price * data.quantity;
        var row =
          ' <div class="cart-product"><div class="cart-product-image"><a href="single-product.html"> <img src="/images/' +
          data.book.image +
          '" class="w-25" alt=""></a></div>';
        row +=
          '<div class="cart-product-info"><p><span>' +
          data.quantity +
          '</span>x<a href="single-product.html">' +
          data.book.title +
          '</a></p><a href="single-product.html">Mới</a><span class="cart-price">' +
          data.price.toLocaleString("en-US") +
          "đ</span></div>";
        row +=
          '<div class="cart-product-remove" onclick="deleteCart('+data.id+')"><i class="fa fa-times"></i></div>';
        row += "</div>";
        $(".add-to-cart-product").append(row);
        dem++;
      });
      var attr =
        '<div class="total-cart-price"><div class="cart-product-line fast-line"><span>Phí Ship</span><span class="free-shiping">25.000đ</span></div>';
      attr +=
        '<div class="cart-product-line"><span>Thanh toán</span><span class="total">' +
        total.toLocaleString("en-US") +
        'đ</span></div></div><div class="cart-checkout"><a href="checkout.html">Check out<i class="fa fa-chevron-right"></i></a></div></div>';
      $(".add-to-cart-product").append(attr);
      $(".shoping-cart a span").html(dem);
    },
    error: function () {
      console.log("Lỗi khi tải dữ liệu qua AJAX");
    },
  });
}
