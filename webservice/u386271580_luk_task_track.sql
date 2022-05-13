-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: May 13, 2022 at 08:00 AM
-- Server version: 10.5.13-MariaDB-cll-lve
-- PHP Version: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `u386271580_luk_task_track`
--

-- --------------------------------------------------------

--
-- Table structure for table `assignment`
--

CREATE TABLE `assignment` (
  `id` int(10) NOT NULL,
  `task_id` int(10) DEFAULT NULL,
  `staff_id` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `assignment`
--

INSERT INTO `assignment` (`id`, `task_id`, `staff_id`) VALUES
(1, 1, 1),
(8, 1, 5),
(2, 2, 2),
(10, 3, 1),
(3, 3, 3),
(4, 4, 4),
(5, 5, 5),
(6, 6, 5),
(7, 7, 1);

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

CREATE TABLE `comment` (
  `id` int(10) NOT NULL,
  `staff_id` int(10) DEFAULT NULL,
  `date` datetime NOT NULL,
  `comment` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `comment`
--

INSERT INTO `comment` (`id`, `staff_id`, `date`, `comment`, `task_id`) VALUES
(1, 1, '2022-05-12 11:26:06', 'making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words', 1),
(2, 2, '2022-05-12 11:26:06', 'combined with a handful of model sentence structures, to generate ', 2),
(3, 3, '2022-05-12 11:26:52', 'The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from', 3),
(4, 4, '2022-05-12 11:26:52', '\"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form', 4),
(5, 5, '2022-05-12 11:27:32', 'dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum', 5),
(6, 1, '2022-05-12 11:27:32', 'dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum', 6),
(7, 4, '2022-05-12 11:28:06', 'dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum', 7),
(8, 3, '2022-05-12 11:28:06', 'Ipsum, you need to be sure there isn\'t anything embarrassing hidden in the', 8),
(9, 5, '2022-05-12 11:28:44', 'm et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a ', 9),
(10, 1, '2022-05-12 11:28:44', 'Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots ', 10);

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `id` int(10) NOT NULL,
  `first_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `last_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`id`, `first_name`, `last_name`, `email`, `password`) VALUES
(1, 'Jonathan', 'Sample', 'jsample@gmail.com', 'qwerty'),
(2, 'Lauren', 'Sample', 'lsample@gmail.com', 'abcd'),
(3, 'thomas', 'Sample', 'tsmaple@gmail.com', '12345'),
(4, 'Clara', 'Sample', 'csample@gmail.com', '54321'),
(5, 'Mauren', 'Sample', 'msample@gmail.com', 'zxcv'),
(6, 'sds', 'dsdsd', '', 'dsd'),
(7, 'ooooo', 'dsdsd', 'fdff', 'dsd'),
(8, 'jessica', 'mwase', 'jm@gmail.com', 'qwerty');

-- --------------------------------------------------------

--
-- Table structure for table `status`
--

CREATE TABLE `status` (
  `id` int(11) NOT NULL,
  `state` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `status`
--

INSERT INTO `status` (`id`, `state`) VALUES
(1, 'incomplete'),
(2, 'complete');

-- --------------------------------------------------------

--
-- Table structure for table `task`
--

CREATE TABLE `task` (
  `id` int(11) NOT NULL,
  `title` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `due_date` datetime DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `staff_id` int(10) DEFAULT NULL,
  `status_id` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `task`
--

INSERT INTO `task` (`id`, `title`, `due_date`, `description`, `staff_id`, `status_id`) VALUES
(1, 'Moving cash', '2022-05-12 11:12:12', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s', 1, 1),
(2, 'Shipping chips', '2022-05-12 11:12:12', 'when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries', 2, 2),
(3, 'Computer maintenance', '2022-05-12 11:13:28', 'the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages', 3, 1),
(4, 'Another sample title', '2022-05-12 11:13:28', 'Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old.', 4, 1),
(5, 'The third sample', '2022-05-12 11:14:48', 'professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage,', 5, 2),
(6, 'Sample Title again', '2022-05-12 11:14:48', 'This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum', 1, 1),
(7, 'Somewhere again', '2022-05-12 11:15:45', 'There are many variations of passages of Lorem Ipsum available, but the majority', 2, 2),
(8, 'Again with the words', '2022-05-12 11:15:45', 'If you are going to use a passage of Lorem Ipsum, you need to be', 1, 2),
(9, 'Second to the last', '2022-05-12 11:16:40', 'sure there isn\'t anything embarrassing hidden in the middle of text.', 3, 2),
(10, 'Last Title Sample', '2022-05-12 11:16:40', 'All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary', 4, 1),
(11, 'running home', '0000-00-00 00:00:00', 'qwerty sajkhdkasjhd', NULL, 1),
(12, 'toomo', '2022-06-13 07:06:49', 'runner', NULL, 1),
(13, 'run up hill', '2022-10-29 07:10:54', 'well here we are', 8, 1),
(14, 'go find cheese', '2022-02-13 08:02:12', 'well here you ate', 8, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `assignment`
--
ALTER TABLE `assignment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `task_id` (`task_id`,`staff_id`),
  ADD KEY `staff_id_assignment_constraint` (`staff_id`);

--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `staff_id` (`staff_id`),
  ADD KEY `task_id` (`task_id`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `task`
--
ALTER TABLE `task`
  ADD PRIMARY KEY (`id`),
  ADD KEY `staff_id` (`staff_id`),
  ADD KEY `status_id` (`status_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `assignment`
--
ALTER TABLE `assignment`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `comment`
--
ALTER TABLE `comment`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `staff`
--
ALTER TABLE `staff`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `status`
--
ALTER TABLE `status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `task`
--
ALTER TABLE `task`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `assignment`
--
ALTER TABLE `assignment`
  ADD CONSTRAINT `staff_id_assignment_constraint` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `task_id_assignment_constraint` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `staff_id_comment_constraint` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `task_id_comment_constraint` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `task`
--
ALTER TABLE `task`
  ADD CONSTRAINT `staff_id_task_constraint` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `status_id_task_constraint` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
