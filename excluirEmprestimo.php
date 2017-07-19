<?php
	$integrante = $_POST['integrante'];
	$peca = $_POST['peca'];
	
	$conexao = mysql_connect('localhost','root','');
	mysql_select_db('android', $conexao);
	$sql = "delete from emprestimo where integrante='$integrante' and peca='$peca'";
	$resultado = mysql_query($sql) or die ("Erro:". mysql_error());
	if(!$resultado)
		echo "0";
	else
		echo "1";
?>
