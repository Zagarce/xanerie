document.addEventListener('DOMContentLoaded', function () {
    generateThumbnails();
});

function changeImage(source) {
    document.getElementById('preview-image').src = source;
}

function generateThumbnails() {
    const thumbnailsContainer = document.getElementById('thumbnails-container');

    // Array con las rutas de las miniaturas
    const thumbnailImages = ['./src/fem_ava.png', './src/mal_ava.png'];

    thumbnailImages.forEach(thumb => {
        const img = document.createElement('img');
        img.src = thumb;
        img.onclick = function () {
            changeImage(img.src);
        };
        thumbnailsContainer.appendChild(img);
    });
}

function triggerFileInput() {
    const inputFile = document.getElementById('input-file');
    inputFile.click();
}

function previewImage() {
    const inputFile = document.getElementById('input-file');
    const previewImage = document.getElementById('preview-image');

    const file = inputFile.files[0];

    if (file) {
        const reader = new FileReader();

        reader.onload = function (e) {
            previewImage.src = e.target.result;

            // Almacena la imagen en sessionStorage
            sessionStorage.setItem('userImage', e.target.result);
        };

        reader.readAsDataURL(file);
    }
}

// Restaura la imagen desde sessionStorage al cargar la página
document.addEventListener('DOMContentLoaded', function () {
    const storedImage = sessionStorage.getItem('userImage');
    if (storedImage) {
        document.getElementById('preview-image').src = storedImage;
    }
});