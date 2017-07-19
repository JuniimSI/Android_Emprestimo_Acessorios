<?php
	$conexao = mysql_connect('localhost','root','');
	mysql_select_db('android', $conexao);
	$sql = "select * from integrante";
	$resultado = mysql_query($sql) or die ("Erro:". mysql_error());

?>