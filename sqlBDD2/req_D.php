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
	
	/* Requete des noms de continents */
	$query_continent = "SELECT NOM FROM LIEU WHERE NOM_CONTINENT IS NULL ORDER BY NOM ASC";
	$stid_continent = @qrs($con, $query_continent);

	/* Table avec en colonnes : le nombre des Livres, des Musiques, des Films, en fonction de leur origine */ 
        print '<table border="1"><tr><td></td><td><strong>Livre</strong></td><td><strong>Musique</strong></td><td><strong>Film</strong></td></tr>';

        while ($tuple = @oci_fetch_array($stid_continent, OCI_RETURN_NULLS)){
        	
	      	print '<tr><td>'.$tuple[0].'</td>';
      
       /* Count des oeuvres par continent */        
       
                $query_count_l = "SELECT COUNT(*) FROM livre JOIN oeuvre ON (((livre.titre) = (oeuvre.titre)) AND ((livre.datecreation) = (oeuvre.datecreation))) JOIN lieu ON (lieu.nom = oeuvre.nom)  WHERE lieu.nom_continent = '".$tuple[0]."'";
                $query_count_m = "SELECT COUNT(*) FROM film JOIN oeuvre ON (((film.titre) = (oeuvre.titre)) AND ((film.datecreation) = (oeuvre.datecreation))) JOIN lieu ON (lieu.nom = oeuvre.nom) WHERE lieu.nom_continent = '".$tuple[0]."'";
                $query_count_f = "SELECT COUNT(*) FROM oeuvre_musicale JOIN oeuvre ON (((oeuvre_musicale.titre) = (oeuvre.titre)) AND ((oeuvre_musicale.datecreation) = (oeuvre.datecreation))) JOIN lieu ON (lieu.nom = oeuvre.nom) WHERE lieu.nom_continent = '".$tuple[0]."'";

                $stidl = @qrs($con, $query_count_l);
                $stidm = @qrs($con, $query_count_m);
                $stidf = @qrs($con, $query_count_f);


                $tuple_l = @oci_fetch_array($stidl, OCI_RETURN_NULLS);
                $tuple_m = @oci_fetch_array($stidm, OCI_RETURN_NULLS);
                $tuple_f = @oci_fetch_array($stidf, OCI_RETURN_NULLS);

                print '<td>'.$tuple_l[0].'</td><td>'.$tuple_m[0].'</td><td>'.$tuple_f[0].'</td></tr>';
       
       		/* Requete de pays en fonction du continent */
		$query_pays = "SELECT NOM FROM LIEU WHERE NOM_CONTINENT = "."'".$tuple[0]."'"." ORDER BY NOM ASC";
	      	$stid_pays = @qrs($con, $query_pays);

		while ($tuple2 = @oci_fetch_array($stid_pays, OCI_RETURN_NULLS)){
			
	 	      print '<tr><td><i>--- '.$tuple2[0].'</i></td>';

                      $query_count_l = "SELECT COUNT(*) FROM livre JOIN oeuvre ON (((livre.titre) = (oeuvre.titre)) AND ((livre.datecreation) = (oeuvre.datecreation))) WHERE oeuvre.nom = '".$tuple2[0]."'";
                      $query_count_m = "SELECT COUNT(*) FROM film JOIN oeuvre ON (((film.titre) = (oeuvre.titre)) AND ((film.datecreation) = (oeuvre.datecreation))) WHERE oeuvre.nom = '".$tuple2[0]."'";
                      $query_count_f = "SELECT COUNT(*) FROM oeuvre_musicale JOIN oeuvre ON (((oeuvre_musicale.titre) = (oeuvre.titre)) AND ((oeuvre_musicale.datecreation) = (oeuvre.datecreation))) WHERE oeuvre.nom = '".$tuple2[0]."'";

                      $stidl = @qrs($con, $query_count_l);
                      $stidm = @qrs($con, $query_count_m);
                      $stidf = @qrs($con, $query_count_f);
                      
                      $tuple_l = @oci_fetch_array($stidl, OCI_RETURN_NULLS);
                      $tuple_m = @oci_fetch_array($stidm, OCI_RETURN_NULLS);
                      $tuple_f = @oci_fetch_array($stidf, OCI_RETURN_NULLS);

                      print '<td>'.$tuple_l[0].'</td><td>'.$tuple_m[0].'</td><td>'.$tuple_f[0].'</td></tr>';
		}

	}
	
	print '</table>';

	@oci_close($con);

     ?>
     </DIV>
  </body>
</html>
