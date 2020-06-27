<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!--   chamada da tag jstl    -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de Usuário</title>
<link rel="stylesheet" href="resources/css/cadastro.css">

<!-- Adicionando JQuery -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"
	integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
	crossorigin="anonymous">
	
</script>

</head>
<body>

<!-- Menu superior -->
 

<table  >
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
 
<!-- Formulário de Cadastro -->
	<center>
		<h3><font face="arial">Cadastro de Usuários</font></h3>
		<h3 style="color: red">${msg}</h3>
		<h3 style="color: green">${msg2}</h3>
	</center>


	<form action="salvarUsuario" method="post" id="formUser"
		onsubmit="return validarCampos() ? true : false"
		enctype="multipart/form-data">
		<ul class="form-style-1">
		   
			 <div style="text-align: center;">
	 					<c:if test="${user.fotoBase64Miniatura.isEmpty() == false}">
						<a href="salvarUsuario?acao=download&tipo=imagem&id=${user.id}">
							<img src='<c:out value="${user.fotoBase64Miniatura}"/>' alt="Imagem"
							title="Imagem" width="45px" height="45px" />
						</a>
					</c:if> 
					<c:if test="${user.fotoBase64Miniatura.isEmpty() == true ||
				              	user.fotoBase64Miniatura.isEmpty() == null}">				
							<img src='resources/img/usuario.png' alt="Imagem"
							title="Imagem" width="45px" height="45px"  onclick="alert('Não possui imagem!')" />
					</c:if>	
			</div>
		 				<br>		
		
			
			<table>
				<tr>
					<td>Código:</td>
					<td><input type="text" readonly="readonly" id="id" name="id"
						value="${user.id}"></td>

					<td>CEP:</td>
					<td><input type="text" id="cep" name="cep"
						onblur="consultaCep()" value="${user.cep}"
						placeholder="Número do CEP"  maxlength="25"></td>
				</tr>
				<tr>
					<td>Login:*</td>
					<td><input type="text" id="login" name="login"
						value="${user.login}" placeholder="Informe o login" maxlength="25"></td>
						
					<td>Rua:</td>
					<td><input type="text" id="rua" name="rua" value="${user.rua}" maxlength="50"></td>
				</tr>
				<tr>
					<td>Senha:*</td>
					<td><input type="password" id="senha" name="senha"
						value="${user.senha}" placeholder="Informe a senha" maxlength="25"></td>

					<td>Bairro:</td>
					<td><input type="text" id="bairro" name="bairro"
						value="${user.bairro}" maxlength="20"></td>
				</tr>
				<tr>
					<td>Nome:*</td>
					<td><input type="text" id="nome" name="nome"
						value="${user.nome}" placeholder="Nome do Usuario" maxlength="50"></td>

					<td>Cidade:</td>
					<td><input type="text" id="cidade" name="cidade"
						value="${user.cidade}"  maxlength="50"></td>

				</tr>
				<tr>
					<td>Ativo:</td>
					<td> <input type="checkbox" id="ativo" name="ativo" 			
                      <% 
                          if(request.getAttribute("user")!=null){
                        	  BeansCursoJsp user = (BeansCursoJsp) request.getAttribute("user");  
                        	  if(user.isAtivo()){
                        		  out.print(" checked ");
                        	  }
                          }
                      %>
                    >  
                      <%@ page import="beans.BeansCursoJsp" %>                 
					</td>				
					

					<td>Estado:</td>
					<td><input type="text" id="estado" name="estado"
						value="${user.estado}" maxlength="25"></td>
				 
				</tr>
			 
				
				<tr>
					<td>Sexo:</td>
					<td> 
					    <input type="radio" name="sexo" value="masculino" 	id="m" 				    
                        <% 
                         if(request.getAttribute("user")!=null){
                        	  BeansCursoJsp user = (BeansCursoJsp) request.getAttribute("user");  
                        	  if(user.getSexo().equalsIgnoreCase("masculino")){
                        		  out.print(" checked ");
                        	  }
                          }
                        %>					    				   
					    > Masculino 
                        <input type="radio" name="sexo" value="feminino"  id="f" 
                        <% 
                        if(request.getAttribute("user")!=null){
                        	  BeansCursoJsp user = (BeansCursoJsp) request.getAttribute("user");  
                        	  if(user.getSexo().equalsIgnoreCase("feminino")){
                        		  out.print(" checked ");
                        	  }
                          }
					    %>	
                        >  Feminino           
					</td>	
					
				    <td>IBGE:</td>
					<td><input type="text" id="ibge" name="ibge"
						value="${user.ibge}"  maxlength="25"></td>	
									
				</tr>
				<tr>
				   <td>Perfil:</td>
				    <td>
					<select id="perfil" name="perfil">	
								
					    <option value="não-informado">[Selecione]</option>
					    
					    <option value="administrador"					    
					    <% 
                        if(request.getAttribute("user")!=null){
                        	  BeansCursoJsp user = (BeansCursoJsp) request.getAttribute("user");  
                        	  if(user.getPerfil().equalsIgnoreCase("administrador")){
                        		  out.print(" selected ");
                        	  }
                          }
					    %>
					    >Administrador</option>
					    
					    
					    <option value="secretario"
					    <% 
                        if(request.getAttribute("user")!=null){
                        	  BeansCursoJsp user = (BeansCursoJsp) request.getAttribute("user");  
                        	  if(user.getPerfil().equalsIgnoreCase("secretario")){
                        		  out.print(" selected ");
                        	  }
                          }
					    %>
					   >Secretário</option>
					   
					   
					    <option value="gerente"					    
					    <% 
                        if(request.getAttribute("user")!=null){
                        	  BeansCursoJsp user = (BeansCursoJsp) request.getAttribute("user");  
                        	  if(user.getPerfil().equalsIgnoreCase("gerente")){
                        		  out.print(" selected ");
                        	  }
                          }
					    %>			    					 				    
					    >Gerente</option>
					   
					    <option value="funcionario"
					    <% 
                        if(request.getAttribute("user")!=null){
                        	  BeansCursoJsp user = (BeansCursoJsp) request.getAttribute("user");  
                        	  if(user.getPerfil().equalsIgnoreCase("funcionario")){
                        		  out.print(" selected ");
                        	  }
                          }
					    %>					    
					    >Funcionário</option>					    
					  </select>
					  
					  

					  
					  
					</td>
				</tr>
				
				<tr>
					<td>Foto:</td>
					<td><input type="file" name="foto"></td> 
				</tr>
				<tr>
					<td>Curriculo:</td>
					<td><input type="file" name="curriculo"></td>
					
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Salvar"> <input
						type="submit" value="Cancelar"
						onclick="document.getElementById('formUser').action = 'salvarUsuario?acao=reset'">
					</td>
				</tr>
			</table>
		</ul>
	</form>
	<script type="text/javascript">
		function validarCampos() {
			
			
			if(document.getElementById("f").checked == false && document.getElementById("m").checked == false){
	    		alert("Informe o sexo!");
	    		return false;
	       } 
			
			if (document.getElementById("login").value == '') {
				alert("Informe o login!");
				return false;
			} else if (document.getElementById("senha").value == '') {
				alert("Informe o senha!");
				return false;
			} else if (document.getElementById("nome").value == '') {
				alert("Informe a nome!");
				return false;
			} else if (document.getElementById("telefone").value == '') {
				alert("Informe o telefone!");
				return false;
			} 

			return true;
		}
		
		
		

		//Consultando CEP com jQuery

		function consultaCep() {
			var cep = $("#cep").val();

			//Consulta o webservice viacep.com.br/
			$.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?",
					function(dados) {

						if (!("erro" in dados)) {
							//Atualiza os campos com os valores da consulta.
							$("#rua").val(dados.logradouro);
							$("#bairro").val(dados.bairro);
							$("#cidade").val(dados.localidade);
							$("#estado").val(dados.uf);
							$("#ibge").val(dados.ibge);
						} //end if.
						else {
							$("#rua").val('');
							$("#bairro").val('');
							$("#cidade").val('');
							$("#estado").val('');
							$("#ibge").val('');
							//CEP pesquisado não foi encontrado.
							alert("CEP não encontrado.");
						}
					});

		}
	</script>
	<br>



 
  <%@ page import="java.util.List" %>
  <%
         List<BeansCursoJsp> lista = (List<BeansCursoJsp>) request.getAttribute("usuarios");   
         if(lista != null){
             if(lista.size() == 0){
           	  String script = "<script>alert('Nome não encontrado!')</script>";
           	    out.print(script);
            }        	      	 
         }
   
  %>


<!-- Formulário de pesquisa -->


<form action="servletPesquisa" method="post">
  <ul class="form-style-1">
   <table>
      <tr>
        <td> Descrição </td>
         <td> <input type="text" id="descricaoconsulta" name="descricaoconsulta" placeholder="Digite o nome"> </td>
         <td> <input type="submit"  value="Pesquisar"> </td>
      </tr>
   </table>
  </ul>
 </form>
 

<!-- Lista de clientes -->

	<center>
		<h3><font face="arial">Lista de Usuários</font></h3>
    <center>
	<table class="customers">
		<tr>
			<th>Id</th>
			<th>Login</th>
			<th>Imagem</th>
			<th>Curriculo</th>
			<th>Nome</th>
			<th>Telefones</th>
			<th>Delete</th>
			<th>Editar</th>
		</tr>
		<c:forEach items="${usuarios}" var="user">
			<tr>
				<td><c:out value="${user.id}"></c:out></td>
				<td><c:out value="${user.login}"></c:out></td>
				<td>
					<c:if test="${user.fotoBase64Miniatura.isEmpty() == false}">
						<a href="salvarUsuario?acao=download&tipo=imagem&id=${user.id}">
							<img src='<c:out value="${user.fotoBase64Miniatura}"/>' alt="Imagem"
							title="Imagem" width="32px" height="32px" />
						</a>
					</c:if> 
					<c:if test="${user.fotoBase64Miniatura.isEmpty() == true ||
				              	user.fotoBase64Miniatura == null}">				
							<img src='resources/img/usuario.png' alt="Imagem"
							title="Imagem" width="32px" height="32px"  onclick="alert('Não possui imagem!')" />
					</c:if>				
				</td>
				<td>
				<c:if test="${user.curriculoBase64.isEmpty() == false}">
				   <a	href="salvarUsuario?acao=download&tipo=curriculo&id=${user.id}">
				      <img src="resources/img/pdf.png"  width="25px" height="32px" />
				   </a>
				</c:if>	
				<c:if test="${user.curriculoBase64 == null}">				  
				      <img src="resources/img/no.png"  width="20px" height="20px" onclick="alert('Não possui curriculo!')" />
				</c:if>		
						</td>
				<td><c:out value="${user.nome}"></c:out></td>
				
				
				<td>
				<a href="salvarTelefones?acao=addFone&id=${user.id}"> 	  
		      	<img src="resources/img/telefone.png" width="20px" height="20px"
						alt="Telefones" title="Telefones" />  (${user.qtdTelefones})	 				
				</a> 	
				</td>
				<td><a href="salvarUsuario?acao=delete&id=${user.id}"> <img
						src="resources/img/excluir.png" width="20px" height="20px"
						alt="Excluir" title="Excluir"   onclick="return confirm('Confirmar a exclusão?')" />
				</a></td>
				<td><a href="salvarUsuario?acao=editar&id=${user.id}"> <img
						src="resources/img/editar.png" width="20px" height="20px"
						alt="Editar" title="Editar" />
				</a></td>
				

			</tr>
		</c:forEach>
	</table>


</body>
</html>