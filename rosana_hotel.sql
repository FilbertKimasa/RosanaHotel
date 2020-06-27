-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 14, 2017 at 10:25 AM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 5.6.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `rosana_hotel`
--

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `booking_id` varchar(15) NOT NULL COMMENT 'Booking ID',
  `num_rooms` int(1) NOT NULL COMMENT 'Number of rooms a guest books',
  `rooms_type` int(1) NOT NULL COMMENT 'Type of rooms a guest books. 1 for Standard, 2 for Classic, 3 for Superior and 4 for Family Suite',
  `booking_datetime` datetime DEFAULT NULL COMMENT 'Date and time when a booking is created',
  `arrival` date NOT NULL COMMENT 'Date that a guest expects to start using a booked room',
  `departure` date NOT NULL COMMENT 'Date that a guest expects to leave a booked room',
  `seen` int(1) DEFAULT '0' COMMENT 'A boolean value that indicates whether a booking is marked as read or not by an administrator'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`booking_id`, `num_rooms`, `rooms_type`, `booking_datetime`, `arrival`, `departure`, `seen`) VALUES
('2JKvSvEKpBb7', 1, 3, '2017-10-28 13:34:56', '2017-09-16', '2017-09-17', 1),
('ckSlCDaE21cQ', 3, 1, '2017-11-06 17:29:41', '2017-11-12', '2017-11-15', 0),
('HWvpwBliunR7', 1, 1, '2017-11-06 17:45:22', '2017-11-19', '2017-11-21', 1),
('IY4UJFyQPdaI', 2, 2, '2017-11-11 11:24:50', '2016-12-12', '2016-12-14', 0),
('jIUtlEPNBJJF', 2, 2, '2017-10-27 15:13:02', '2017-12-07', '2017-12-10', 1),
('L6jecFSe4kL3', 1, 3, '2017-11-09 22:29:10', '2017-12-23', '2017-12-24', 1),
('LjoLVcD3Ttjl', 2, 1, '2017-11-11 15:45:34', '2018-04-11', '2018-04-12', 1),
('lS8exyfS6bXE', 1, 1, '2017-11-01 17:19:31', '2017-12-21', '2017-12-23', 1),
('PcC9AAEbfcoH', 2, 4, '2017-11-14 11:37:53', '2017-11-20', '2017-11-22', 1),
('QJxqdUB25NLo', 2, 4, '2017-11-11 15:59:07', '2017-10-22', '2017-10-24', 0),
('RYxOZsnXbbFL', 2, 3, '2017-10-27 17:04:41', '2018-06-24', '2018-06-27', 1),
('TYaqOrTH954s', 1, 3, '2017-11-11 11:08:49', '2017-12-12', '2017-12-13', 1),
('UfNvggTS0X7Q', 3, 3, '2017-10-27 15:22:51', '2017-11-13', '2017-11-18', 1),
('WYRrmNvPxUSB', 1, 1, '2017-10-27 15:19:21', '2017-10-03', '2017-10-06', 1),
('xJNI9BfOp95E', 1, 3, '2017-11-14 12:05:31', '0207-12-24', '2017-12-26', 1);

-- --------------------------------------------------------

--
-- Table structure for table `guest`
--

CREATE TABLE `guest` (
  `email` varchar(100) NOT NULL COMMENT 'Email of a guest',
  `name` varchar(100) NOT NULL COMMENT 'Full name of a guest',
  `mobile` varchar(30) NOT NULL COMMENT 'Mobile phone number of a guest',
  `nationality` varchar(100) NOT NULL COMMENT 'Nationality of a guest',
  `booking_id` varchar(15) DEFAULT NULL COMMENT 'Booking ID linked to'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `guest`
--

INSERT INTO `guest` (`email`, `name`, `mobile`, `nationality`, `booking_id`) VALUES
('jaymash01@gmail.com', 'Jay Mashauri', '0757206864', 'Tanzania', 'jIUtlEPNBJJF'),
('catty.james@gmail.com', 'Catty James', '0752781346', 'Tanzania', 'WYRrmNvPxUSB'),
('mensenrachs06@gmail.com', 'Mensen Rachs', '+1 0826 5735 927', 'USA', 'UfNvggTS0X7Q'),
('honeypot.lin@gmail.com', 'Irene Ngenda', '0713617764', 'Tanzania', 'RYxOZsnXbbFL'),
('judiemayyonce@gmail.com', 'Judith Japhet', '0743126187', 'Tanzania', '2JKvSvEKpBb7'),
('felix.campos@gmail.com', 'Felix Campos', '+1 6877 236 978', 'USA', 'lS8exyfS6bXE'),
('lou.vuitton@gmail.com', 'Louis Vuitton', '+96 741 5736 2737', 'England', 'ckSlCDaE21cQ'),
('youngkillah@gmail.com', 'Eric Msodoki', '0763201677', 'Tanzania', 'HWvpwBliunR7'),
('andy@gmail.com', 'Andy Kawa', '0757256253', 'Tanzania', 'L6jecFSe4kL3'),
('said@gmail.com', 'Lusavya Said', '075475636', 'Tanzania', 'TYaqOrTH954s'),
('nandy@gmail.com', 'Nandera Jackson', '0753456891', 'Tanzania', 'IY4UJFyQPdaI'),
('mach@gmail.com', 'Machius Rwg', '067736564', 'Tanzanian', 'LjoLVcD3Ttjl'),
('jsimon@gmail.com', 'John Simon', '0757374727', 'Tanzania', 'QJxqdUB25NLo'),
('j.khalid@gmail.com', 'Juma Khalid', '057364242', 'Kenya', 'PcC9AAEbfcoH'),
('jmasha@gmail.com', 'Jose Masha', '0683747242', 'Tanzanian', 'xJNI9BfOp95E');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `amount` float NOT NULL COMMENT 'Amount in USD a guest pays in order to book',
  `bank` varchar(100) NOT NULL COMMENT 'Name of bank a guest uses to pay for a booking',
  `ref_no` varchar(20) NOT NULL COMMENT 'Bank reference numbers for payment made by a guest',
  `booking_id` varchar(15) DEFAULT NULL COMMENT 'Booking ID linked to'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`amount`, `bank`, `ref_no`, `booking_id`) VALUES
(200, 'NMB', 'UJSE3Y73T6E', 'jIUtlEPNBJJF'),
(80, 'CRDB', 'YB63TD6WKM', 'WYRrmNvPxUSB'),
(300, 'Barclays', 'NBJRYE6SD6E', 'UfNvggTS0X7Q'),
(200, 'NMB', 'MVRHE6CTEK8', 'RYxOZsnXbbFL'),
(100, 'NMD', 'JDUE47WTERU', '2JKvSvEKpBb7'),
(80, 'Barclays', 'E2XVJRUVYSN', 'lS8exyfS6bXE'),
(180, 'Barclays', '4YK9LT02JS1B', 'ckSlCDaE21cQ'),
(60, 'NMB', 'SUH84J21K7M', 'HWvpwBliunR7'),
(120, 'CRDB', '5K74H755D72', 'L6jecFSe4kL3'),
(60, 'CRDB', 'jfrudc7e4', 'TYaqOrTH954s'),
(160, 'KFC', '3GJ5ET7AJG', 'IY4UJFyQPdaI'),
(200, 'CRDB', 'HYSWEGCY63', 'LjoLVcD3Ttjl'),
(240, 'NMB', 'HTURUGHFHA', 'QJxqdUB25NLo'),
(200, 'NMB', 'FY46F6364YED', 'PcC9AAEbfcoH'),
(100, 'NMB', 'GYRYWRYDTS', 'xJNI9BfOp95E');

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE `room` (
  `room_no` varchar(10) NOT NULL COMMENT 'Number of a room',
  `type` int(1) NOT NULL COMMENT 'Type of a room. Values range from 1 to 4. 1 for Standard, 2 for Classic, 3 for Superior and 4 for Family',
  `occupied` int(1) DEFAULT '0' COMMENT 'A boolean value that indicates whether a room is occupied or not'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`room_no`, `type`, `occupied`) VALUES
('101', 1, 1),
('102', 1, 0),
('103', 1, 0),
('104', 1, 0),
('105', 1, 0),
('106', 1, 0),
('107', 1, 0),
('108', 1, 1),
('109', 1, 1),
('110', 1, 0),
('111', 1, 0),
('112', 1, 0),
('113', 1, 1),
('114', 1, 0),
('115', 1, 0),
('116', 1, 0),
('117', 1, 0),
('118', 1, 0),
('119', 1, 1),
('121', 2, 1),
('122', 2, 0),
('123', 2, 0),
('124', 2, 0),
('125', 2, 0),
('126', 2, 0),
('127', 2, 0),
('128', 2, 0),
('129', 2, 0),
('130', 2, 0),
('131', 2, 0),
('132', 2, 1),
('133', 2, 0),
('134', 2, 0),
('135', 2, 0),
('136', 2, 0),
('137', 2, 0),
('138', 2, 0),
('139', 2, 0),
('140', 2, 0),
('141', 3, 1),
('142', 3, 1),
('143', 3, 1),
('144', 3, 1),
('145', 3, 1),
('146', 3, 1),
('147', 3, 1),
('148', 3, 1),
('149', 3, 1),
('150', 3, 0),
('151', 4, 0),
('152', 4, 0),
('153', 4, 0),
('154', 4, 0),
('155', 4, 0),
('156', 4, 0),
('157', 4, 1),
('158', 4, 0),
('160', 4, 1),
('6B-T02', 2, 0);

-- --------------------------------------------------------

--
-- Table structure for table `room_booking`
--

CREATE TABLE `room_booking` (
  `room_no` varchar(10) NOT NULL COMMENT 'Room number linked to',
  `booking_id` varchar(15) NOT NULL COMMENT 'Booking ID linked to'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `room_booking`
--

INSERT INTO `room_booking` (`room_no`, `booking_id`) VALUES
('121', 'jIUtlEPNBJJF'),
('132', 'jIUtlEPNBJJF'),
('109', 'WYRrmNvPxUSB'),
('141', 'RYxOZsnXbbFL'),
('142', 'RYxOZsnXbbFL'),
('143', 'UfNvggTS0X7Q'),
('144', 'UfNvggTS0X7Q'),
('145', 'UfNvggTS0X7Q'),
('146', '2JKvSvEKpBb7'),
('113', 'HWvpwBliunR7'),
('147', 'L6jecFSe4kL3'),
('148', 'TYaqOrTH954s'),
('101', 'lS8exyfS6bXE'),
('119', 'LjoLVcD3Ttjl'),
('108', 'LjoLVcD3Ttjl'),
('160', 'PcC9AAEbfcoH'),
('157', 'PcC9AAEbfcoH'),
('149', 'xJNI9BfOp95E');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`booking_id`),
  ADD KEY `booking_id` (`booking_id`);

--
-- Indexes for table `guest`
--
ALTER TABLE `guest`
  ADD KEY `fk_guest_booking` (`booking_id`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD KEY `fk_payment_booking` (`booking_id`);

--
-- Indexes for table `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`room_no`),
  ADD KEY `room_no` (`room_no`);

--
-- Indexes for table `room_booking`
--
ALTER TABLE `room_booking`
  ADD KEY `fk_rb_room` (`room_no`),
  ADD KEY `fk_rb_booking` (`booking_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `guest`
--
ALTER TABLE `guest`
  ADD CONSTRAINT `fk_guest_booking` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`booking_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `fk_payment_booking` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`booking_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `room_booking`
--
ALTER TABLE `room_booking`
  ADD CONSTRAINT `fk_rb_booking` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`booking_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_rb_room` FOREIGN KEY (`room_no`) REFERENCES `room` (`room_no`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
