<?php
	$integrante = $_POST['integrante'];
	$peca = $_POST['peca'];
	$data = $_POST['data'];
	
	$conexao = mysql_connect('localhost','root','');
	mysql_select_db('android', $conexao);
	$sql = "insert into emprestimo(integrante, peca, data) values ('$integrante', '$peca', '$data')";
	$resultado = mysql_query($sql) or die ("Erro:".mysql_error());
	if(!$resultado)
		echo 0;
	else
		echo "1";
?>
