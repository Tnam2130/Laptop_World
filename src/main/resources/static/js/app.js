// JavaScript để hiển thị hộp xem ảnh khi click vào thẻ a
const imageLinks = document.querySelectorAll('.image-link');
const modal = document.getElementById('image-modal');
const modalImage = document.getElementById('modal-image');
const closeModal = document.querySelector('.close');
const productElement = document.querySelector('.productGallery_thumb');
const product = productElement.getAttribute('data-image-name');

imageLinks.forEach((link) => {
    link.addEventListener('click', (e) => {
        e.preventDefault(); // Ngăn trình duyệt điều hướng đến URL
        const imageIndex = link.getAttribute('data-image-index');
        const imageSource = `/uploads/products/${product.getImages()[imageIndex].getName()}`;
        modalImage.src = imageSource;
        modal.style.display = 'block';
    });
});

closeModal.addEventListener('click', () => {
    modal.style.display = 'none';
});

window.addEventListener('click', (e) => {
    if (e.target == modal) {
        modal.style.display = 'none';
    }
});