const buttons = document.getElementsByClassName("delete-button");

Array.from(buttons).forEach(button => {
    button.addEventListener("click", function() {
        const deleteUrl = button.getAttribute("data-url");

        Swal.fire({
            title: "Tem certeza que deseja excluir esse produto?",
            showDenyButton: true,
            confirmButtonText: "Excluir",
            denyButtonText: `Não excluir`
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = deleteUrl;
            } 
        });
    });
});