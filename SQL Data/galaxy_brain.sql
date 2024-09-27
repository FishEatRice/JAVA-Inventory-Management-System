-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 22, 2024 at 03:57 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `galaxy_brain`
--
CREATE DATABASE IF NOT EXISTS `galaxy_brain` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `galaxy_brain`;

-- --------------------------------------------------------

--
-- Table structure for table `invoice`
--

CREATE TABLE `invoice` (
  `Invoice_No` int(11) NOT NULL,
  `Invoice_ID` varchar(10) NOT NULL,
  `Item_ID` varchar(10) NOT NULL,
  `Quantity` int(11) NOT NULL DEFAULT 0,
  `Price` double NOT NULL DEFAULT 0,
  `Invoice_Datetime` datetime NOT NULL,
  `Staff_ID` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `invoice`
--

INSERT INTO `invoice` (`Invoice_No`, `Invoice_ID`, `Item_ID`, `Quantity`, `Price`, `Invoice_Datetime`, `Staff_ID`) VALUES
(1, 'N12', 'I1', 0, 0, '2024-09-01 12:12:48', '123'),
(2, 'N12', 'I1', 0, 0, '2024-09-01 12:12:48', '123'),
(3, 'N13', 'I19', 2, 2.5, '2024-09-20 14:22:31', 'STF1'),
(4, 'N13', 'I5', 1, 2.5, '2024-09-20 14:22:31', 'STF1'),
(5, 'N14', 'I17', 5, 80.8, '2024-09-21 17:10:21', 'stf1');

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `Item_ID` varchar(10) NOT NULL,
  `Item_Name` varchar(50) NOT NULL,
  `Item_Price` double NOT NULL DEFAULT 0,
  `Supplier_ID` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`Item_ID`, `Item_Name`, `Item_Price`, `Supplier_ID`) VALUES
('I11', 'PPAP Bun', 2.5, 'SP1'),
('I12', 'Durian Bun', 2.5, 'SP1'),
('I15', 'Aqua Bun', 44.5, 'SP1'),
('I17', 'Towa Bun', 80.8, 'SP1'),
('I18', 'Aqua Bun', 2.5, 'SP2'),
('I19', 'Suisei Bun', 2.5, 'SP1'),
('I2', 'Chocolate Bread', 2, 'SP2'),
('I21', 'Coco Dragon Bun', 98, 'SP1'),
('I22', 'Kanata Bun', 25, 'SP1'),
('I23', 'Startend Bun', 2.5, 'SP2'),
('I26', 'Ame Bun', 2.5, 'SP2'),
('I9', 'Startend Bun', 2.5, 'SP1');

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE `order` (
  `Order_No` int(11) NOT NULL,
  `Order_ID` varchar(10) NOT NULL,
  `Supplier_ID` varchar(10) NOT NULL,
  `Item_ID` varchar(10) NOT NULL,
  `Quantity` int(11) NOT NULL DEFAULT 0,
  `Price` double NOT NULL DEFAULT 0,
  `Order_Datetime` datetime NOT NULL,
  `Staff_ID` varchar(10) NOT NULL,
  `Store_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order`
--

INSERT INTO `order` (`Order_No`, `Order_ID`, `Supplier_ID`, `Item_ID`, `Quantity`, `Price`, `Order_Datetime`, `Staff_ID`, `Store_ID`) VALUES
(4, 'S4', 'SP2', 'I26', 5, 2.5, '2024-09-17 11:50:58', '123', 'ST6'),
(10, 'S10', 'SP2', 'I26', 7, 2.5, '2024-09-17 11:42:33', '123', 'ST11'),
(11, 'S11', 'SP1', 'I21', 10, 98, '2024-09-17 12:08:30', '123', 'ST10'),
(12, 'S12', 'SP2', 'I26', 7, 2.5, '2024-09-17 15:26:28', '123', 'ST12'),
(13, 'S13', 'SP2', 'I26', 100, 2.5, '2024-09-20 14:24:48', 'STF1', 'ST13'),
(15, 'S15', 'SP2', 'I2', 10, 2, '2024-09-21 20:46:21', 'stf1', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `Staff_ID` varchar(10) NOT NULL,
  `Staff_Name` varchar(50) NOT NULL,
  `Staff_Password` varchar(50) NOT NULL,
  `Position` char(1) NOT NULL DEFAULT 'S'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`Staff_ID`, `Staff_Name`, `Staff_Password`, `Position`) VALUES
('123', 'On Yuen Shern', '123', 'A'),
('STF1', 'Minato Aqua', '123', 'S'),
('STF12', 'Tan Yock Khang', 'tan123', 'S'),
('STF2', 'Aqua Tan', '123', 'S'),
('STF3', 'Sui can', '123', 'S');

-- --------------------------------------------------------

--
-- Table structure for table `store`
--

CREATE TABLE `store` (
  `Store_ID` varchar(10) NOT NULL,
  `Item_ID` varchar(10) NOT NULL,
  `Quantity` int(11) NOT NULL DEFAULT 0,
  `Expiry_Date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `store`
--

INSERT INTO `store` (`Store_ID`, `Item_ID`, `Quantity`, `Expiry_Date`) VALUES
('ST10', 'I21', 29, '2024-09-30'),
('ST11', 'I26', 7, '2024-09-23'),
('ST12', 'I26', 100, '2024-09-26'),
('ST13', 'I26', 100, '2024-09-30'),
('ST5', 'I19', 8, '2025-08-21'),
('ST6', 'I17', 15, '2024-08-26'),
('ST8', 'I2', 15, '2024-09-25'),
('ST9', 'I5', 9, '2024-09-30');

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `Supplier_ID` varchar(10) NOT NULL,
  `Supplier_Name` varchar(50) NOT NULL,
  `Supplier_Phone` varchar(11) NOT NULL,
  `Supplier_Password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`Supplier_ID`, `Supplier_Name`, `Supplier_Phone`, `Supplier_Password`) VALUES
('SP1', 'Kanade Chan', '0123456789', '123'),
('SP2', 'Ina', '0123456789', '123');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `invoice`
--
ALTER TABLE `invoice`
  ADD PRIMARY KEY (`Invoice_No`),
  ADD KEY `Staff_ID` (`Staff_ID`);

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`Item_ID`),
  ADD KEY `Supplier_ID` (`Supplier_ID`);

--
-- Indexes for table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`Order_No`),
  ADD KEY `Item_ID` (`Item_ID`),
  ADD KEY `Staff_ID` (`Staff_ID`),
  ADD KEY `Supplier_ID` (`Supplier_ID`),
  ADD KEY `Store_ID` (`Store_ID`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`Staff_ID`);

--
-- Indexes for table `store`
--
ALTER TABLE `store`
  ADD PRIMARY KEY (`Store_ID`),
  ADD KEY `Item_ID` (`Item_ID`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`Supplier_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
