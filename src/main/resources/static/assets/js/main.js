let currentReview = 0;
function showReview(index) {
    const reviews = document.querySelectorAll('.review-card');
    const dots = document.querySelectorAll('.dot');
    
    // Скрыть все отзывы и убрать активный класс с точек
    reviews.forEach((review, i) => {
        review.classList.remove('active');
        dots[i].classList.remove('active');
    });
    
    // Показать выбранный отзыв и активировать соответствующую точку
    reviews[index].classList.add('active');
    dots[index].classList.add('active');
    
    currentReview = index;
}

// Автоматическое переключение отзывов каждые 5 секунд
setInterval(() => {
    currentReview = (currentReview + 1) % document.querySelectorAll('.review-card').length;
    showReview(currentReview);
}, 5000);