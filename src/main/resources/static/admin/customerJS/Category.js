        getAllCate();

        function getAllCate() {
          $.ajax({
            type: "GET",
            url: "http://localhost:8080/category/list",
            success: function (data) {
              // Xử lý dữ liệu JSON và hiển thị trên trang HTML
              CTable(data);
            },
            error: function (error) {
              alert(err);
            },
          });
        }

        function CTable(data) {
          var dataTable = $("#tblCategory");
          $("#tblCategory tbody").empty();
          for (var i = 0; i < data.length; i++) {
            console.log(data[i].name);
            dataTable
              .find("tbody")
              .append(
                "<tr><td>" +
                  data[i].id +
                  "</td><td>" +
                  data[i].name +
                  "</td><td>" +
                  data[i].description +
                  "</td><td><button type='button' class='btn btn-primary btn_EditCate'  onclick='getCateID(" +
                  data[i].id +
                  ")'>Edit</button><button type='button' class='btn btn-danger btn_deleteCate' onclick='deleteCategory(" +
                  data[i].id +
                  ")'>Delete</button></td></tr>"
              );
          }
        }
        
        function getCateID(id) {
          // $('.updateCate').removeClass('saveCate');
            document.getElementById("exampleModalLabel").innerHTML ="Cập nhật thể loại";
            $(".updateCate").text("Cập nhật");
            $(".saveCate").hide();
            $(".updateCate").show();

            $("#exampleModal").modal("show");
            // alert("ok");
          $.ajax({
            url: "http://localhost:8080/category/details/" + id, // Đường dẫn đến controller để thêm mới dữ liệu
            method: "GET",
            contentType: "application/json",
            success: function (data) {

              $('#cid').val(data.id);
              $("#name").val(data.name);
              $("#description").val(data.description);

            },
            error: function (error) {
              // Xử lý lỗi (nếu có)
              alert("Có lỗi xảy ra: " + error.responseText);
            },
          });
        }
          
        $(".btn_saveCate").click(function () {
          document.getElementById("exampleModalLabel").innerHTML ="Thêm mới thể loại";
            $(".saveCate").text("Lưu");
            $(".updateCate").hide();
            $(".saveCate").show();

        })

          $(".saveCate").click(function () {
            
            var newName = $("#name").val();
            var newDesc = $("#description").val();
            let val = {
              id: null,
              name: newName,
              description: newDesc,
            };
            // console.log(val);
            $.ajax({
              url: "http://localhost:8080/category/create", // Đường dẫn đến controller để thêm mới dữ liệu
              method: "POST",
              contentType: "application/json",
              data: JSON.stringify(val),
              success: function (data) {
                $(".toast").remove();

                getAllCate();
                $("#cid").val("");

                $("#name").val("");
                $("#description").val("");
                $("#exampleModal").modal("hide");
                var toast = `
                            <div class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-delay="2000" style='position: fixed;top: 10px;right: 10px;
        width: 300px; 
        z-index: 100000;'>
                                <div class="toast-header">
                                    <strong class="mr-auto">Thông báo</strong>
                                    <button type="button" class="ml-2 mb-1 close" data-dismiss="toast">&times;</button>
                                </div>
                                <div class="toast-body">
                                    Thêm mới dữ liệu thành công
                                </div>
                            </div>
                        `;
                $(".container-fluid").append(toast);

                // Hiển thị toast
                $(".toast").toast("show");
              },
              error: function (error) {
                // Xử lý lỗi (nếu có)
                alert("Có lỗi xảy ra: " + error.responseText);
              },
            });
          });
          
        function editCategory() {
          // alert(123);
            var cid = $("#cid").val();
            var newName = $("#name").val();
            var newDesc = $("#description").val();
            let val = {
              id: cid,
              name: newName,
              description: newDesc,
            };
            // alert(val.name);
            $.ajax({
              type: "PUT",
              contentType: "application/json",
              url: "http://localhost:8080/category/update",
              data: JSON.stringify(val),
              success: function (data) {
                $(".toast").remove();

                $("#cid").val("");
            
                getAllCate();
                $("#name").val("");
                $("#description").val("");
                $("#exampleModal").modal("hide");
                var toast = `
                            <div class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-delay="2000" style='position: fixed;top: 10px;right: 10px;
        width: 300px; 
        z-index: 100000;'>
                                <div class="toast-header">
                                    <strong class="mr-auto">Thông báo</strong>
                                    <button type="button" class="ml-2 mb-1 close" data-dismiss="toast">&times;</button>
                                </div>
                                <div class="toast-body">
                                    Cập nhật dữ liệu thành công
                                </div>
                            </div>
                        `;
                $(".container-fluid").append(toast);

                // Hiển thị toast
                $(".toast").toast("show");
                // alert(data);
              },
              error: function (error) {
                // Xử lý lỗi (nếu có)
                alert("Có lỗi xảy ra: " + error.responseText);
              },complete: function() {
          getAllCate;
                // Sự kiện AJAX Hoàn Thành (AJAX Complete Event)
            }
            });
          };
        function deleteCategory(id) {
          var confirmDelete = confirm("Bạn có chắc chắn muốn xóa?");
        if (confirmDelete) {
            // Gửi yêu cầu xóa bằng AJAX ở đây nếu người dùng xác nhận
            $.ajax({
                type: "DELETE",
              contentType: "application/json",
              url: "http://localhost:8080/category/delete/"+id,
                success: function (response) {
          getAllCate();
                },
                error: function (error) {
                    console.error("Lỗi xóa:", error);
                }
            });
        } else {
            // Người dùng đã hủy xóa
        }
    
        }
