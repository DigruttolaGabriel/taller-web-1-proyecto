<%@ include file="header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h1 class="text-center h1 display-3 bebas mb-4"><i class="fas fa-utensils mr-3"></i> Nueva mesa</h1>
<section class="mx-auto" style="width:80%;">
	<div class = "restaurantes">
		<div id="loginbox" style="margin-top:50px;" class="card-body">
			<h3>${restaurante.nombre}</h3>
			<form:form action="guardar-nueva-mesa" method="POST" modelAttribute="formularioNuevaMesa">
				<form:hidden path = "idRestaurante" value = "${restaurante.idRestaurante}"/>
				<div class="form-group">
				  	<label>N�mero de mesa:</label>
					<form:input path = "numeroDeMesa" type="text" class="form-control"/>
					<label>Cantidad de comensales:</label>
					<form:input path = "cantidad" type="text" class="form-control"/>
				</div>
				<input type="submit" class="float-right btn btn-dark" value="Guardar"/>
			</form:form>
		</div>
	</div>
</section>

<%@ include file="footer.jsp" %>