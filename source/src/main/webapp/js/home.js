"use strict"

window.onload = function () {
    const needsInput = "${needsInput}" === "true";
    if (needsInput) {
        document.getElementById("popup").style.display = "block";
    }
};
