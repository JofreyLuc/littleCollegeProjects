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
	
	/* Requete des genres */
	$query_genre = "SELECT NOM FROM GENRE WHERE NOM_GENRE IS NULL";
	$stid_genre = @qrs($con, $query_genre);

	/* Creation d'une table affichant en colonnes : Les genres, les sous-genres d'un genre, le nombre d'oeuvre de ce sous-genre, et le nombre d'oeuvre du genre et de ses sous-genre */
	print '<table border="1">';
	print '<tr><td><strong>Genre</strong></td><td><strong>Sous-Genre</strong></td><td><strong>NB d\'Oeuvre pour Sous-Genre</strong></td><td><strong>NB Total d\'Oeuvre pour Genre</strong></tr>';

       while ($tuple = @oci_fetch_array($stid_genre, OCI_RETURN_NULLS)){

        /* Comptage du total d'oeuvres pour ce genre */
        $query_total = "SELECT COUNT(*) FROM apourgenre a WHERE a.nom_genre = '$tuple[0]' OR a.nom_genre IN (SELECT nom FROM genre g WHERE g.nom_genre = '$tuple[0]')";
        $stid_total = @qrs($con, $query_total);
        $total_count = @oci_fetch_array($stid_total, OCI_RETURN_NULLS);
        $total = $total_count[0];
            
        if (!$total == 0){

         /* Requete des sous genre d'un genre */
         $query_sousgenre = "SELECT nom FROM genre WHERE nom_genre = '".$tuple[0]."'";
         /* Comptage de ces sous genre */
         $query_sg_count = "SELECT COUNT(*) FROM genre WHERE nom_genre = '".$tuple[0]."'";

         $stid2 = @qrs($con, $query_sousgenre);
         $stid3 = @qrs($con, $query_sg_count);

         $sg_count = @oci_fetch_array($stid3, OCI_RETURN_NULLS);

         /* Comptage des oeuvres *directement* de ce genre */
         $query_title_count = "SELECT COUNT(*) FROM apourgenre a WHERE a.nom_genre = '$tuple[0]'";
         $stid_t = @qrs($con, $query_title_count);
         $title_count = @oci_fetch_array($stid_t, OCI_RETURN_NULLS);

         $plus = $sg_count[0] + 1; //Taille de la cellule (nombre de sous-genres + 1)
         
         /* Cellule Genre + affachige des oeuvres directempent de ce genre */
         print '<tr align="center"><td rowspan="'.$plus.'">'.$tuple[0].'</td>';
         print '<td> Général </td><td>'.$title_count[0].'</td>';
     
         /* Cellule total */
         print '<td rowspan="'.$plus.'">'.$total.'</td>';

         /* Parcours des cellules des sous-genres : comptage des sous-totaux */
         while ($sg = @oci_fetch_array($stid2, OCI_RETURN_NULLS)){ 
          print '<tr align="center">';
          $query_title_count = "SELECT COUNT(*) FROM apourgenre a WHERE a.nom_genre = '$sg[0]'"; 
          $stid_t = @qrs($con, $query_title_count);
          $title_count = @oci_fetch_array($stid_t, OCI_RETURN_NULLS);
          print '<td>'.$sg[0].'</td> <td>'.$title_count[0].'</td>';
          print '</tr>';
         }
        }
       }

       print '</table>';

       @oci_close($con);

     ?>
     </DIV>
  </body>
</html>
