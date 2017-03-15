<html>
  <head>
    <link rel="stylesheet" type="text/css" href="style.css">
    <title>AJOUT OEUVRE BDD</title>
    </head>
  <body>
    <DIV class="paragraphe">

    <?php
       
        /* query return stid */
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

        /* Récupération des données connues */ 
	$query_theme = "SELECT * FROM THEME";
        $query_genre = "SELECT nom FROM GENRE WHERE nom_genre IS NULL ORDER BY nom ASC";
	$query_continent = "SELECT NOM FROM LIEU WHERE NOM_CONTINENT IS NULL ORDER BY NOM ASC";
        $query_auteur = "SELECT nomartiste FROM artiste";
        $query_langue = "SELECT DISTINCT langue FROM oeuvre ORDER BY langue ASC";
        $query_manif = "SELECT nom_manif, date_manif FROM manifestation";
        $query_oe_adapt = "SELECT titre, datecreation FROM oeuvre";

	$stid_theme = @qrs($con, $query_theme);
        $stid_genre = @qrs($con, $query_genre);
        $stid_continent = @qrs($con, $query_continent);
        $stid_auteur = @qrs($con, $query_auteur);
        $stid_langue = @qrs($con, $query_langue);
        $stid_manif = @qrs($con, $query_manif);
        $stid_oe_adapt = @qrs($con, $query_oe_adapt);

	/* Formulaire d'ajout d'oeuvre */
	print '<b>Bienvenue sur le formulaire d\'ajout d\'oeuvre</b><br><br>';
	print '<form method="post" action="ajout_LMF.php">';

	print 'Quel type d\'oeuvre voulez-vous ajouter ?<br>';
	print '<input type="radio" name="type" value="musique" checked> Musique ';
	print '<input type="radio" name="type" value="film"> Film ';
	print '<input type="radio" name="type" value="livre"> Livre ';
	print '<br><br>';

        print 'Quel est son titre ? ';
        print '<input type="text" name="nom_oe">';
        print '<br>';

        /**************** DATE ***********************/
        print 'Quelle est sa date de création ? ';
        print '<input type="date" name="datecrea">';
        print '<br>';

        print 'Quelle est sa langue ? ';
        print '<input type="text" name="langue" value="Francais">';
	print '<br>';

        print 'Quelle est son époque ? ';
	print '<select name="epoque" id="epoque">';
	      print '<option value="Moyen age">Moyen age</option>';
              print '<option value="Renaissance">Renaissance</option>';
              print '<option selected value="Epoque contemporaine">Epoque contemporaine</option>';
	print '</select>';
        print '<br><br>';

        print 'Qui est son auteur ? ';
        print '<select multiple name="auteur" id="auteur">';
        while ($tuple = @oci_fetch_array($stid_auteur, OCI_RETURN_NULLS)){
	      print '<option value="'.$tuple[0].'">'.$tuple[0].'</option>';
	}
        print '</select><br><br>';

        print 'Quelle est son origine ? ';
	print '<select name="origine" id="origine">';
	while ($tuple = @oci_fetch_array($stid_continent, OCI_RETURN_NULLS)){
	      	print '<optgroup label="'.$tuple[0].'">';	  
		$query_pays = "SELECT NOM FROM LIEU WHERE NOM_CONTINENT = '$tuple[0]' ORDER BY NOM ASC";
	      	$stid_pays = @qrs($con, $query_pays);
		while ($tuple2 = @oci_fetch_array($stid_pays, OCI_RETURN_NULLS)){
	 	      print '<option value="'.$tuple2[0].'">'.$tuple2[0].'</option>';
		}
                print '</optgroup>';
	}
	print '</select>';
        print '<br><br>';

        print 'A-t-elle un/des thème(s) ? ';
        print '<input type="radio" name="b_theme" value="False" checked> Non  ';
	print '<input type="radio" name="b_theme" value="True"> Oui';
        print '<br>';

	print 'Si Oui, lesquels ? ';
	print '<select multiple name="theme[]" id="theme">';
	while ($tuple = @oci_fetch_array($stid_theme, OCI_RETURN_NULLS)){
	      print '<option value="'.$tuple[0].'">'.$tuple[0].'</option>';
	}
	print '</select><br><br>';
	
        print 'A-t-elle un/des genre(s) ? ';
        print '<input type="radio" name="b_genre" value="False" checked> Non  ';
	print '<input type="radio" name="b_genre" value="True"> Oui';
        print '<br>';

        print 'Si Oui, lesquels ? ';
        print '<select multiple name="genre[]" id="genre">';
        while ($tuple = @oci_fetch_array($stid_genre, OCI_RETURN_NULLS)){
                print '<option value="'.$tuple[0].'">'.$tuple[0].'</option>';	  
		$query_sgenre = "SELECT nom FROM genre WHERE nom_genre = '$tuple[0]' ORDER BY nom ASC";
	      	$stid_sgenre = @qrs($con, $query_sgenre);
		while ($tuple2 = @oci_fetch_array($stid_sgenre, OCI_RETURN_NULLS)){
	 	      print '<option value="'.$tuple2[0].'">--- '.$tuple2[0].'</option>';
		}
        }
        print '</select><br><br>';

        print 'A-t-il reçu un/des prix ? ';
        print '<input type="radio" name="recu_prix" value="False" checked> Non  ';
	print '<input type="radio" name="recu_prix" value="True"> Oui';
        print '<br>';
        print 'Si oui, choisissez un prix : ';
        print '<select multiple name="manif[]" id="manif">';
        while ($tuple = @oci_fetch_array($stid_manif, OCI_RETURN_NULLS)){
              $date = DateTime::createFromFormat('d-M-Y', $tuple[1]);
              $formdate = $date->format('d/m/y');
	      print '<option value="'.$tuple[0].'_'.$tuple[1].'">'.$tuple[0].' - '.$formdate.'</option>';
	}
        print '</select><br><br>';

        print 'Est-ce une adaptation d\'une ou plusieurs oeuvres déjà existantes ? ';
        print '<input type="radio" name="bool_adapt" value="False" checked> Non  ';
	print '<input type="radio" name="bool_adapt" value="True"> Oui';
        print '<br>';
        print 'Si oui, choisissez une/des oeuvre(s) : ';
        print '<select multiple name="oe_adapt[]" id="oe_adapt">';
        while ($tuple = @oci_fetch_array($stid_oe_adapt, OCI_RETURN_NULLS)){
              $date = DateTime::createFromFormat('d-M-Y', $tuple[1]);
              $formdate = $date->format('d/m/y');
	      print '<option value="'.$tuple[0].'_'.$tuple[1].'">'.$tuple[0].' - '.$formdate.'</option>';
	}
        print '</select><br><br>';
        
        print '<input type="submit" value="Envoyer">';

	print '</form><br>';

	@oci_close($con);
    ?>
    </DIV>
 </body>
</html>
	
