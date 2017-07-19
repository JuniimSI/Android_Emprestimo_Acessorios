<?php

	$conexao = mysql_connect('localhost','root','');
	mysql_select_db('android', $conexao);
	$sql = "select * from peca";
	$res = mysql_query( $sql) or die ("
		Erro:".mysql_error());

	while($linha = mysql_fetch_object($res))
		echo $linha->nome."#";
	echo "^";

/*	while ($linha = mysql_fetch_assoc($resultado)) {
		$output[] = $linha;
	}
	print json_encode($output);
*/	

/*;	if(mysql_num_rows($resultado) > 0)
		echo "1";
	else
		echo 0;
*/


?>
