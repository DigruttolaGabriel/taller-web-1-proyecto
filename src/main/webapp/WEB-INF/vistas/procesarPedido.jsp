<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>

<h3 class="pedido-titulo">Su N�mero Identificador de Pedido es: ${idPedido} </h3> <br>
<section class="pedido mx-auto">
	<c:forEach items="${pedidoComidaList}" var="pedidoComida" varStatus="status">
		<article class="pedido-flex rounded">
			<div class="pedido-detalle">
			<p style = "text-decoration-line: underline;">${pedidoComida.comidaModel.tipo}</p>
			<p>${pedidoComida.comidaModel.nombre}</p>
			
			</div> 
			<div class="pedido-detalle">Precio: $${pedidoComida.comidaModel.precio}</div> 
			<div><img src="https://sevilla.abc.es/gurme/wp-content/uploads/sites/24/2012/01/comida-rapida-casera.jpg" alt="comida"
					width="300" height="200"></div>
		</article><br>
	</c:forEach>
	<p>Pedido realizado a las ${hora} hs.</p>
	<form:form action="pagar" method="post" class="comidas-pedido mx-auto">
		<input type="submit" value="Pagar" class="btn btn-primary btn-block" />
	</form:form>
</section>

<%@ include file="footer.jsp" %>