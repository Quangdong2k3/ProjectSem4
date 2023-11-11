
function xacnhan(){
	    var toast = `
    <div class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-delay="2000" style='position: fixed;top: 10px;right: 10px;
width: 300px; 
z-index: 100000;'>
        <div class="toast-header">
            <strong class="mr-auto bg-success">Thông báo</strong>
            <button type="button" class="ml-2 mb-1 close" data-dismiss="toast">&times;</button>
        </div>
        <div class="toast-body">
            Xóa dữ liệu thành công
        </div>
    </div>
`;
$(".container-fluid").append(toast);

// Hiển thị toast
$(".toast").toast("show");
}
