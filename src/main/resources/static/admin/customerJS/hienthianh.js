$(document).ready(function(){



	document.getElementById('imageUpload').addEventListener('change', function (e) {
    var imagePreview = document.getElementById('imagePreview');
    imagePreview.innerHTML = ''; // Xóa bất kỳ hình ảnh hiện tại trong #imagePreview

    var files = e.target.files; // Lấy danh sách các tệp đã chọn

    for (var i = 0; i < files.length; i++) {
        var file = files[i];

        if (file.type.match('image.*')) {
            var reader = new FileReader();

            reader.onload = function (event) {
                var img = new Image();
                img.src = event.target.result;
                img.className = 'preview-image w-50';

                imagePreview.appendChild(img);
            };

            reader.readAsDataURL(file);
        }
    }
});


});
	function loadImageASinggle(input) {
	console.log(123)
  var anhXemtrc = document.getElementById("anhxemtrc");
  if (input.files && input.files[0]) {
    var reader = new FileReader();
    reader.onload = function (e) {
      anhXemtrc.src = e.target.result;
    };
    reader.readAsDataURL(input.files[0]);
  } else {
    anhXemtrc.src = "";
  }
}