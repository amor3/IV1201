-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 04, 2015 at 08:50 PM
-- Server version: 5.5.41
-- PHP Version: 5.3.10-1ubuntu3.15

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `RDB`
--

-- --------------------------------------------------------

--
-- Table structure for table `availability`
--

CREATE TABLE IF NOT EXISTS `availability` (
  `availability_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `person_id` bigint(20) NOT NULL,
  `from_date` date DEFAULT NULL,
  `to_date` date DEFAULT NULL,
  PRIMARY KEY (`availability_id`),
  KEY `person_id` (`person_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `availability`
--

INSERT INTO `availability` (`availability_id`, `person_id`, `from_date`, `to_date`) VALUES
(1, 2, '2014-02-23', '2014-05-25'),
(2, 2, '2014-07-10', '2014-08-10');

-- --------------------------------------------------------

--
-- Table structure for table `competence`
--

CREATE TABLE IF NOT EXISTS `competence` (
  `competence_id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`competence_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Table structure for table `competence_en`
--

CREATE TABLE IF NOT EXISTS `competence_en` (
  `name` varchar(100) NOT NULL,
  `competenceId` bigint(20) NOT NULL,
  PRIMARY KEY (`competenceId`),
  UNIQUE KEY `competenceId` (`competenceId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `competence_profile`
--

CREATE TABLE IF NOT EXISTS `competence_profile` (
  `competence_profile_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `person_id` bigint(20) DEFAULT NULL,
  `competence_id` bigint(20) DEFAULT NULL,
  `years_of_experience` decimal(4,2) DEFAULT NULL,
  PRIMARY KEY (`competence_profile_id`),
  UNIQUE KEY `competence_id` (`competence_id`),
  KEY `person_id` (`person_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Table structure for table `competence_sv`
--

CREATE TABLE IF NOT EXISTS `competence_sv` (
  `name` varchar(100) NOT NULL,
  `competenceId` bigint(20) NOT NULL,
  PRIMARY KEY (`competenceId`),
  UNIQUE KEY `competenceId` (`competenceId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Group`
--

CREATE TABLE IF NOT EXISTS `Group` (
  `role` varchar(100) NOT NULL,
  `person` varchar(100) NOT NULL,
  PRIMARY KEY (`role`,`person`),
  KEY `person` (`person`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `person`
--

CREATE TABLE IF NOT EXISTS `person` (
  `person_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `ssn` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`person_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `person`
--

INSERT INTO `person` (`person_id`, `name`, `surname`, `ssn`, `email`, `password`) VALUES
(1, 'Greta', 'Borg', NULL, '', 'wl9nk23a'),
(2, 'Per', 'Strand', '19671212-1211', 'per@strand.kth.se', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE IF NOT EXISTS `role` (
  `roleName` varchar(255) NOT NULL,
  PRIMARY KEY (`roleName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`roleName`) VALUES
('applicant'),
('recruit');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `availability`
--
ALTER TABLE `availability`
  ADD CONSTRAINT `availability_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`);

--
-- Constraints for table `competence_en`
--
ALTER TABLE `competence_en`
  ADD CONSTRAINT `competence_en_ibfk_2` FOREIGN KEY (`competenceId`) REFERENCES `competence` (`competence_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `competence_profile`
--
ALTER TABLE `competence_profile`
  ADD CONSTRAINT `competence_profile_ibfk_5` FOREIGN KEY (`competence_id`) REFERENCES `competence` (`competence_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `competence_profile_ibfk_4` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `competence_sv`
--
ALTER TABLE `competence_sv`
  ADD CONSTRAINT `competence_sv_ibfk_3` FOREIGN KEY (`competenceId`) REFERENCES `competence` (`competence_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `Group`
--
ALTER TABLE `Group`
  ADD CONSTRAINT `Group_ibfk_1` FOREIGN KEY (`role`) REFERENCES `role` (`roleName`),
  ADD CONSTRAINT `Group_ibfk_2` FOREIGN KEY (`person`) REFERENCES `person` (`email`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;