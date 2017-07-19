<?php
	$nome = $_POST['nome'];
	
	$conexao = mysql_connect('localhost','root','');
	mysql_select_db('android', $conexao);
	$sql = "delete from peca where nome = '$nome'";
	$resultado = mysql_query($sql) or die ("Erro:". mysql_error());
	if(!$resultado)
		echo "0";
	else
		echo "1";
?>
