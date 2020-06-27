<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Curso JSP</title>
<link rel="stylesheet" href="resources/css/estilo.css">
</head>
<body>



  <div class="login-page">
   <center><h3>Projeto Didático</h3></center>
  <center><h1>Servlets + JSP + JDBC</h1></center>
   <center> USUARIO = admin <br> SENHA = admin</center>
  
      <div class="form">
	  <form action="LoginServlet" method="post" onsubmit = "return validarCampos()?true:false">
	    Login: <input type="text" id="login" name="login"> <br />
	    Senha: <input type="password" id="senha" name="senha"> <br />
	    <button type="submit" value="Logar">Logar</button>
	 </form>
     </div>
     
      <center>Formação Java Web</center>
       
  </div>
  

  
  
  
  
  
  <script>
     function validarCampos(){
    	 var login = document.getElementById("login").value;
    	 var senha = document.getElementById("senha").value;
    	 
    	 if(login == ""){
    		 alert("O campo login está vazio!");
    	 }else if(senha == ""){
    		 alert("O campo senha está vazio!");
    	 }else{
    		 return true;
    	 }
    	 
    	 return false;
     }
  
  </script>
</body>
</html>
