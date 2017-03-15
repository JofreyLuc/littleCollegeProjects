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

         
        print '<strong>Insertion des informations d\'oeuvre...</strong><br><br>';       
       
	$titre = $_POST['nom'];
	$datec = $_POST['dc'];

	$type = $_POST['bouton'];

	$pt = $_POST['poids_th'];
	$pg = $_POST['poids_g'];
	$np = $_POST['nat_prix'];
	$na = $_POST['nat_adapt'];

	$i_th = 0;
	$i_g = 0;
	$i_prix = 0;
	$i_ada = 0;
	
        /* Transmission titre et datecreation */ 
        print '<DIV class="invisible">';
        print '<input type="radio" name="ti" value="'.$titre.'" checked>';
        print '<input type="radio" name="da" value="'.$datec.'" checked>';
        print '</DIV>';
        
       
        /* Insertion des themes */
	foreach ($_POST['th'] as $theme){
	
	        $insert_th = "INSERT INTO apourtheme (titre,datecreation,libelle,poids) VALUES (:ti, :da, :th, :pt)";
		
                $stid_th = @oci_parse($con, $insert_th);
                if (!$stid_th){
	            $e = @oci_error($con);
	            print($e['message']);
	            exit;
	        }

                @oci_bind_by_name($stid_th, ":ti", $titre);
                @oci_bind_by_name($stid_th, ":da", $datec);
                @oci_bind_by_name($stid_th, ":th", $theme);
                @oci_bind_by_name($stid_th, ":pt", $pt[$i_th]);                

		$i_th++;

                $r_res = @oci_execute($stid_th, OCI_DEFAULT);      
                if (!$r_res){
	           $e = @oci_error($stid_th);
                   echo($e['message']);
	           exit;
	        }
	}


        /* Insertion des genres */
	foreach ($_POST['g'] as $genre){
	
	        $insert_g = "INSERT INTO apourgenre (titre,datecreation,nom_genre,poids) VALUES (:ti, :da, :ge, :pg)";
		
	        $stid_g = @oci_parse($con, $insert_g);
                if (!$stid_g){
	            $e = @oci_error($con);
	            print($e['message']);
	            exit;
	        }

                @oci_bind_by_name($stid_g, ":ti", $titre);
                @oci_bind_by_name($stid_g, ":da", $datec);
                @oci_bind_by_name($stid_g, ":ge", $genre);
                @oci_bind_by_name($stid_g, ":pg", $pg[$i_g]);
       
	
		$i_g++;

                $r_res = @oci_execute($stid_g, OCI_DEFAULT);      
                if (!$r_res){
	           $e = @oci_error($stid_g);
                   echo($e['message']);
	           exit;
	        }
	}


        /* Insertion des prix */
	foreach ($_POST['prix'] as $priks){

		$manifdate = explode("_", $priks);
	
	        $insert_p = "INSERT INTO distinction (nom_manif,date_manif,titre,datecreation,nature) VALUES (:no, :dam, :ti, :da, :np)";
		
                $stid_p = @oci_parse($con, $insert_p);
                if (!$stid_p){
	            $e = @oci_error($con);
	            print($e['message']);
	            exit;
	        }

                @oci_bind_by_name($stid_p, ":no", $manifdate[0]);
                @oci_bind_by_name($stid_p, ":dam", $manifdate[1]);
                @oci_bind_by_name($stid_p, ":ti", $titre);
                @oci_bind_by_name($stid_p, ":da", $datec);
                @oci_bind_by_name($stid_p, ":np", $np[$i_prix]);
               
       	
		$i_prix++;

                $r_res = @oci_execute($stid_p, OCI_DEFAULT);      
                if (!$r_res){
	           $e = @oci_error($stid_p);
                   echo($e['message']);
	           exit;
	        }

	}


        /* Insertion des adaptations */
	foreach ($_POST['adapt'] as $ada){

		$adate = explode("_", $ada);
	
	        $insert_a = "INSERT INTO adaptation (titre_oe_adaptee,datecreation_oe_adaptee,titre_oe_originale,datecreation_oe_originale,nature) VALUES (:ta, :da, :ti, :do, :na)";

                $stid_a = @oci_parse($con, $insert_a);
                if (!$stid_a){
	            $e = @oci_error($con);
	            print($e['message']);
	            exit;
	        }

                @oci_bind_by_name($stid_a, ":ta", $adate[0]);
                @oci_bind_by_name($stid_a, ":da", $adate[1]);
                @oci_bind_by_name($stid_a, ":ti", $titre);
                @oci_bind_by_name($stid_a, ":do", $datec);
                @oci_bind_by_name($stid_a, ":na", $na[$i_ada]);
	
		$i_ada++;

                $r_res = @oci_execute($stid_a, OCI_DEFAULT);      
                if (!$r_res){
	           $e = @oci_error($stid_a);
                   echo($e['message']);
	           exit;
	        }	
       }

        /* Cas des musiques */
	if ($type == "Ajouter Musique"){

	   $enreg = $_POST['enr'];
           $form = $_POST['format'];
           $besto = $_POST['bestof'];
           $bgr = $_POST['b_gr'];

           /* Cas des groupes */
           if (!($bgr == 'False')){

              $gr = $_POST['groupe'];

              $insert_mus = "INSERT INTO oeuvre_musicale (titre,datecreation,nom_groupe,enregistrement,audio,bestof) VALUES (:ti, :da, :gr, :en, :fo, :bo)";

              $stid_mus = @oci_parse($con, $insert_mus);
              if (!$stid_mus){
	          $e = @oci_error($con);
	          print($e['message']);
	          exit;
	      }

              @oci_bind_by_name($stid_mus, ":ti", $titre);
              @oci_bind_by_name($stid_mus, ":da", $datec);
              @oci_bind_by_name($stid_mus, ":gr", $gr);
              @oci_bind_by_name($stid_mus, ":en", $enreg);
              @oci_bind_by_name($stid_mus, ":fo", $form);
              @oci_bind_by_name($stid_mus, ":bo", $besto);

              $r_res = @oci_execute($stid_mus, OCI_DEFAULT);      
              if (!$r_res){
	         $e = @oci_error($stid_mus);
                 echo($e['message']);
	         exit;
	      }

              $rm = @oci_commit($con);

              print '<strong>L\'ajout de votre musique a été effectuée avec succès</strong><br><br>';

              print '<a href="acceuil.html" target="_self"><input type="button" value="Retour à l\'acceuil"></a><br><br>';

           } else {
           /* Cas des orchestres */
              
              $insert_mus = "INSERT INTO oeuvre_musicale (titre,datecreation,nom_groupe,enregistrement,audio,bestof) VALUES (:ti, :da, null, :en, :fo, :bo)";

              $stid_mus = @oci_parse($con, $insert_mus);
              if (!$stid_mus){
	          $e = @oci_error($con);
	          print($e['message']);
	          exit;
	      }

              @oci_bind_by_name($stid_mus, ":ti", $titre);
              @oci_bind_by_name($stid_mus, ":da", $datec);
              @oci_bind_by_name($stid_mus, ":en", $enreg);
              @oci_bind_by_name($stid_mus, ":fo", $form);
              @oci_bind_by_name($stid_mus, ":bo", $besto);

              $r_res = @oci_execute($stid_mus, OCI_DEFAULT);      
              if (!$r_res){
	         $e = @oci_error($stid_mus);
                 echo($e['message']);
	         exit;
	      }

              $query_inter = "SELECT nom FROM instrument";

              $rm = @oci_commit($con);

              $i_inte = 0;

              print '<form method="post" action="insert_M.php">';

              print '<DIV class="invisible">';
              print '<input type="radio" name="ti" value="'.$titre.'" checked>';
              print '<input type="radio" name="da" value="'.$datec.'" checked>';
              print '</DIV>';

              /* Ajout des instruments */
              foreach ($_POST['inter'] as $inter){

                   $stid_inter = @qrs($con, $query_inter);
       
                   print 'Précisez l\'instrument utilisé par l\'interprete : ';
                   print '<DIV class="invisible"><input type="radio" name="inte['.$i_inte.']" value="'.$inter.'" checked></DIV>'.$inter.' : ';
                   print '<select name="instr['.$i_inte.']" id="instr">';
                   while ($tuple = @oci_fetch_array($stid_inter, OCI_RETURN_NULLS)){
                      print '<option value="'.$tuple[0].'">'.$tuple[0].'</option>';	
                   }
                   print '</select><br>';

                   $i_inte++;
              }

	      print '<br><input type="submit" value="Envoyer">';

              print '</form><br><br>';
       
           }

          /* Cas des films */
	} elseif ($type == "Ajouter Film"){

           $longm = $_POST['lm'];
           $col = $_POST['coul'];
           $fnum = $_POST['num'];
	   $naudio = $_POST['audio'];
           $bbo = $_POST['b_bo'];

           /* Cas avec bande originale */
	   if (!($bbo == 'False')){
				       
              $bodate =  explode("_", $_POST['bo']);

              $insert_film = "INSERT INTO film (titre,datecreation,titre_bo,datecreation_bo,long_metrage,couleur,numerique,audio) VALUES (:ti, :da, :bot, :bod, :lm, :co, :nu, :na)";

              $stid_film = @oci_parse($con, $insert_film);
              if (!$stid_film){
	          $e = @oci_error($con);
	          print($e['message']);
	          exit;
	      }

              @oci_bind_by_name($stid_film, ":ti", $titre);
              @oci_bind_by_name($stid_film, ":da", $datec);
              @oci_bind_by_name($stid_film, ":bot", $bodate[0]);
              @oci_bind_by_name($stid_film, ":bod", $bodate[1]);
              @oci_bind_by_name($stid_film, ":lm", $longm);
              @oci_bind_by_name($stid_film, ":co", $col);
              @oci_bind_by_name($stid_film, ":nu", $fnum);
              @oci_bind_by_name($stid_film, ":na", $naudio);       

              $r_res = @oci_execute($stid_film, OCI_DEFAULT);      
              if (!$r_res){
	         $e = @oci_error($stid_film);
                 echo($e['message']);
	         exit;
	      }


           } else {
            /* Cas sans bande originale */

              $insert_film = "INSERT INTO film (titre,datecreation,titre_bo,datecreation_bo,long_metrage,couleur,numerique,audio) VALUES (:ti, :da, null, null, :lm, :co, :nu, :na)";

              $stid_film = @oci_parse($con, $insert_film);
              if (!$stid_film){
	          $e = @oci_error($con);
	          print($e['message']);
	          exit;
	      }

              @oci_bind_by_name($stid_film, ":ti", $titre);
              @oci_bind_by_name($stid_film, ":da", $datec);
              @oci_bind_by_name($stid_film, ":lm", $longm);
              @oci_bind_by_name($stid_film, ":co", $col);
              @oci_bind_by_name($stid_film, ":nu", $fnum);
              @oci_bind_by_name($stid_film, ":na", $naudio);       

              $r_res = @oci_execute($stid_film, OCI_DEFAULT);      
              if (!$r_res){
	         $e = @oci_error($stid_film);
                 echo($e['message']);
	         exit;
	      }

           }


           $rf = @oci_commit($con);

           $i_dist = 0;

           print '<form method="post" action="insert_F.php">';

           print '<DIV class="invisible">';
           print '<input type="radio" name="ti" value="'.$titre.'" checked>';
           print '<input type="radio" name="da" value="'.$datec.'" checked>';
           print '</DIV>';

           /* Ajout de la distribution */
           foreach ($_POST['distri'] as $distri){
				       
	           print 'Précisez pour l\'acteur : ';
                   print '<DIV class="invisible"><input type="radio" name="dist['.$i_dist.']" value="'.$distri.'" checked></DIV>'.$distri;   
                   print ' : si c\'est un personnage principal : ';
                   print '<input type="radio" name="princip['.$i_dist.']" value="V"> Oui ';
                   print '<input type="radio" name="princip['.$i_dist.']" value="F" checked> Non <br>';
	           print ' et son role : ';
                   print '<select name="role['.$i_dist.']" id="role">';
	           print '<option value="amoureux eperdu">Amoureux Eperdu</option>';
		   print '<option value="detective decadent">Detective Decadent</option>';
		   print '<option value="idiot du village">Idiot du Village</option>';
                   print '<option value="comique de service">Comique de Service</option>';
                   print '</select>';
                   print '<br><br>';

                   $i_dist++;
          }

          print '<br><input type="submit" value="Envoyer">';

          print '</form><br>';

	} else {

	   $rl = @oci_commit($con);

           print '<strong>L\'ajout de votre livre a été effectué avec succès</strong><br><br>';

           print '<a href="acceuil.html" target="_self"><input type="button" value="Retour à l\'acceuil"></a><br><br>';
	
	}	

	@oci_close($con);
    	
	?>
    </DIV>
  </body>
</html>
