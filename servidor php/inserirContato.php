<?php
	$nome = $_POST['nome'];
	$telefone = $_POST['telefone'];
	$endereco = $_POST['endereco'];
	$imagem = $_POST['imagem'];
	$conexao = mysql_connect('localhost','root','');
	mysql_select_db('android', $conexao);
	$sql = "insert into integrante(nome, telefone, endereco, imagem) values ('$nome', '$telefone', '$endereco', '$imagem')";
	$resultado = mysql_query($sql) or die ("Erro:".mysql_error());
	if(!$resultado)
		echo 0;
	else
		echo "1";
?>