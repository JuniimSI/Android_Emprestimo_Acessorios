<?php
	$nome = $_POST['nome'];
	
	$detalhes = $_POST['detalhes'];
	$conexao = mysql_connect('localhost','root','');
	mysql_select_db('android', $conexao);
	$sql = "insert into peca(nome, detalhes) values ('$nome', '$detalhes')";
	$resultado = mysql_query($sql) or die ("Erro:".mysql_error());
	if(!$resultado)
		echo 0;
	else
		echo "1";
?>