$(document).ready(function(){
    console.log(123)
    getAllWishlist();
})
function editWishlist(w_id,book_id,w_price){
    var sl = $(".w_quantity").val();
    alert(sl)
    let itemToAdd = {
        id:w_id,
        b_id: book_id,
        w_price: w_price,
        quantity: sl,
        // Thêm các thuộc tính khác tại đây
    };
    $.ajax({
        url: 'http://localhost:8080/BookStore/mywishlist/edit', // Thay thế bằng URL của API hoặc phương thức máy chủ của bạn.
        type: 'PUT',
        contentType: "application/json",
        data: JSON.stringify(itemToAdd),
        success: function(data) {
            getAllWishlist();
            
            
        },
        error: function(error) {
            console.log(error.responseText);
        }
    });
}
function deleteWishlist(w_id){
    getAllWishlist();
    $.ajax({
        url: "http://localhost:8080/BookStore/mywishlist/remove/"+w_id, // Thay thế bằng URL của API hoặc phương thức máy chủ của bạn.
        type: 'DELETE',
        success: function() {
            getAllWishlist();
            
        },
        error: function() {
            console.log('Lỗi khi tải dữ liệu qua AJAX');
        }
    });
}
function addWishList(book_id,w_price,w_quantity){
    let itemToAdd = {
        b_id: book_id,
        w_price: w_price,
        quantity: w_quantity,
        // Thêm các thuộc tính khác tại đây
    };
    $.ajax({
        url: 'http://localhost:8080/BookStore/mywishlist/add', // Thay thế bằng URL của API hoặc phương thức máy chủ của bạn.
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(itemToAdd),
        success: function(data) {
            alert("Thêm vào danh sách thành công")
            
        },
        error: function(error) {
            console.log(error.responseText);
        }
    });
}

function getAllWishlist(){
    $.ajax({
        url: 'mywishlist/getAll', // Thay thế bằng URL của API hoặc phương thức máy chủ của bạn.
        type: 'GET',
        contentType: "application/json",
        dataType: 'json',
        success: function(data) {
            // Xử lý dữ liệu đã lấy được từ máy chủ ở đây.
            // Ví dụ: data sẽ chứa danh sách bạn muốn hiển thị.
            // Hãy cập nhật HTML trong tbody của bạn với dữ liệu này.
            $('.wish-list-table tbody').empty();
            
            // Duyệt qua danh sách dữ liệu và cập nhật tbody
            data.forEach(function(w) {
                var row = '<tr>';
                row += '<td class="product-image"><a href="#"><img  src="'+'/images/'+ w.book.image+'" alt=""></a></td>';
                row += '<td class="product-details"><h4>' + w.book.title + '</h4><p>' + w.book.description + '</p><textarea placeholder="Please Enter Your Comment"></textarea></td>';
                row += '<td class="product-cart"><div class="product-cart-details"><span>' + w.book.price.toLocaleString('en-US') + 'đ</span><input type="number" name="w_quantity" class="w_quantity" value="' + w.quantity + '"><input type="submit" value="ADD TO CART"></div><p><a  onclick="editWishlist('+w.id+','+w.book.id+','+w.book.price+')">Edit</a></p></td>';
                row += '<td class="product-remove"><a onclick="deleteWishlist(' + w.id + ')"><i class="flaticon-delete"></i></a></td>';
                row += '</tr>';
                $('tbody').append(row);
            });
            
        },
        error: function() {
            console.log('Lỗi khi tải dữ liệu qua AJAX');
        }
    });


}