// Auto-dismiss success alerts after 3 seconds
document.addEventListener('DOMContentLoaded', () => {
    const alert = document.querySelector('.alert');
    if (alert) {
        setTimeout(() => {
            alert.style.transition = 'opacity 0.4s';
            alert.style.opacity = '0';
            setTimeout(() => alert.remove(), 400);
        }, 3000);
    }

    // Live character count for title field
    const titleInput = document.getElementById('title');
    if (titleInput) {
        titleInput.addEventListener('input', () => {
            const remaining = 80 - titleInput.value.length;
            let hint = titleInput.parentElement.querySelector('.char-hint');
            if (!hint) {
                hint = document.createElement('span');
                hint.className = 'char-hint';
                hint.style.cssText = 'font-size:0.75rem;color:#94a3b8;float:right;margin-top:3px;display:block';
                titleInput.parentElement.appendChild(hint);
            }
            hint.textContent = remaining < 20 ? `${remaining} chars left` : '';
        });
    }
});
