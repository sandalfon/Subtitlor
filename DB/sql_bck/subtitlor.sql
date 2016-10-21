-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Ven 21 Octobre 2016 à 16:33
-- Version du serveur :  5.6.17
-- Version de PHP :  5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `subtitlor`
--

-- --------------------------------------------------------

--
-- Structure de la table `subtitle_info`
--

CREATE TABLE IF NOT EXISTS `subtitle_info` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name_video` varchar(45) NOT NULL,
  `vo` enum('en','es','pt','fr','al') NOT NULL DEFAULT 'en',
  `table_name` varchar(45) NOT NULL,
  `name_en` varchar(45) DEFAULT NULL,
  `finished_en` tinyint(1) DEFAULT '0',
  `name_fr` varchar(45) DEFAULT NULL,
  `finished_fr` tinyint(1) DEFAULT '0',
  `name_al` varchar(45) DEFAULT NULL,
  `finished_al` tinyint(1) DEFAULT '0',
  `name_es` varchar(45) DEFAULT NULL,
  `finished_es` tinyint(1) DEFAULT '0',
  `name_pt` varchar(45) DEFAULT NULL,
  `finished_pt` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  UNIQUE KEY `table_name_UNIQUE` (`table_name`),
  UNIQUE KEY `name_UNIQUE` (`name_en`),
  UNIQUE KEY `name_fr_UNIQUE` (`name_fr`),
  UNIQUE KEY `name_zl_UNIQUE` (`name_al`),
  UNIQUE KEY `name_es_UNIQUE` (`name_es`),
  UNIQUE KEY `name_pt_UNIQUE` (`name_pt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
