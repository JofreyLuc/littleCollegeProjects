<html>
  <head>
    <link rel="stylesheet" type="text/css" href="style.css">
    <title>INTERROGATION BDD : Requètes thèmes</title>
    </head>
  <body>
    <DIV class="paragraphe">

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

	/* Recupération de l'origine demandé */
	$origine = $_POST['origine'];

	/* Requete B en fonction du pays ou du continent demandé */
	$query_B = "SELECT TITRE FROM OEUVRE, LIEU WHERE (LIEU.NOM = '".$origine."' OR LIEU.NOM_CONTINENT = '".$origine."') AND LIEU.NOM = OEUVRE.NOM";
	
	/* Comptage des résultats */
	$query_count = "SELECT COUNT(*) FROM OEUVRE, LIEU WHERE (LIEU.NOM = '".$origine."' OR LIEU.NOM_CONTINENT = '".$origine."') AND LIEU.NOM = OEUVRE.NOM";

	$stid_B = @qrs($con, $query_B);
	$stid_count = @qrs($con, $query_count);

	/* Traitement */
	$tuple_count = @oci_fetch_array($stid_count, OCI_RETURN_NULLS);	

	print 'Votre requete pour l\'origine '.$origine.' a retourné '.$tuple_count[0].' résultat(s).'; 
        print '<br>';

	if ($tuple_count[0] != 0){ /* Affichage d'un tableau contenant les resultats si il y en a */

           print '<br>';
        
	   print '<table border="1">';

	   while ($tuple = @oci_fetch_array($stid_B, OCI_RETURN_NULLS)){

	      	 print '<tr><td>'.$tuple[0].'</td></tr>';
	      
	   }

	   print '</table>';
	   
	} else { /* Message de désolation si aucun résultat n'a été trouvé, unrustelez vos jimmies */

	   print '<br>Nous sommes désolés. Mais la vie continue. Ce n\'est pas grave...<br><br>';
	   
	   /* Lol */
	   print '<iframe width="420" height="315" src="https://www.youtube.com/embed/ygr5AHufBN4" frameborder="0" allowfullscreen></iframe>';

	}

	@oci_close($con);
     ?>
     </DIV>
  </body>
</html>
