-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 09, 2023 at 09:02 AM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tablereservation`
--

-- --------------------------------------------------------

--
-- Table structure for table `reservation_table`
--

CREATE TABLE `reservation_table` (
  `ID_reservasi` int(11) NOT NULL,
  `nomor_meja_reservasi` text DEFAULT NULL,
  `nama_reserver` text DEFAULT NULL,
  `kontak_reserver` text DEFAULT NULL,
  `dp_reserver` int(11) DEFAULT NULL,
  `status_reserver` text DEFAULT NULL,
  `date_reserver` date DEFAULT NULL,
  `time_reserver` time DEFAULT NULL,
  `pass_reserver` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `reservation_table`
--

INSERT INTO `reservation_table` (`ID_reservasi`, `nomor_meja_reservasi`, `nama_reserver`, `kontak_reserver`, `dp_reserver`, `status_reserver`, `date_reserver`, `time_reserver`, `pass_reserver`) VALUES
(7, 'table 1', 'harmor', '3858', 6800, 'REJECTED', '2023-05-09', '11:03:00', 'jbv31'),
(8, 'table 2', 'Sooyun', '60098', 338, 'ACCEPTED', '2023-05-09', '11:50:00', '1ke55'),
(9, 'table 1', 'Hero', '93555', 3888, 'ACCEPTED', '2023-05-09', '11:50:00', 'nrqjc');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `reservation_table`
--
ALTER TABLE `reservation_table`
  ADD PRIMARY KEY (`ID_reservasi`),
  ADD UNIQUE KEY `pass_reserver` (`pass_reserver`) USING HASH;

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `reservation_table`
--
ALTER TABLE `reservation_table`
  MODIFY `ID_reservasi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
