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

 

        print '<strong>Insertion des informations d\'une musique...</strong><br><br>';

        $titre = $_POST['ti'];
	$datec = $_POST['da'];

	$instrument = $_POST['instr'];

	$i_in = 0;

	foreach ($_POST['inte'] as $interp){

            $insert_inter_ins = "INSERT INTO interpretation (nomartiste,titre,datecreation,nom_instrument) VALUES (:na, :ti, :da, :ni)";
 
            $stid_ii = @oci_parse($con, $insert_ii);
            if (!$stid_ii){
	        $e = @oci_error($con);
	        print($e['message']);
	        exit;
	    }

            @oci_bind_by_name($stid_ii, ":na", $interp);
            @oci_bind_by_name($stid_ii, ":ti", $titre);
            @oci_bind_by_name($stid_ii, ":da", $datec);
            @oci_bind_by_name($stid_ii, ":ni", $instrument[$i_in]);

            $r_res = @oci_execute($stid_ii, OCI_DEFAULT);      
            if (!$r_res){
	       $e = @oci_error($stid_ii);
               echo($e['message']);
	       exit;
	    }

	    $i_in++;

 	}

	$r = @oci_commit($con);

	print '<strong>Tout s\'est bien passé !</strong><br><br>';

	print '<a href="acceuil.html" target="_self"><input type="button" value="Retour à l\'acceuil"></a><br><br>';

	@oci_close($con);
    	
	?>
    </DIV>
  </body>
</html>
