<?php

	$conexao = mysql_connect('localhost','root','');
	mysql_select_db('android', $conexao);
	$sql = "select * from integrante";
	$resultado = mysql_query($sql) or die ("Erro:".mysql_error());
	while($linha = mysql_fetch_object($resultado))
		echo $linha->nome."#";
	echo "^";


/*;	if(mysql_num_rows($resultado) > 0)
		echo "1";
	else
		echo 0;
*/
?>