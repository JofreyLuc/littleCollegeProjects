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

        print '<strong>Insertion des informations d\'oeuvre</strong><br>';

	$type = $_POST['type'];
	$nom = $_POST['nom_oe'];
	$dc = $_POST['datecrea'];
	$langue = $_POST['langue'];
	$epo = $_POST['epoque'];
        $origin = $_POST['origine'];
	$b_th = $_POST['b_theme'];
	//$th_array = $_POST['theme'];
	$b_g = $_POST['b_genre'];
	//$genre_array = $_POST['genre'];
	$b_prix = $_POST['recu_prix'];
	//$manif_array = $_POST['manif'];
	$b_adapt = $_POST['bool_adapt'];
	//$oe_adapt = $_POST['oe_adapt'];

        /* INSERTION DES INFO COMMUNES */
        $insert_oe = "INSERT INTO oeuvre (titre,datecreation,nom,langue,epoque) VALUES ('$nom', '$dc', '$origin', '$langue', '$epo')";
        $stid_insert = @qrs($con, $insert_oe);

        foreach ($_POST['auteur'] as $auteur){
                $insert_aut = "INSERT INTO auteur (nomartiste,titre,datecreation) VALUES ('$auteur', '$nom', '$dc')";
                $stid_insert_aut = @qrs($con, $insert_aut);
        }

        $r_com = @oci_commit($con);

        print '<form method="post" action="insert_LMF.php">';

        print 'Les informations ont été insérées, veuillez remplir les informations suivantes pour l\'oeuvre <DIV class="invisible"><input type="radio" name="nom" value="'.$nom.'" checked><input type="radio" name="dc" value="'.$dc.'" checked></DIV>'.$nom.' : <br><br>';

        if (!($b_th == 'False')){
       
           print 'Ajout d\'informations sur les thèmes<br>';

           $i_th = 0;

           foreach ($_POST['theme'] as $theme){

                   print 'Précisez le poids du thème ';
                   print '<DIV class="invisible"><input type="radio" name="th['.$i_th.']" value="'.$theme.'" checked></DIV>'.$theme.' : ';
                   print '1 <input type="radio" name="poids_th['.$i_th.']" value="1">';
                   print '<input type="radio" name="poids_th['.$i_th.']" value="2">';
                   print '<input type="radio" name="poids_th['.$i_th.']" value="3">';
                   print '<input type="radio" name="poids_th['.$i_th.']" value="4" checked> 4<br>';   
                   $i_th++;
           }

           print '<br>';
        }
        
        if (!($b_g == 'False')){

           print 'Ajout d\'informations sur les genres<br>';

           $i_g = 0;

           foreach ($_POST['genre'] as $genre){
       
                   print 'Précisez le poids du genre ';
                   print '<DIV class="invisible"><input type="radio" name="g['.$i_g.']" value="'.$genre.'" checked></DIV>'.$genre.' : ';
                   print '1 <input type="radio" name="poids_g['.$i_g.']" value="1">';
                   print '<input type="radio" name="poids_g['.$i_g.']" value="2">';
                   print '<input type="radio" name="poids_g['.$i_g.']" value="3">';
                   print '<input type="radio" name="poids_g['.$i_g.']" value="4" checked> 4<br>';   
                   $i_g++;
           }

           print '<br>';
        }

        if (!($b_prix == 'False')){

           print 'Ajout d\'informations sur les prix<br>';

           $i_prix = 0;

           foreach ($_POST['manif'] as $man){
                   $mandate = explode("_", $man);
                   print 'Precisez la nature du prix obtenue à la manifestation : ';
                   print '<DIV class="invisible"><input type="radio" name="prix['.$i_prix.']" value="'.$man.'" checked></DIV>'.$mandate[0].' du '.$mandate[1].' : ';
                   print '<input type="text" name="nat_prix['.$i_prix.']"><br>';   
                   $i_prix++;
           }
           
           print '<br>';
        }

        if (!($b_adapt == 'False')){

           print 'Ajout d\'informations sur les adaptations<br>';

           $i_adapt = 0;

           foreach ($_POST['oe_adapt'] as $adapt){
                   $adate = explode("_", $adapt);
                   print 'Precisez la nature de l\'adaptation du film : ';
                   print '<DIV class="invisible"><input type="radio" name="adapt['.$i_adapt.']" value="'.$adapt.'" checked></DIV>'.$adate[0].' du '.$adate[1].' : ';
                   print '<input type="text" name="nat_adapt['.$i_adapt.']"><br>';   
                   $i_adapt++;
           }
           
           print '<br>';
        }

        if ($type == musique){

                $query_groupe = "SELECT nom FROM groupe";
                $query_inter = "SELECT nomartiste FROM artiste";
                

                $stid_groupe = @qrs($con, $query_groupe);
                $stid_inter = @qrs($con, $query_inter);

                print '<strong>Ajout d\'informations propres aux oeuvres musicales<br><br></strong>';
	
		print 'Quelle est la nature de l\'enregistrement ? ';
                print '<input type="radio" name="enr" value="Studio"> Studio ';
                print '<input type="radio" name="enr" value="Public"> Public ';
                print '<input type="radio" name="enr" value="Autre" checked> Autre ';
                print '<br><br>';

                print 'Quel est son format ? ';
                print '<select name="format" id="format">';
	        print '<option value="RIF">RIF</option>';
                print '<option selected="selected" value="WAV">WAV</option>';
	        print '<option value="BWF">BWF</option>';
                print '<option value="Ogg">OGG</option>';
	        print '<option value="AIFF">AIFF</option>';
                print '<option value="CAF">CAF</option>';
	        print '</select>';
                print '<br><br>';

                print 'Est-ce un best of ? ';
                print '<input type="radio" name="bestof" value="V"> Oui ';
                print '<input type="radio" name="bestof" value="F" checked> Non ';
                print '<br><br>';

                print 'Est-elle interprétée par un groupe/orchestre ? ';
                print '<input type="radio" name="b_gr" value="False" checked> Non  ';
	        print '<input type="radio" name="b_gr" value="True"> Oui';
                print '<br><br>';

                print 'Si Oui, lequel ? ';
                print '<select name="groupe" id="groupe">';
	        while ($tuple = @oci_fetch_array($stid_groupe, OCI_RETURN_NULLS)){
	              print '<option value="'.$tuple[0].'">'.$tuple[0].'</option>';
	        }
	        print '</select><br>';

                print 'Si Non, par quels artistes l\'oeuvre a-t-elle été interprétée ? ';
                print '<select multiple name="inter[]" id="inter">';
                while ($tuple = @oci_fetch_array($stid_inter, OCI_RETURN_NULLS)){
                      print '<option value="'.$tuple[0].'">'.$tuple[0].'</option>';	
		}
                print '</select><br><br>';

                print '<input type="submit" name="bouton" value="Ajouter Musique">';
	
	} elseif ($type == film){

                $query_mus = "SELECT titre, datecreation FROM oeuvre_musicale";
                $query_artiste = "SELECT nomartiste FROM artiste";

                $stid_bo = @qrs($con, $query_mus);
                $stid_artiste = @qrs($con, $query_artiste);

                print '<strong>Ajout d\'informations propre aux films<br><br></strong>';

                print 'Est-ce un long métrage ? ';
                print '<select name="lm" id="lm">';
	        print '<option value="F">Non</option>';
                print '<option selected="selected" value="V">Oui</option>';
                print '</select>';
                print '<br>';

                print 'Est-ce un film en couleur ? ';
                print '<select name="coul" id="coul">';
	        print '<option value="F">Non</option>';
                print '<option selected="selected" value="V">Oui</option>';
                print '</select>';
                print '<br>';
	  	
                print 'Est-ce un film numérique ? ';
                print '<select name="num" id="num">';
	        print '<option value="F">Non</option>';
                print '<option selected="selected" value="V">Oui</option>';
                print '</select>';
                print '<br>';

                print 'Quelle est la nature de l\'audio ? ';
                print '<select name="audio" id="audio">';
	        print '<option value="Mono">Mono</option>';
                print '<option selected="selected" value="Stereo">Stéréo</option>';
                print '<option value="Autre">Autre</option>';
                print '</select>';
                print '<br><br>';

                print 'A-t-elle un bande originale ? ';
                print '<input type="radio" name="b_bo" value="False" checked> Non  ';
	        print '<input type="radio" name="b_bo" value="True"> Oui';
                print '<br>';
        
                print 'Si Oui, précisez la B.O. : ';
                print '<select name="bo" id="bo">';
                while ($tuple = @oci_fetch_array($stid_bo, OCI_RETURN_NULLS)){
                      print '<option value="'.$tuple[0].'_'.$tuple[1].'">'.$tuple[0].' - Date : '.$tuple[1].'</option>';	
		}
                print '</select><br><br>';

                print 'Selectionnez les artistes jouant un role dans le flim : ';
                print '<select multiple name="distri[]" id="distri">';
                while ($tuple = @oci_fetch_array($stid_artiste, OCI_RETURN_NULLS)){
	              print '<option value="'.$tuple[0].'">'.$tuple[0].'</option>';
	        }
                print '</select><br><br>';

	  	print '<input type="submit" name="bouton" value="Ajouter Film">';

	} else {

                $insert_livre = "INSERT INTO livre (titre,datecreation) VALUES ('$nom', '$dc')";
                $stid_insert_livre = @qrs($con, $insert_livre);

                print '<input type="submit" name="bouton" value="Ajouter Livre">';
       
        } 	

        print '</form><br><3';

	@oci_close($con);
    	
	?>
    </DIV>
  </body>
</html>
