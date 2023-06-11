<?php
error_reporting(0);

// parameter koneksi ke MySQL
$dbhost = "localhost";
$dbuser = "root";
$dbpass = "";
$dbname = "tablereservation";
$conn = mysqli_connect($dbhost, $dbuser, $dbpass, $dbname);


$baca=$_GET['mode'];

if($baca=="baca"){
   
    $q_baca=mysqli_query($conn,"select * from reservation_table");
    //$data_reserve=mysqli_fetch_array($q_baca);

    $result=array();
    while ($data_reserve = mysqli_fetch_array($q_baca)) {
        $reservation = array(
            'ID_reservasi' => $data_reserve['ID_reservasi'],
            'nomor_meja_reservasi' => $data_reserve['nomor_meja_reservasi'],
            'nama_reserver' => $data_reserve['nama_reserver'],
            'kontak_reserver' => $data_reserve['kontak_reserver'],
            'dp_reserver' => $data_reserve['dp_reserver'],
            'status_reserver' => $data_reserve['status_reserver'],
            'date_reserver' => $data_reserve['date_reserver'],
            'time_reserver' => $data_reserve['time_reserver'],
            'pass_reserver' => $data_reserve['pass_reserver']
        );
    
        $result[] = $reservation;
    }
    echo json_encode(array("result"=>$result));


}else if($baca=="simpan"){

    $simpan=mysqli_query($conn,"insert into reservation_table values('','".$_POST['nomor_meja_reservasi']."','".$_POST['nama_reserver']."','".$_POST['kontak_reserver']."','".$_POST['dp_reserver']."','".$_POST['status_reserver']."','".$_POST['date_reserver']."','".$_POST['time_reserver']."','".$_POST['pass_reserver']."')");
   if($simpan == 1){
        $result=array();
        array_push($result, array(
            'status'=>"Data Tersimpan"));
        echo json_encode(array("result"=>$result));
   }else{
        $result=array();
        array_push($result, array(
            'status'=>"Data Tidak Tersimpan"));
        echo json_encode(array("result"=>$result));
   }


}
else if($baca=="gantiAccepted"){
    $gantiAccepted=mysqli_query($conn, "UPDATE reservation_table SET status_reserver = 'ACCEPTED' WHERE pass_reserver= '".$_POST['pass_reserver']."'");
    if($gantiAccepted == 1){
         $result=array();
         array_push($result, array(
             'status'=>"Data Terganti"));
         echo json_encode(array("result"=>$result));
    }else{
         $result=array();
         array_push($result, array(
             'status'=>"Data Terganti"));
         echo json_encode(array("result"=>$result));
    }
}
else if($baca=="gantiRejected"){
    $gantiRejected=mysqli_query($conn, "UPDATE reservation_table SET status_reserver = 'REJECTED' WHERE pass_reserver= '".$_POST['pass_reserver']."'");
    if($gantiRejected == 1){
         $result=array();
         array_push($result, array(
             'status'=>"Data Terganti"));
         echo json_encode(array("result"=>$result));
    }else{
         $result=array();
         array_push($result, array(
             'status'=>"Data Terganti"));
         echo json_encode(array("result"=>$result));
    }
}

else if($baca=="cekStatus"){
    $cekStatus=mysqli_query($conn, "SELECT * FROM reservation_table WHERE pass_reserver= '".$_POST['pass_reserver']."'");
    $data_reserve=mysqli_fetch_array($cekStatus);
    $result=array();
    array_push($result, array(
        'ID_reservasi'=>$data_reserve['ID_reservasi'],
        'nomor_meja_reservasi'=>$data_reserve['nomor_meja_reservasi'],
        'nama_reserver'=>$data_reserve['nama_reserver'],
        'kontak_reserver'=>$data_reserve['kontak_reserver'],
        'dp_reserver'=>$data_reserve['dp_reserver'],
        'status_reserver'=>$data_reserve['status_reserver'],
        'date_reserver'=>$data_reserve['date_reserver'],
        'time_reserver'=>$data_reserve['time_reserver'],
        'pass_reserver'=>$data_reserve['pass_reserver']));
    echo json_encode(array("result"=>$result));
}

else if($baca=="cekMeja"){
    $cekStatus=mysqli_query($conn, "SELECT * FROM reservation_table WHERE date_reserver='".$_POST['date_reserver']."' AND time_reserver='".$_POST['time_reserver']."' AND status_reserver='ACCEPTED'");
   // $data_reserve=mysqli_fetch_array($cekStatus);
    $result=array();

    while ($data_reserve = mysqli_fetch_array($cekStatus)){
        $reservation = array(
            'ID_reservasi' => $data_reserve['ID_reservasi'],
            'nomor_meja_reservasi' => $data_reserve['nomor_meja_reservasi'],
        );
    
        $result[] = $reservation;
    }
    echo json_encode(array("result"=>$result));

   
    
}


?>