document.querySelectorAll(".wobble_container").forEach(container => {
    let wobble_btn = container.querySelector(".wobble_btn");
    let originalLeft = wobble_btn.style.left;
    let originalTop = wobble_btn.style.top;
    let isMouseInside = false;

    container.addEventListener("mousemove", (e) => {
        if (!isMouseInside) {
            isMouseInside = true;
            wobble_btn.style.transition = "left 0.1s ease, top 0.1s ease";
        }
        let rect = container.getBoundingClientRect();
        let x = e.clientX - rect.left - (wobble_btn.offsetWidth / 2);
        let y = e.clientY - rect.top - (wobble_btn.offsetHeight / 2);

        // Check if the button is within the container's boundaries
        if (x < 0 || x + wobble_btn.offsetWidth > rect.width || y < 0 || y + wobble_btn.offsetHeight > rect.height) {
            wobble_btn.style.left = originalLeft;
            wobble_btn.style.top = originalTop;
            isMouseInside = false;
        } else {
            requestAnimationFrame(() => {
                wobble_btn.style.left = x + "px";
                wobble_btn.style.top = y + "px";
            });
        }
    });

    container.addEventListener("mouseleave", () => {
        isMouseInside = false;
        wobble_btn.style.transition = "left 0.5s ease, top 0.5s ease";
        wobble_btn.style.left = originalLeft;
        wobble_btn.style.top = originalTop;
    });

    wobble_btn.addEventListener("mouseout", () => {
        isMouseInside = false;
        wobble_btn.style.transition = "left 0.5s ease, top 0.5s ease";
        wobble_btn.style.left = originalLeft;
        wobble_btn.style.top = originalTop;
    });
});