</main>

<footer class="container-footer bg-dark footer mt-5">
    <div class="container d-flex justify-content-between align-items-center">
        <span class="text-light">Copyright � 2020, Capa 8. Todos los derechos reservados.</span>
        <a href="#" class="btn btn-light float-right">Ir arriba</a>
    </div>
</footer>
<script>
    $('.delete-btn').on('click', function(e) {
        e.preventDefault();
        const href = $(this).attr('href');

        Swal.fire({
            title: 'Eliminar Restaurante',
            text: "�Est�s seguro de querer eliminar este restaurante?",
            icon: 'warning',
            showCancelButton: true,
            cancelButtonText: 'Cancelar',
            confirmButtonColor: '#d33',
            cancelButtonColor: '#3085d6',
            confirmButtonText: 'Eliminar Restaurante'
        }).then((result) => {
            if (result.isConfirmed) {
                document.location.href = href;
            }
        })
    });
</script>
</body>

</html>