document.addEventListener('DOMContentLoaded', function () {
    const quantityControls = document.getElementsByClassName('quantity-control');

    Array.from(quantityControls).forEach(function (control) {
        control.addEventListener('click', function () {
            const quantityInput = control.parentNode.querySelector('input[type="text"]');
            const action = control.getAttribute('data-action');

            let quantity = parseInt(quantityInput.value);
            if (action === 'increase') {
                quantity += 1;
            } else if (action === 'decrease') {
                if (quantity > 1) {
                    quantity -= 1;
                }
            }

            quantityInput.value = quantity;
        });
    });
});