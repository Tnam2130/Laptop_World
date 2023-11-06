document.addEventListener('DOMContentLoaded', function () {
    const quantityControls = document.getElementsByClassName('quantity-control');

    Array.from(quantityControls).forEach(function (control) {
        control.addEventListener('click', function () {
            const quantityInput = control.parentNode.querySelector('input[type="text"]');
            const action = control.getAttribute('data-action');
            const maxQuantity = parseInt(quantityInput.getAttribute('max'));
            console.log(maxQuantity)
            let quantity = parseInt(quantityInput.value);
            if (action === 'increase') {
                if (quantity < maxQuantity) {
                    quantity += 1;
                }
            } else if (action === 'decrease') {
                if (quantity > 1) {
                    quantity -= 1;
                }
            }

            quantityInput.value = quantity;
        });
    });
});

document.addEventListener("DOMContentLoaded", function () {
    // Ẩn trang tải khi trang đã tải xong
    document.getElementById("loading-page").style.display = "none";
});