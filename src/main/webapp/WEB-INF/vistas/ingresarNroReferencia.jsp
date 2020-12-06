<%@ include file="header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<section class="mx-auto" style="width:50%;">
	<div class="text-center">
		<h1 class="bebas display-4 mb-4">Ingresar Nro de Referencia de Mercado Pago</h1>
		<h4 class="mb-4">${error}</h4>
		
		<form action="/proyecto-limpio-spring-master/finalizar-pedido/nro-referencia" method="post">
			<input type="hidden" value="${idPedido}" name="idPedido" />
			<div class="form-group">
				<input type="number" placeholder="Nro de Referencia" name="nroReferencia" class="form-control">
				<input type="submit" value="Enviar" class="btn btn-dark mt-3" />
			</div>
		</form>
	</div>
</section>

<%@ include file="footer.jsp" %>