$(document).ready(function () {
    // Lấy URL hiện tại của trang
    var currentUrl = window.location.href;
    console.log(currentUrl)
    var adminIndex = currentUrl.indexOf("/admin/");

// Kiểm tra xem chuỗi "/admin/" có tồn tại trong URL không
    if (adminIndex !== -1) {
        // Trích xuất phần của URL sau chuỗi "/admin/"
        var pathAfterAdmin = currentUrl.substring(adminIndex);

        console.log(pathAfterAdmin); // In ra kết quả "/admin/order"
    } else {
        console.log("Không tìm thấy chuỗi '/admin/' trong URL");
    }
    // Bắt sự kiện click trên tất cả các thẻ "a" trong #sidebarMenu
    $('#sidebarMenu a').click(function () {
        // Loại bỏ lớp CSS "active" từ tất cả các thẻ "a" trong #sidebarMenu
        $('#sidebarMenu a').removeClass('active');

        // Thêm lớp CSS "active" cho thẻ "a" được click
        $(this).addClass('active');
    });

    // Tìm và so sánh URL hiện tại với href của thẻ "a" trong #sidebarMenu
    $('#sidebarMenu a').each(function () {
        var href = $(this).attr('href');

        if (pathAfterAdmin === href) {
            $(this).addClass('active');
        }
    });
});