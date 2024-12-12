let currentReview = 0;
function showReview(index) {
    const reviews = document.querySelectorAll('.review-card');
    const dots = document.querySelectorAll('.dot');
    
    reviews.forEach((review, i) => {
        review.classList.remove('active');
        dots[i].classList.remove('active');
    });
    
    reviews[index].classList.add('active');
    dots[index].classList.add('active');
    
    currentReview = index;
}

    setInterval(() => {
        currentReview = (currentReview + 1) % document.querySelectorAll('.review-card').length;
        showReview(currentReview);
    }, 5000);