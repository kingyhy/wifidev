CREATE TABLE IF NOT EXISTS `stock` (
  `STOCK_ID` int(10) NOT NULL AUTO_INCREMENT,
  `STOCK_CODE` varchar(10) NOT NULL DEFAULT '',
  `STOCK_NAME` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`STOCK_ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;
