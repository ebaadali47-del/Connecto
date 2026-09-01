-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 01, 2026 at 12:54 PM
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
-- Database: `socialconnector`
--

-- --------------------------------------------------------

--
-- Table structure for table `chats`
--

CREATE TABLE `chats` (
  `id` int(11) NOT NULL,
  `user1` int(11) DEFAULT NULL,
  `user2` int(11) DEFAULT NULL,
  `chat_key` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `chats`
--

INSERT INTO `chats` (`id`, `user1`, `user2`, `chat_key`) VALUES
(1, 1, 2, '1_2'),
(2, 2, 4, '2_4');

-- --------------------------------------------------------

--
-- Table structure for table `deleted_messages`
--

CREATE TABLE `deleted_messages` (
  `user_id` int(11) DEFAULT NULL,
  `chat_id` int(11) DEFAULT NULL,
  `message_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `friends`
--

CREATE TABLE `friends` (
  `user_id` int(11) DEFAULT NULL,
  `friend_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `friends`
--

INSERT INTO `friends` (`user_id`, `friend_id`) VALUES
(2, 1),
(1, 2),
(2, 4),
(4, 2),
(3, 4),
(4, 3);

-- --------------------------------------------------------

--
-- Table structure for table `friend_actions`
--

CREATE TABLE `friend_actions` (
  `user_id` int(11) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `other_user` int(11) DEFAULT NULL,
  `request_id` int(11) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `friend_actions`
--

INSERT INTO `friend_actions` (`user_id`, `type`, `other_user`, `request_id`, `timestamp`) VALUES
(2, 'SEND_REQUEST', 1, 1, 1772462767092),
(1, 'ACCEPT_REQUEST', 2, 1, 1772462891701),
(4, 'SEND_REQUEST', 5, 2, 1781424764251),
(1, 'SEND_REQUEST', 4, 3, 1781424859580),
(3, 'SEND_REQUEST', 4, 4, 1781424883988),
(2, 'SEND_REQUEST', 4, 5, 1781424919187),
(4, 'ACCEPT_REQUEST', 2, 5, 1781424993651),
(4, 'ACCEPT_REQUEST', 3, 4, 1781424996916);

-- --------------------------------------------------------

--
-- Table structure for table `friend_requests`
--

CREATE TABLE `friend_requests` (
  `id` int(11) NOT NULL,
  `from_user` int(11) DEFAULT NULL,
  `to_user` int(11) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `friend_requests`
--

INSERT INTO `friend_requests` (`id`, `from_user`, `to_user`, `status`) VALUES
(1, 2, 1, 'ACCEPTED'),
(2, 4, 5, 'PENDING'),
(3, 1, 4, 'PENDING'),
(4, 3, 4, 'ACCEPTED'),
(5, 2, 4, 'ACCEPTED');

-- --------------------------------------------------------

--
-- Table structure for table `hobbies`
--

CREATE TABLE `hobbies` (
  `user_id` int(11) DEFAULT NULL,
  `hobby` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `hobbies`
--

INSERT INTO `hobbies` (`user_id`, `hobby`) VALUES
(1, 'gym'),
(1, 'bike riding'),
(2, 'sleeping again'),
(2, 'eating again'),
(3, 'gym'),
(4, 'gym'),
(4, 'hobby');

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE `messages` (
  `id` int(11) NOT NULL,
  `chat_id` int(11) DEFAULT NULL,
  `sender` int(11) DEFAULT NULL,
  `receiver` int(11) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `messages`
--

INSERT INTO `messages` (`id`, `chat_id`, `sender`, `receiver`, `content`, `timestamp`) VALUES
(1, 1, 1, 2, 'hiii', 1772462935819),
(2, 1, 2, 1, 'hlo wassup', 1772463083467),
(3, 2, 2, 4, 'heyyy', 1781425046174),
(4, 2, 4, 2, 'hii wassup', 1781425059465);

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

CREATE TABLE `notifications` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `message` text DEFAULT NULL,
  `isRead` tinyint(1) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `notifications`
--

INSERT INTO `notifications` (`id`, `user_id`, `message`, `isRead`, `timestamp`) VALUES
(1, 1, 'You received a friend request from Alisha Naz', 1, 1772462767164),
(2, 2, 'ebaad accepted your friend request', 1, 1772462891754),
(3, 5, 'You received a friend request from Ebaad ali', 0, 1781424764321),
(4, 4, 'You received a friend request from ebaad ali', 1, 1781424859625),
(5, 4, 'You received a friend request from Bob', 1, 1781424884032),
(6, 4, 'You received a friend request from Alisha Naz', 1, 1781424919232),
(7, 2, 'ebaad ali accepted your friend request', 0, 1781424993697),
(8, 3, 'ebaad ali accepted your friend request', 0, 1781424996959);

-- --------------------------------------------------------

--
-- Table structure for table `profiles`
--

CREATE TABLE `profiles` (
  `user_id` int(11) NOT NULL,
  `about` text DEFAULT NULL,
  `education` varchar(100) DEFAULT NULL,
  `picture` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `profiles`
--

INSERT INTO `profiles` (`user_id`, `about`, `education`, `picture`) VALUES
(1, 'hi, I am a student at gift university ', 'computer science', 'zain.jfif'),
(2, 'I am a ebaad\'s sister', 'ics', 'kim.jfif'),
(3, 'Hi i am bob', 'computer science', 'chand.jfif'),
(4, 'hi i am a coder', 'BS CS', 'ross.jfif');

-- --------------------------------------------------------

--
-- Table structure for table `read_receipts`
--

CREATE TABLE `read_receipts` (
  `user_id` int(11) NOT NULL,
  `chat_id` int(11) NOT NULL,
  `last_read_message` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `read_receipts`
--

INSERT INTO `read_receipts` (`user_id`, `chat_id`, `last_read_message`) VALUES
(1, 1, 2),
(2, 1, 2),
(2, 2, 4),
(4, 2, 4);

-- --------------------------------------------------------

--
-- Table structure for table `search_history`
--

CREATE TABLE `search_history` (
  `user_id` int(11) DEFAULT NULL,
  `query` text DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `search_history`
--

INSERT INTO `search_history` (`user_id`, `query`, `timestamp`) VALUES
(2, 'ebaa', 1772462760661),
(4, 'ebaad ali', 1781425235713);

-- --------------------------------------------------------

--
-- Table structure for table `skills`
--

CREATE TABLE `skills` (
  `user_id` int(11) DEFAULT NULL,
  `skill` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `skills`
--

INSERT INTO `skills` (`user_id`, `skill`) VALUES
(1, 'java'),
(1, 'html'),
(1, 'jsp'),
(2, 'sleeping'),
(2, 'eating'),
(3, 'java'),
(3, 'html'),
(4, 'java'),
(4, 'frontend');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `dob` varchar(20) DEFAULT NULL,
  `intro` double DEFAULT NULL,
  `ambi` double DEFAULT NULL,
  `extro` double DEFAULT NULL,
  `quizCompleted` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `name`, `age`, `gender`, `dob`, `intro`, `ambi`, `extro`, `quizCompleted`) VALUES
(1, 'ebaad', '123abc', 'ebaad ali', 20, 'Male', '2005-08-01', 0.009427958611261696, 0.02371236087810521, 0.9668596805106332, 1),
(2, 'alisha', '123abc', 'Alisha Naz', 17, 'Female', '2006-05-24', 0, 0.028090016254206634, 0.9719099837457933, 1),
(3, 'bob', '123abc', 'Bob', 21, 'Male', '2004-04-12', 0, 0.014354066985645933, 0.9856459330143542, 1),
(4, 'ebaad ali', '123abc', 'Ebaad ali', 20, 'Male', '2005-03-01', 0.01941538668617872, 0.9344078024157728, 0.04617681089804841, 1),
(5, 'alice', '123abc', NULL, 0, NULL, NULL, 0.23076923076923078, 0.23076923076923078, 0.5384615384615384, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `chats`
--
ALTER TABLE `chats`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `friend_requests`
--
ALTER TABLE `friend_requests`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `profiles`
--
ALTER TABLE `profiles`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `read_receipts`
--
ALTER TABLE `read_receipts`
  ADD PRIMARY KEY (`user_id`,`chat_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
