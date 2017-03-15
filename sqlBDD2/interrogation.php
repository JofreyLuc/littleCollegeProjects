<html>
  <head>
    <link rel="stylesheet" type="text/css" href="style.css">
    <title>INTERROGATION DE LA BDD</title>
    </head>
  <body>
    <DIV class="paragraphe">

    <b>Que voulez vous, wesh ?</b><br><br>

    <?php
    
    	 /* query return stid function */
	function qrs($connexion, $query){
		$stid_res = @oci_parse($connexion, $query);
		
		if (!$stid_res){
			$e = @oci_error($connexion);
			print($e['message']);
			exit;
		}
		
		$r_res = @oci_execute($stid_res, OCI_DEFAULT);
		
		if (!$r_res){
			$e = @oci_error($stid_res);
			echo($e['message']);
			exit;
		}
		
		return $stid_res;
	}

	/* chaine de connexion et identifiants */
	$db = "(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=oracle.depinfo.uhp-nancy.fr)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=depinfo)))";
	$user = "etud040";
	$pwd = "oracle";

	/* connexion */
	$con = @oci_connect($user, $pwd, $db);	

	if (! $con){
	   $e = @oci_error();
	   print("Erreur con " .$e ['message']);
	   exit;
	}

	/* Requete A */
	$query_theme = "SELECT * FROM THEME";
	
	$stid_theme = @qrs($con, $query_theme);		   

	print '<form method="post" action="req_A.php"> <label for="theme">Liste des oeuvres qui ont pour theme : </label>';
	print '<select name="theme" id="theme">';

	while ($tuple = @oci_fetch_array($stid_theme, OCI_RETURN_NULLS)){

	      print '<option value="'.$tuple[0].'">'.$tuple[0].'</option>';
	      
	}

	print '</select>';	
	print '<input type="submit" value="Envoyer">';
	print '</form>';

	/* Requete B */
	$query_continent = "SELECT NOM FROM LIEU WHERE NOM_CONTINENT IS NULL ORDER BY NOM ASC";

	$stid_continent = @qrs($con, $query_continent);

	print '<form method="post" action="req_B.php"><label for="origine">Oeuvres par origine : </label>';
	print '<select name="origine" id="origine">';

	while ($tuple = @oci_fetch_array($stid_continent, OCI_RETURN_NULLS)){
	      	print '<option value="'.$tuple[0].'">'.$tuple[0].'</option>';
	  
		$query_pays = "SELECT NOM FROM LIEU WHERE NOM_CONTINENT = "."'".$tuple[0]."'"." ORDER BY NOM ASC";

	      	$stid_pays = @qrs($con, $query_pays);

		while ($tuple2 = @oci_fetch_array($stid_pays, OCI_RETURN_NULLS)){
	 	      print '<option value="'.$tuple2[0].'">--- '.$tuple2[0].'</option>';
		}

	}

	print '</select>';	
	print '<input type="submit" value="Envoyer">';
	print '</form>';

	/* Requete C */
	print '<form method="post" action="req_C.php"><label for="musique">Nombre d\'oeuvres musicales par genre : </label>';	
	print '<input type="submit" value="Envoyer"></form>';
	
	/* Requete D */
	print '<form method="post" action="req_D.php"><label for="oeuvre">Nombre d’œuvres par type (film, musique, livre) et par origine : </label>';	
	print '<input type="submit" value="Envoyer"></form>';
        print '<a href="acceuil.html" target="_self"><input type="button" value="Retour"></a><br>';

	/* Lol */
        print '<br>N\'oubliez pas le Dieu Panda qui veille sur nous...<br><br><iframe width="420" height="315" src="https://www.youtube.com/embed/N5oYdKJEpNo" frameborder="0" allowfullscreen></iframe><br><br><3';

	@oci_close($con);

     ?>
     </DIV>
  </body>
</html>
	
