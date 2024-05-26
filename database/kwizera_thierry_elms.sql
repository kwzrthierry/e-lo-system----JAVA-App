-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 19, 2024 at 07:58 AM
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
-- Database: `kwizera_thierry_elms`
--

-- --------------------------------------------------------

--
-- Stand-in structure for view `allinformation_in_citizen`
-- (See below for the actual view)
--
CREATE TABLE `allinformation_in_citizen` (
`citizen_id` int(11)
,`first_name` varchar(50)
,`last_name` varchar(50)
,`phone_number` varchar(15)
,`email` varchar(100)
,`birth_date` date
,`national_id` varchar(20)
,`address` varchar(255)
,`marital_status` varchar(20)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `allinformation_in_login_citizen`
-- (See below for the actual view)
--
CREATE TABLE `allinformation_in_login_citizen` (
`Login_citizen_id` int(11)
,`Citizen_id` int(11)
,`Username` varchar(50)
,`Password` varchar(100)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `allinformation_in_login_data_staff`
-- (See below for the actual view)
--
CREATE TABLE `allinformation_in_login_data_staff` (
`Login_id` int(11)
,`Staff_id` int(11)
,`Username` varchar(50)
,`Password` varchar(100)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `allinformation_in_requests`
-- (See below for the actual view)
--
CREATE TABLE `allinformation_in_requests` (
`Request_id` int(11)
,`Citizen_id` int(11)
,`Staff_id` int(11)
,`Request` text
,`Date` date
,`Status` varchar(20)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `allinformation_in_staff`
-- (See below for the actual view)
--
CREATE TABLE `allinformation_in_staff` (
`Staff_id` int(11)
,`First_name` varchar(50)
,`Last_name` varchar(50)
,`Phone_number` varchar(15)
,`Email` varchar(100)
,`Position` varchar(50)
);

-- --------------------------------------------------------

--
-- Table structure for table `citizen`
--

CREATE TABLE `citizen` (
  `citizen_id` int(11) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `phone_number` varchar(15) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `national_id` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `marital_status` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `citizen`
--

INSERT INTO `citizen` (`citizen_id`, `first_name`, `last_name`, `phone_number`, `email`, `birth_date`, `national_id`, `address`, `marital_status`) VALUES
(1, 'thierry', 'kwizera', '1234567890', 'kwizzo@gmail.com', '1990-01-15', '123456789', '123 Main St', 'Single');

--
-- Triggers `citizen`
--
DELIMITER $$
CREATE TRIGGER `AfterDeleteCitizen` AFTER DELETE ON `citizen` FOR EACH ROW BEGIN
    INSERT INTO deleted_citizen (citizen_id, first_name, last_name, phone_number, email, birth_date, national_id, address, marital_status)
    VALUES (OLD.citizen_id, OLD.first_name, OLD.last_name, OLD.phone_number, OLD.email, OLD.birth_date, OLD.national_id, OLD.address, OLD.marital_status);
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `AfterDeleteCitizen_table` AFTER DELETE ON `citizen` FOR EACH ROW BEGIN
    
    DELETE FROM Requests WHERE Citizen_id = OLD.citizen_id;
    DELETE FROM Login_citizen WHERE Citizen_id = OLD.citizen_id;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `AfterUpdateCitizen` AFTER UPDATE ON `citizen` FOR EACH ROW BEGIN
    DECLARE updated_columns VARCHAR(255);
    IF OLD.first_name != NEW.first_name THEN
        SET updated_columns = CONCAT(updated_columns, 'first_name ');
    END IF;
    
    IF OLD.last_name != NEW.last_name THEN
        SET updated_columns = CONCAT(updated_columns, 'last_name ');
    END IF;
    
    IF OLD.phone_number != NEW.phone_number THEN
        SET updated_columns = CONCAT(updated_columns, 'phone_number ');
    END IF;
    
    IF OLD.email != NEW.email THEN
        SET updated_columns = CONCAT(updated_columns, 'email ');
    END IF;
    
    IF LENGTH(updated_columns) > 0 THEN
        INSERT INTO updates_on_citizen (citizen_id, updated_column, old_value, new_value, update_time)
        VALUES (
            NEW.citizen_id,
            updated_columns,
            CONCAT_WS('|', OLD.first_name, OLD.last_name, OLD.phone_number, OLD.email), 
            CONCAT_WS('|', NEW.first_name, NEW.last_name, NEW.phone_number, NEW.email), 
            NOW()
        );
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `deletedataview_citizen`
-- (See below for the actual view)
--
CREATE TABLE `deletedataview_citizen` (
`citizen_id` int(11)
,`first_name` varchar(50)
,`last_name` varchar(50)
,`phone_number` varchar(15)
,`email` varchar(100)
,`birth_date` date
,`national_id` varchar(20)
,`address` varchar(255)
,`marital_status` varchar(20)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `deletedataview_staff`
-- (See below for the actual view)
--
CREATE TABLE `deletedataview_staff` (
`Staff_id` int(11)
,`First_name` varchar(50)
,`Last_name` varchar(50)
,`Phone_number` varchar(15)
,`Email` varchar(100)
,`Position` varchar(50)
);

-- --------------------------------------------------------

--
-- Table structure for table `deleted_citizen`
--

CREATE TABLE `deleted_citizen` (
  `Deleted_citizen_id` int(11) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `phone_number` varchar(15) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `national_id` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `marital_status` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `deleted_staff`
--

CREATE TABLE `deleted_staff` (
  `Deleted_Staff_id` int(11) NOT NULL,
  `First_name` varchar(50) DEFAULT NULL,
  `Last_name` varchar(50) DEFAULT NULL,
  `Phone_number` varchar(15) DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `Position` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Stand-in structure for view `insertintocitizen`
-- (See below for the actual view)
--
CREATE TABLE `insertintocitizen` (
`citizen_id` int(11)
,`first_name` varchar(50)
,`last_name` varchar(50)
,`phone_number` varchar(15)
,`email` varchar(100)
,`birth_date` date
,`national_id` varchar(20)
,`address` varchar(255)
,`marital_status` varchar(20)
);

-- --------------------------------------------------------

--
-- Table structure for table `login_citizen`
--

CREATE TABLE `login_citizen` (
  `Login_citizen_id` int(11) NOT NULL,
  `Citizen_id` int(11) DEFAULT NULL,
  `Username` varchar(50) DEFAULT NULL,
  `Password` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `login_citizen`
--

INSERT INTO `login_citizen` (`Login_citizen_id`, `Citizen_id`, `Username`, `Password`) VALUES
(1, 1, 'kwzrthieery', 'mucyo123456');

-- --------------------------------------------------------

--
-- Table structure for table `login_data_staff`
--

CREATE TABLE `login_data_staff` (
  `Login_id` int(11) NOT NULL,
  `Staff_id` int(11) DEFAULT NULL,
  `Username` varchar(50) DEFAULT NULL,
  `Password` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `login_data_staff`
--

INSERT INTO `login_data_staff` (`Login_id`, `Staff_id`, `Username`, `Password`) VALUES
(1, 101, 'bccm101', 'bbdcdd2e6ece');

-- --------------------------------------------------------

--
-- Table structure for table `reports`
--

CREATE TABLE `reports` (
  `Report_id` int(11) NOT NULL,
  `Staff_id` int(11) DEFAULT NULL,
  `Citizen_id` int(11) DEFAULT NULL,
  `Request_id` int(11) DEFAULT NULL,
  `Date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reports`
--

INSERT INTO `reports` (`Report_id`, `Staff_id`, `Citizen_id`, `Request_id`, `Date`) VALUES
(1, 101, 1, 301, '2023-09-18'),
(2, NULL, 1, 302, '2024-02-18');

-- --------------------------------------------------------

--
-- Table structure for table `requests`
--

CREATE TABLE `requests` (
  `Request_id` int(11) NOT NULL,
  `Citizen_id` int(11) DEFAULT NULL,
  `Staff_id` int(11) DEFAULT NULL,
  `Request` text DEFAULT NULL,
  `Date` date DEFAULT NULL,
  `Status` varchar(20) DEFAULT NULL,
  `responses` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `requests`
--

INSERT INTO `requests` (`Request_id`, `Citizen_id`, `Staff_id`, `Request`, `Date`, `Status`, `responses`) VALUES
(301, 1, 101, 'asking for single certificate', '2023-08-15', 'processing', NULL),
(302, 1, NULL, 'change name', '2024-02-18', 'processing', NULL);

--
-- Triggers `requests`
--
DELIMITER $$
CREATE TRIGGER `AfterInsertRequestsToReports` AFTER INSERT ON `requests` FOR EACH ROW BEGIN
    
    INSERT INTO Reports (Staff_id, Citizen_id, Request_id, Date)
    VALUES (NEW.Staff_id, NEW.Citizen_id, NEW.Request_id, NOW());
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `AfterUpdateRequest` AFTER UPDATE ON `requests` FOR EACH ROW BEGIN
    
    IF NEW.status = 'Completed' THEN
        
        UPDATE Report
        SET Date = NOW()
        WHERE Request_id = NEW.Request_id;
    END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `staff_assigned` BEFORE INSERT ON `requests` FOR EACH ROW BEGIN
    IF NEW.Staff_id IS NULL OR NOT EXISTS (SELECT 1 FROM staff WHERE Staff_id = NEW.Staff_id) THEN
        SET NEW.Staff_id = (SELECT Staff_id FROM staff WHERE Staff_id NOT IN (SELECT Staff_id FROM requests) LIMIT 1);
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `Staff_id` int(11) NOT NULL,
  `First_name` varchar(50) DEFAULT NULL,
  `Last_name` varchar(50) DEFAULT NULL,
  `Phone_number` varchar(15) DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `Position` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`Staff_id`, `First_name`, `Last_name`, `Phone_number`, `Email`, `Position`) VALUES
(101, 'cyiza', 'marcel', '9876543210', 'cyizzo@gmail.com', 'Officer');

--
-- Triggers `staff`
--
DELIMITER $$
CREATE TRIGGER `AfterDeleteStaff` AFTER DELETE ON `staff` FOR EACH ROW BEGIN
    INSERT INTO deleted_staff (Staff_id, First_name, Last_name, Phone_number, Email, Position)
    VALUES (OLD.Staff_id, OLD.First_name, OLD.Last_name, OLD.Phone_number, OLD.Email, OLD.Position);
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `AfterInsertStaff` AFTER INSERT ON `staff` FOR EACH ROW BEGIN
    
    SET @password = SUBSTRING(MD5(RAND()), 1, 12);
    
    SET @consonants = 'bcdfghjklmnpqrstvwxyz';
    SET @username = 
        CONCAT(
            SUBSTRING(@consonants, 1, 1),
            SUBSTRING(@consonants, 2, 1),
            SUBSTRING(NEW.First_name, 1, 1),
            SUBSTRING(NEW.Last_name, 1, 1),
            NEW.Staff_id
        );
    
    INSERT INTO Login_data_staff (Staff_id, Username, Password)
    VALUES (NEW.Staff_id, @username, @password);
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `subqueryview`
-- (See below for the actual view)
--
CREATE TABLE `subqueryview` (
`first_name` varchar(50)
,`last_name` varchar(50)
,`Request` text
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `updateinfoview_in_citizen`
-- (See below for the actual view)
--
CREATE TABLE `updateinfoview_in_citizen` (
`citizen_id` int(11)
,`first_name` varchar(50)
,`last_name` varchar(50)
,`phone_number` varchar(15)
,`email` varchar(100)
,`birth_date` date
,`national_id` varchar(20)
,`address` varchar(255)
,`marital_status` varchar(20)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `updateinfoview_in_staff`
-- (See below for the actual view)
--
CREATE TABLE `updateinfoview_in_staff` (
`Staff_id` int(11)
,`First_name` varchar(50)
,`Last_name` varchar(50)
,`Phone_number` varchar(15)
,`Email` varchar(100)
,`Position` varchar(50)
);

-- --------------------------------------------------------

--
-- Table structure for table `updates_on_citizen`
--

CREATE TABLE `updates_on_citizen` (
  `update_id` int(11) NOT NULL,
  `citizen_id` int(11) NOT NULL,
  `updated_column` varchar(50) NOT NULL,
  `old_value` text DEFAULT NULL,
  `new_value` text DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure for view `allinformation_in_citizen`
--
DROP TABLE IF EXISTS `allinformation_in_citizen`;

CREATE ALGORITHM=UNDEFINED DEFINER=`kwizera_thierry`@`localhost` SQL SECURITY DEFINER VIEW `allinformation_in_citizen`  AS SELECT `citizen`.`citizen_id` AS `citizen_id`, `citizen`.`first_name` AS `first_name`, `citizen`.`last_name` AS `last_name`, `citizen`.`phone_number` AS `phone_number`, `citizen`.`email` AS `email`, `citizen`.`birth_date` AS `birth_date`, `citizen`.`national_id` AS `national_id`, `citizen`.`address` AS `address`, `citizen`.`marital_status` AS `marital_status` FROM `citizen` ;

-- --------------------------------------------------------

--
-- Structure for view `allinformation_in_login_citizen`
--
DROP TABLE IF EXISTS `allinformation_in_login_citizen`;

CREATE ALGORITHM=UNDEFINED DEFINER=`kwizera_thierry`@`localhost` SQL SECURITY DEFINER VIEW `allinformation_in_login_citizen`  AS SELECT `login_citizen`.`Login_citizen_id` AS `Login_citizen_id`, `login_citizen`.`Citizen_id` AS `Citizen_id`, `login_citizen`.`Username` AS `Username`, `login_citizen`.`Password` AS `Password` FROM `login_citizen` ;

-- --------------------------------------------------------

--
-- Structure for view `allinformation_in_login_data_staff`
--
DROP TABLE IF EXISTS `allinformation_in_login_data_staff`;

CREATE ALGORITHM=UNDEFINED DEFINER=`kwizera_thierry`@`localhost` SQL SECURITY DEFINER VIEW `allinformation_in_login_data_staff`  AS SELECT `login_data_staff`.`Login_id` AS `Login_id`, `login_data_staff`.`Staff_id` AS `Staff_id`, `login_data_staff`.`Username` AS `Username`, `login_data_staff`.`Password` AS `Password` FROM `login_data_staff` ;

-- --------------------------------------------------------

--
-- Structure for view `allinformation_in_requests`
--
DROP TABLE IF EXISTS `allinformation_in_requests`;

CREATE ALGORITHM=UNDEFINED DEFINER=`kwizera_thierry`@`localhost` SQL SECURITY DEFINER VIEW `allinformation_in_requests`  AS SELECT `requests`.`Request_id` AS `Request_id`, `requests`.`Citizen_id` AS `Citizen_id`, `requests`.`Staff_id` AS `Staff_id`, `requests`.`Request` AS `Request`, `requests`.`Date` AS `Date`, `requests`.`Status` AS `Status` FROM `requests` ;

-- --------------------------------------------------------

--
-- Structure for view `allinformation_in_staff`
--
DROP TABLE IF EXISTS `allinformation_in_staff`;

CREATE ALGORITHM=UNDEFINED DEFINER=`kwizera_thierry`@`localhost` SQL SECURITY DEFINER VIEW `allinformation_in_staff`  AS SELECT `staff`.`Staff_id` AS `Staff_id`, `staff`.`First_name` AS `First_name`, `staff`.`Last_name` AS `Last_name`, `staff`.`Phone_number` AS `Phone_number`, `staff`.`Email` AS `Email`, `staff`.`Position` AS `Position` FROM `staff` ;

-- --------------------------------------------------------

--
-- Structure for view `deletedataview_citizen`
--
DROP TABLE IF EXISTS `deletedataview_citizen`;

CREATE ALGORITHM=UNDEFINED DEFINER=`kwizera_thierry`@`localhost` SQL SECURITY DEFINER VIEW `deletedataview_citizen`  AS SELECT `citizen`.`citizen_id` AS `citizen_id`, `citizen`.`first_name` AS `first_name`, `citizen`.`last_name` AS `last_name`, `citizen`.`phone_number` AS `phone_number`, `citizen`.`email` AS `email`, `citizen`.`birth_date` AS `birth_date`, `citizen`.`national_id` AS `national_id`, `citizen`.`address` AS `address`, `citizen`.`marital_status` AS `marital_status` FROM `citizen` WHERE `citizen`.`marital_status` = 'Single' ;

-- --------------------------------------------------------

--
-- Structure for view `deletedataview_staff`
--
DROP TABLE IF EXISTS `deletedataview_staff`;

CREATE ALGORITHM=UNDEFINED DEFINER=`kwizera_thierry`@`localhost` SQL SECURITY DEFINER VIEW `deletedataview_staff`  AS SELECT `staff`.`Staff_id` AS `Staff_id`, `staff`.`First_name` AS `First_name`, `staff`.`Last_name` AS `Last_name`, `staff`.`Phone_number` AS `Phone_number`, `staff`.`Email` AS `Email`, `staff`.`Position` AS `Position` FROM `staff` WHERE `staff`.`Position` = 'Intern' ;

-- --------------------------------------------------------

--
-- Structure for view `insertintocitizen`
--
DROP TABLE IF EXISTS `insertintocitizen`;

CREATE ALGORITHM=UNDEFINED DEFINER=`kwizera_thierry`@`localhost` SQL SECURITY DEFINER VIEW `insertintocitizen`  AS SELECT `citizen`.`citizen_id` AS `citizen_id`, `citizen`.`first_name` AS `first_name`, `citizen`.`last_name` AS `last_name`, `citizen`.`phone_number` AS `phone_number`, `citizen`.`email` AS `email`, `citizen`.`birth_date` AS `birth_date`, `citizen`.`national_id` AS `national_id`, `citizen`.`address` AS `address`, `citizen`.`marital_status` AS `marital_status` FROM `citizen` ;

-- --------------------------------------------------------

--
-- Structure for view `subqueryview`
--
DROP TABLE IF EXISTS `subqueryview`;

CREATE ALGORITHM=UNDEFINED DEFINER=`kwizera_thierry`@`localhost` SQL SECURITY DEFINER VIEW `subqueryview`  AS SELECT `c`.`first_name` AS `first_name`, `c`.`last_name` AS `last_name`, `r`.`Request` AS `Request` FROM (`citizen` `c` join `requests` `r` on(`c`.`citizen_id` = `r`.`Citizen_id`)) WHERE `r`.`Status` = 'processing' ;

-- --------------------------------------------------------

--
-- Structure for view `updateinfoview_in_citizen`
--
DROP TABLE IF EXISTS `updateinfoview_in_citizen`;

CREATE ALGORITHM=UNDEFINED DEFINER=`kwizera_thierry`@`localhost` SQL SECURITY DEFINER VIEW `updateinfoview_in_citizen`  AS SELECT `citizen`.`citizen_id` AS `citizen_id`, `citizen`.`first_name` AS `first_name`, `citizen`.`last_name` AS `last_name`, `citizen`.`phone_number` AS `phone_number`, `citizen`.`email` AS `email`, `citizen`.`birth_date` AS `birth_date`, `citizen`.`national_id` AS `national_id`, `citizen`.`address` AS `address`, `citizen`.`marital_status` AS `marital_status` FROM `citizen` ;

-- --------------------------------------------------------

--
-- Structure for view `updateinfoview_in_staff`
--
DROP TABLE IF EXISTS `updateinfoview_in_staff`;

CREATE ALGORITHM=UNDEFINED DEFINER=`kwizera_thierry`@`localhost` SQL SECURITY DEFINER VIEW `updateinfoview_in_staff`  AS SELECT `staff`.`Staff_id` AS `Staff_id`, `staff`.`First_name` AS `First_name`, `staff`.`Last_name` AS `Last_name`, `staff`.`Phone_number` AS `Phone_number`, `staff`.`Email` AS `Email`, `staff`.`Position` AS `Position` FROM `staff` ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `citizen`
--
ALTER TABLE `citizen`
  ADD PRIMARY KEY (`citizen_id`);

--
-- Indexes for table `deleted_citizen`
--
ALTER TABLE `deleted_citizen`
  ADD PRIMARY KEY (`Deleted_citizen_id`);

--
-- Indexes for table `deleted_staff`
--
ALTER TABLE `deleted_staff`
  ADD PRIMARY KEY (`Deleted_Staff_id`);

--
-- Indexes for table `login_citizen`
--
ALTER TABLE `login_citizen`
  ADD PRIMARY KEY (`Login_citizen_id`),
  ADD KEY `Citizen_id` (`Citizen_id`);

--
-- Indexes for table `login_data_staff`
--
ALTER TABLE `login_data_staff`
  ADD PRIMARY KEY (`Login_id`),
  ADD KEY `Staff_id` (`Staff_id`);

--
-- Indexes for table `reports`
--
ALTER TABLE `reports`
  ADD PRIMARY KEY (`Report_id`),
  ADD KEY `Staff_id` (`Staff_id`),
  ADD KEY `Citizen_id` (`Citizen_id`);

--
-- Indexes for table `requests`
--
ALTER TABLE `requests`
  ADD PRIMARY KEY (`Request_id`),
  ADD KEY `Citizen_id` (`Citizen_id`),
  ADD KEY `Staff_id` (`Staff_id`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`Staff_id`);

--
-- Indexes for table `updates_on_citizen`
--
ALTER TABLE `updates_on_citizen`
  ADD PRIMARY KEY (`update_id`),
  ADD KEY `citizen_id` (`citizen_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `citizen`
--
ALTER TABLE `citizen`
  MODIFY `citizen_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `deleted_citizen`
--
ALTER TABLE `deleted_citizen`
  MODIFY `Deleted_citizen_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `deleted_staff`
--
ALTER TABLE `deleted_staff`
  MODIFY `Deleted_Staff_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `login_citizen`
--
ALTER TABLE `login_citizen`
  MODIFY `Login_citizen_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `login_data_staff`
--
ALTER TABLE `login_data_staff`
  MODIFY `Login_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `reports`
--
ALTER TABLE `reports`
  MODIFY `Report_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `requests`
--
ALTER TABLE `requests`
  MODIFY `Request_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=303;

--
-- AUTO_INCREMENT for table `staff`
--
ALTER TABLE `staff`
  MODIFY `Staff_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=102;

--
-- AUTO_INCREMENT for table `updates_on_citizen`
--
ALTER TABLE `updates_on_citizen`
  MODIFY `update_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `login_citizen`
--
ALTER TABLE `login_citizen`
  ADD CONSTRAINT `login_citizen_ibfk_1` FOREIGN KEY (`Citizen_id`) REFERENCES `citizen` (`citizen_id`);

--
-- Constraints for table `login_data_staff`
--
ALTER TABLE `login_data_staff`
  ADD CONSTRAINT `login_data_staff_ibfk_1` FOREIGN KEY (`Staff_id`) REFERENCES `staff` (`Staff_id`);

--
-- Constraints for table `reports`
--
ALTER TABLE `reports`
  ADD CONSTRAINT `reports_ibfk_1` FOREIGN KEY (`Staff_id`) REFERENCES `staff` (`Staff_id`),
  ADD CONSTRAINT `reports_ibfk_2` FOREIGN KEY (`Citizen_id`) REFERENCES `citizen` (`citizen_id`);

--
-- Constraints for table `requests`
--
ALTER TABLE `requests`
  ADD CONSTRAINT `requests_ibfk_1` FOREIGN KEY (`Citizen_id`) REFERENCES `citizen` (`citizen_id`),
  ADD CONSTRAINT `requests_ibfk_2` FOREIGN KEY (`Staff_id`) REFERENCES `staff` (`Staff_id`);

--
-- Constraints for table `updates_on_citizen`
--
ALTER TABLE `updates_on_citizen`
  ADD CONSTRAINT `updates_on_citizen_ibfk_1` FOREIGN KEY (`citizen_id`) REFERENCES `citizen` (`citizen_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
