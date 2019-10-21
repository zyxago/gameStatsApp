-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Värd: 127.0.0.1
-- Tid vid skapande: 21 okt 2019 kl 16:07
-- Serverversion: 10.4.6-MariaDB
-- PHP-version: 7.3.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Databas: `match_stats`
--
CREATE DATABASE IF NOT EXISTS `match_stats` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `match_stats`;

-- --------------------------------------------------------

--
-- Ersättningsstruktur för vy `away_team`
-- (See below for the actual view)
--
CREATE TABLE `away_team` (
`awayTeam` int(11)
,`name` varchar(45)
);

-- --------------------------------------------------------

--
-- Tabellstruktur `game_match`
--

CREATE TABLE `game_match` (
  `id` int(11) NOT NULL,
  `home_team_id` int(11) NOT NULL,
  `away_team_id` int(11) NOT NULL,
  `score_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumpning av Data i tabell `game_match`
--

INSERT INTO `game_match` (`id`, `home_team_id`, `away_team_id`, `score_id`) VALUES
(1, 1, 2, 1),
(2, 1, 2, 2),
(3, 1, 3, 3),
(4, 3, 1, 4);

-- --------------------------------------------------------

--
-- Ersättningsstruktur för vy `getaway`
-- (See below for the actual view)
--
CREATE TABLE `getaway` (
`gameId` int(11)
,`away` varchar(45)
,`scoreAway` int(11)
);

-- --------------------------------------------------------

--
-- Ersättningsstruktur för vy `gethome`
-- (See below for the actual view)
--
CREATE TABLE `gethome` (
`gameId` int(11)
,`home` varchar(45)
,`scoreHome` int(11)
);

-- --------------------------------------------------------

--
-- Ersättningsstruktur för vy `get_match`
-- (See below for the actual view)
--
CREATE TABLE `get_match` (
`gameId` int(11)
,`home` varchar(45)
,`away` varchar(45)
,`scoreHome` int(11)
,`scoreAway` int(11)
);

-- --------------------------------------------------------

--
-- Ersättningsstruktur för vy `home_team`
-- (See below for the actual view)
--
CREATE TABLE `home_team` (
`homeTeam` int(11)
,`name` varchar(45)
);

-- --------------------------------------------------------

--
-- Tabellstruktur `score`
--

CREATE TABLE `score` (
  `id` int(11) NOT NULL,
  `home_score` int(11) DEFAULT NULL,
  `away_score` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumpning av Data i tabell `score`
--

INSERT INTO `score` (`id`, `home_score`, `away_score`) VALUES
(1, 0, 4),
(2, 3, 2),
(3, 1, 2),
(4, 0, 5),
(5, 4, 3);

-- --------------------------------------------------------

--
-- Tabellstruktur `team`
--

CREATE TABLE `team` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumpning av Data i tabell `team`
--

INSERT INTO `team` (`id`, `name`) VALUES
(1, 'testTeam1'),
(2, 'testTeam2'),
(3, 'testTeam3');

-- --------------------------------------------------------

--
-- Ersättningsstruktur för vy `teampoints`
-- (See below for the actual view)
--
CREATE TABLE `teampoints` (
`name` varchar(45)
,`points` decimal(32,0)
);

-- --------------------------------------------------------

--
-- Ersättningsstruktur för vy `team_total_points`
-- (See below for the actual view)
--
CREATE TABLE `team_total_points` (
`team` varchar(45)
,`points` decimal(54,0)
);

-- --------------------------------------------------------

--
-- Tabellstruktur `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktur för vy `away_team`
--
DROP TABLE IF EXISTS `away_team`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `away_team`  AS  select `team`.`id` AS `awayTeam`,`team`.`name` AS `name` from `team` ;

-- --------------------------------------------------------

--
-- Struktur för vy `getaway`
--
DROP TABLE IF EXISTS `getaway`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `getaway`  AS  select `game_match`.`id` AS `gameId`,`team`.`name` AS `away`,`score`.`away_score` AS `scoreAway` from ((`game_match` join `team`) join `score`) where `game_match`.`away_team_id` = `team`.`id` and `game_match`.`score_id` = `score`.`id` ;

-- --------------------------------------------------------

--
-- Struktur för vy `gethome`
--
DROP TABLE IF EXISTS `gethome`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `gethome`  AS  select `game_match`.`id` AS `gameId`,`team`.`name` AS `home`,`score`.`home_score` AS `scoreHome` from ((`game_match` join `team`) join `score`) where `game_match`.`home_team_id` = `team`.`id` and `game_match`.`score_id` = `score`.`id` ;

-- --------------------------------------------------------

--
-- Struktur för vy `get_match`
--
DROP TABLE IF EXISTS `get_match`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `get_match`  AS  select `gethome`.`gameId` AS `gameId`,`gethome`.`home` AS `home`,`getaway`.`away` AS `away`,`gethome`.`scoreHome` AS `scoreHome`,`getaway`.`scoreAway` AS `scoreAway` from (`gethome` join `getaway` on(`getaway`.`gameId` = `gethome`.`gameId`)) ;

-- --------------------------------------------------------

--
-- Struktur för vy `home_team`
--
DROP TABLE IF EXISTS `home_team`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `home_team`  AS  select `team`.`id` AS `homeTeam`,`team`.`name` AS `name` from `team` ;

-- --------------------------------------------------------

--
-- Struktur för vy `teampoints`
--
DROP TABLE IF EXISTS `teampoints`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `teampoints`  AS  select `team`.`name` AS `name`,sum(`score`.`home_score`) AS `points` from (`team` join (`score` join `game_match` on(`game_match`.`score_id` = `score`.`id`))) where `game_match`.`home_team_id` = `team`.`id` union all select `team`.`name` AS `name`,sum(`score`.`away_score`) AS `SUM(score.away_score)` from (`team` join (`score` join `game_match` on(`game_match`.`score_id` = `score`.`id`))) where `game_match`.`away_team_id` = `team`.`id` group by `team`.`name` ;

-- --------------------------------------------------------

--
-- Struktur för vy `team_total_points`
--
DROP TABLE IF EXISTS `team_total_points`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `team_total_points`  AS  select `teampoints`.`name` AS `team`,sum(`teampoints`.`points`) AS `points` from `teampoints` group by `teampoints`.`name` ;

--
-- Index för dumpade tabeller
--

--
-- Index för tabell `game_match`
--
ALTER TABLE `game_match`
  ADD PRIMARY KEY (`id`),
  ADD KEY `team2_id_idx` (`away_team_id`),
  ADD KEY `team1_id_idx` (`home_team_id`),
  ADD KEY `score_id_idx` (`score_id`);

--
-- Index för tabell `score`
--
ALTER TABLE `score`
  ADD PRIMARY KEY (`id`);

--
-- Index för tabell `team`
--
ALTER TABLE `team`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name_idx` (`name`) USING BTREE;

--
-- Index för tabell `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- AUTO_INCREMENT för dumpade tabeller
--

--
-- AUTO_INCREMENT för tabell `game_match`
--
ALTER TABLE `game_match`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT för tabell `score`
--
ALTER TABLE `score`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT för tabell `team`
--
ALTER TABLE `team`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT för tabell `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restriktioner för dumpade tabeller
--

--
-- Restriktioner för tabell `game_match`
--
ALTER TABLE `game_match`
  ADD CONSTRAINT `game_match_ibfk_1` FOREIGN KEY (`home_team_id`) REFERENCES `team` (`id`),
  ADD CONSTRAINT `game_match_ibfk_2` FOREIGN KEY (`away_team_id`) REFERENCES `team` (`id`),
  ADD CONSTRAINT `game_match_ibfk_3` FOREIGN KEY (`score_id`) REFERENCES `score` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
