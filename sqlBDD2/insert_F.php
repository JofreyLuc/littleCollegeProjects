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

        print '<strong>Insertion des informations d\'un film...</strong><br><br>';

        $titre = $_POST['ti'];
	$datec = $_POST['da'];

	$prin = $_POST['princip'];
	$rol = $_POST['role'];

	$i_dis = 0;

	foreach ($_POST['dist'] as $dist){

            $insert_dist = "INSERT INTO distribution (titre,datecreation,nomartiste,principal,personnage) VALUES ('".$titre."', '".$datec."', '".$dist."', '".$prin[$i_dis]."', '".$rol[$i_dis]."')";

 
            $stid_dist = @oci_parse($con, $insert_dist);
            if (!$stid_dist){
	        $e = @oci_error($con);
	        print($e['message']);
	        exit;
	    }

            @oci_bind_by_name($stid_dist, ":ti", $titre);
            @oci_bind_by_name($stid_dist, ":da", $datec);
            @oci_bind_by_name($stid_dist, ":na", $dist);
            @oci_bind_by_name($stid_dist, ":pr", $prin[$i_dis]);
            @oci_bind_by_name($stid_dist, ":pn", $rol[$i_dis]);

            $r_res = @oci_execute($stid_dist, OCI_DEFAULT);      
            if (!$r_res){
	       $e = @oci_error($stid_dist);
               echo($e['message']);
	       exit;
	    }

	    $i_dist++;

 	}

	$r = @oci_commit($con);

	print '<strong>Tout s\'est bien passé !</strong><br><br>';

	print '<a href="acceuil.html" target="_self"><input type="button" value="Retour à l\'acceuil"></a><br><br>';

	@oci_close($con);
    	
	?>
    </DIV>
  </body>
</html>
