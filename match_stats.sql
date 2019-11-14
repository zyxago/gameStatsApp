-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Värd: 127.0.0.1
-- Tid vid skapande: 14 nov 2019 kl 11:42
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
-- Ersättningsstruktur för vy `games_played`
-- (See below for the actual view)
--
CREATE TABLE `games_played` (
`id` int(11)
,`games` bigint(21)
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
-- Ersättningsstruktur för vy `get_away`
-- (See below for the actual view)
--
CREATE TABLE `get_away` (
`gameId` int(11)
,`awayId` int(11)
,`away` varchar(45)
,`scoreAway` int(11)
);

-- --------------------------------------------------------

--
-- Ersättningsstruktur för vy `get_home`
-- (See below for the actual view)
--
CREATE TABLE `get_home` (
`gameId` int(11)
,`homeId` int(11)
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
,`homeId` int(11)
,`away` varchar(45)
,`awayId` int(11)
,`scoreHome` int(11)
,`scoreAway` int(11)
,`winner` varchar(45)
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
-- Ersättningsstruktur för vy `team_stats`
-- (See below for the actual view)
--
CREATE TABLE `team_stats` (
`teamId` int(11)
,`name` varchar(45)
,`wins` decimal(32,0)
,`losses` decimal(32,0)
,`games` bigint(21)
);

-- --------------------------------------------------------

--
-- Ersättningsstruktur för vy `team_wins_losses`
-- (See below for the actual view)
--
CREATE TABLE `team_wins_losses` (
`teamId` int(11)
,`name` varchar(45)
,`games_won` int(11)
,`games_lost` int(11)
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
-- Struktur för vy `games_played`
--
DROP TABLE IF EXISTS `games_played`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `games_played`  AS  select `team`.`id` AS `id`,count(`game_match`.`home_team_id`) AS `games` from (`team` join `game_match`) where `team`.`id` = `game_match`.`away_team_id` or `team`.`id` = `game_match`.`home_team_id` group by `team`.`id` ;

-- --------------------------------------------------------

--
-- Struktur för vy `get_away`
--
DROP TABLE IF EXISTS `get_away`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `get_away`  AS  select `game_match`.`id` AS `gameId`,`team`.`id` AS `awayId`,`team`.`name` AS `away`,`score`.`away_score` AS `scoreAway` from ((`game_match` join `team`) join `score`) where `game_match`.`away_team_id` = `team`.`id` and `game_match`.`score_id` = `score`.`id` ;

-- --------------------------------------------------------

--
-- Struktur för vy `get_home`
--
DROP TABLE IF EXISTS `get_home`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `get_home`  AS  select `game_match`.`id` AS `gameId`,`team`.`id` AS `homeId`,`team`.`name` AS `home`,`score`.`home_score` AS `scoreHome` from ((`game_match` join `team`) join `score`) where `game_match`.`home_team_id` = `team`.`id` and `game_match`.`score_id` = `score`.`id` ;

-- --------------------------------------------------------

--
-- Struktur för vy `get_match`
--
DROP TABLE IF EXISTS `get_match`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `get_match`  AS  select `get_home`.`gameId` AS `gameId`,`get_home`.`home` AS `home`,`get_home`.`homeId` AS `homeId`,`get_away`.`away` AS `away`,`get_away`.`awayId` AS `awayId`,`get_home`.`scoreHome` AS `scoreHome`,`get_away`.`scoreAway` AS `scoreAway`,case `get_home`.`home` when `get_home`.`scoreHome` > `get_away`.`scoreAway` then `get_away`.`away` when `get_home`.`scoreHome` < `get_away`.`scoreAway` then `get_home`.`home` end AS `winner` from (`get_home` join `get_away` on(`get_away`.`gameId` = `get_home`.`gameId`)) ;

-- --------------------------------------------------------

--
-- Struktur för vy `home_team`
--
DROP TABLE IF EXISTS `home_team`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `home_team`  AS  select `team`.`id` AS `homeTeam`,`team`.`name` AS `name` from `team` ;

-- --------------------------------------------------------

--
-- Struktur för vy `team_stats`
--
DROP TABLE IF EXISTS `team_stats`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `team_stats`  AS  select `team_wins_losses`.`teamId` AS `teamId`,`team_wins_losses`.`name` AS `name`,sum(`team_wins_losses`.`games_won`) AS `wins`,sum(`team_wins_losses`.`games_lost`) AS `losses`,`games_played`.`games` AS `games` from (`team_wins_losses` join `games_played` on(`games_played`.`id` = `team_wins_losses`.`teamId`)) group by `team_wins_losses`.`name` ;

-- --------------------------------------------------------

--
-- Struktur för vy `team_wins_losses`
--
DROP TABLE IF EXISTS `team_wins_losses`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `team_wins_losses`  AS  select `team`.`id` AS `teamId`,`team`.`name` AS `name`,`get_home`.`scoreHome` AS `games_won`,`get_away`.`scoreAway` AS `games_lost` from ((`team` join `get_home`) join `get_away`) where `get_home`.`gameId` = `get_away`.`gameId` and `team`.`id` = `get_home`.`homeId` union all select `team`.`id` AS `teamId`,`team`.`name` AS `name`,`get_away`.`scoreAway` AS `games_won`,`get_home`.`scoreHome` AS `games_lost` from ((`team` join `get_home`) join `get_away`) where `get_home`.`gameId` = `get_away`.`gameId` and `team`.`id` = `get_away`.`awayId` group by `team`.`name` ;

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
