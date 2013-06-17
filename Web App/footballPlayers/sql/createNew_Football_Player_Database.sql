DROP TABLE IF EXISTS `players`;
CREATE TABLE  `players` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `team` varchar(25) NOT NULL,
  `firstName` varchar(20) NOT NULL,
  `lastName` varchar(20) NOT NULL,
  `age` varchar(3) NOT NULL,
  `country_origin` varchar(40) NOT NULL,
  `position` varchar(10) NOT NULL,
  `pref_foot` varchar(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

INSERT INTO `players` VALUES  (1,'FC Barcelona','Cesc','Fabregas','24','Spain','RM','Left'),
 (2,'Arsenal','Robin','Van Persie','28','Holland','ST','Left'),
 (3,'Arsenal','Tomas','Rosicky','30','Chzech Republic','CAM','Right'),
 (4,'Chelsea','John','Terry','29','England','CB','Right'),
 (6,'Manchester United','Ryan','Giggs','35','Wales','LM','Left'),
 (7,'Macnchester City','Sergio','Khun Aguero','25','Argentina','ST','Left');