<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!--   chamada da tag jstl    -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de Usuário</title>
<link rel="stylesheet" href="resources/css/cadastro.css">

<script src="resources/javascript/jquery.min.js" type="text/javascript"></script>
<script src="resources/javascript/jquery.maskMoney.min.js" type="text/javascript"></script> 
</head>
<body>

<!-- Menu superior -->
<table >
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
	<tr>
</table>



<!-- Cadastro de Produtos -->

	<center>

		<h3><font face="arial">Cadastro de Produtos</font></h3>
		<h3 style="color: red">${msg}</h3>
		<h3 style="color: green">${msg2}</h3>
	</center>


	<form action="salvarProduto" method="post" id="formUser" onsubmit=" return validarCampos() ? true : false">
		<ul class="form-style-1">
			<table>
				<tr>
					<td>Código:</td>
					<td><input type="text" readonly="readonly" id="id" name="id"
						value="${produto.id}"></td>
				</tr>
				<tr>
					<td>Nome:*</td>
					<td><input type="text" id="nome" name="nome"
						value="${produto.nome}"></td>
				</tr>
				<tr>
					<td>Quantidade:*</td>
					<td><input type="text" id="qtd" name="qtd"  onkeyup="BloqueiaLetras(this)"  maxlength="10"
						value="${produto.qtd}"></td>
				</tr>
				<tr>
					<td>Valor (R$):*</td>
					<td><input type="text" id="valor" name="valor" data-thousands="." data-decimal="," data-prefix="R$ " 
					
						value="${produto.valorEmText}"				 
						></td>
					 
						
				</tr>
				<tr>
					<td>Categoria:</td>
					<td> 
					      <select name="categoria_id" id="categoria">
					           <c:forEach items="${categorias}"  var="cat"> 
					               <option value="${cat.id}" id="${cat.id}"
					                    <c:if test="${cat.id == produto.categoria_id}">
					                    	<%   out.print(" selected ");
					                    	//<c:out value=" selected "></c:out>	
					                    	%>			                    
					                    </c:if>   
					               > ${cat.nome} </option>			           
					           </c:forEach>					
					     </select>
					 </td>
					 
						
				</tr>
				
				
				<tr>
					<td></td>
					<td><input type="submit" value="Salvar"> <input
						type="submit" value="Cancelar"
						onclick="document.getElementById('formUser').action = 'salvarProduto?acao=reset'">
					</td>
				</tr>
			</table>
		</ul>
	</form>
<script type="text/javascript">

function validarCampos(){
	if(document.getElementById("nome").value == ''){
		alert("Informe o nome!");
		return false;
	}else 	if(document.getElementById("qtd").value == ''){
		alert("Informe a quantidade!");
		return false;
	}else 	if(document.getElementById("valor").value == ''){
		alert("Informe o valor!");
		return false;
	}
	/*	else 	if(isNaN(document.getElementById("qtd").value)){
		alert("O campo quantidade deve ser número!");
		return false;
	}else 	if(isNaN(document.getElementById("valor").value)){
		alert("O campo valor deve ser número com casa decimal separada por ponto!");
		return false;
	}*/
	
	return true;
}



  var aux;
  function limitarValorInput(obj,min,max,limite) { 
     obj.value = obj.value.substring(0,limite+1); 
       if(obj.value >= min  && obj.value <= max){
             aux = obj.value;
       }else{
           obj.value = aux; 
       }
   }

</script>


<script>
  $(function() {
    $('#valor').maskMoney();
  })
  
  function BloqueiaLetras(obj)
{     
     var er = new RegExp("[^0-9]" , "g");
     obj.value = obj.value.replace(er, "");  
}

  
  
  
  
</script>


	<br>

<!-- Lista de Produtos -->
	<h3><font face="arial">Lista de Produtos</font></h3>
	<table class="customers2">
		<tr>
			<th>Id</th>
			<th>Nome</th>
			<th>Qtd</th>
			<th>Valor</th>
			<th>Delete</th>
			<th>Editar</th>
		</tr>
		<c:forEach items="${produtos}" var="produto">
			<tr>
				<td><c:out value="${produto.id}"></c:out></td>
				<td><c:out value="${produto.nome}"></c:out></td>
				<td><c:out value="${produto.qtd}"></c:out></td>
				<td>
				    <fmt:setLocale value="pt-BR"/>
            	    <fmt:formatNumber value="${produto.valor}" type="currency" />				
				</td>
				
				<td><a href="salvarProduto?acao=delete&id=${produto.id}"> <img
						src="resources/img/excluir.png" width="20px" height="20px"
						alt="Excluir" title="Excluir"  onclick="return confirm('Confirmar a exclusão?')"/>
				</a></td>
				<td><a href="salvarProduto?acao=editar&id=${produto.id}"> <img
						src="resources/img/editar.png" width="20px" height="20px"
						alt="Editar" title="Editar" />
				</a></td>
			</tr>
		</c:forEach>
	</table>


</body>
</html>