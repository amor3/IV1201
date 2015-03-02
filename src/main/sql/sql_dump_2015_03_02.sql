-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 02, 2015 at 09:21 PM
-- Server version: 5.5.41
-- PHP Version: 5.3.10-1ubuntu3.16

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5111 ;

--
-- Dumping data for table `competence`
--

INSERT INTO `competence` (`competence_id`) VALUES
(2951),
(2954);

-- --------------------------------------------------------

--
-- Table structure for table `competence_en`
--

CREATE TABLE IF NOT EXISTS `competence_en` (
  `name` varchar(100) NOT NULL,
  `competenceId` bigint(20) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `competenceId` (`competenceId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5113 ;

--
-- Dumping data for table `competence_en`
--

INSERT INTO `competence_en` (`name`, `competenceId`, `id`) VALUES
('competence1', 2951, 2953),
('competence2', 2954, 2956);

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
  UNIQUE KEY `person_id_2` (`person_id`,`competence_id`),
  KEY `person_id` (`person_id`),
  KEY `competence_id` (`competence_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3822 ;

--
-- Dumping data for table `competence_profile`
--

INSERT INTO `competence_profile` (`competence_profile_id`, `person_id`, `competence_id`, `years_of_experience`) VALUES
(3820, 5152, 2951, 1.00),
(3821, 5152, 2954, 1.00);

-- --------------------------------------------------------

--
-- Table structure for table `competence_sv`
--

CREATE TABLE IF NOT EXISTS `competence_sv` (
  `name` varchar(100) NOT NULL,
  `competenceId` bigint(20) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `competenceId` (`competenceId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5112 ;

--
-- Dumping data for table `competence_sv`
--

INSERT INTO `competence_sv` (`name`, `competenceId`, `id`) VALUES
('competence1', 2951, 2952),
('competence2', 2954, 2955);

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5153 ;

--
-- Dumping data for table `person`
--

INSERT INTO `person` (`person_id`, `name`, `surname`, `ssn`, `email`, `password`) VALUES
(1, 'Greta', 'Borg', NULL, 'greta@kth.se', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8'),
(2, 'Per', 'Strand', '19671212-1211', 'per@strand.kth.se', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8'),
(3, 'Andre', 'More', '19890110-0011', 'amore@kth.se', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b'),
(5152, 'firstname', 'surname', '191900001111', 'email@kth.se', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8');

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
('ADMINS'),
('APPLICANTS'),
('RECRUITERS');

-- --------------------------------------------------------

--
-- Table structure for table `SEQUENCE`
--

CREATE TABLE IF NOT EXISTS `SEQUENCE` (
  `SEQ_NAME` varchar(50) NOT NULL,
  `SEQ_COUNT` decimal(38,0) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `SEQUENCE`
--

INSERT INTO `SEQUENCE` (`SEQ_NAME`, `SEQ_COUNT`) VALUES
('SEQ_GEN', 5200);

-- --------------------------------------------------------

--
-- Table structure for table `user_group`
--

CREATE TABLE IF NOT EXISTS `user_group` (
  `roleName` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`roleName`,`email`),
  KEY `person` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_group`
--

INSERT INTO `user_group` (`roleName`, `email`) VALUES
('ADMINS', 'amore@kth.se'),
('APPLICANTS', 'email@kth.se');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `availability`
--
ALTER TABLE `availability`
  ADD CONSTRAINT `availability_ibfk_2` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `competence_en`
--
ALTER TABLE `competence_en`
  ADD CONSTRAINT `competence_en_ibfk_1` FOREIGN KEY (`competenceId`) REFERENCES `competence` (`competence_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `competence_profile`
--
ALTER TABLE `competence_profile`
  ADD CONSTRAINT `competence_profile_ibfk_4` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `competence_profile_ibfk_5` FOREIGN KEY (`competence_id`) REFERENCES `competence` (`competence_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `competence_sv`
--
ALTER TABLE `competence_sv`
  ADD CONSTRAINT `competence_sv_ibfk_1` FOREIGN KEY (`competenceId`) REFERENCES `competence` (`competence_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user_group`
--
ALTER TABLE `user_group`
  ADD CONSTRAINT `Group_ibfk_1` FOREIGN KEY (`roleName`) REFERENCES `role` (`roleName`),
  ADD CONSTRAINT `user_group_ibfk_1` FOREIGN KEY (`email`) REFERENCES `person` (`email`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
