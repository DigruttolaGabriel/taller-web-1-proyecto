<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ include file="header.jsp" %>

        <h3 class="pedido-titulo">Su N�mero Identificador de Pedido es: ${idPedido} </h3> <br>
        <section class="pedido mx-auto">
            <c:forEach items="${Comidas}" var="comida" varStatus="status">
                <article class="pedido-flex">
                    <div class="pedido-detalle">
                        <p style="text-decoration-line: underline;">${pedidoComida.comidaModel.tipo}</p>
                        <p>${pedidoComida.comidaModel.nombre}</p>

                    </div>
                    <div class="pedido-detalle">Precio: $${pedidoComida.comidaModel.precio}</div>
                    <div>
                        <c:if test="${not empty comida.imageName}">
                            <img src="img/comidas/${comida.imageName}" width="300" height="200">
                        </c:if>
                        <c:if test="${empty comida.imageName}">
                            <img src="img/comidas/defaultComida.jpg" width="300" height="200">
                        </c:if>
                    </div>
                </article><br>
            </c:forEach>
            <p>Pedido realizado a las ${hora} hs.</p>
            <form:form action="pagar" method="post" class="comidas-pedido mx-auto">
                <input type="submit" value="Pagar" class="btn btn-primary btn-block" />
            </form:form>
        </section>

        <%@ include file="footer.jsp" %>