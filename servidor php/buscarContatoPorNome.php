<?php

	$nome = $_POST['nome'];
	$conexao = mysql_connect('localhost','root','');
	mysql_select_db('android', $conexao);
	$sql = "select * from integrante where nome='$nome'";
	$resultado = mysql_query($sql) or die ("Erro:".mysql_error());
	while($linha = mysql_fetch_object($resultado)){
		echo $linha->nome."#";
		echo $linha->telefone."#";
		echo $linha->endereco."#";
		echo $linha->imagem."#";
	}
	
	echo "^";
	



?>