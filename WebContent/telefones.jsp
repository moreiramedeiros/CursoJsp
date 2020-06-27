<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!--   chamada da tag jstl    -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de Telefones</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
</head>
<body>

<!-- Menu superior -->
<table>
	<tr> 
	  <td>
    	<a href="acessoliberado.jsp"> 
		    <img alt="Início" title="Início"
			width="30px" height="30px" src="resources/img/home.png" />	
	   </a>
	 </td>
	 <td>
	    <a href="index.jsp"> 
	     	<img alt="Sair" title="Sair"
			width="30px" height="30px" src="resources/img/excluir.png" />	
	   </a>
	 </td>
	 	  <td>
    	<a href="salvarUsuario?acao=listartodos"> 
		    <img alt="Voltar" title="Voltar"
			width="30px" height="30px" src="resources/img/voltar.png"/>	
	   </a>
	 </td>
	<tr>
</table>

 


<!-- Formulario de cadastro de telefones -->

	<center>
		<h3>Cadastro de Telefones</h3>
		<h3 style="color: green">${msg}</h3>
	</center>


	<form action="salvarTelefones" method="post" id="formUser"
		onsubmit="return validarCampos() ? true : false">
		<ul class="form-style-1">
			<table>
				<tr>
					<td>Código do Usuario:</td>
					<td><input type="text" readonly="readonly" id="id" name="id"
						value="${usuario.id}"></td>
				</tr>
				<tr>
					<td>Nome do Usuario:</td>
					<td><input type="text" readonly="readonly" id="nome"
						name="nome" value="${usuario.nome}"></td>
				</tr>
				<tr>
					<td>Telefone:</td>
					<td><input type="number" id="numero" name="numero" value=""></td>
					<td><select id="tipo" name="tipo">
							<option>Casa</option>
							<option>Celular</option>
							<option>Contato</option>
					</select></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Salvar">
				</tr>
			</table>
		</ul>
	</form>


	<script type="text/javascript">
		function validarCampos() {
			 if (document.getElementById("numero").value == '') {
				alert("Informe o numero do telefone!");
				return false;
			} 
			return true;
		}
	</script>
	<br>
	
	<!-- Lista de telefones -->

	<table class="customers">
		<tr>
			<th>Id</th>
			<th>Nome</th>
			<th>Tipo</th>
			<th>Excluir</th>
		</tr>
		<c:forEach items="${telefones}" var="fones">
			<tr>
				<td><c:out value="${fones.id}"></c:out></td>
				<td><c:out value="${fones.numero}"></c:out></td>
				<td><c:out value="${fones.tipo}"></c:out></td>
				<td><a href="salvarTelefones?acao=deleteFone&foneId=${fones.id}"> <img
						src="resources/img/excluir.png" width="20px" height="20px"
						alt="Excluir" title="Excluir"  onclick="return confirm('Confirmar a exclusão?')"/>
				</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>