USE mpd;


DROP TABLE IF EXISTS `mpd`.`trafficinfraction`;
CREATE TABLE  `mpd`.`trafficinfraction` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'AutoIncrement',
  `Code` varchar(45) CHARACTER SET latin1 NOT NULL COMMENT 'Parking Or Traffic Code',
  `Description` text CHARACTER SET latin1 NOT NULL,
  `ShortDescription` varchar(45) CHARACTER SET latin1 NOT NULL COMMENT 'Short Description of Violation',
  `Fine` decimal(7,2) unsigned NOT NULL COMMENT 'Dollar Fine',
  `Display` tinyint(1) NOT NULL COMMENT 'Should this row be displayed',
  PRIMARY KEY (`ID`,`Code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5801 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Metropolitan Police Departmen';



DROP TABLE IF EXISTS `mpd`.`requestlog`;
CREATE TABLE  `mpd`.`requestlog` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `IpAddress` varchar(45) NOT NULL,
  `RequestURI` text NOT NULL,
  `AuthUser` varchar(45) DEFAULT 'not authenticated',
  `DateTimeOfRequest` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ParameterNames` text,
  `referer` text,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `mpd`.`mpdusers`;
CREATE TABLE  `mpd`.`mpdusers` (
  `MPDUserID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `MPDUserName` varchar(45) NOT NULL COMMENT 'email address',
  `MPDEmailAddress` varchar(250) NOT NULL,
  `LastName` varchar(70) NOT NULL,
  `FirstName` varchar(70) NOT NULL,
  `MiddleName` varchar(75) DEFAULT NULL,
  `DateAccountRequested` datetime NOT NULL,
  `MPDUserPassword` varchar(250) NOT NULL COMMENT 'password will be encrypted',
  `Verified` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `LastLogin` datetime DEFAULT NULL,
  `Locked` tinyint(3) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`MPDUserID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `mpd`.`mpdroles`;
CREATE TABLE  `mpd`.`mpdroles` (
  `RoleId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `RoleName` varchar(45) NOT NULL,
  `RoleValue` int(10) unsigned NOT NULL,
  PRIMARY KEY (`RoleId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `mpd`.`mpduserroles`;
CREATE TABLE  `mpd`.`mpduserroles` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `MPDUserID` int(10) unsigned NOT NULL,
  `MPDRoleID` int(10) unsigned NOT NULL,
  `RecordCreatedOn` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_mpduserroles_1` (`MPDUserID`),
  KEY `FK_mpduserroles_2` (`MPDRoleID`),
  CONSTRAINT `FK_mpduserroles_1` FOREIGN KEY (`MPDUserID`) REFERENCES `mpdusers` (`MPDUserID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_mpduserroles_2` FOREIGN KEY (`MPDRoleID`) REFERENCES `mpdroles` (`RoleId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;









